package AutomatedScriptOfJaleh01AsPOM.util;

import java.text.SimpleDateFormat;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Util {

    private WebDriver driver;

    // Constructor
    public Util(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Capture text from a popup with ID 'swal2-title'
     */
    public String capturePopupMessageText() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement popup = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("swal2-title")));
            String message = popup.getText().trim();
            System.out.println("Popup message: " + message);
            return message;
        } catch (Exception e) {
            System.out.println("❌ Popup not found or disappeared too fast: " + e.getMessage());
            return null;
        }
    }

    /**
     * Converts date from dd-MM-yyyy to yyyy-MM-dd (HTML format)
     */
    public static String convertAppDateToHtmlDate(String appDate) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd")
                    .format(new SimpleDateFormat("dd-MM-yyyy").parse(appDate));
        } catch (Exception e) {
            System.out.println("❌ Error converting date: " + e.getMessage());
            return appDate; // Fallback
        }
    }

    /**
     * Set a date value in an input field using JavaScript
     */
    public  void setDateById(WebDriver driver, String fieldId, String appDate) {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            String htmlDate = convertAppDateToHtmlDate(appDate);
            String script = "var el = document.getElementById('" + fieldId + "');" +
                            "el.value = '" + htmlDate + "';" +
                            "el.dispatchEvent(new Event('change', { bubbles: true }));";
            js.executeScript(script);
            System.out.println("✅ Date set for field: " + fieldId + " → " + htmlDate);
        } catch (Exception e) {
            System.out.println("❌ Error setting date for field '" + fieldId + "': " + e.getMessage());
        }
    }

    /**
     * Select a site by name using search and radio button
     */
    public void selectSite(String siteName) {
        try {
            WebElement searchInput = driver.findElement(By.id("txtSearchOrgUnit"));
            searchInput.sendKeys(siteName, Keys.ENTER);

            String xpath = "//div[@class='list-field']//label[text()='" + siteName +
                           "']/preceding-sibling::input[@type='radio']";

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement radioButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));

            if (!radioButton.isSelected()) {
                radioButton.click();
            }

            System.out.println("✅ Site selected: " + siteName);
        } catch (Exception e) {
            System.out.println("❌ Failed to select site: " + e.getMessage());
        }
    }

    /**
     * Clicks on the count of recipes (to open menu recipe section)
     */
    public void selectMenuRecipe() {
        try {
            WebElement recipeCount = driver.findElement(By.id("spnCountRecipes"));
            recipeCount.click();
            System.out.println("✅ Menu recipe section opened.");
        } catch (Exception e) {
            System.out.println("❌ Error clicking on recipe count: " + e.getMessage());
        }
    }
}
