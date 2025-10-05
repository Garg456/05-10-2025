package Jaleh;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

public class SystemMassage {

    public static WebDriver driver;

    public static void main(String[] args) {
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
            
            
        
            setDateById("StartDateTime","15-09-2025");
            
            System.out.println("print");
            
        

            // ✅ Use JavaScript to set the date field
          /*  try {
                String targetDate = "2025-09-15";

                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript(
                    "let input = document.getElementById('StartDateTime');" +
                    "input.value = '" + targetDate + "';" +
                    "input.dispatchEvent(new Event('input', { bubbles: true }));" +
                    "input.dispatchEvent(new Event('change', { bubbles: true }));"
                );

                // Verify
                String value = driver.findElement(By.id("StartDateTime")).getAttribute("value");
                System.out.println("📅 Field value after JS: " + value);

            } catch (Exception e) {
                System.out.println("❌ JavaScript injection failed: " + e.getMessage());
            }

        } catch (Exception e) {
            System.err.println("❌ Error during automation: " + e.getMessage());
        } finally {
            // Uncomment this if you want to close the browser automatically
            // driver.quit();
        }*/
    }
    
    public static String convertAppDateToHtmlDate(String appDate) throws ParseException {
        String[] possibleFormats = {"dd-MM-yyyy", "M/d/yy", "MM/dd/yy"};
        ParseException lastException = null;

        for (String format : possibleFormats) {
            try {
                SimpleDateFormat appFormat = new SimpleDateFormat(format);
                appFormat.setLenient(false);
                Date date = appFormat.parse(appDate);
                SimpleDateFormat htmlFormat = new SimpleDateFormat("yyyy-MM-dd");
                return htmlFormat.format(date);
            } catch (ParseException e) {
                lastException = e;
            }
        }
        throw lastException;
    }
    
    public static void setDateById(String fieldId, String dateValue) {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            String htmlDate = convertAppDateToHtmlDate(dateValue);
            System.out.println(fieldId + " (converted): " + htmlDate);
            
            String script = 
                "var dateField = document.getElementById('" + fieldId + "');" +
                "dateField.value = '" + htmlDate + "';" +
                "dateField.dispatchEvent(new Event('input', { bubbles: true }));" +
                "dateField.dispatchEvent(new Event('change', { bubbles: true }));" +
                "if (typeof DisableDate === 'function') { DisableDate(dateField, 'Add'); }";
            
            js.executeScript(script);
            
            // Optionally, add delay here in Java if needed
            Thread.sleep(1000);
            
            // Verify value after setting
            String value = driver.findElement(By.id(fieldId)).getAttribute("value");
            System.out.println("After JS, date field value: " + value);
        } catch (Exception e) {
            System.out.println("Error setting date for " + fieldId + ": " + e.getMessage());
        }
    }

}
