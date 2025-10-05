package Jaleh.recipe;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(myListener2.class)
public class DeleteDraftRecipe2 extends BaseTest {

	@Test(dataProvider = "loginData")
	public  void Test_DeleteRecipe(String username, String password, String recipeName, String productDescription,
			String ingredientGroup,String recipedisplayname,String mealtype,String cuisine,String recipecategory) throws Throwable {
		//setup();
		loginToApplication(username, password);
		
		dltrcp(recipeName);
		//tearDown();
		 // recipedisplayname,mealtype,cuisine,recipecategory
	}

	

	public void dltrcp(String rcpname) throws Exception {
	    clickHome();
	    clickRecipeMain();
	    clickOnNewRecipeDraft();

	    if (rcpname == null || rcpname.trim().isEmpty()) {
	        throw new AssertionError("❌ Recipe name is missing or empty.");
	    }

	    try {
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

	        // Enter recipe name in search field
	        driver.findElement(By.id("SearchText")).clear();
	        driver.findElement(By.id("SearchText")).sendKeys(rcpname, Keys.ENTER);

	        // Wait for delete icon to appear
	        WebElement deleteIcon = wait.until(ExpectedConditions.elementToBeClickable(
	                By.xpath("//i[@class='fas fa-trash-alt']")));
	        deleteIcon.click();

	        // Wait for confirmation dialog and click 'Yes'
	        WebElement confirmButton = wait.until(ExpectedConditions.elementToBeClickable(
	                By.xpath("//button[text()='Yes']")));
	        confirmButton.click();

	        // Wait for deletion to complete (could be waiting for message, or disappearance of recipe)
	        wait.until(ExpectedConditions.invisibilityOfElementLocated(
	                By.xpath("//a[contains(text(), '" + rcpname + "')]")));

	        System.out.println("✅ RECIPE DELETED: " + rcpname);

	    } catch (Exception e) {
	        throw new AssertionError("❌ Failed to delete recipe '" + rcpname + "': " + e.getMessage());
	    }
	}


}
