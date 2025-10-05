package AutomatedScriptOfJaleh01AsPOM;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class myListener3 implements ITestListener {

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
        ExtentTest extentTest = test.get();
        WebDriver driver = BaseTest2.driver;

        extentTest.pass("✅ Test case passed: " + result.getMethod().getMethodName());

        if (driver == null) {
            extentTest.warning("⚠️ WebDriver was null. Screenshot could not be taken on pass.");
            return;
        }

        try {
            // Wait for full page load and stable UI before screenshot
            waitForPageLoad(driver);
            waitForBodyVisible(driver);
            Thread.sleep(500); // small delay to stabilize UI

            String folderPath = System.getProperty("user.dir") + "/screenshots";
            Files.createDirectories(Paths.get(folderPath));

            String testTimestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String screenshotPath = folderPath + "/PASS_" + result.getName() + "_" + testTimestamp + ".png";

            // Take screenshot file
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Files.copy(screenshot.toPath(), Paths.get(screenshotPath));

            // Also take Base64 screenshot for embedding
            String base64Screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);

            extentTest.pass("✅ Screenshot after test pass:",
                    MediaEntityBuilder.createScreenCaptureFromBase64String(base64Screenshot).build());

            System.out.println("📸 Screenshot for PASS saved: " + screenshotPath);
        } catch (Exception e) {
            extentTest.warning("⚠️ Failed to capture screenshot on pass: " + e.getMessage());
        }
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ExtentTest extentTest = test.get();

        if (extentTest == null) {
            System.err.println("Warning: ExtentTest instance is null in onTestFailure for test: " + result.getMethod().getMethodName());
            extentTest = reports.createTest(result.getMethod().getMethodName());
            test.set(extentTest);
        }

        WebDriver driver = BaseTest2.driver;

        extentTest.fail("❌ Test case failed: " + result.getMethod().getMethodName());
        extentTest.fail(result.getThrowable());

        if (driver == null) {
            extentTest.warning("⚠️ WebDriver was null. Screenshot could not be taken on failure.");
            return;
        }

        try {
            // Wait for full page load and stable UI before screenshot
            waitForPageLoad(driver);
            waitForBodyVisible(driver);
            Thread.sleep(500); // small delay to stabilize UI

            String folderPath = System.getProperty("user.dir") + "/screenshots";
            Files.createDirectories(Paths.get(folderPath));

            String testTimestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String screenshotPath = folderPath + "/FAIL_" + result.getName() + "_" + testTimestamp + ".png";

            // Take screenshot file
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Files.copy(screenshot.toPath(), Paths.get(screenshotPath));

            // Also take Base64 screenshot for embedding
            String base64Screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);

            extentTest.fail("📸 Screenshot of failure:",
                    MediaEntityBuilder.createScreenCaptureFromBase64String(base64Screenshot).build());

            System.out.println("❌ Screenshot for FAIL saved: " + screenshotPath);
        } catch (Exception e) {
            extentTest.fail("⚠️ Error capturing screenshot: " + e.getMessage());
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        test.get().skip("⚠️ Test case skipped: " + result.getMethod().getMethodName());
    }

    @Override
    public void onFinish(ITestContext context) {
        reports.flush();
        System.out.println("📄 Extent report generated at: " + reportPath);
    }

    public static ExtentTest getTest() {
        return test.get();
    }

    // Helper method to wait for page load complete
    private void waitForPageLoad(WebDriver driver) {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            for (int i = 0; i < 10; i++) { // try max 10 times with 500ms wait (total 5s)
                String readyState = js.executeScript("return document.readyState").toString();
                if ("complete".equals(readyState)) {
                    break;
                }
                Thread.sleep(500);
            }
        } catch (InterruptedException e) {
            // ignore
        }
    }

    // Helper method to wait until <body> is visible
    private void waitForBodyVisible(WebDriver driver) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("body")));
        } catch (Exception e) {
            // ignore if fails
        }
    }
}
