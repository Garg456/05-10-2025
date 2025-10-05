
package AutomatedScriptOfJaleh01AsPOM;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class SearchRecipeBeforeCreation extends BaseTest2 {
	
	//SearchRecipeBeforeCreation

   
    public void searchProduct(String productName) throws Exception {
      

    	 WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
         
         // Wait for the search input box to be clickable and ready
         WebElement srchRecipe = wait.until(ExpectedConditions.elementToBeClickable(By.id("SearchText")));
         
         // Clear any existing text in the search box
         srchRecipe.clear();
         
         // Enter the recipe name and press ENTER
         srchRecipe.sendKeys(productName, Keys.ENTER);

        String message = driver.findElement(By.xpath("//td[text()='No record found']")).getText();
        Assert.assertEquals(message, "No record found", "Expected 'No record found' message not displayed.");
        System.out.println("Recipe not found as expected.");
    }
}
