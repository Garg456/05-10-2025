package compass.commonclass;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class myListener implements ITestListener {

    private static ExtentReports reports;
    private static ExtentTest test;
    private static String reportPath;
    private static final String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

    @Override
    public void onStart(ITestContext context) {
        // Create report directory
        String reportDir = System.getProperty("user.dir") + "/report";
        new File(reportDir).mkdirs();

        reportPath = reportDir + "/ExtentReport_" + timestamp + ".html";
        ExtentSparkReporter reporter = new ExtentSparkReporter(reportPath);

        reports = new ExtentReports();
        reports.attachReporter(reporter);
        reports.setSystemInfo("Tester", "Jaleh");

        System.out.println("Extent report path: " + reportPath);
    }

    @Override
    public void onTestStart(ITestResult result) {
        test = reports.createTest(result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test.pass("✅ Test case passed: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        test.fail("❌ Test case failed: " + result.getMethod().getMethodName());
        test.fail(result.getThrowable());

        Object testClass = result.getInstance();
        WebDriver driver = null;

        // Try to extract WebDriver from BaseTest or any class that holds it
        try {
            driver = (WebDriver) testClass.getClass().getField("driver").get(testClass);
        } catch (Exception e) {
            test.warning("Could not access WebDriver from test class.");
        }

        if (driver == null) {
            test.warning("WebDriver is null. Screenshot cannot be captured.");
            return;
        }

        try {
            String screenshotsDir = System.getProperty("user.dir") + "/screenshots";
            Files.createDirectories(Paths.get(screenshotsDir));

            String testTimestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String screenshotPath = screenshotsDir + "/FAIL_" + result.getName() + "_" + testTimestamp + ".png";

            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Files.copy(screenshot.toPath(), Paths.get(screenshotPath));

            test.fail("Screenshot of failure:",
                MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());

            System.out.println("Screenshot saved to: " + screenshotPath);
        } catch (IOException e) {
            test.fail("Error capturing screenshot: " + e.getMessage());
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        test.skip("⚠️ Test case skipped: " + result.getMethod().getMethodName());
    }

    @Override
    public void onFinish(ITestContext context) {
        reports.flush();
        System.out.println("✅ Extent report generated at: " + reportPath);
    }
}
