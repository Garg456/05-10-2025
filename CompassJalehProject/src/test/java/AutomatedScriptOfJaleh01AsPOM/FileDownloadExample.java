package AutomatedScriptOfJaleh01AsPOM;

import java.io.File;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v134.page.Page;

import io.github.bonigarcia.wdm.WebDriverManager;

public class FileDownloadExample {
    public static void main(String[] args) {
        WebDriverManager.chromedriver().setup();

        // ✅ Recommended: No spaces in folder path
        String downloadFilepath = "E:\\AutomationTestingData\\Jaleh\\SeleniumDownloads";

        // ✅ Ensure folder exists
        File downloadDir = new File(downloadFilepath);
        if (!downloadDir.exists()) {
            downloadDir.mkdirs();
        }

        // ✅ Clean existing files before test run
        for (File file : downloadDir.listFiles()) {
            if (file.isFile()) {
                file.delete();
            }
        }

        // ✅ Chrome Preferences
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("download.default_directory", downloadFilepath);
        prefs.put("download.prompt_for_download", false);
        prefs.put("download.directory_upgrade", true);
        prefs.put("safebrowsing.enabled", true); // Required for downloads

        // ✅ ChromeOptions
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", prefs);
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-popup-blocking");

        // ✅ Start ChromeDriver with options
        ChromeDriver driver = new ChromeDriver(options);

        // ✅ Setup DevTools for download behavior
        DevTools devTools = driver.getDevTools();
        devTools.createSession();

        devTools.send(Page.setDownloadBehavior(
            Page.SetDownloadBehaviorBehavior.ALLOW,
            Optional.of(downloadFilepath)
        ));

        System.out.println("Download path set to: " + downloadFilepath);

        // ✅ URL with credentials
        String username = "admin-path15@compass-external.com.au";
        String password = "Path@03072024512";
        String URL = "http://" + username + ":" + password + "@cgrrweb01t:8086/Menu/PublishedMenuList";

        // ✅ Open application
        driver.get(URL);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        System.out.println("Browser opened.");

        // ✅ UI Navigation and download
        driver.findElement(By.xpath("//a[text()=' Re-Login']")).click();
        driver.findElement(By.xpath("//i[@class='nav-icon fas fa-home']")).click();
        driver.findElement(By.xpath("(//i[@class='nav-icon fas fa-bowl-rice'])[1]")).click();
        driver.findElement(By.xpath("(//i[@class='nav-icon fas fa-bowl-rice'])[2]")).click();
        driver.findElement(By.xpath("//button[text()='Download All Recipe']")).click();
        driver.findElement(By.xpath("(//button[@type='submit'])[2]")).click();

        System.out.println("Download triggered. Check your folder.");
        
        // Optional: driver.quit(); // Close browser if needed
    }
}
