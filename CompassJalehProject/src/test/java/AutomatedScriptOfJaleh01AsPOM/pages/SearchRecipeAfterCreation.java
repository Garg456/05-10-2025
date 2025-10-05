package AutomatedScriptOfJaleh01AsPOM.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import java.time.Duration;

public class SearchRecipeAfterCreation {

    WebDriver driver;

    public SearchRecipeAfterCreation(WebDriver driver) {
        this.driver = driver;
    }

    public void searchProduct(String productName) throws Exception {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        WebElement srchRecipe = wait.until(ExpectedConditions.elementToBeClickable(By.id("SearchText")));
        srchRecipe.clear();

        System.out.println("Typing in search box: " + productName);
        srchRecipe.sendKeys(productName, Keys.ENTER);
        System.out.println("Search submitted.");

        WebElement recipeLink = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//a[normalize-space(text())='" + productName + "']")
        ));
        
        String actualRecipeName = recipeLink.getText();
        Assert.assertEquals(actualRecipeName, productName, "Product name matched");
        System.out.println("Product found: " + actualRecipeName);
    }
}
