package AutomatedScriptOfJaleh01AsPOM;

import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;

@Listeners(myListener3.class)
public class TC_DeleteDraftRecipe extends BaseTest2 {

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
	//@Parameters("recipename")
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
	    deleteRecipe(rcpname);
	    
	    clickHome();
	    clickRecipeMain();
	    clickOnNewRecipeDraft();
	    
	    SearchRecipeBeforeCreation srbc = new SearchRecipeBeforeCreation();
		  srbc.searchProduct(rcpname);
		 

	}
	
	public void deleteRecipe(String rcpname) {
	    if (rcpname == null || rcpname.trim().isEmpty()) {
	        throw new AssertionError("❌ Recipe name is missing or empty.");
	    }

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	    ExtentTest testLogger = myListener3.getTest();

	    try {
	        // Search for the recipe
	        WebElement searchBox = wait.until(ExpectedConditions.elementToBeClickable(By.id("SearchText")));
	        searchBox.clear();
	        searchBox.sendKeys(rcpname, Keys.ENTER);
	        Thread.sleep(2000); // Consider replacing this with dynamic wait if needed

	        // Click delete icon
	        WebElement deleteIcon = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//i[@class='fas fa-trash-alt']")));
	        deleteIcon.click();

	        // Confirm deletion by clicking 'Yes'
	        By yesButtonLocator = By.xpath("//button[text()='Yes']");
	        driver.findElement(yesButtonLocator).click();

	        // Wait for confirmation dialog to disappear
	        wait.until(ExpectedConditions.invisibilityOfElementLocated(yesButtonLocator));
	        System.out.println("'Yes' button is now invisible.");
	        System.out.println("✅ Recipe deleted successfully.");

	        // Capture confirmation popup message
	       // String msg = capturePopupMessageText();
	        testLogger.info("<span style='color:green; font-weight:bold;'>" + rcpname + "</span>"+ "    Recipe is deleted");

	    } catch (Exception e) {
	        e.printStackTrace();
	        testLogger.fail("❌ Exception occurred while deleting recipe: " + e.getMessage());
	        Assert.fail("❌ Failed to delete recipe due to: " + e.getMessage());
	    }
	}
}
	    
