package Jaleh.recipe;

import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(myListener2.class)
public class DeleteDraftRecipe_D extends BaseTest2 {

	@DataProvider(name = "recipeDataProvider") // ✅ Rename here

	public Object[][] recipeDataProvider() throws IOException {
	    List<Map<String, String>> dataList = readData("E:\\Automation Testing data\\Jaleh\\test1_creation_Master.xlsx", "TC_Delete Draft Recipe");
	    Object[][] data = new Object[dataList.size()][3]; // expects 10 columns

	    for (int i = 0; i < dataList.size(); i++) {
	        Map<String, String> row = dataList.get(i);
	        data[i][0] = row.get("username");
	        data[i][1] = row.get("password");
	        data[i][2] = row.get("recipename");
	       
	    }
	    return data;
	}







	@Test(dataProvider = "recipeDataProvider")
	public void Test_DeleteDraftRecipe(String username, String password, String recipeName)
			throws Throwable {
		// setup();
		loginToApplication(username, password);
		deleteDraftRecipe(recipeName);
		// tearDown();
		// recipedisplayname,mealtype,cuisine,recipecategory
	}
	

	public void deleteDraftRecipe(String rcpname) throws Exception {
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
			/*
			 * WebElement deleteIcon = wait.until(ExpectedConditions.elementToBeClickable(
			 * By.xpath("//i[@class='fas fa-trash-alt']"))); deleteIcon.click();
			 */

	        Thread.sleep(4000);
	        driver.findElement(By.xpath("//i[@class='fas fa-trash-alt']")).click();
	        // Wait for confirmation dialog and click 'Yes'
//	        WebElement confirmButton = wait.until(ExpectedConditions.elementToBeClickable(
//	                By.xpath("//button[text()='Yes']")));
//	        confirmButton.click();
	        driver.findElement(By.xpath("//button[text()='Yes']")).click();

	        // Wait for deletion to complete (could be waiting for message, or disappearance of recipe)
	        wait.until(ExpectedConditions.invisibilityOfElementLocated(
	                By.xpath("//a[contains(text(), '" + rcpname + "')]")));

	        System.out.println("✅ RECIPE DELETED: " + rcpname);

	    } catch (Exception e) {
	        throw new AssertionError("❌ Failed to delete recipe '" + rcpname + "': " + e.getMessage());
	    }
	}


}
