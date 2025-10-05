package AutomatedScriptOfJaleh01AsPOM;

import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;

import io.github.bonigarcia.wdm.WebDriverManager;

@Listeners(myListener3.class)
public class TC003_EditTemplateMenu extends BaseTest2 {
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
		// setup();
		loginToApplication(username, password);
		TemplateMenu(menuname, sitename, recipeName, Menu_Type, MenuCategory,NoOfCustomer,addMeal);
		// tearDown();
		// recipedisplayname,mealtype,cuisine,recipecategory
	}

	public static void TemplateMenu(String menuname, String sitename, String Recipename, String Menu_Type,
			String MenuCategory,String NoOfCustomer,String addMeal) throws Exception {

		clickHome();
		addSiteGoRecipe(sitename);
		selectMenuRecipe();

		clickHome();
		clickMenus();
		clickTemplateMenus();
		

		clickHome();
		clickMenus();
		//clickTemplateMenus();

		navigateToMenus();
		addmenu(menuname, Menu_Type, MenuCategory);
		addCustomer(NoOfCustomer);
		addMealPeriods(addMeal);
		selectMealPeriod();
		addRecipeOnMenu(Recipename);
		clickPublishMenu();
		ExtentTest testLogger = myListener3.getTest();
		
		testLogger.info("🔰 Menu : <span style='color:green; font-weight:bold;'>" + menuname + "</span>" +" created as "+ "🔰 created as : <span style='color:green; font-weight:bold;'>" + MenuCategory + "</span>");
		
		
		
		
		
		
		
		
		
		
		clickHome();
		clickMenus();
		clickTemplateMenus();
		SearchRecipeAfterCreation srac = new SearchRecipeAfterCreation();

		srac.searchProduct(menuname);

	}

	public static void navigateToMenus() {
		driver.findElement(By.xpath("(//i[@class='nav-icon fas fa-table'])[1]")).click();
		driver.findElement(By.xpath("//i[@class='nav-icon fas fa-utensils']")).click();
	}

	// click on Addmenu button
	public static void addmenu(String menuname, String Menu_Type, String MenuCategory) {
		driver.findElement(By.xpath("//button[text()='Add Menu']")).click();

		System.out.println("Entered :-" + menuname);

		// enter menu name

		driver.findElement(By.xpath("//input[@id='MenuName']")).sendKeys(menuname, Keys.ENTER);

		// enter menutype

		WebElement mntype = driver.findElement(By.id("MenuTypeId"));

		Select sl1 = new Select(mntype);
		sl1.selectByVisibleText(Menu_Type);

		// enter MenuCategoryId

		WebElement mncatid = driver.findElement(By.id("MenuCategoryId"));

		Select sl2 = new Select(mncatid);
		sl2.selectByVisibleText(MenuCategory);

		if (MenuCategory.equals("Cyclic")) {

			// click of Weeks/Cycle

			driver.findElement(By.id("NoOfWeekPerCycle")).sendKeys("2", Keys.ENTER);

		} else {

			// of Days

			driver.findElement(
					By.xpath("//label[contains(text(),'# of Days')]/following-sibling::input[@id='txtNoOfDays']"))
					.sendKeys("7", Keys.ENTER);

		}

		// click on add button

		driver.findElement(By.xpath("//button[text()='Add']")).click();

		System.out.println("menu added successfully");
	}

	// click on edit

	public static void addCustomer(String NoOfCustomer) throws Exception {
		driver.findElement(By.xpath("//button[@id='editCustomerRow_11']")).click();

		// clickon add data
		driver.findElement(By.xpath("//tr[@id='tr_W1_Cycle1']//input[@id='editableValue7']")).sendKeys(NoOfCustomer);

		// click on save
		driver.findElement(By.xpath("//button[@id='saveCustomerRow_11']")).click();

		System.out.println(" Customer added");

		Thread.sleep(3000);
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
	public static void addRecipeOnMenu(String Recipename) {
		driver.findElement(By.id("tabFiveTab")).click();
		try {
			// for breakfast

			driver.findElement(By.xpath("(//i[@class='fa fa-chevron-down'])[1]")).click();

			// click on plus icon

			driver.findElement(By.xpath("(//i[@class='fa fa-plus'])[1]")).click();

			// recipe creach an add activiy
			WebElement srchrcptxt = driver.findElement(By.id("SearchText"));
			srchrcptxt.clear();

			srchrcptxt.sendKeys(Recipename, Keys.ENTER);

			driver.findElement(By.xpath("(//button[text()='Add'])[4]")).click();

			driver.findElement(By.id("btnAddRecipe")).click();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		System.out.println("Recipe added on menu");
		String msg = capturePopupMessageText();
		System.out.println("Popup after adding recipe: " + msg);

		Assert.assertNotNull(msg, "Customer popup not found.");
		Assert.assertTrue(msg.toLowerCase().contains("menu") || msg.toLowerCase().contains("success"),
				"Unexpected customer popup: " + msg);

	}

	public static void clickPublishMenu() {
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
