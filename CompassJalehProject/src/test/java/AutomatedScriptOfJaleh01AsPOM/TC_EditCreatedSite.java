package AutomatedScriptOfJaleh01AsPOM;

import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TC_EditCreatedSite extends BaseTest2 {
	
	

@DataProvider(name = "recipeDataProvider")
public Object[][] recipeDataProvider() throws IOException {
	List<Map<String, String>> dataList = readData("E:\\Automation Testing data\\Jaleh\\test1_creation_Master.xlsx",
			"TC_CreateSite");
	Object[][] data = new Object[dataList.size()][11];

	for (int i = 0; i < dataList.size(); i++) {
		Map<String, String> row = dataList.get(i);
		data[i][0] = row.get("username");
		data[i][1] = row.get("password");
		data[i][2] = row.get("sitename");
		data[i][3] = row.get("sitetype");
		data[i][4] = row.get("RealSite");
		data[i][5] = row.get("sector");
		data[i][6] = row.get("siteName");
		data[i][7] = row.get("adhocsitelist");
		data[i][8] = row.get("view");
		data[i][9] = row.get("status");
		data[i][10] = row.get("productname");
		
		
		
		//sitetype,RealSite, sector,siteName, adhocsitelist, view,status.productname
		

	}

	return data;
}
@Test(dataProvider = "recipeDataProvider")
public void Test_createSite(String username, String password, String sitename,String sitetype,String RealSite,String sector,String siteName,String adhocsitelist,String view,String status ,String productname ) throws Throwable {
	// setup();
	loginToApplication(username, password);
	createSite(sitename,sitetype,RealSite, sector,siteName, adhocsitelist, view,status,productname );
	// tearDown();
	// recipedisplayname,mealtype,cuisine,recipecategory
}


public static void createSite(String sitename,String sitetype,String RealSite,String sector,String siteName,String adhocsitelist,String view ,String status,String productname) throws Exception {
	
	clickHome();
	addSiteGoRecipe(sitename);
	selectMenuRecipe();
	
	clickHome();
	clickConfiguration();
	clickSiteManagement();
	
	searchedproduct(productname);
	
	clickedit();
	if(sitetype.equalsIgnoreCase("Adhoc")) {
		
		clicksiteName(siteName);
		clickAdhocsitelist(adhocsitelist);
		clickStatus(status);
	
	}
	clickbrandedView(view);
	//clickStatus(status);
	
	
	
	
	
	
		
	clikcsavebutton();
	
	 String popupmessage = capturePopupMessageText();
     System.out.println("ℹ️ Popup Message after save: " + popupmessage);
	
	
	
	
}


public static void clickAddSitebutton() {
	
	
	
	driver.findElement(By.xpath("//a[text()='Add Site']")).click();
	
}


public static void clickSitetype(String sitetype) {
    try {
        WebElement siteType = driver.findElement(
                By.xpath("//label[contains(text(),'Site Type')]/following::span[@id='select2-OrgUnitType-container']"));
        siteType.click();

        if (sitetype.equalsIgnoreCase("Real")) {
            driver.findElement(By.xpath("//li[text()='Real']")).click();
        } else if (sitetype.equalsIgnoreCase("Adhoc")) {
            driver.findElement(By.xpath("//li[text()='Adhoc']")).click();
        } else {
            System.out.println("Invalid Site Type: " + sitetype);
        }
    } catch (Exception e) {
        System.out.println("Error in selecting Site Type");
        e.printStackTrace();
    }
}

public static void clickRealSite(String RealSite) {
    try {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Wait for and click the dropdown to activate the search box
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//span[text()='Select Real Site']")));
        dropdown.click();

        // Wait for the search box and enter the RealSite name
        WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//input[contains(@class,'select2-search__field')]")
        ));
        searchBox.sendKeys(RealSite);

        // Wait for the desired option to appear and click it
        WebElement siteOption = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//li[contains(text(), '" + RealSite + "')]")
        ));
        siteOption.click();

    } catch (Exception e) {
        e.printStackTrace();
        // You can add additional error handling or logging here
    }
}
    
      

//public static void clickRealSite(String RealSite) {
//    try {
//        driver.findElement(By.xpath("//span[text()='Select Real Site']")).click();
//
//        List<WebElement> realSiteList = driver.findElements(
//                By.xpath("//ul[contains(@class, 'select2-results__options')]//li"));
//
//        boolean found = false;
//        for (WebElement realsite : realSiteList) {
//            if (realsite.getText().trim().equalsIgnoreCase(RealSite.trim())) {
//                realsite.click();
//                found = true;
//                break;
//            }
//        }
//
//        if (!found) {
//            System.out.println("Real Site not found: " + RealSite);
//        }
//
//    } catch (Exception e) {
//        System.out.println("Error in selecting Real Site: " + RealSite);
//        e.printStackTrace();
//    }
//}

public static void clickbrandedView(String view) {
    try {
        WebElement brandedview = driver.findElement(
                By.xpath("//label[contains(text(),'Branded View')]/following::span[@id='select2-OptForBrandedview-container']"));
        brandedview.click();

        if (view.equalsIgnoreCase("Default")) {
            driver.findElement(By.xpath("//li[text()='Default']")).click();
        } else if (view.equalsIgnoreCase("Custom")) {
            driver.findElement(By.xpath("//li[text()='Custom']")).click();
        } else if (view.equalsIgnoreCase("None")) {
            driver.findElement(By.xpath("//li[text()='None']")).click();
        } else {
            System.out.println("Invalid branded view: " + view);
        }
    } catch (Exception e) {
        System.out.println("Error in selecting Branded View: " + view);
        e.printStackTrace();
    }
}

	
	
public static void clicksearchsector(String sector) {
	if (sector == null || sector.trim().isEmpty()) {
		throw new AssertionError("❌ product Text is missing or empty.");
	}

	try {
		Thread.sleep(5000); // Replace with WebDriverWait if needed
		driver.findElement(By.xpath("//*[@id='select2-ddlSectorSearch-container']")).click();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//li[contains(@class,'select2-results__option') and text()='" + sector + "']")))
				.click();
		System.out.println("✅ Product description selected: " + sector);
	} catch (Exception e) {
		throw new AssertionError("❌ Failed to select product description '" + sector + "': " + e.getMessage());
	}
}
	

public static  void clicksiteName(String siteName) {
	
	
	WebElement sitnme =driver.findElement(By.xpath("//label[contains(text(),'Site Name')]/following::input[1]"));
	
	sitnme.clear();
	sitnme.sendKeys(siteName,Keys.ENTER);
	
}


public static void clickAdhocsitelist(String adhocsitelist) {
	if (adhocsitelist == null || adhocsitelist.trim().isEmpty()) {
		throw new AssertionError("❌ product Text is missing or empty.");
	}

	try {
		Thread.sleep(5000); // Replace with WebDriverWait if needed
		driver.findElement(By.xpath("//*[@id='select2-OrgUnitProductListId-container']")).click();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//li[contains(@class,'select2-results__option') and text()='" + adhocsitelist + "']")))
				.click();
		System.out.println("✅ Product description selected: " + adhocsitelist);
	} catch (Exception e) {
		throw new AssertionError("❌ Failed to select product description '" + adhocsitelist + "': " + e.getMessage());
	}
}
	
public static void clikcsavebutton() {
	driver.findElement(By.xpath("//button[contains(text(),'Save')]")).click();
	
}

public static void clickStatus(String status) {
    try {
        WebElement siteType = driver.findElement(
                By.xpath("//label[contains(text(),'Site Type')]/following::span[@id='select2-OrgUnitType-container']"));
        siteType.click();

        if (status.equalsIgnoreCase("Active")) {
            driver.findElement(By.xpath("//li[text()='Active']")).click();
        } else if (status.equalsIgnoreCase("Inactive")) {
            driver.findElement(By.xpath("//li[text()='Inactive']")).click();
        } else {
            System.out.println("Invalid Site Type: " + status);
        }
    } catch (Exception e) {
        System.out.println("Error in selecting Site Type");
        e.printStackTrace();
    }
}

public static void searchedproduct(String productname) {
    try {
        WebElement searchProduct = driver.findElement(By.id("SearchText"));
        searchProduct.clear();
        searchProduct.sendKeys(productname, Keys.ENTER);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement searchProductLink = wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//a[contains(text(),'" + productname + "')]")
            )
        );

        // Optional: click on the product link if needed
        // searchProductLink.click();

    } catch (Exception e) {
        System.err.println("Error in searchedproduct: " + e.getMessage());
        e.printStackTrace();
    }
}


public static void clickedit() {
	
	driver.findElement(By.xpath("//i[@title='Edit']/parent::a")).click();
}
}
