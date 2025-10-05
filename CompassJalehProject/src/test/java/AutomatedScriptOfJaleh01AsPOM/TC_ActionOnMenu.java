package AutomatedScriptOfJaleh01AsPOM;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;

@Listeners(myListener3.class)
public class TC_ActionOnMenu extends BaseTest2 {

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
				"TC_ActionONMenu");
		Object[][] data = new Object[dataList.size()][6];

		for (int i = 0; i < dataList.size(); i++) {
			Map<String, String> row = dataList.get(i);
			data[i][0] = row.get("username");
			data[i][1] = row.get("password");
			data[i][2] = row.get("sitename");
			data[i][3] = row.get("productname");
			data[i][4] = row.get("action");
			data[i][5] = row.get("changedName");
//			data[i][6] = row.get("MenuCategory");
//			data[i][7] = row.get("EffectiveStartDate").trim();
//			data[i][8] = row.get("EffectiveEndDate").trim();
		}

		return data;
	}

	@Test(dataProvider = "recipeDataProvider")
	public void Test_ProductionMenu(String username, String password, String sitename, String productname, String action,String changedName)
			throws Throwable {
		loginToApplication(username, password);
		ProductionMenu(sitename, productname,  action, changedName);
	}

	public static void ProductionMenu(String sitename, String productname, String action,String changedName) throws Exception {
		clickHome();
		addSiteGoRecipe(sitename);
		selectMenuRecipe();

		clickHome();
		clickMenus();
		clickNewOrUnpublishedMenu();
		
		clicksearchedLink(productname);
		//clickeditfromList();
		
		clickmoreActionbtn(action,changedName);
		
		
		
	
	}
	public static void clicksearchedLink(String productname) throws Exception {
	    // Trim the productname to remove leading/trailing spaces
	    String trimmedProductName = productname.trim();

	    WebElement searchProduct = driver.findElement(By.id("SearchText"));
	    searchProduct.clear();
	    searchProduct.sendKeys(trimmedProductName, Keys.ENTER);

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

	    // Wait for the element to be clickable, refreshing the reference to handle stale elements
	    WebElement element = wait.until(ExpectedConditions.refreshed(
	        ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'" + trimmedProductName + "')]"))
	    ));

	    int attempts = 0;
	    while(attempts < 3) {
	        try {
	            driver.findElement(By.xpath("//a[contains(text(),'" + trimmedProductName + "')]")).click();
	            break;
	        } catch (org.openqa.selenium.StaleElementReferenceException e) {
	            attempts++;
	            Thread.sleep(1000);
	        }
	    }
	}

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

	 public static void clickmoreActionbtn(String action,String changedName) {
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    // Click "More Actions"
    wait.until(ExpectedConditions.elementToBeClickable(By.id("btnMoreAction"))).click();

    ExtentTest testLogger = myListener3.getTest();
    String msg;

    switch (action.trim().toLowerCase()) {

        case "publish":
            // Click 3rd item in dropdown (Publish)
            wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//div[@class='dropdown-menu show']//ul//li[3]"))).click();

            // Click Draft Publish button
            wait.until(ExpectedConditions.elementToBeClickable(By.id("btnDraftPublish"))).click();

            System.out.println("✅ Clicked 'Publish' menu");
            msg = capturePopupMessageText();
            testLogger.info(msg);
            break;

        case "delete":
            // Click "Delete" option
            wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//a[contains(text(),'Delete')]/parent::li"))).click();

            // Confirm delete
            wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[text()='OK']"))).click();

            System.out.println("✅ Clicked 'Delete' menu");
            msg = capturePopupMessageText();
            testLogger.info(msg);
            break;

        case "copy menu":
            // Click "Copy Menu" option
            wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//a[contains(text(),'Copy Menu')]/parent::li"))).click();

            // Enter menu name
           WebElement newcopyName = wait.until(ExpectedConditions.elementToBeClickable(By.id("txtClaimMenuName")));
           newcopyName.clear();
           
           newcopyName.sendKeys(changedName, Keys.ENTER);

            // Click "Copy Menu" button
            wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(text(),'Copy Menu')]"))).click();

            System.out.println("✅ Clicked 'Copy Menu'");
            msg = capturePopupMessageText();
            testLogger.info(msg);
            break;

        case "convert":
            // Click "Convert" option
            wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//a[contains(text(),'Convert')]/parent::li"))).click();

            // Enter template name
            WebElement newcnvertName = wait.until(ExpectedConditions.elementToBeClickable(By.id("txtConvertTemplateName")));
            newcnvertName.clear();
            
            newcnvertName.sendKeys(changedName, Keys.ENTER);

            // Click Convert button
            wait.until(ExpectedConditions.elementToBeClickable(By.id("btnPToTemMenu"))).click();

            System.out.println("✅ Clicked 'Convert to Template'");
            msg = capturePopupMessageText();
            testLogger.info(msg);
            break;

        default:
            throw new IllegalArgumentException("❌ Unsupported action: " + action);
    }
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
	
	public static void clickNewOrUnpublishedMenu() {
		driver.findElement(By.xpath("//p[contains(text(),'New or Unpublished Menus')] /ancestor::a[1]")).click();
	}
	
	public static void clickeditfromList() {
		
		driver.findElement(By.xpath("//div[contains(@class,'action-buttons btn-group')]//button")).click();
		
		
		driver.findElement(By.xpath("//div[@title='Edit']")).click();
		

	}

}




