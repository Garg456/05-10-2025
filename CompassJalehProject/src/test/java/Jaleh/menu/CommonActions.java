package Jaleh.menu;

import java.time.Duration;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;

public class CommonActions {
    public WebDriver driver;

    public void setupDriver() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    public void loginToURL() {
        String username = "admin-path15@compass-external.com.au";
        String password = "Path@0307202451";
        String URL = "http://" + username + ":" + password + "@" + "cgrrweb01t:8086/Menu/PublishedMenuList";
        driver.get(URL);
    }

    public void clickRelogin() {
        try {
            driver.findElement(By.xpath("//a[text()=' Re-Login']")).click();
        } catch (Exception e) {
            System.out.println("Re-login not present. Skipping...");
        }
    }

    public void goToHome() {
        driver.findElement(By.xpath("//i[@class='nav-icon fas fa-home']")).click();
    }

    public void goToMenuSection() {
        driver.findElement(By.xpath("(//i[@class='nav-icon fas fa-table'])[1]")).click();
    }

    public void goToUnpublishedMenus() {
        driver.findElement(By.xpath("//i[@class='nav-icon fas fa-utensils']")).click();
    }
    
    public void searchMenuByName(String menuName) throws InterruptedException {
        WebElement searchBox = driver.findElement(By.id("SearchText"));
        searchBox.clear();
        searchBox.sendKeys(menuName, Keys.ENTER);
        wait(3000);
    }

    public void clickMenuByName(String menuName) {
        driver.findElement(By.xpath("//a[contains(text(), '" + menuName + "')]")).click();
    }

    public void wait(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
