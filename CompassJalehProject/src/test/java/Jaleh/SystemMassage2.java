package Jaleh;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;

public class SystemMassage2 {

    public static WebDriver driver;

    public static void main(String[] args) throws Exception {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        String username = "admin-path22@compass-external.com.au";
        String password = "Pathinfotech@01";
        String URL = "http://" + username + ":" + password + "@" + "cgrrweb01t:8086/Menu/PublishedMenuList";

        driver.get(URL);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@type='button']"))).click();
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//i[@class='nav-icon fas fa-gear']"))).click(); // Configuration
    wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//i[@class='nav-icon fas fa-comment']"))).click(); // System Message
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//button[@class='button add'])[1]"))).click(); // Add message

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//textarea[@name='MessageText']")))
                .sendKeys("Compass Application");
            
         try {   List<WebElement> elements = driver.findElements(By.id("StartDateTime"));
            System.out.println("Matching inputs: " + elements.size());
            for (WebElement el : elements) {
                System.out.println("Displayed: " + el.isDisplayed() + " | Enabled: " + el.isEnabled());
            }
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript(
                "let input = document.querySelector('#StartDateTime');" +
                "input.value = '2025-09-15';" +
                "input.dispatchEvent(new Event('input', { bubbles: true }));" +
                "input.dispatchEvent(new Event('change', { bubbles: true }));" +
                "if (typeof DisableDate === 'function') { DisableDate(input, 'Add'); }"
            );
         }
         catch(Throwable e) {System.out.println(e.getMessage());}


            
            
        
           
}}
