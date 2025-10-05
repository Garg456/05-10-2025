package AutomatedScriptOfJaleh01AsPOM;

import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;

@Listeners(myListener3.class)
public class TC_Edit_Recipe extends BaseTest2 {
	@DataProvider(name = "recipeDataProvider") // ✅ Rename here

	public Object[][] recipeDataProvider() throws IOException {
		List<Map<String, String>> dataList = readData("E:\\Automation Testing data\\Jaleh\\test1_creation_Master.xlsx",
				"TC_Edit Recipe");
		Object[][] data = new Object[dataList.size()][9]; // expects 10 columns

		for (int i = 0; i < dataList.size(); i++) {
			Map<String, String> row = dataList.get(i);
			data[i][0] = row.get("username");
			data[i][1] = row.get("password");
			data[i][2] = row.get("recipename");
			data[i][3] = row.get("productdescription");
			data[i][4] = row.get("ingredientgroup");
			data[i][5] = row.get("recipedisplayname");
			data[i][6] = row.get("mealtype");
			data[i][7] = row.get("cuisine");
			data[i][8] = row.get("recipecategory");
			// data[i][9] = row.get("publishType");
		}
		return data;
	}

	@Test(dataProvider = "recipeDataProvider")
	public void Test_EditDraftRecipe(String username, String password, String recipeName, String productDescription,
			String ingredientGroup, String recipedisplayname, String mealtype, String cuisine, String recipecategory)
			throws Throwable {
		// setup();
		loginToApplication(username, password);
		editRecipe(recipeName, recipedisplayname, mealtype, cuisine, recipecategory);
		// tearDown();
		// recipedisplayname,mealtype,cuisine,recipecategory
	}

	public void editRecipe(String rcpname, String recipedisplayname, String mealtype, String cuisine,
			String recipecategory) throws Throwable {
		clickHome();
		clickHome();
		clickRecipeMain();
		clickSearchRecipe();

		searchRecipe(rcpname);

		enterRecipeDisplayName(recipedisplayname);
		String oldMealType = getOldMealType();
		selectMealType(mealtype);
		ExtentTest testLogger = myListener3.getTest();

		// testLogger.info(oldMealType + " meal type changed to " + mealtype);
		testLogger.info("<span style='color:green; font-weight:bold;'>" + oldMealType + "</span>"
				+ " meal type changed to " + "<span style='color:blue; font-weight:bold;'>" + mealtype + "</span>");

		// "<span style='color:blue; font-weight:bold;'>" + mealtype + "</span>"

		String oldcuisine = getoldcuisine();
		selectCuisine(cuisine);
		// testLogger.info(oldcuisine + " meal type changed to " + cuisine);
		testLogger.info("<span style='color:green; font-weight:bold;'>" + oldcuisine + "</span>"
				+ " cuisine changed to " + "<span style='color:blue; font-weight:bold;'>" + cuisine + "</span>");

		String oldrecipecategory = getoldRecipeCategory();
		selectRecipeCategory(recipecategory);
		// testLogger.info(oldrecipecategory + " meal type changed to " +
		// recipecategory);
		testLogger.info("<span style='color:green; font-weight:bold;'>" + oldrecipecategory + "</span>"
				+ " recipecategory changed to " + "<span style='color:blue; font-weight:bold;'>" + recipecategory
				+ "</span>");

		clickONPublishChangestoSourceBUTTON();

	}

	public static void clickonRecipesUnderEdit() {
		driver.findElement(By.xpath("//div//i[@class='nav-icon fa-solid fa-utensils']")).click();
	}

	public void searchRecipe(String rcpname) throws Throwable {
		// driver.findElement(By.xpath("//i[@class='nav-icon fa-brands
		// fa-firstdraft']")).click();

		driver.findElement(By.id("SearchText")).sendKeys(rcpname, Keys.ENTER);
		Thread.sleep(3000);

		// driver.findElement(By.xpath("//a[contains(text(), '" + rcpname +
		// "')]")).click();
		// i[@class='fa fa-edit']
		driver.findElement(By.xpath("//i[@class='fa fa-edit']")).click();
		driver.findElement(By.xpath("//button[text()='OK']")).click();

	}

	public void enterRecipeDisplayName(String displayName) {
		if (displayName == null || displayName.trim().isEmpty()) {
			throw new AssertionError("❌ display Name is missing or empty.");
		}
		try {
			driver.findElement(By.id("RecipeDisplayName")).sendKeys(displayName, Keys.ENTER);
		} catch (Exception e) {
			throw new AssertionError("❌ Failed to enter display name: " + e.getMessage());
		}
	}

	public String getOldMealType() {
		try {
			WebElement oldMealTypeElement = driver.findElement(
					By.xpath("(//label[contains(text(),'Meal Type')]/following-sibling::span//span[last()]//span)[1]"));
			return oldMealTypeElement.getText();
		} catch (Exception e) {
			return "Unknown (Failed to fetch old Meal Type)";
		}
	}

	public void selectMealType(String mealType) {
		if (mealType == null || mealType.trim().isEmpty()) {
			throw new AssertionError("❌ Meal Type is missing or empty.");
		}
		try {
			driver.findElement(By.id("select2-MealTypeId-container")).click();
			driver.findElement(By.xpath("//input[@type='search']")).sendKeys(mealType);

			Thread.sleep(1000); // wait for dropdown options to load

			List<WebElement> options = driver
					.findElements(By.xpath("//li[contains(@class,'select2-results__option')]"));

			for (WebElement option : options) {
				if (option.getText().equalsIgnoreCase(mealType)) {
					option.click();
					return; // success
				}
			}

			// If code reaches here, option not found
			throw new AssertionError("❌ Meal Type '" + mealType + "' not found in dropdown.");

		} catch (InterruptedException e) {
			throw new AssertionError("❌ Interrupted Exception: " + e.getMessage());
		} catch (Exception e) {
			throw new AssertionError("❌ Failed to select Meal Type: " + e.getMessage());
		}
	}

	public String getoldcuisine() {
		try {
			WebElement oldcuisineElement = driver.findElement(
					By.xpath("(//label[contains(text(),'Cuisine')]/following-sibling::span//span[last()]//span)[1]"));
			return oldcuisineElement.getText();
		} catch (Exception e) {
			return "Unknown (Failed to fetch  old cuisine)";
		}
	}

	public void selectCuisine(String cuisine) {
		if (cuisine == null || cuisine.trim().isEmpty()) {
			throw new AssertionError("❌ Cuisine is missing or empty.");
		}
		try {
			driver.findElement(By.id("select2-CuisineId-container")).click();
			driver.findElement(By.xpath("//input[@type='search']")).sendKeys(cuisine);

			Thread.sleep(1000);

			List<WebElement> options = driver
					.findElements(By.xpath("//li[contains(@class,'select2-results__option')]"));

			for (WebElement option : options) {
				if (option.getText().equalsIgnoreCase(cuisine)) {
					option.click();
					return;
				}
			}

			throw new AssertionError("❌ Cuisine '" + cuisine + "' not found in dropdown.");

		} catch (InterruptedException e) {
			throw new AssertionError("❌ Interrupted Exception: " + e.getMessage());
		} catch (Exception e) {
			throw new AssertionError("❌ Failed to select Cuisine: " + e.getMessage());
		}
	}

	public static void clickONPublishChangestoSourceBUTTON() throws InterruptedException {
		driver.findElement(By.id("btnPublishtoBase")).click();
		By okButtonLocator = By.xpath("//button[text()='OK']");

		  // Click the OK button
		  driver.findElement(okButtonLocator).click();

		  // Wait until the OK button is no longer visible (invisible)
		  WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		  wait.until(ExpectedConditions.invisibilityOfElementLocated(okButtonLocator));

		  System.out.println("'OK' button is now invisible.");
		
		
		String msg = capturePopupMessageText();
        System.out.println("Popup after adding recipe: " + msg);
        ExtentTest testLogger = myListener3.getTest();
        testLogger.info(msg);
   	
        Assert.assertNotNull(msg, "Customer popup not found.");
        Assert.assertTrue(msg.toLowerCase().contains("Recipe") || msg.toLowerCase().contains("success"), "Unexpected customer popup: " + msg);
		Thread.sleep(4000);

	}

	public String getoldRecipeCategory() {
		try {
			WebElement oldRecipeCategoryElement = driver.findElement(By.xpath(
					"(//label[contains(text(),'Recipe Category')]/following-sibling::span//span[last()]//span)[1]"));
			return oldRecipeCategoryElement.getText();
		} catch (Exception e) {
			return "Unknown (Failed to fetch old Recipe Category)";
		}
	}

	public void selectRecipeCategory(String category) {
		if (category == null || category.trim().isEmpty()) {
			throw new AssertionError("❌ Recipe Category is missing or empty.");
		}
		try {
			driver.findElement(By.id("select2-RecipeCategoryId-container")).click();
			driver.findElement(By.xpath("//input[@type='search']")).sendKeys(category);

			Thread.sleep(1000);

			List<WebElement> options = driver
					.findElements(By.xpath("//li[contains(@class,'select2-results__option')]"));

			for (WebElement option : options) {
				if (option.getText().equalsIgnoreCase(category)) {
					option.click();
					return;
				}
			}

			throw new AssertionError("❌ Recipe Category '" + category + "' not found in dropdown.");

		} catch (InterruptedException e) {
			throw new AssertionError("❌ Interrupted Exception: " + e.getMessage());
		} catch (Exception e) {
			throw new AssertionError("❌ Failed to select Recipe Category: " + e.getMessage());
		}
	}

}
