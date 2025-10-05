package AutomatedScriptOfJaleh01AsPOM.pages;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class SearchRecipeBeforeCreation {

    WebDriver driver;

    public SearchRecipeBeforeCreation(WebDriver driver) {
        this.driver = driver;
    }

    public void searchProduct(String productName) throws Exception {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        WebElement srchRecipe = wait.until(ExpectedConditions.elementToBeClickable(By.id("SearchText")));
        srchRecipe.clear();
        srchRecipe.sendKeys(productName, Keys.ENTER);

        WebElement noRecordMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//td[text()='No record found']")
        ));

        String message = noRecordMessage.getText();
        Assert.assertEquals(message, "No record found", "Expected 'No record found' message not displayed.");

        System.out.println("Recipe not found as expected.");
    }
}
