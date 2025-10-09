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

public class TC_ReportsProductionMenu extends BaseTest2 {

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
				"TC_Create Productio Menu Report");
		Object[][] data = new Object[dataList.size()][10];

		for (int i = 0; i < dataList.size(); i++) {
			Map<String, String> row = dataList.get(i);
			data[i][0] = row.get("username");
			data[i][1] = row.get("password");
			data[i][2] = row.get("sitename");
			data[i][3] = row.get("productname");
			data[i][4] = row.get("adminrole");
			data[i][5] = row.get("priceOption");
			data[i][6] = row.get("Report_Type");
			data[i][7] = row.get("menu_Type");
			data[i][8] = row.get("ReportStartDate").trim();
			data[i][9] = row.get("ReportEndDate").trim();
		}

		return data;
	}

	@Test(dataProvider = "recipeDataProvider")
	public void Test_TemplateMenu(String username, String password, String sitename, String productname,
			String adminrole, String priceOption, String Report_Type, String menu_Type, String ReportStartDate,
			String ReportEndDate) throws Throwable {
		// setup();
		loginToApplication(username, password);
		if (TestContext.createdProductMenuName != null && !TestContext.createdProductMenuName.isEmpty()) {
			productname = TestContext.createdProductMenuName;
	        System.out.println("Overriding recipeName from TC01: " + productname);
	    } else {
	        System.out.println("Using recipeName from Excel: " + productname);
	    }
		menureport(sitename, productname, adminrole, priceOption, Report_Type, menu_Type, ReportStartDate,
				ReportEndDate);
		// tearDown();
		// recipedisplayname,mealtype,cuisine,recipecategory
	}

	public static void menureport(String sitename, String productname, String adminrole, String priceOption,
			String Report_Type, String menu_Type, String ReportStartDate, String ReportEndDate) throws Exception {

		clickHome();
		addSiteGoRecipe(sitename);
		selectMenuRecipe();

		clickHome();
		clickMenus();
		
			clickProductionMenus();
		
		// clickNewORUnpublishedMenus();
		clicksearchedLink(productname);
		clickMenuRecipeTab();
		// DownloadTemplateMenusReports.clickPrintExcel(priceOption);

		//DownloadTemplateMenusReports dwnldReport = new DownloadTemplateMenusReports();

		if (Report_Type.equalsIgnoreCase("print menu")) {
			clickPrintMenu(priceOption, adminrole);
		} else if (Report_Type.equalsIgnoreCase("Download Excel")) {
			clickDownloadExcel(priceOption, adminrole);
		} else if (Report_Type.equalsIgnoreCase("menu report")) {

			
				clickONProductionMenuReport(ReportStartDate, ReportEndDate);

			
		} else {
			System.out.println("Excel is empty");
		}
	}

	// clickONExcel(adminrole,priceOption);

	public static void clickMenuRecipeTab() {
		driver.findElement(By.id("tabFiveTab")).click();
		System.out.println("✅ Clicked on menu recipe tab");
	}

	public static void clicksearchedLink(String productname) throws Exception {
		WebElement searchProduct = driver.findElement(By.id("SearchText"));
		searchProduct.clear();
		searchProduct.sendKeys(productname, Keys.ENTER);

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		// Wait for the element to be clickable, refreshing the reference to handle
		// stale elements
		WebElement element = wait.until(ExpectedConditions.refreshed(
				ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'" + productname + "')]"))));

		int attempts = 0;
		while (attempts < 3) {
			try {
				driver.findElement(By.xpath("//a[contains(text(),'" + productname + "')]")).click();
				break;
			} catch (org.openqa.selenium.StaleElementReferenceException e) {
				attempts++;
				Thread.sleep(1000);
			}
		}
	}

	public static void clickONExcel(String adminRole, String priceOption) throws Exception {

		driver.findElement(By.id("tabFiveTab")).click();
		System.out.println("✅ Clicked on menu recipe tab");

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		if (adminRole.equalsIgnoreCase("Jaleh Admin") || adminRole.equalsIgnoreCase("Sector Admin")) {

			driver.findElement(By.xpath("//div[@id='btnExlPrint']")).click();
			System.out.println("✅ Clicked on report button");

			// Wait for buttons to be visible
			WebElement withPriceBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(
					By.xpath("//div[@id='btnExlPrint']//button[contains(text(),'With Price')]")));
			WebElement withoutPriceBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(
					By.xpath("//div[@id='btnExlPrint']//button[contains(text(),'Without Price')]")));

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
			WebElement downloadBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='btnExlPrint']")));
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

	public static void clickONProductionMenuReport(String ReportStartDate, String ReportEndDate) throws Exception {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		driver.findElement(By.id("tabFiveTab")).click();
		System.out.println("✅ Clicked on Menu Recipe tab");

		driver.findElement(By.xpath("//li[@id='menuRptbtn']")).click();
		System.out.println("✅ Clicked on menu report");
		Thread.sleep(3000);

		setDateById("Rpt_RecpIng_Menu_startDate", ReportStartDate);
		System.out.println("✅ Start date set");
		Thread.sleep(2000);

		setDateById("Rpt_RecpIng_Menu_endDate", ReportEndDate);
		System.out.println("✅ End date set");

		// Fallback/default: Click Download button (used when no price option needed)
		driver.findElement(By.xpath("//button[text()='Download']")).click();
		System.out.println("✅ Clicked default Download button (no price option)");

		Thread.sleep(5000); // Wait for download prompt

		try {
			Runtime.getRuntime().exec("C:\\Users\\pradeep.garg\\Downloads\\clickKeep.exe");
			System.out.println("✅ Triggered AutoIt script to click 'Keep'");
		} catch (IOException e) {
			System.out.println("⚠️ AutoIt script failed: " + e.getMessage());
		}

		Thread.sleep(10000); // Wait for download to complete
	}

	public static void clickPrintMenu(String priceOption, String adminrole) {
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

	    if (adminrole.equalsIgnoreCase("Site Admin")) {
	        driver.findElement(By.xpath("//div[@id='btnMenuPrint']")).click();
	        System.out.println("✅ Clicked on print Report");
	    } else if (adminrole.equalsIgnoreCase("Jaleh Admin") || adminrole.equalsIgnoreCase("Sector Admin")) {
	        driver.findElement(By.xpath("//div[@id='btnMenuPrint']")).click();
	        System.out.println("✅ Clicked on print Report");

	        // Wait for buttons to be visible
	        WebElement withPriceBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(
	            By.xpath("//div[@id='btnMenuPrint']//button[contains(text(),'With Price')]")
	        ));
	        WebElement withoutPriceBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(
	            By.xpath("//div[@id='btnMenuPrint']//button[contains(text(),'Without Price')]")
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
	        }
	    }
	}
	
	public static void clickDownloadExcel( String priceOption,String adminrole ) {
		 WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		 if (adminrole.equalsIgnoreCase("Site Admin")) {
		 driver.findElement(By.xpath("//div[@id='btnExlPrint']")).click();
	        System.out.println("✅ Clicked on Download Excel");
		 } else if (adminrole.equalsIgnoreCase("Jaleh Admin") || adminrole.equalsIgnoreCase("Sector Admin")) {
			 driver.findElement(By.xpath("//div[@id='btnExlPrint']")).click();
		        System.out.println("✅ Clicked on Download Excel");

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
	}}
	
}
