package Jaleh.recipe;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class myListener2 implements ITestListener {

    public static ExtentReports reports;
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
    private static String reportPath;
    String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

    @Override
    public void onStart(ITestContext context) {
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
        ExtentTest extentTest = reports.createTest(result.getMethod().getMethodName());
        test.set(extentTest);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test.get().pass("Test case passed: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ExtentTest extentTest = test.get();

        if (extentTest == null) {
            System.err.println("Warning: ExtentTest instance is null in onTestFailure for test: " + result.getMethod().getMethodName());
            // Optionally create a new ExtentTest here to avoid NPE:
            extentTest = reports.createTest(result.getMethod().getMethodName());
            test.set(extentTest);
        }

        WebDriver driver = BaseTest2.driver;

        extentTest.fail("Test case failed: " + result.getMethod().getMethodName());
        extentTest.fail(result.getThrowable());

        if (driver == null) {
            extentTest.warning("WebDriver was null. Screenshot could not be taken.");
            return;
        }

        try {
            String folderPath = System.getProperty("user.dir") + "/screenshots";
            Files.createDirectories(Paths.get(folderPath));

            String testTimestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String screenshotPath = folderPath + "/FAIL_" + result.getName() + "_" + testTimestamp + ".png";

            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Files.copy(screenshot.toPath(), Paths.get(screenshotPath));

            extentTest.fail("Screenshot of failure:",
                MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());

            System.out.println("Screenshot saved to: " + screenshotPath);
        } catch (IOException e) {
            extentTest.fail("Error capturing screenshot: " + e.getMessage());
        }
    }


    @Override
    public void onTestSkipped(ITestResult result) {
        test.get().skip("Test case skipped: " + result.getMethod().getMethodName());
    }

    @Override
    public void onFinish(ITestContext context) {
        reports.flush();
        System.out.println("Extent report generated at: " + reportPath);
    }
    
}
