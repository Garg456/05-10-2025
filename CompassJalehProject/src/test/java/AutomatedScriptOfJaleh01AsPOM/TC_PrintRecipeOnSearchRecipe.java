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
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
@Listeners(myListener3.class)
public class TC_PrintRecipeOnSearchRecipe extends BaseTest2 {

	
	
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
			
//String adminRole,String priceOption
		}

		return data;
	}
	@Test(dataProvider = "recipeDataProvider")
	public void Test_TemplateMenu(String username, String password, String sitename,String productname,String adminRole,String priceOption) throws Throwable {
		// setup();
		loginToApplication(username, password);
		FeatureWithorWithoutPrice(sitename,productname,adminRole,priceOption);
		// tearDown();
		// recipedisplayname,mealtype,cuisine,recipecategory
	}
	
	
	public static void FeatureWithorWithoutPrice(String sitename,String productname ,String adminRole,String priceOption) throws Exception {
		
		clickHome();
		addSiteGoRecipe(sitename);
		selectMenuRecipe();
		
		clickHome();
		clickRecipeMain();
		clickSearchRecipe();
		clicksearchedLink(productname);
		
		clickPrintsecrhRecipe(adminRole,priceOption);
		
}
	
	
	public static void clicksearchedLink(String productname) throws Exception {
		
		 WebElement searchProduct = driver.findElement(By.id("SearchText"));
		    searchProduct.clear();
		    searchProduct.sendKeys(productname, Keys.ENTER);

		    // Use WebDriverWait instead of Thread.sleep (wait up to 10 seconds)
		    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		    
		    // Wait until the link with product name appears
		    WebElement searchProductLink = wait.until(ExpectedConditions.visibilityOfElementLocated(
		        By.xpath("//a[contains(text(),'" + productname + "')]")
		    ));

		    // Click on the product link
		    searchProductLink.click();
		

		
	}
	
	/*
	 * public static void validationPriceOrWithoutPriceOnRecipe() {
	 * 
	 * // Click on the "Print" button
	 * driver.findElement(By.xpath("//button[text()='Print']")).click();
	 * 
	 * // Create a WebDriverWait to wait for the new buttons to appear WebDriverWait
	 * wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	 * 
	 * // Wait for both buttons to be visible WebElement withPriceBtn =
	 * wait.until(ExpectedConditions.visibilityOfElementLocated(
	 * By.xpath("//button[contains(text(),'With Price')]") ));
	 * 
	 * WebElement withoutPriceBtn =
	 * wait.until(ExpectedConditions.visibilityOfElementLocated(
	 * By.xpath("//button[contains(text(),'Without Price')]") ));
	 * 
	 * // Validate both buttons are displayed if (withPriceBtn.isDisplayed() &&
	 * withoutPriceBtn.isDisplayed()) { System.out.
	 * println("✅ Both 'With Price' and 'Without Price' buttons are visible."); }
	 * else { System.out.println("❌ One or both buttons are not visible."); } }
	 */
	
public static void clickPrintsecrhRecipe(String adminRole, String priceOption) throws Exception {

	    

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

	    if (adminRole.equalsIgnoreCase("Jaleh Admin") || adminRole.equalsIgnoreCase("Sector Admin")) {

	        driver.findElement(By.xpath("//button[text()='Print']")).click();
	        System.out.println("✅ Clicked on report button");

	        // Wait for buttons to be visible
	        WebElement withPriceBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(
	            By.xpath("//button[contains(text(),'With Price')]")
	        ));
	        WebElement withoutPriceBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(
	            By.xpath("//button[contains(text(),'Without Price')]")
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
	        WebElement downloadBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Print']")));
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
	
}
