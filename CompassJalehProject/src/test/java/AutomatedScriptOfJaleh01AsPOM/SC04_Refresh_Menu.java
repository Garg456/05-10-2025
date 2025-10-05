package AutomatedScriptOfJaleh01AsPOM;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;

public class SC04_Refresh_Menu extends BaseTest2 {

	public static String convertAppDateToHtmlDate(String appDate) throws ParseException {
		String[] possibleFormats = { "dd-MM-yyyy", "M/d/yy", "MM/dd/yy" };
		ParseException lastException = null;

		for (String format : possibleFormats) {
			try {
				SimpleDateFormat appFormat = new SimpleDateFormat(format);
				appFormat.setLenient(false);
				Date date = appFormat.parse(appDate);
				SimpleDateFormat htmlFormat = new SimpleDateFormat("yyyy-MM-dd");
				return htmlFormat.format(date);
			} catch (ParseException e) {
				lastException = e;
			}
		}
		throw lastException;
	}

	@DataProvider(name = "recipeDataProvider")
	public Object[][] recipeDataProvider() throws IOException {
		List<Map<String, String>> dataList = readData("E:\\Automation Testing data\\Jaleh\\test1_creation_Master.xlsx",
				"SC_RefreshMenu");
		Object[][] data = new Object[dataList.size()][14];

		for (int i = 0; i < dataList.size(); i++) {
			Map<String, String> row = dataList.get(i);
			data[i][0] = row.get("username");
			data[i][1] = row.get("password");
			data[i][2] = row.get("recipename");
			data[i][3] = row.get("menuname");
			data[i][4] = row.get("sitename");
			data[i][5] = row.get("Menu_Type");
			data[i][6] = row.get("MenuCategory");
			data[i][7] = row.get("EffectiveStartDate").trim();
			data[i][8] = row.get("EffectiveEndDate").trim();
			data[i][9] = row.get("sitename2");
			data[i][10] = row.get("priceOption");
			data[i][11] = row.get("NoOfCustomer");
			data[i][12] = row.get("ReportStartDate");
			data[i][13] = row.get("ReportEndDate");
			
			
			
			
			
			//NoOfCustomer ,String ReportStartDate,String ReportEndDate
		}

		return data;
	}

	@Test(dataProvider = "recipeDataProvider")
	public void Test_ProductionMenu(String username, String password, String recipeName, String menuname,
			String sitename, String Menu_Type, String MenuCategory, String EffectiveStartDate, String EffectiveEndDate,String sitename2,String priceOption,String NoOfCustomer,String ReportStartDate,String ReportEndDate)
			throws Throwable {
		loginToApplication(username, password);
		ProductionMenu(menuname, sitename, recipeName, Menu_Type, MenuCategory, EffectiveStartDate, EffectiveEndDate,sitename2,priceOption,NoOfCustomer,ReportStartDate,ReportEndDate);
	}

	public static void ProductionMenu(String menuname, String sitename, String Recipename, String Menu_Type,
			String MenuCategory, String EffectiveStartDate, String EffectiveEndDate,String sitename2,String priceOption,String NoOfCustomer,String ReportStartDate,String ReportEndDate) throws Exception {
		clickHome();
		addSiteGoRecipe(sitename);
		selectMenuRecipe();

//		clickHome();
//		clickMenus();
//		clickProductionMenus();
//		SearchRecipeBeforeCreation srbc = new SearchRecipeBeforeCreation();
//		srbc.searchProduct(menuname);

		clickHome();
		navigateToMenus();
		addmenu(menuname, Menu_Type, MenuCategory, EffectiveStartDate, EffectiveEndDate);
		addCustomer(NoOfCustomer);
		//savecutomers();
		addMealPeriod();
		selectMealPeriod();
		addRecipeOnMenu(Recipename);
		clickPublishMenu();
		/*
		 * ExtentTest testLogger = myListener3.getTest();
		 * 
		 * testLogger .info("🔰 Menu : <span style='color:green; font-weight:bold;'>" +
		 * menuname + "</span>" + " created as " +
		 * "🔰 created as : <span style='color:green; font-weight:bold;'>" +
		 * MenuCategory + "</span>");
		 */
//		clickHome();
//		clickMenus();
//		clickProductionMenus();
//		SearchRecipeAfterCreation srac = new SearchRecipeAfterCreation();
//
//		srac.searchProduct(menuname);
		
		clickHome();
		addSiteGoRecipe(sitename2);
		selectMenuRecipe();
		
		clickHome();
		clickRecipeMain();
		clickSearchRecipe();
		clicksearchedLink(Recipename);
         editRecipeonsite();
         publishRecipeonsite();
         
         clickHome();
 		addSiteGoRecipe(sitename);
 		selectMenuRecipe();
 		clickHome();
 		clickMenus();
 		clickProductionMenus();
 		clicksearchedLink(menuname);
 		
 		clickOnMenuRecipeTab();
 		//downloading menu Report
 		clickONMenuReport(priceOption, ReportStartDate,ReportEndDate);
 		
 		
 		
 		
 		
 		
 		
         
         
		
		
		
		
		
		
		

	}
	
	public static void clickONMenuReport(String priceOption,String ReportStartDate,String ReportEndDate ) throws Exception {

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

	    driver.findElement(By.id("tabFiveTab")).click();
	    System.out.println("Clicked on Menu Recipe tab");

	    driver.findElement(By.xpath("//li[@id='menuRptbtn']")).click();
	    System.out.println("Clicked on Report button");
	    Thread.sleep(3000);

	    setDateById("Rpt_RecpIng_Menu_startDate", ReportStartDate);
	    System.out.println("Start date set");

	    Thread.sleep(2000);

	    setDateById("Rpt_RecpIng_Menu_endDate", ReportEndDate);
	    System.out.println("End date set");

//	    // Handle price option
//	    if (priceOption.equalsIgnoreCase("With Price")) {
//	        WebElement withPriceBtn = wait.until(ExpectedConditions.elementToBeClickable(
//	            By.xpath("//button[contains(text(),'With Price')]")));
//	        withPriceBtn.click();
//	        System.out.println("Clicked 'With Price' button");
//	    } else if (priceOption.equalsIgnoreCase("Without Price")) {
//	        WebElement withoutPriceBtn = wait.until(ExpectedConditions.elementToBeClickable(
//	            By.xpath("//button[contains(text(),'Without Price')]")));
//	        withoutPriceBtn.click();
//	        System.out.println("Clicked 'Without Price' button");
//	    } else {
	        // Fallback: default "Download" button (for roles like Site Admin)
	        driver.findElement(By.xpath("//button[text()='Download']")).click();
	        System.out.println("Clicked default Download button (no price option)");
	    

	    Thread.sleep(5000); // Wait for download prompt to appear

	    try {
	        Runtime.getRuntime().exec("C:\\Users\\pradeep.garg\\Downloads\\clickKeep.exe");
	        System.out.println("Triggered AutoIt script to click 'Keep'");
	    } catch (IOException e) {
	        System.out.println("⚠️ AutoIt script failed: " + e.getMessage());
	    }

	    Thread.sleep(10000); // Wait for download to complete
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*
	 * public static void clickONMenuReport() throws Exception {
	 * 
	 * driver.findElement(By.xpath("//ul//li//button[@data-bs-target='#menuReport']"
	 * )).click(); System.out.println("click ON Menu Report"); Thread.sleep(3000);
	 * 
	 * setDateById("Rpt_RecpIng_Menu_startDate","1-09-2025");
	 * System.out.println("print start date"); Thread.sleep(3000);
	 * setDateById("Rpt_RecpIng_Menu_endDate","12-09-2025");
	 * System.out.println("print end date");
	 * 
	 * Thread.sleep(3000);
	 * 
	 * WebElement linkreport=
	 * driver.findElement(By.xpath("//a[contains(text(),' Show Multi Menu Report')]"
	 * ));
	 * 
	 * 
	 * 
	 * System.out.println(linkreport.getAttribute("title"));
	 * 
	 * 
	 * 
	 * }
	 */
	
	
	
	
	
	

	public static void navigateToMenus() {
		driver.findElement(By.xpath("(//i[@class='nav-icon fas fa-table'])[1]")).click();
		driver.findElement(By.xpath("//i[@class='nav-icon fas fa-utensils']")).click();
	}

	public static void addmenu(String menuname, String Menu_Type, String MenuCategory, String EffectiveStartDate,
			String EffectiveEndDate) throws Exception {
		driver.findElement(By.xpath("//button[text()='Add Menu']")).click();
		driver.findElement(By.xpath("//input[@id='MenuName']")).sendKeys(menuname, Keys.ENTER);

		new Select(driver.findElement(By.id("MenuTypeId"))).selectByVisibleText(Menu_Type);
		new Select(driver.findElement(By.id("MenuCategoryId"))).selectByVisibleText(MenuCategory);

		setDateById("EffectiveStartDate", EffectiveStartDate);
		setDateById("EffectiveEndDate", EffectiveEndDate);

		if (MenuCategory.equals("Cyclic")) {

			WebElement weekCycle = driver.findElement(By.id("NoOfWeekPerCycle"));
			weekCycle.clear();
			weekCycle.sendKeys("2", Keys.ENTER);
		} else {

			WebElement NoOfDays = driver.findElement(
					By.xpath("//label[contains(text(),'Days')]/following-sibling::input[@id='txtNoOfDays']"));
			NoOfDays.clear();
			NoOfDays.sendKeys("12");
		}

		driver.findElement(By.xpath("//label[text()='Per Customer Target']/following-sibling::input")).sendKeys("12",
				Keys.ENTER);

		driver.findElement(By.xpath("//button[text()='Add']")).click();

		System.out.println("menu added successfully");
	}
	 public static void editRecipeonsite() {
	        try {
	            driver.findElement(By.xpath("//button[normalize-space(text())='Edit Source Recipe']")).click();
	            driver.findElement(By.xpath("//button[text()='OK']")).click();
	            Thread.sleep(5000);
	            System.out.println("Recipe edited on site");
	        } catch (Exception e) {
	            System.out.println("Error in editRecipeonsite: " + e.getMessage());
	        }
	    }
	 public static void publishRecipeonsite() {
	        try {
	            driver.findElement(By.xpath("//button[contains(text(),'Publish Changes to Source')]")).click();
	            driver.findElement(By.xpath("//button[text()='OK']")).click();
	            Thread.sleep(5000);
	            System.out.println("Recipe published on site");
	        } catch (Exception e) {
	            System.out.println("Error in publishRecipeonsite: " + e.getMessage());
	        }
	    }
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


	 
	 
	 
	
	 public static void clickOnSearchRecipe(String Recipename) {
	        try {
	            WebElement searchBox = driver.findElement(By.id("SearchText"));
	            searchBox.clear();
	            searchBox.sendKeys(Recipename, Keys.ENTER);
	            System.out.println("Recipe searched: " + Recipename);
	        } catch (Exception e) {
	            System.out.println("Error in clickOnSearchRecipe: " + e.getMessage());
	        }
	    }
	public static void addMealPeriod() throws Exception {
		driver.findElement(By.id("tabThreeTab")).click();

		driver.findElement(By.xpath("//span[text()='Add Meal Period']")).click();
		driver.findElement(By.xpath("(//input[@type='search'])[5]")).sendKeys("Breakfast", Keys.ENTER);
		driver.findElement(By.id("addMealPeriod")).click();

		Thread.sleep(2000);

		driver.findElement(By.xpath("//span[text()='Add Meal Period']")).click();
		driver.findElement(By.xpath("(//input[@type='search'])[5]")).sendKeys("Crib", Keys.ENTER);
		driver.findElement(By.id("addMealPeriod")).click();

		String msg = capturePopupMessageText();
		System.out.println("Popup after adding meal period: " + msg);

		Assert.assertNotNull(msg, "Meal period popup not found.");
		Assert.assertTrue(msg.toLowerCase().contains("added") || msg.toLowerCase().contains("success"),
				"Unexpected meal period popup: " + msg);

		Thread.sleep(3000);
	}

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

	public static void clickOnMenuRecipeTab() {
		
		driver.findElement(By.xpath("//a[contains(text(), 'Menu Recipes')]")).click();
		
	}
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

			
			
		


//	public static void addRecipeOnMenu(String Recipename) {
//	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//
//	    try {
//	        // Click on the tab to open menu section
//	        driver.findElement(By.id("tabFiveTab")).click();
//
//	        // Expand the breakfast section dropdown (first chevron icon)
//	        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//i[@class='fa fa-chevron-down'])[1]"))).click();
//
//	        // Get list of all plus icons for adding recipes
//	        List<WebElement> plusIcons = driver.findElements(By.xpath("//i[@class='fa fa-plus']"));
//	        System.out.println("Number of plus icons found: " + plusIcons.size());
//
//	        for (int i = 0; i < plusIcons.size(); i++) {
//	            // Refresh plus icons list each iteration to avoid stale element exceptions
//	            plusIcons = driver.findElements(By.xpath("//i[@class='fa fa-plus']"));
//
//	            // Click the current plus icon to add a recipe
//	            wait.until(ExpectedConditions.elementToBeClickable(plusIcons.get(i))).click();
//
//	            // Search and enter recipe name
//	            WebElement searchInput = wait.until(ExpectedConditions.elementToBeClickable(By.id("SearchText")));
//	            searchInput.clear();
//	            searchInput.sendKeys(Recipename);
//	            searchInput.sendKeys(Keys.ENTER);
//
//	            // Wait for the Add button, scroll into view, and click
//	            WebElement addBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='action-buttons']//button[text()='Add']")));
//	            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", addBtn);
//	            
//	            // Small pause to ensure UI ready, can be removed if stable
//	            Thread.sleep(1000);
//
//	            addBtn.click();
//
//	            // Click the final Add Recipe button
//	            wait.until(ExpectedConditions.elementToBeClickable(By.id("btnAddRecipe"))).click();
//
//	            // Optional: Add wait here for any popup or UI update if needed
//	        }
//
//	        System.out.println("Recipe added on menu");
//
//	        // Capture and verify the popup message after adding recipe
//	        String msg = capturePopupMessageText();
//	        System.out.println("Popup after adding recipe: " + msg);
//
//	        Assert.assertNotNull(msg, "Customer popup not found.");
//	        Assert.assertTrue(msg.toLowerCase().contains("menu") || msg.toLowerCase().contains("success"),
//	                "Unexpected customer popup: " + msg);
//
//	    } catch (Exception e) {
//	        System.out.println("Error in addRecipeOnMenu: " + e.getMessage());
//	        e.printStackTrace();
//	        Assert.fail("Failed to add recipe on menu due to exception.");
//	    }
//	}


	public static void addSiteGoRecipe(String siteName) throws Exception {
		driver.findElement(By.id("txtSearchOrgUnit")).sendKeys(siteName, Keys.ENTER);

		String xpath = "//div[@class='list-field']//label[text()='" + siteName
				+ "']/preceding-sibling::input[@type='radio']";

		WebElement radioButton = driver.findElement(By.xpath(xpath));

		if (!radioButton.isSelected()) {
			radioButton.click();
		}
		System.out.println("site selected");

		Thread.sleep(3000);
	}

	public static void selectMenuRecipe() {
		driver.findElement(By.id("spnCountRecipes")).click();
	}

	public static void setDateById(String fieldId, String dateValue) {
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			String htmlDate = convertAppDateToHtmlDate(dateValue);
			System.out.println(fieldId + " (converted): " + htmlDate);

			String script = "var dateField = document.getElementById('" + fieldId + "');" + "dateField.value = '"
					+ htmlDate + "';" + "dateField.dispatchEvent(new Event('change', { bubbles: true }));";

			js.executeScript(script);
		} catch (Exception e) {
			System.out.println("Error setting date for " + fieldId + ": " + e.getMessage());
		}
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
		
		/*
		 * ExtentTest testLogger = myListener3.getTest(); String msg =
		 * capturePopupMessageText(); System.out.println("Popup after adding recipe: " +
		 * msg); testLogger.info(msg);
		 */
	        
	        Thread.sleep(3000);
	        
	        
	        
		
		

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
