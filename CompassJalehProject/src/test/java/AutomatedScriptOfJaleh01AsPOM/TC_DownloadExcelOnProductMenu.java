package AutomatedScriptOfJaleh01AsPOM;

import java.io.File;
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
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TC_DownloadExcelOnProductMenu extends BaseTest2 {

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
				"TC_Create Menu Report");
		Object[][] data = new Object[dataList.size()][6];

		for (int i = 0; i < dataList.size(); i++) {
			Map<String, String> row = dataList.get(i);
			data[i][0] = row.get("username");
			data[i][1] = row.get("password");
			data[i][2] = row.get("sitename");
			data[i][3] = row.get("productname");
			data[i][4] = row.get("adminrole");
			data[i][5] = row.get("priceOption");
//			data[i][6] = row.get("MenuCategory");
//			data[i][7] = row.get("EffectiveStartDate").trim();
//			data[i][8] = row.get("EffectiveEndDate").trim();
		}

		return data;
	}
	@Test(dataProvider = "recipeDataProvider")
	public void Test_TemplateMenu(String username, String password, String sitename,String productname,String adminrole,String priceOption) throws Throwable {
		// setup();
		loginToApplication(username, password);
		menureport(sitename,productname,adminrole,priceOption);
		// tearDown();
		// recipedisplayname,mealtype,cuisine,recipecategory
	}
	
	
	public static void menureport(String sitename ,String productname,String adminrole,String priceOption) throws Exception {
		
		clickHome();
		addSiteGoRecipe(sitename);
		selectMenuRecipe();
		
		clickHome();
		clickMenus();
		clickProductionMenus();
		clicksearchedLink(productname);
		clickONExcel(adminrole,priceOption);
		
		
		
		
		
		
		
		
		
	}
	
	
	public static void clicksearchedLink(String productname) throws Exception {
	    WebElement searchProduct = driver.findElement(By.id("SearchText"));
	    searchProduct.clear();
	    searchProduct.sendKeys(productname, Keys.ENTER);

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

	    // Wait for the element to be clickable, refreshing the reference to handle stale elements
	    WebElement element = wait.until(ExpectedConditions.refreshed(
	        ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'" + productname + "')]"))
	    ));

	    int attempts = 0;
	    while(attempts < 3) {
	        try {
	            driver.findElement(By.xpath("//a[contains(text(),'" + productname + "')]")).click();
	            break;
	        } catch (org.openqa.selenium.StaleElementReferenceException e) {
	            attempts++;
	            Thread.sleep(1000);
	        }}
	}

	
	public static void clickONExcel(String adminRole, String priceOption) throws Exception {

	    driver.findElement(By.id("tabFiveTab")).click();
	    System.out.println("✅ Clicked on menu recipe tab");

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

	    if (adminRole.equalsIgnoreCase("Jaleh Admin") || adminRole.equalsIgnoreCase("Sector Admin")) {

	        driver.findElement(By.id("btnExlPrint")).click();
	        System.out.println("✅ Clicked on report button");

	        // Wait for buttons to be visible
	        WebElement withPriceBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(
	            By.xpath("//div[@id='btnExlPrint']//button[contains(text(),'With Price')]")
	        ));
	        WebElement withoutPriceBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(
	            By.xpath("//div[@id='btnExlPrint']//button[contains(text(),'Without Price')]")
	        ));

	        // Click based on priceOption
	        if (priceOption.equalsIgnoreCase("With Price")) {
	            withPriceBtn.click();
	            System.out.println("✅ Clicked 'With Price' button");
	        } else if (priceOption.equalsIgnoreCase("Without Price")) {
	            withoutPriceBtn.click();
	            System.out.println("✅ Clicked 'Without Price' button");
	        } else {
	            System.out.println("❌ Invalid price option provided. Must be 'With Price' or 'Without Price'");
	            return;
	        }

	        // Optional validation
	        if (withPriceBtn.isDisplayed() && withoutPriceBtn.isDisplayed()) {
	            System.out.println("✅ Both 'With Price' and 'Without Price' buttons are visible.");
	        }

	    } else if (adminRole.equalsIgnoreCase("Site Admin")) {

	        // Site Admin - only one download button expected
	        WebElement downloadBtn = wait.until(ExpectedConditions.elementToBeClickable(By.id("btnExlPrint")));
	        Thread.sleep(2000);
	        downloadBtn.click();
	        System.out.println("✅ Clicked download button directly for Site Admin.");

	    

	    } else {
	        System.out.println("❌ User role not recognized. No action performed.");
	        return;
	    }

	    // AutoIt script for handling browser download popup
	    try {
	        Runtime.getRuntime().exec("C:\\Users\\pradeep.garg\\Downloads\\clickKeep.exe");
	        System.out.println("✅ Triggered AutoIt script to click 'Keep'");
	    } catch (IOException e) {
	        System.out.println("❌ Failed to run AutoIt script: " + e.getMessage());
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
	public static void clickMenus() {
		driver.findElement(By.xpath("(//p[contains(text(),'Menus')] /ancestor::a[1])[1]")).click();
	}

	public static void clickProductionMenus() {
		driver.findElement(By.xpath("//p[contains(text(),'Production Menus')] /ancestor::a[1]")).click();
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



}

