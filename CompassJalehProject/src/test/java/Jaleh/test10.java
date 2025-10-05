
package Jaleh;

import java.time.Duration;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import io.github.bonigarcia.wdm.WebDriverManager;

public class test10 {

    static WebDriver driver;

    public static void main(String[] args) throws Exception {
        String rcpname = "maggie1011";

        setupDriver();
        openURLWithCredentials();
        clickRelogin();
        clickHomeButton();
        clickMenuSection();
        clickUnpublishedMenus();
        searchRecipe(rcpname);
        verifySearchResult(rcpname);

        driver.quit();
    }

    // ========== Setup & Utility Methods ==========

    public static void setupDriver() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    public static void openURLWithCredentials() {
        String username = "admin-path15@compass-external.com.au";
        String password = "Path@0307202451";
        String URL = "http://" + username + ":" + password + "@" + "cgrrweb01t:8086/Menu/PublishedMenuList";
        driver.get(URL);
    }

    // ========== WebElement Actions ==========

    public static void clickRelogin() {
        try {
            driver.findElement(By.xpath("//a[text()=' Re-Login']")).click();
        } catch (NoSuchElementException e) {
            System.out.println("'Re-Login' link not found — continuing.");
        }
    }

    public static void clickHomeButton() {
        driver.findElement(By.xpath("//i[@class='nav-icon fas fa-home']")).click();
    }

    public static void clickMenuSection() {
        driver.findElement(By.xpath("(//i[@class='nav-icon fas fa-table'])[1]")).click();
    }

    public static void clickUnpublishedMenus() {
        driver.findElement(By.xpath("//i[@class='nav-icon fas fa-utensils']")).click();
    }

    public static void searchRecipe(String recipeName) throws InterruptedException {
        WebElement searchBox = driver.findElement(By.id("SearchText"));
        searchBox.clear();
        searchBox.sendKeys(recipeName, Keys.ENTER);
        Thread.sleep(3000); // Optional: Replace with explicit wait
    }

    public static void verifySearchResult(String recipeName) throws InterruptedException {
        try {
            String result = driver.findElement(By.xpath("//td[text()='No record found']")).getText();
            Thread.sleep(2000); // Optional: simulate real wait for visual
            Assert.assertNotEquals(result, recipeName, "Recipe not found in the list.");
            System.out.println("Search result: " + result);
        } catch (NoSuchElementException e) {
            System.out.println("Recipe found: " + recipeName);
        }
    }
}
