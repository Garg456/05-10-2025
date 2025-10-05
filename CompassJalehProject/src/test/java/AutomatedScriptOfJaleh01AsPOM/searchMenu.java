package AutomatedScriptOfJaleh01AsPOM;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class searchMenu extends BaseTest2 {

	
	public void searchProduct(String productName) throws Exception {
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
	    
	    WebElement srchRecipe = wait.until(ExpectedConditions.elementToBeClickable(By.id("SearchText")));
	    srchRecipe.clear();
	    srchRecipe.sendKeys(productName);
	    
	    // If there's a search button, click it here
	    // Otherwise, send ENTER key
	    srchRecipe.sendKeys(Keys.ENTER);
	    
	    System.out.println("Search submitted for: " + productName);

	    // Wait for results container to appear
	    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("resultsContainer")));

	    // Now wait for the link/text element to be visible
	    WebElement recipeLink = wait.until(ExpectedConditions.visibilityOfElementLocated(
	        By.xpath("//a[contains(normalize-space(text()), '" + productName + "')]")
	    ));
	    
	    System.out.println("Product found: " + recipeLink.getText());
	}


}
