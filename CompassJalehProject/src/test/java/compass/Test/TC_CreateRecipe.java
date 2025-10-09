package compass.Test;

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
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;

import AutomatedScriptOfJaleh01AsPOM.SearchRecipeAfterCreation;
import AutomatedScriptOfJaleh01AsPOM.SearchRecipeBeforeCreation;

@Listeners(myListener3.class)
public class TC_CreateRecipe extends BaseTest2 {
	
	ExtentTest testLogger = myListener3.getTest(); // Add a getter method for 'test'
	/*
	 * testLogger.info("Navigating to Login Page");
	 * testLogger.pass("Login button clicked");
	 */

	@DataProvider(name = "recipeDataProvider") // ✅ Rename here

	public Object[][] recipeDataProvider() throws IOException {
	    List<Map<String, String>> dataList = readData("E:\\Automation Testing data\\Jaleh\\test1_creation_Master.xlsx", "TC_Create Recipe");
	    Object[][] data = new Object[dataList.size()][10]; // expects 10 columns

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
	        data[i][9] = row.get("publishType");
	    }
	    return data;
	}



	@Test(dataProvider = "recipeDataProvider")
	//@Parameters("recipename")
	public void Test_createRecipe(String username, String password, String recipeName, String productDescription,
	        String ingredientGroup, String recipedisplayname, String mealtype, String cuisine, String recipecategory, String publishType)
	        throws Throwable {

	    ExtentTest testLogger = myListener3.getTest(); // ✅ retrieve logger when test actually starts
	   // testLogger.info("🔰 Starting test for: " + recipeName);
	    testLogger.info("🔰 Starting test for: <span style='color:green; font-weight:bold;'>" + recipeName + "</span>");


	    loginToApplication(username, password);
	    testLogger.info("🔐 Logged into application");

	    createRecipe(recipeName, productDescription, ingredientGroup, recipecategory, cuisine, mealtype, recipedisplayname, publishType);
	  //  testLogger.pass("✅ Recipe created successfully for: " + recipeName);
	    //testLogger.info("✅ Recipe created successfully for: <span style='color:green; font-weight:bold;'>" + recipeName + "</span>");
	    testLogger.info("✅ Recipe <span style='color:green; font-weight:bold;'>" + recipeName + "</span> successfully created as <span style='color:green; font-weight:bold;'>" + publishType + "</span>");
	    TestContext.createdRecipeName = recipeName;
	
	}


	

	public void createRecipe(String recipeName, String prdctDcrption, String Ingredientgroup,String recipecategory, String cuisine,String mealtype,String recipedisplayname, String publishType)
			throws Throwable {
		clickHome();
		addSiteGoRecipe("Production | 208150-South32 - Groote Eylandt | 11-Catering(Real)");
		/*
		 * clickHome(); clickRecipeMain(); if (publishType.equalsIgnoreCase("DRAFT")) {
		 * clickOnNewRecipeDraft(); } else { clickSearchRecipe(); }
		 * 
		 * 
		 * SearchRecipeBeforeCreation srbc = new SearchRecipeBeforeCreation();
		 * srbc.searchProduct(recipeName);
		 */
		clickHome();
		clickRecipeMain();

		clickOnNewRecipeDraft();
		
		clickOnAddRecipe();
		enterRecipeName(recipeName);
		enterRecipeDisplayName(recipedisplayname);// maggie2.0_updated
		enterRecipeSearchTag("maggie2.0_tag");
		selectMealType(mealtype);
		selectCuisine(cuisine);
		selectRecipeCategory(recipecategory);
		enterPortionSize("half");
		enterDietaryInfo("ESS OR - PLUS");
		enterRecipeDescription("item added");
		enterPortion("1");
		scrollDown();
		selectProductDescription(prdctDcrption);
		enterIngredientGroup(Ingredientgroup);
		// selectProductDescription("Vinegar White 4Lt Edlyn - Superior Food Melbourne -
		// GWV5");
		// enterIngredientGroup("VINEGAR, WHITE");
		clickAddIngredientSymbol();
		scrollUp();
		//clickPublishDraft();
		
		publishRecipe(publishType);
		clickHome();
		clickRecipeMain();

		if (publishType.equalsIgnoreCase("DRAFT")) {
		    clickOnNewRecipeDraft();
		} else {
		    clickSearchRecipe();
		}
		
		 ExtentTest testLogger = myListener3.getTest();
		 String screenshotPath = takeScreenshot("s101");
         if (screenshotPath != null) {
             testLogger.pass("First screenshot captured",
                 MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
         } else {
             testLogger.warning("Screenshot failed to capture.");
         }

			/*
			 * SearchRecipeAfterCreation srac = new SearchRecipeAfterCreation();
			 * 
			 * srac.searchProduct(recipeName);
			 */
		
		


	}

	// ==================== Reusable Methods ====================

	public void clickOnNewRecipeDraft() {
		driver.findElement(By.xpath("//i[@class='nav-icon fa-brands fa-firstdraft']")).click();
	}

	public void clickOnAddRecipe() {
		driver.findElement(By.xpath("//i[@class='fas fa-plus']")).click();
	}

	public void enterRecipeName(String name) {
		if (name == null || name.trim().isEmpty()) {
			throw new AssertionError("❌ Recipe name is missing. Please provide a valid name in the Excel sheet.");
		}

		try {
			driver.findElement(By.id("RecipeName")).sendKeys(name, Keys.ENTER);
			
			System.out.println("✅ Recipe name entered: " + name);
		} catch (Exception e) {
			throw new AssertionError("❌ Failed to enter recipe name: " + e.getMessage());
		}
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

	public void enterRecipeSearchTag(String tag) {

		try {
			driver.findElement(By.id("RecipeSearchTag")).sendKeys(tag, Keys.ENTER);
		} catch (Exception e) {
			throw new AssertionError("❌ Failed to enter search tag: " + e.getMessage());
		}
	}

	public void selectMealType(String mealType) {
	    if (mealType == null || mealType.trim().isEmpty()) {
	        throw new AssertionError("❌ Meal Type is missing or empty.");
	    }
	    try {
	        driver.findElement(By.id("select2-MealTypeId-container")).click();
	        driver.findElement(By.xpath("//input[@type='search']")).sendKeys(mealType);
	        
	        Thread.sleep(1000);  // wait for dropdown options to load
	        
	        List<WebElement> options = driver.findElements(By.xpath("//li[contains(@class,'select2-results__option')]"));
	        
	        for (WebElement option : options) {
	            if (option.getText().equalsIgnoreCase(mealType)) {
	                option.click();
	                return;  // success
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


	public void selectCuisine(String cuisine) {
	    if (cuisine == null || cuisine.trim().isEmpty()) {
	        throw new AssertionError("❌ Cuisine is missing or empty.");
	    }
	    try {
	        driver.findElement(By.id("select2-CuisineId-container")).click();
	        driver.findElement(By.xpath("//input[@type='search']")).sendKeys(cuisine);
	        
	        Thread.sleep(1000);
	        
	        List<WebElement> options = driver.findElements(By.xpath("//li[contains(@class,'select2-results__option')]"));
	        
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


	public void selectRecipeCategory(String category) {
	    if (category == null || category.trim().isEmpty()) {
	        throw new AssertionError("❌ Recipe Category is missing or empty.");
	    }
	    try {
	        driver.findElement(By.id("select2-RecipeCategoryId-container")).click();
	        driver.findElement(By.xpath("//input[@type='search']")).sendKeys(category);
	        
	        Thread.sleep(1000);
	        
	        List<WebElement> options = driver.findElements(By.xpath("//li[contains(@class,'select2-results__option')]"));
	        
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

	public void enterPortionSize(String portionSize) {
		driver.findElement(By.id("RecomPortionPerSize")).sendKeys(portionSize, Keys.ENTER);
	}

	public void enterDietaryInfo(String dietaryInfo) {
		driver.findElement(By.xpath("//span[text()='Dietary Information']")).click();
		driver.findElement(By.xpath("//input[@type='search']")).sendKeys(dietaryInfo, Keys.ENTER);
	}

	public void enterRecipeDescription(String description) {
		driver.findElement(By.id("Description")).sendKeys(description, Keys.ENTER);
	}

	public void enterPortion(String portion) {
		driver.findElement(By.id("portion")).sendKeys(portion);
	}

	public void scrollDown() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,500)");
	}

	public void scrollUp() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollTo(0, 0);");
	}

	/*
	 * public void selectProductDescription(String productText) throws
	 * InterruptedException { Thread.sleep(5000);
	 * driver.findElement(By.xpath("//*[@id='select2-desc-container']")).click();
	 * WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	 * wait.until(ExpectedConditions.elementToBeClickable(By.
	 * xpath("//li[contains(@class,'select2-results__option') and text()='" +
	 * productText + "']"))).click(); }
	 * 
	 * public void enterIngredientGroup(String ingredientGroup) throws
	 * InterruptedException { Thread.sleep(5000);
	 * driver.findElement(By.xpath("//input[@class='select2-search__field']")).
	 * sendKeys(ingredientGroup, Keys.ENTER); }
	 */

	public void selectProductDescription(String productText) {
		if (productText == null || productText.trim().isEmpty()) {
			throw new AssertionError("❌ product Text is missing or empty.");
		}

		try {
			Thread.sleep(5000); // Replace with WebDriverWait if needed
			driver.findElement(By.xpath("//*[@id='select2-desc-container']")).click();
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.elementToBeClickable(
					//changed x path  By.xpath("//li[contains(@class,'select2-results__option') and text()='" + productText + "']")))
					
					 By.xpath("//li[contains(@class,'select2-results__option')]//span[contains(text(),'" + productText + "')]")))

			
			
					.click();
			System.out.println("✅ Product description selected: " + productText);
		} catch (Exception e) {
			throw new AssertionError("❌ Failed to select product description '" + productText + "': " + e.getMessage());
		}
	}

	public void enterIngredientGroup(String ingredientGroup) {
		if (ingredientGroup == null || ingredientGroup.trim().isEmpty()) {
			throw new AssertionError("❌ Ingredient group is missing or empty.");
		}

		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			By inputLocator = By.xpath("//input[@class='select2-search__field']");
			wait.until(ExpectedConditions.visibilityOfElementLocated(inputLocator)).sendKeys(ingredientGroup,
					Keys.ENTER);
			System.out.println("✅ Ingredient group entered: " + ingredientGroup);
		} catch (Exception e) {
			throw new AssertionError("❌ Failed to enter ingredient group '" + ingredientGroup + "': " + e.getMessage());
		}
	}

	public void clickAddIngredientSymbol() {
		driver.findElement(By.xpath("//i[@class='fa fa-plus-circle']")).click();
	}

	public void publishRecipe(String publishTypeFromExcel) throws Throwable {
		 if (publishTypeFromExcel == null || publishTypeFromExcel.trim().isEmpty()) {
		        throw new IllegalArgumentException("❌ Publish type is null or empty. Please provide a valid value in the Excel sheet.");
		
		 }
		 
		 switch (publishTypeFromExcel.trim().toUpperCase()) {
		case "MASTER":
			clickPublishMaster();
			break;
		case "SITE":
			clickPublishSite();
			break;
		case "SECTOR":
			clickPublishSector();
			break;
		case "DRAFT":
			clickPublishDraft();
			break;
		default:
			throw new IllegalArgumentException("❌ Unknown publish type: " + publishTypeFromExcel);
		}

	}

	public void clickPublishDraft() {
		try {
			Thread.sleep(4000); // Prefer WebDriverWait if the button appears dynamically
			driver.findElement(By.id("btnSavedraft")).click();
			//String message = capturePopupMessageText();
			//System.out.println("Menu add popup: " + message);
			//test.info(message);
			ExtentTest testLogger = myListener3.getTest();
			  String msg = capturePopupMessageText();
		        System.out.println("Popup after adding recipe: " + msg);
		        testLogger.info(msg);
		   	
		        Assert.assertNotNull(msg, "Customer popup not found.");
		        Assert.assertTrue(msg.toLowerCase().contains("Recipe") || msg.toLowerCase().contains("success"), "Unexpected customer popup: " + msg);
					
		} catch (Exception e) {
			throw new AssertionError("❌ Failed to click 'Save Draft' button: " + e.getMessage());
		}
		System.out.println("✅ Draft published successfully.");
	}

	public void clickPublishSector() throws Throwable {
		Thread.sleep(4000);
		driver.findElement(By.xpath("//button[text()='Publish to...']")).click();
		driver.findElement(By.id("btnPublishTosector")).click();
		// Locator for the OK button
		  By okButtonLocator = By.xpath("//button[text()='OK']");

		  // Click the OK button
		  driver.findElement(okButtonLocator).click();

		  // Wait until the OK button is no longer visible (invisible)
		  WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		  wait.until(ExpectedConditions.invisibilityOfElementLocated(okButtonLocator));

		  System.out.println("'OK' button is now invisible.");
		System.out.println("✅ Sector published successfully.");
		 String msg = capturePopupMessageText();
		ExtentTest testLogger = myListener3.getTest();
		 testLogger.info(msg);
	}

	//
	public void clickPublishSite() throws Throwable {
		Thread.sleep(4000);
		driver.findElement(By.xpath("//button[text()='Publish to...']")).click();
		driver.findElement(By.id("btnPublishSiteLib")).click();
		// Locator for the OK button
		  By okButtonLocator = By.xpath("//button[text()='OK']");

		  // Click the OK button
		  driver.findElement(okButtonLocator).click();

		  // Wait until the OK button is no longer visible (invisible)
		  WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		  wait.until(ExpectedConditions.invisibilityOfElementLocated(okButtonLocator));

		  System.out.println("'OK' button is now invisible.");
		System.out.println("✅ Site published successfully.");
		 String msg = capturePopupMessageText();
			ExtentTest testLogger = myListener3.getTest();
			 testLogger.info(msg);
	}

	//
	 public void clickPublishMaster() throws Throwable {
	    	Thread.sleep(4000); 
	    	driver.findElement(By.xpath("//button[text()='Publish to...']")).click();
	   // driver.findElement(By.xpath("//button[text()='Publish to...']")).click();
		  driver.findElement(By.xpath("//button[text()='Publish as Master']")).click();
		// Locator for the OK button
		  By okButtonLocator = By.xpath("//button[text()='OK']");

		  // Click the OK button
		  driver.findElement(okButtonLocator).click();

		  // Wait until the OK button is no longer visible (invisible)
		  WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		  wait.until(ExpectedConditions.invisibilityOfElementLocated(okButtonLocator));

		  System.out.println("'OK' button is now invisible.");

		  System.out.println("✅ Master published successfully.");
		  String msg = capturePopupMessageText();
			ExtentTest testLogger = myListener3.getTest();
			 testLogger.info(msg);
		  
		 
	    
	}

	public void validationOfCreateRecipe(String recipeName) throws Exception {
		// Enter recipe name and search
		driver.findElement(By.id("SearchText")).clear();
		driver.findElement(By.id("SearchText")).sendKeys(recipeName, Keys.ENTER);

		Thread.sleep(3000); // Replace with wait if needed

		try {
			// Try to find recipe link
			String actualRecipeName = driver.findElement(By.xpath("//a[contains(text(), '" + recipeName + "')]"))
					.getText();
			Assert.assertEquals(actualRecipeName, recipeName, "Recipe name found matches searched name.");
			System.out.println("✅ Recipe found: " + actualRecipeName);

		} catch (NoSuchElementException e) {
			// If recipe not found, fail test with message
			Assert.fail("❌ Recipe '" + recipeName + "' not found.");
		}

	}

	// Read data from Excel and return a List of Maps

	public static void addSiteGoRecipe(String siteName) throws Exception {
	    // Enter site name in the search field and press Enter
	    driver.findElement(By.id("txtSearchOrgUnit")).sendKeys(siteName, Keys.ENTER);

	    // Build dynamic XPath for the radio button based on the site name
	    String xpath = "//div[@class='list-field']//label[text()='" + siteName + "']/preceding-sibling::input[@type='radio']";

	    // Locate the radio button
	    WebElement radioButton = driver.findElement(By.xpath(xpath));

	    // Click the radio button if it's not already selected
	    if (!radioButton.isSelected()) {
	        radioButton.click();
	        
	    }
	    System.out.println("site selected");
	    Thread.sleep(3000);
	    driver.findElement(By.id("spnCountRecipes")).click();
	}
}
