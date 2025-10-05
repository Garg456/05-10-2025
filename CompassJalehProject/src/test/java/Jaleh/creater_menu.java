package Jaleh;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class creater_menu {
	public static WebDriver driver;

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		String menuname = "Menu009okko7";
		String sitename2 = "Production | 180800-AFP Majura | 31-Retail 1(Real)";
		String sitename1 = "Production | 208150-South32 - Groote Eylandt | 11-Catering(Real)";
		String Recipename = "test46";

		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		String username = "admin-path22@compass-external.com.au";
		// Set the password
		String password = "Pathinfotech@01";

		String URL = "http://" + username + ":" + password + "@" + "cgrrweb01t:8086/Menu/PublishedMenuList";
		// driver.get(config.getProperty("URL"));
		driver.get(URL);
		// driver.get("http://admin-path15@compass-external.com.au:Path@0307202451:8086/Menu/PublishedMenuList");

		// http://admin-path15@compass-external.com.au:Path@0307202451:8086/Menu/PublishedMenuList
		// "http://" +username +":" +password +"@"+
		// "cgrrweb01t:8086/Menu/PublishedMenuList"
		// admin-path15@compass-external.com.au
		// Path@0307202451
		// Goodlife@123456

		// http://cgrrweb01t:8086/Recipe/List
		// http://cgrrweb01t:8086/Recipe/List
		driver.manage().window().maximize();

		// driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		driver.findElement(By.xpath("//a[text()=' Re-Login']")).click();

		// click to home button

		driver.findElement(By.xpath("//i[@class='nav-icon fas fa-home']")).click();
		addSiteGoRecipe(sitename2);

		// click on menu

		driver.findElement(By.xpath("(//i[@class='nav-icon fas fa-table'])[1]")).click();

		// click to New or Unpublished Menus button
		driver.findElement(By.xpath("//i[@class='nav-icon fas fa-utensils']")).click();

		// click on Addmenu button

		driver.findElement(By.xpath("//button[text()='Add Menu']")).click();

		// enter menu name

		driver.findElement(By.xpath("//input[@id='MenuName']")).sendKeys(menuname, Keys.ENTER);

		// enter menutype

		WebElement mntype = driver.findElement(By.id("MenuTypeId"));

		Select sl1 = new Select(mntype);
		sl1.selectByVisibleText("Production");

		// enter MenuCategoryId

		WebElement mncatid = driver.findElement(By.id("MenuCategoryId"));

		Select sl2 = new Select(mncatid);
		sl2.selectByVisibleText("Cyclic");
		
		driver.findElement(By.id("EffectiveStartDate")).click();

		// Wait for calendar popup
		/* public static void clickdate() { */
		
		//EffectiveStartDate
		    try {
		        JavascriptExecutor js = (JavascriptExecutor) driver;
		 
		        String script =
		            "var dateField = document.getElementById('EffectiveStartDate');" +
		            "dateField.value = '"+ "2025-09-15" +"';" +
		            "dateField.dispatchEvent(new Event('change', { bubbles: true }));";
		 
		        js.executeScript(script);
		    } catch(Exception e) {
		        System.out.println(e.getMessage());
		    }
		//}
		    
		    //EffectiveEndDate
		    try {
		        JavascriptExecutor js = (JavascriptExecutor) driver;
		 
		        String script =
		            "var dateField = document.getElementById('EffectiveEndDate');" +
		            "dateField.value = '"+ "2025-09-22" +"';" +
		            "dateField.dispatchEvent(new Event('change', { bubbles: true }));";
		 
		        js.executeScript(script);
		    } catch(Exception e) {
		        System.out.println(e.getMessage());
		    }
		//}




System.out.println("date submit");
		// click of Weeks/Cycle

		driver.findElement(By.id("NoOfWeekPerCycle")).sendKeys("2", Keys.ENTER);

		// click on add button

		//driver.findElement(By.xpath("//button[text()='Add']")).click();
		
		String message = capturePopupMessageText();
		System.out.println("Menu add popup: " + message);

		// click on edit
		driver.findElement(By.xpath("//button[@id='editCustomerRow_11']")).click();

		// clickon add data
		driver.findElement(By.xpath("//tr[@id='tr_W1_Cycle1']//input[@id='editableValue7']")).sendKeys("122");

		// click on save
		driver.findElement(By.xpath("//button[@id='saveCustomerRow_11']")).click();
		
		String message1 = capturePopupMessageText();
		System.out.println("Menu add popup: " + message1);

		System.out.println("data saved");

		Thread.sleep(3000);

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

		driver.findElement(By.id("tabThreeTab")).click();

		driver.findElement(By.xpath("//span[text()='Add Meal Period']")).click();
		driver.findElement(By.xpath("(//input[@type='search'])[5]")).sendKeys("Breakfast", Keys.ENTER);
		driver.findElement(By.id("addMealPeriod")).click();

		driver.findElement(By.xpath("//span[text()='Add Meal Period']")).click();
		driver.findElement(By.xpath("(//input[@type='search'])[5]")).sendKeys("Crib", Keys.ENTER);
		driver.findElement(By.id("addMealPeriod")).click();
		String message2 = capturePopupMessageText();
		System.out.println("Menu add popup: " + message2);
		// click on section tab

		driver.findElement(By.id("tabFourTab")).click();

		driver.findElement(By.xpath("//span[text()='Select Meal Period']")).click();
		driver.findElement(By.xpath("(//input[@type='search'])[5]")).sendKeys("Breakfast", Keys.ENTER);

		// click on Select Section

		driver.findElement(By.xpath("//span[text()='Select Section']")).click();
		driver.findElement(By.xpath("(//input[@type='search'])[5]")).sendKeys("Bakery", Keys.ENTER);

		driver.findElement(By.id("MealPeriodSectionAddId")).click();

		driver.findElement(By.xpath("//span[@id='select2-ddlMealPeriodForSec-container']")).click();
		driver.findElement(By.xpath("(//input[@type='search'])[5]")).sendKeys("Crib", Keys.ENTER);
		

		// click on Select Section

		driver.findElement(By.xpath("//span[text()='Select Section']")).click();
		driver.findElement(By.xpath("(//input[@type='search'])[5]")).sendKeys("Carvery", Keys.ENTER);

		driver.findElement(By.id("MealPeriodSectionAddId")).click();
		String message3 = capturePopupMessageText();
		System.out.println("Menu add popup: " + message3);

		// click on menu Recipe

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
//			     try {   
//			      //for crib  
//                    driver.findElement(By.xpath("(//i[@class='fa fa-chevron-down'])[2]")).click();
//			        
//			        // click on plus icon
//			        
//			        driver.findElement(By.xpath("(//i[@class='fa fa-plus'])[1]")).click();
//			        
//			        // recipe creach an add activiy
//			        
//			        driver.findElement(By.id("SearchText")).sendKeys("maggie743abc",Keys.ENTER);
//			        
//			        driver.findElement(By.xpath("(//button[text()='Add'])[4]")).click();
//			        
//			        driver.findElement(By.id("btnAddRecipe")).click();
//			     }catch(Exception e){System.out.println(e.getMessage());}
//			        // click on moreaction button
//			        
//			        driver.findElement(By.id("btnMoreAction")).click();
//			        driver.findElement(By.xpath("//a[text()='Publish']")).click();

		System.out.println("RECIPE ADD IN MENU");
		// search main recipe

		driver.findElement(By.xpath("(//i[@class='nav-icon fas fa-bowl-rice'])[1]")).click();

		// search recipe
		driver.findElement(By.xpath("(//i[@class='nav-icon fas fa-bowl-rice'])[2]")).click();

		WebElement searchBox = driver.findElement(By.id("SearchText"));

		searchBox.clear();
		searchBox.sendKeys(Recipename, Keys.ENTER);

		driver.findElement(By.xpath("//a[contains(text(),'" + Recipename + "')]")).click();

		driver.findElement(By.xpath("//button[text()=' Edit Source Recipe ']")).click();
		driver.findElement(By.xpath("//button[text()='OK']")).click();
		Thread.sleep(3000);
		driver.findElement(By.id("btnPublishtoBase")).click();
		driver.findElement(By.xpath("//button[text()='OK']")).click();
		Thread.sleep(3000);

		System.out.println("RECPED UPDATED from site2");

		/*
		 * //search main recipe
		 * 
		 * driver.findElement(By.xpath("(//i[@class='nav-icon fas fa-bowl-rice'])[1]")).
		 * click();
		 * 
		 * //search recipe
		 * driver.findElement(By.xpath("(//i[@class='nav-icon fas fa-bowl-rice'])[2]")).
		 * click();
		 */

		driver.findElement(By.xpath("//i[@class='nav-icon fas fa-home']")).click();
		addSiteGoRecipe(sitename1);
		System.out.println("SEARCH IN GROOT");

		searchBox = driver.findElement(By.id("SearchText"));

		searchBox.clear();
		searchBox.sendKeys(Recipename, Keys.ENTER);

		// click to refresh button

		Thread.sleep(5000);

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
		WebElement refreshBtn = wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(@class, 'refresh2')]")));
		refreshBtn.click();

		System.out.println("refreshed by sitename1");

		// next process
		// search main recipe

		// search main recipe
		driver.findElement(By.xpath("//i[@class='nav-icon fas fa-home']")).click();

		driver.findElement(By.xpath("(//i[@class='nav-icon fas fa-bowl-rice'])[1]")).click();

		// search recipe
		driver.findElement(By.xpath("(//i[@class='nav-icon fas fa-bowl-rice'])[2]")).click();
		System.out.println("search recipe page");

		searchBox = driver.findElement(By.id("SearchText"));

		searchBox.clear();
		searchBox.sendKeys(Recipename, Keys.ENTER);

		driver.findElement(By.xpath("//a[contains(text(),'" + Recipename + "')]")).click();

		driver.findElement(By.xpath("//button[text()=' Edit Source Recipe ']")).click();
		driver.findElement(By.xpath("//button[text()='OK']")).click();

		Thread.sleep(3000);
		driver.findElement(By.id("btnPublishtoBase")).click();
		driver.findElement(By.xpath("//button[text()='OK']")).click();
		Thread.sleep(3000);
		driver.findElement(By.xpath("//i[@class='nav-icon fas fa-home']")).click();
		addSiteGoRecipe(sitename2);

		/*
		 * //search main recipe
		 * 
		 * driver.findElement(By.xpath("(//i[@class='nav-icon fas fa-bowl-rice'])[1]")).
		 * click();
		 * 
		 * //search recipe
		 * driver.findElement(By.xpath("(//i[@class='nav-icon fas fa-bowl-rice'])[2]")).
		 * click();
		 */
		searchBox = driver.findElement(By.id("SearchText"));
		searchBox.clear();
		searchBox.sendKeys(Recipename, Keys.ENTER);

		driver.findElement(By.xpath("//a[contains(text(),'" + Recipename + "')]")).click();

		Thread.sleep(5000);
		driver.findElement(By.xpath("//button[normalize-space(text())='Refresh']")).click();
		System.out.println("refreshed by site2");

		driver.quit();

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
		driver.findElement(By.id("spnCountRecipes")).click();
	}
	public static String capturePopupMessageText() {
	    try {
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
	        WebElement popup = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("swal2-title")));
	        String message = popup.getText().trim();
	        System.out.println("Popup message: " + message);
	        return message;
	    } catch (Exception e) {
	        System.out.println("Popup not found or disappeared too fast.");
	        return null;
	    }
	}
}
