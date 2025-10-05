package Jaleh;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import Jaleh.recipe.BaseTest;

public class popupMessage extends BaseTest {



public void capturescreenshot() throws Exception{
	String folderPath = System.getProperty("user.dir") + "/screenshots"; 
Files.createDirectories(Paths.get(folderPath));

// Generate timestamp
String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//String screenshotPath = folderPath + "/" + status.toUpperCase() + "_" + testName + "_" + timestamp + ".png";
String screenshotPath= "screnshot"+timestamp + ".png";

// Take screenshot
File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
Files.copy(screenshot.toPath(), Paths.get(screenshotPath));
/*
 * extentTest.Pass("Screenshot of failure:",
 * MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
 */
}
}
