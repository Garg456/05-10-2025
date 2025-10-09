package AutomatedScriptOfJaleh01AsPOM;

import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;

@Listeners(myListener3.class)
public class TC_CreateTemplateMenu extends BaseTest2 {
	/*
	 * String menuname = "MenuA19H"; String sitename2
	 * ="Production | 180800-AFP Majura | 31-Retail 1(Real)"; String sitename1
	 * ="Production | 208150-South32 - Groote Eylandt | 11-Catering(Real)"; String
	 * Recipename="Rec98TY";
	 */

	@DataProvider(name = "recipeDataProvider") // ✅ Rename here

	public Object[][] recipeDataProvider() throws IOException {
		List<Map<String, String>> dataList = readData("E:\\Automation Testing data\\Jaleh\\test1_creation_Master.xlsx",
				"TC_Create Template Menu");
		Object[][] data = new Object[dataList.size()][9]; // expects 10 columns

		for (int i = 0; i < dataList.size(); i++) {
			Map<String, String> row = dataList.get(i);
			data[i][0] = row.get("username");
			data[i][1] = row.get("password");
			data[i][2] = row.get("recipename");
			data[i][3] = row.get("menuname");
			data[i][4] = row.get("sitename");
			data[i][5] = row.get("Menu_Type");
			data[i][6] = row.get("MenuCategory");
			data[i][7] = row.get("NoOfCustomer");
			data[i][8] = row.get("addMeal");
		
			
			
			//NoOfCustomer
			//addMeal,repeatCount
			//section

		}
		return data;
	}

	@Test(dataProvider = "recipeDataProvider")
	public void Test_TemplateMenu(String username, String password, String recipeName, String menuname, String sitename,
	        String Menu_Type, String MenuCategory, String NoOfCustomer,String addMeal) throws Throwable {
	    
	    loginToApplication(username, password);
	    
	    
	    if (TestContext.createdRecipeName != null && !TestContext.createdRecipeName.isEmpty()) {
	        recipeName = TestContext.createdRecipeName;
	        System.out.println("Overriding recipeName from TC01: " + recipeName);
	    } else {
	        System.out.println("Using recipeName from Excel: " + recipeName);
	    }

		/*
		 * // Override recipeName if TC01 created recipe exists if
		  (TestContext.createdRecipeName != null &&
		 * !TestContext.createdRecipeName.isEmpty()) { recipeName =
		 * TestContext.createdRecipeName;
		 * System.out.println("Overriding recipeName from TC01: " + recipeName); } else
		 * { System.out.println("Using recipeName from Excel: " + recipeName); }
		 */

	    TemplateMenu(menuname, sitename, recipeName, Menu_Type, MenuCategory, NoOfCustomer, addMeal);
	    
	    TestContext.createdTemplateMenuName = menuname;
	}

	// Method to create a template menu with given parameters and publish it
	public static void TemplateMenu(String menuname, String sitename, String Recipename, String Menu_Type,
	                                String MenuCategory, String NoOfCustomer, String addMeal) throws Exception {

	    clickHome(); // Navigate to the home page

	    addSiteGoRecipe(sitename); // Select the site by name to work within the correct context

	    selectMenuRecipe(); // Open or navigate to the menu recipe section

	    clickHome(); // Return to home page

	    clickMenus(); // Open the Menus module

	    clickTemplateMenus(); // Open Template Menus section

	    // Create a helper instance to search for existing menu with the same name before creation
	    SearchRecipeBeforeCreation srbc = new SearchRecipeBeforeCreation();

	    srbc.searchProduct(menuname); // Search the menu name to avoid duplication

	    clickHome(); // Navigate back to home page

	    clickMenus(); // Go to Menus module again

	    //clickTemplateMenus(); // This line is commented out, probably because it's redundant here

	    ExtentTest testLogger = myListener3.getTest(); // Initialize the logger for test reporting

	    navigateToMenus(); // Navigate to the menu management/creation page

	    addmenu(menuname, Menu_Type, MenuCategory); // Add a new menu with name, type, and category

	    addCustomer(NoOfCustomer); // Add the specified number of customers to the menu

	    addMealPeriods(addMeal); // Add the meal period(s) specified by the parameter

	    selectMealPeriod(); // Select the added meal period(s)

	    addRecipeOnMenu(Recipename); // Add the specified recipe to the menu

	    // Log the recipe name added to the menu in the test report
	    testLogger.info("🔰 Enter Recipename: <span style='color:green; font-weight:bold;'>" + Recipename + "</span>");

	    clickPublishMenu(); // Publish the created template menu

	    // Log the menu creation details including menu name, type, and category
	    testLogger.info("🔰 Menu : <span style='color:green; font-weight:bold;'>" + menuname + "</span>" +
	                    " created as " + "🔰 created as Menu_Type : <span style='color:green; font-weight:bold;'>" + 
	                    Menu_Type + "   MenuCategory  is " + MenuCategory + "</span>");

	    clickHome(); // Return to home page after publishing

	    clickMenus(); // Go to Menus module again

	    clickTemplateMenus(); // Open Template Menus section again to verify

	    // Create an instance to search and verify the newly created menu exists
	    SearchRecipeAfterCreation srac = new SearchRecipeAfterCreation();

	    //searchMenu sm = new searchMenu(); // This is commented out, potentially unused code

	    //sm.searchProduct(menuname); // Also commented out alternative search method

	    srac.searchProduct(menuname); // Search the menu by name to confirm creation
	    
	    
	}


	public static void navigateToMenus() {
		driver.findElement(By.xpath("(//i[@class='nav-icon fas fa-table'])[1]")).click();
		driver.findElement(By.xpath("//i[@class='nav-icon fas fa-utensils']")).click();
	}

	// click on Addmenu button
	public static void addmenu(String menuname, String Menu_Type, String MenuCategory) {
	    // Validate menuname
		ExtentTest testLogger = myListener3.getTest();
	    if (menuname == null || menuname.trim().isEmpty()) {
	        throw new AssertionError("❌ Menu name is missing. Please provide a valid name in the Excel sheet.");
	    }

	    try {
	        driver.findElement(By.xpath("//button[text()='Add Menu']")).click();
	        System.out.println("Entered :-" + menuname);
	        driver.findElement(By.xpath("//input[@id='MenuName']")).sendKeys(menuname, Keys.ENTER);
	        
			 testLogger.info("🔰 Enter menuname: <span style='color:green; font-weight:bold;'>" + menuname + "</span>");

	    } catch (Exception e) {
	        throw new AssertionError("❌ Failed to enter menu name: " + e.getMessage());
	    }

	    // Validate Menu_Type
	    if (Menu_Type == null || Menu_Type.trim().isEmpty()) {
	        throw new AssertionError("❌ Menu type is missing. Please provide a valid name in the Excel sheet.");
	    }

	    try {
	        WebElement mntype = driver.findElement(By.id("MenuTypeId"));
	        Select sl1 = new Select(mntype);
	        sl1.selectByVisibleText(Menu_Type);
	        
			 testLogger.info("🔰 Enter Menu_Type: <span style='color:green; font-weight:bold;'>" + Menu_Type + "</span>");

	    } catch (Exception e) {
	        throw new AssertionError("❌ Failed to select menu type: " + e.getMessage());
	    }

	    // Validate MenuCategory
	    if (MenuCategory == null || MenuCategory.trim().isEmpty()) {
	        throw new AssertionError("❌ Menu category is missing. Please provide a valid name in the Excel sheet.");
	    }

	    try {
	        WebElement mncatid = driver.findElement(By.id("MenuCategoryId"));
	        Select sl2 = new Select(mncatid);
	        sl2.selectByVisibleText(MenuCategory);
	        
			 testLogger.info("🔰 Enter MenuCategory: <span style='color:green; font-weight:bold;'>" + MenuCategory + "</span>");

	    } catch (Exception e) {
	        throw new AssertionError("❌ Failed to select menu category: " + e.getMessage());
	    }

	    try {
	        // Handle cyclic / non-cyclic menu durations
	        if (MenuCategory.equalsIgnoreCase("Cyclic")) {
	            driver.findElement(By.id("NoOfWeekPerCycle")).sendKeys("2", Keys.ENTER);
	        } else {
	            driver.findElement(
	                By.xpath("//label[contains(text(),'# of Days')]/following-sibling::input[@id='txtNoOfDays']")
	            ).sendKeys("7", Keys.ENTER);
	        }

	        // Click Add button
	        driver.findElement(By.xpath("//button[text()='Add']")).click();

	        System.out.println("✅ Menu added successfully");
	    } catch (Exception e) {
	        throw new AssertionError("❌ Failed to complete menu creation: " + e.getMessage());
	    }
	}

	// click on edit

	/*
	 * public static void addCustomer(String NoOfCustomer) throws Exception {
	 * 
	 * 
	 * driver.findElement(By.xpath("//button[@id='editCustomerRow_11']")).click();
	 * 
	 * // clickon add data driver.findElement(By.xpath(
	 * "//tr[@id='tr_W1_Cycle1']//input[@id='editableValue7']")).sendKeys(
	 * NoOfCustomer);
	 * 
	 * // click on save
	 * driver.findElement(By.xpath("//button[@id='saveCustomerRow_11']")).click();
	 * 
	 * System.out.println(" Customer added");
	 * 
	 * Thread.sleep(3000); }
	 */
	
	
	
	
	
	
	 public static void addCustomer(String NoOfCustomer) {
		    try {
		        // Click edit button for the customer row
		        driver.findElement(By.xpath("//button[@id='editCustomerRow_11']")).click();
		        System.out.println("✅ Clicked edit button for customer row.");

		        String noOfCustomerInt = NoOfCustomer.split("\\.")[0].trim();

		        // XPath targeting input fields inside day-value divs (Monday to Sunday)
		        String xpath = "//div[contains(@class,'day-value')]//input";

		        List<WebElement> inputs = driver.findElements(By.xpath(xpath));
		        if (inputs.isEmpty()) {
		            System.out.println("⚠️ No input fields available (no days visible). Skipping input and save.");
		            return;
		        }

		        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		        boolean atLeastOneInputSuccess = false;

		        for (WebElement input : inputs) {
		            try {
		                if (!input.isDisplayed() || !input.isEnabled()) {
		                    System.out.println("⚠️ Skipping input (not visible or disabled).");
		                    continue;
		                }
		                String readOnly = input.getAttribute("readonly");
		                if (readOnly != null && (readOnly.equalsIgnoreCase("true") || readOnly.equalsIgnoreCase("readonly"))) {
		                    System.out.println("⚠️ Skipping readonly input field.");
		                    continue;
		                }

		                wait.until(ExpectedConditions.elementToBeClickable(input));
		                input.clear();

		                // Use JavaScript to safely set value
		                ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].value = arguments[1];", input, noOfCustomerInt);

		                input.sendKeys(Keys.TAB); // Trigger events

		                String enteredValue = input.getAttribute("value");
		                if (enteredValue.equals(noOfCustomerInt)) {
		                    System.out.println("✅ Value entered successfully for an available day.");
		                    atLeastOneInputSuccess = true;
		                } else {
		                    System.out.printf("❌ Value mismatch. Expected: %s, Found: %s%n", noOfCustomerInt, enteredValue);
		                }

		            } catch (TimeoutException e) {
		                System.out.println("⚠️ Input not clickable, skipping.");
		            } catch (Exception e) {
		                System.out.println("❌ Error with input: " + e.getMessage());
		            }
		        }

		        // Only click save if at least one input was filled successfully
		        if (atLeastOneInputSuccess) {
		            WebElement saveBtnIcon = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@id='saveCustomerRow_11']/child::i")));
		            WebElement saveBtn = saveBtnIcon.findElement(By.xpath("./..")); // Parent button

		            // Hover over the button to make sure it's visible and clickable
		            new Actions(driver).moveToElement(saveBtn).perform();

		            wait.until(ExpectedConditions.elementToBeClickable(saveBtn));
		            saveBtn.click();
		            System.out.println("✅ Clicked save button for customer row.");
		        } else {
		            System.out.println("⚠️ No input fields updated, skipping save button click.");
		        }

		    } catch (Exception e) {
		        System.out.println("❌ Error in addCustomer method: " + e.getMessage());
		        throw new RuntimeException(e);
		    }
		}


//		
//		//click onmore action
//		
//		driver.findElement(By.id("btnMoreAction")).click();
//		
//		//click on delete on button
//		
//		driver.findElement(By.xpath("(//i[@class='fas fa-trash-alt'])[1]")).click();
//		
//		driver.findElement(By.xpath("//button[text()='OK']")).click();
//		
//		System.out.println("menu deleted");

//click on meal period tab
	public static void addMealPeriods(String addMeal) throws Exception {
	    driver.findElement(By.id("tabThreeTab")).click();
	    if (addMeal == null || addMeal.trim().isEmpty()) {
	        throw new AssertionError("❌ Meal is missing. Please provide a valid name in the Excel sheet.");
	    }

	    try {

	    String[] mealArray = addMeal.split(",");
	    for (String meal : mealArray) {
	        driver.findElement(By.xpath("//span[text()='Add Meal Period']")).click();
	        driver.findElement(By.xpath("(//input[@type='search'])[5]")).sendKeys(meal.trim(), Keys.ENTER);
	        driver.findElement(By.id("addMealPeriod")).click();

	        Thread.sleep(2000); // Optional wait between adds
	    }

	    String msg = capturePopupMessageText();
	    System.out.println("Popup after adding meal period(s): " + msg);

	    Assert.assertNotNull(msg, "Meal period popup not found.");
	    Assert.assertTrue(msg.toLowerCase().contains("added") || msg.toLowerCase().contains("success"),
	            "Unexpected meal period popup: " + msg);

	    Thread.sleep(3000);
	    
	    } catch (Exception e) {
	        throw new AssertionError("❌ Failed to complete menu creation: " + e.getMessage());
	    }
	
	}


	// click on section tab
	public static void selectMealPeriod() throws Exception {
		driver.findElement(By.id("tabFourTab")).click();

		driver.findElement(By.xpath("//span[text()='Select Meal Period']")).click();
		driver.findElement(By.xpath("(//input[@type='search'])[5]")).sendKeys("Breakfast", Keys.ENTER);

		driver.findElement(By.xpath("//span[text()='Select Section']")).click();
		driver.findElement(By.xpath("(//input[@type='search'])[5]")).sendKeys("Bakery", Keys.ENTER);
		driver.findElement(By.id("MealPeriodSectionAddId")).click();

		driver.findElement(By.xpath("//span[@id='select2-ddlMealPeriodForSec-container']")).click();
		driver.findElement(By.xpath("(//input[@type='search'])[5]")).sendKeys("Crib", Keys.ENTER);

		driver.findElement(By.xpath("//span[text()='Select Section']")).click();
		driver.findElement(By.xpath("(//input[@type='search'])[5]")).sendKeys("Carvery", Keys.ENTER);
		driver.findElement(By.id("MealPeriodSectionAddId")).click();

		String msg = capturePopupMessageText();
		System.out.println("Popup after selecting meal period: " + msg);

		Assert.assertNotNull(msg, "Meal period section popup not found.");
		Assert.assertTrue(msg.toLowerCase().contains("added") || msg.toLowerCase().contains("success"),
				"Unexpected meal section popup: " + msg);

		Thread.sleep(3000);
	}


	// click on menu Recipe
	/*
	 * public static void addRecipeOnMenu(String Recipename) {
	 * driver.findElement(By.id("tabFiveTab")).click(); try { // for breakfast
	 * 
	 * driver.findElement(By.xpath("(//i[@class='fa fa-chevron-down'])[1]")).click()
	 * ;
	 * 
	 * // click on plus icon
	 * 
	 * driver.findElement(By.xpath("(//i[@class='fa fa-plus'])[1]")).click();
	 * 
	 * // recipe creach an add activiy WebElement srchrcptxt =
	 * driver.findElement(By.id("SearchText")); srchrcptxt.clear();
	 * 
	 * srchrcptxt.sendKeys(Recipename, Keys.ENTER);
	 * 
	 * driver.findElement(By.xpath("(//button[text()='Add'])[4]")).click();
	 * 
	 * driver.findElement(By.id("btnAddRecipe")).click(); } catch (Exception e) {
	 * System.out.println(e.getMessage()); }
	 * System.out.println("Recipe added on menu"); String msg =
	 * capturePopupMessageText(); System.out.println("Popup after adding recipe: " +
	 * msg);
	 * 
	 * Assert.assertNotNull(msg, "Customer popup not found.");
	 * Assert.assertTrue(msg.toLowerCase().contains("menu") ||
	 * msg.toLowerCase().contains("success"), "Unexpected customer popup: " + msg);
	 * 
	 * }
	 */
	
	
	
	public static void addRecipeOnMenu(String recipeName) {
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
	    JavascriptExecutor js = (JavascriptExecutor) driver;

	    // Step 1: Go to "Menu Recipes" tab
	    driver.findElement(By.id("tabFiveTab")).click();
	    System.out.println("✅ Switched to Menu Recipe tab.");

	    // Step 2: Expand first dropdown (e.g., Breakfast)
	    WebElement dropdownIcon = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//i[@class='fa fa-chevron-down'])[1]")));
	    dropdownIcon.click();
	    System.out.println("✅ Expanded the first meal section.");

	    // Step 3: Get the plus icons fresh
	    List<WebElement> plusIcons = driver.findElements(By.xpath("//p[@type='button']"));
	    System.out.println("🔍 Found " + plusIcons.size() + " plus icons.");

	    int recipeCount = 0;

	    for (int i = 0; i < plusIcons.size(); i++) {
	        try {
	            // Refresh the plus icons list to avoid stale elements
	            plusIcons = driver.findElements(By.xpath("//p[@type='button']"));
	            WebElement plusIcon = plusIcons.get(i);

	            if (plusIcon.isDisplayed()) {
	                // Scroll the plus icon into view before clicking
	                js.executeScript("arguments[0].scrollIntoView({block: 'center'});", plusIcon);
	                Thread.sleep(500); // small delay to allow scroll animation to complete

	                wait.until(ExpectedConditions.elementToBeClickable(plusIcon));
	                plusIcon.click();
	                System.out.println("✅ Clicked plus icon at index: " + i);

	                // Search the recipe
	                WebElement searchInput = wait.until(ExpectedConditions.visibilityOfElementLocated(
	                    By.xpath("//button[@id='btnKeywordSearch']/parent::div/child::input[2]")));
	                searchInput.clear();
	                searchInput.sendKeys(recipeName, Keys.ENTER);
	                System.out.println("🔍 Searching for recipe: " + recipeName);

	                // Add the recipe
	                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'" + recipeName + "')]")));
	                wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='action-buttons']//button[text()='Add']"))).click();
	                System.out.println("➕ Clicked 'Add' button for recipe.");

	                wait.until(ExpectedConditions.elementToBeClickable(By.id("btnAddRecipe"))).click();
	                System.out.println("✅ Recipe added successfully at index: " + i);

	                recipeCount++;

	                // After adding, scroll back up a bit to prepare for the next icon
	                js.executeScript("window.scrollBy(0, -150);");
	                Thread.sleep(1000); // Let UI settle

	            }

	        } catch (StaleElementReferenceException stale) {
	            System.out.println("⚠️ Stale element at index " + i + " — skipped.");
	        } catch (TimeoutException timeout) {
	            System.out.println("⚠️ Timeout waiting for element at index " + i + " — skipped.");
	        } catch (Exception e) {
	            System.out.println("❌ Error at index " + i + ": " + e.getMessage());
	        }
	    }

	    System.out.println("✅ Done. Recipe added on " + recipeCount + " menu sections.");
	}


	public static void clickPublishMenu() throws Exception {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		// Click "More Actions"
		wait.until(ExpectedConditions.elementToBeClickable(By.id("btnMoreAction"))).click();

		// Click 3rd item in dropdown
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='dropdown-menu show']//ul//li[3]")))
				.click();

		// Wait and click "Draft Publish"
		wait.until(ExpectedConditions.elementToBeClickable(By.id("btnDraftPublish"))).click();

		System.out.println("Clicked publish menu");
		
		ExtentTest testLogger = myListener3.getTest();
		  String msg = capturePopupMessageText();
	        System.out.println("Popup after adding recipe: " + msg);
	        testLogger.info(msg);
	        
	        Thread.sleep(1000);
	        
	        
	        
		
		

	}

	public static void addSiteGoRecipe(String siteName) throws Exception {
		// Enter site name in the search field and press Enter
		driver.findElement(By.id("txtSearchOrgUnit")).sendKeys(siteName, Keys.ENTER);

		// Build dynamic XPath for the radio button based on the site name
		String xpath = "//div[@class='list-field']//label[text()='" + siteName
				+ "']/preceding-sibling::input[@type='radio']";

		// Locate the radio button
		WebElement radioButton = driver.findElement(By.xpath(xpath));

		// Click the radio button if it's not already selected
		if (!radioButton.isSelected()) {
			radioButton.click();

		}
		System.out.println("site selected");
		Thread.sleep(3000);
	}

	public static void selectMenuRecipe() {
		driver.findElement(By.id("spnCountRecipes")).click();
	}

	public static void clickMenus() {
		driver.findElement(By.xpath("(//p[contains(text(),'Menus')] /ancestor::a[1])[1]")).click();
	}

	public static void clickProductionMenus() {
		driver.findElement(By.xpath("//p[contains(text(),'Production Menus')] /ancestor::a[1]")).click();
	}

	public static void clickTemplateMenus() {
		driver.findElement(By.xpath("//p[contains(text(),'Template Menus')] /ancestor::a[1]")).click();
	}
}
