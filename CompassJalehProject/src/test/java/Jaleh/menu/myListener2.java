package Jaleh.menu;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class myListener2 implements ITestListener {

    public static ExtentReports reports;
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
    private static String reportPath;
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");

    @Override
    public void onStart(ITestContext context) {
        String reportDir = System.getProperty("user.dir") + "/report";
        new File(reportDir).mkdirs();

        reportPath = reportDir + "/ExtentReport_" + sdf.format(new Date()) + ".html";

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
        test.get().info("Test started: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test.get().pass("Test case passed: " + result.getMethod().getMethodName());

        // Capture popup message if available
        try {
            String popupMessage = BaseTest2.capturePopupMessageText();
            if (popupMessage != null && !popupMessage.isEmpty()) {
                test.get().info("Popup Message: " + popupMessage);
            } else {
                test.get().info("No popup message found on success.");
            }
        } catch (Exception e) {
            test.get().warning("Failed to capture popup message on success: " + e.getMessage());
        }
    }

    @Override
    public void onTestFailure(ITestResult result) {
        test.get().fail("Test case failed: " + result.getMethod().getMethodName());

        // Log exception / stacktrace
        if (result.getThrowable() != null) {
            test.get().fail(result.getThrowable());
        }

        // Capture popup message if available
        try {
            String popupMessage = BaseTest2.capturePopupMessageText();
            if (popupMessage != null && !popupMessage.isEmpty()) {
                test.get().info("Popup Message: " + popupMessage);
            } else {
                test.get().info("No popup message found on failure.");
            }
        } catch (Exception e) {
            test.get().warning("Failed to capture popup message on failure: " + e.getMessage());
        }

        // Capture screenshot and attach
        try {
            if (BaseTest2.driver != null) {
                String screenshotPath = takeScreenshot(result.getMethod().getMethodName());
                test.get().addScreenCaptureFromPath(screenshotPath, "Failure Screenshot");
            }
        } catch (Exception e) {
            test.get().warning("Failed to capture screenshot: " + e.getMessage());
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        test.get().skip("Test case skipped: " + result.getMethod().getMethodName());

        // Capture popup message if available
        try {
            String popupMessage = BaseTest2.capturePopupMessageText();
            if (popupMessage != null && !popupMessage.isEmpty()) {
                test.get().info("Popup Message: " + popupMessage);
            } else {
                test.get().info("No popup message found on skip.");
            }
        } catch (Exception e) {
            test.get().warning("Failed to capture popup message on skip: " + e.getMessage());
        }
    }

    @Override
    public void onFinish(ITestContext context) {
        reports.flush();
        System.out.println("Extent report generated at: " + reportPath);
    }

    // Utility method to take screenshot and save with timestamp
    private String takeScreenshot(String methodName) throws IOException {
        String screenshotsDir = System.getProperty("user.dir") + "/screenshots/";
        File dir = new File(screenshotsDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String timestamp = sdf.format(new Date());
        String screenshotPath = screenshotsDir + methodName + "_" + timestamp + ".png";

        TakesScreenshot ts = (TakesScreenshot) BaseTest2.driver;
        File src = ts.getScreenshotAs(OutputType.FILE);
        Files.copy(src.toPath(), Paths.get(screenshotPath));

        return screenshotPath;
    }
}
