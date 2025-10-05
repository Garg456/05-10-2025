package AutomatedScriptOfJaleh01AsPOM;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class SearchRecipeAfterCreation extends BaseTest2 {

    public void searchProduct(String productName) throws Exception {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        
        // Wait for the search input box to be clickable and ready
        WebElement srchRecipe = wait.until(ExpectedConditions.elementToBeClickable(By.id("SearchText")));
        
        //WebElement srchRecipe=driver.findElement(By.xpath("//div[contains(@class, 'search-button')]//input[2]"));
        // Clear any existing text in the search box
        srchRecipe.clear();
        
        // Enter the recipe name and press ENTER
        System.out.println("Typing in search box: " + productName);
        srchRecipe.sendKeys(productName,Keys.ENTER);
        System.out.println("Search submitted.");

        Thread.sleep(3000);
        
        // Optional: Wait for a loading spinner to disappear if your app has one (update selector if needed)
        // wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loadingSpinner")));
        
        // Optional short pause to let UI update after hitting Enter
      //  Thread.sleep(1000);
        
        try {
            // Wait until the link with the recipe name is visible on the page
            WebElement recipeLink = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//a[contains(normalize-space(text()), '" + productName + "')]")
            ));
            Thread.sleep(3000);
            // Get the actual recipe name text from the link
            String actualRecipeName = recipeLink.getText();
            
            // Assert the expected and actual recipe names match
            Assert.assertEquals(actualRecipeName, productName, "Product name matched");
            System.out.println("Product found: " + actualRecipeName);
            
        } catch (Exception e) {
            System.err.println("Failed to find the recipe '" + productName + "': " + e.getMessage());
            throw e;  // Rethrow to fail the test if not found
        }
    }
}
