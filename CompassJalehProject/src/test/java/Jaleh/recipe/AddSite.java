package Jaleh.recipe;

import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
@Listeners(myListener2.class)
public class AddSite extends BaseTest2 {
	
	@DataProvider(name = "recipeDataProvider")
	public Object[][] recipeDataProvider() throws IOException {
	    List<Map<String, String>> dataList = readData("E:\\Automation Testing data\\Jaleh\\test1_creation_Master.xlsx", "TC_Create Site");
	    Object[][] data = new Object[dataList.size()][7];

	    for (int i = 0; i < dataList.size(); i++) {
	        Map<String, String> row = dataList.get(i);
	        data[i][0] = row.get("username");
	        data[i][1] = row.get("password");
	        data[i][2] = row.get("Sitetype");
	        data[i][3] = row.get("Sector");
	        data[i][4] = row.get("sitename");
	        data[i][5] = row.get("BrandedView");
	        data[i][6] = row.get("AdhocSiteList");
	    }

	    return data;
	}
  
	    
	    
	    @Test(dataProvider = "recipeDataProvider")
		public void Test_createSite(String username, String password, String Sitetype, String Sector,
				String sitename, String BrandedView, String AdhocSiteList)
				throws Throwable {
			// setup();
			loginToApplication(username, password);
			createSite(Sitetype, Sector, sitename, BrandedView, AdhocSiteList);



			// recipedisplayname,mealtype,cuisine,recipecategory
		}
	    
	    public static void createSite(String Sitetype,String Sector, String sitename, String BrandedView, String AdhocSiteList) throws Exception {
	    	clickHome();
	    	clickONConfiguration();
	    	clickONSiteManagement();
	    	clickONAddSite();
	    	clickONAddSitetypedp(Sitetype);
	    	selectSectorSearch(Sector);
	    	addsitename(sitename);
	    	clickONBrandedViewdp(BrandedView);
	    	clickAdhocSiteList(AdhocSiteList);
	    	//addSaveButton();
	    	
	    	
	    	
	    	
	    
	    
	    }
		

	
	
	
	
	
	public static void clickONConfiguration() {
		
		 driver.findElement(By.xpath("//p[contains(text(),'Configuration')]/parent::a")).click();
		 
		 System.out.println(" clickONConfiguration");
	}
	

	public static void clickONSiteManagement() throws Exception {
		
		 driver.findElement(By.xpath("//p[contains(text(),'Site Management')]/parent::a")).click();
		 System.out.println("clickONSiteManagement");
		 
		 Thread.sleep(4000);
	}

	
	////li//a//i[contains(@class, 'fa-add')]
	///
	///
	///
	public static void clickONAddSite() {
		
		 driver.findElement(By.xpath("//li//a//i[contains(@class, 'fa-add')]")).click();
		 System.out.println("clickONAddSite");
	}
	
//	public static void clickONAddSitetypedp(String Sitetype) throws Exception {
//		 WebElement sitetypedp =driver.findElement(By.id("OrgUnitType"));
//		Select sl = new Select(sitetypedp);
//		sl.selectByContainsVisibleText(Sitetype);
//		Thread.sleep(3000);
//		System.out.println("clickONAddSitetypedp");
//		
//		}
	
	public static void clickONAddSitetypedp(String Sitetype) throws Exception {
		 driver.findElement(By.id("select2-OrgUnitType-container")).click();
		 driver.findElement(By.xpath("//li[contains(text(),'"+Sitetype +"')]")).click();
		 System.out.println("clickONAddSitetypedp");
	}
	
	public static void selectSectorSearch(String Sector) {
		
		
		driver.findElement(By.xpath("//span[@id='select2-ddlSectorSearch-container']")).click();
		 driver.findElement(By.xpath("//li[contains(text(),'"+Sector +"')]")).click();
		 
		 
		 System.out.println("selectSectorSearch");
		
		
	}
	
	
	
	
	
	//select2-ddlSectorSearch-container
//	public static void selectSectorSearch(String Sector) {
//	    if (Sector == null || Sector.trim().isEmpty()) {
//	        throw new AssertionError("❌ Recipe Category is missing or empty.");
//	    }
//	    try {//select2-ddlSectorSearch-container
//	    	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
//	    	WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(By.id("select2-ddlRealSiteSearch-container")));
//	    	dropdown.click();
//        driver.findElement(By.xpath("//input[@type='search']")).sendKeys(Sector);	        
//        Thread.sleep(1000);
//	        
//	        List<WebElement> options = driver.findElements(By.xpath("//li[contains(@class,'select2-results__option')]"));
//	        
//	        for (WebElement option : options) {
//	            if (option.getText().equalsIgnoreCase(Sector)) {
//                option.click();
//                System.out.println("selectSectorSearch");
//                return;
//	            }
//	        }
//	        
//	        throw new AssertionError("❌ selectSectorSearch '" + Sector + "' not found in dropdown.");
//	        
//    } catch (InterruptedException e) {
//	        throw new AssertionError("❌ Interrupted Exception: " + e.getMessage());
//    } catch (Exception e) {
//	        throw new AssertionError("❌ Failed to select Recipe Category: " + e.getMessage());
//	    }
//	    
//	   // System.out.println("selectSectorSearch");
//	}

	
	
	//
	public static void addsitename(String sitename) {
		driver.findElement(By.xpath("//label[text()='Site Name']/following-sibling::input")).sendKeys(sitename);
		
		System.out.println("addsitename");
	}
	
	public static void clickONBrandedViewdp(String BrandedView) {
		 WebElement BrandedViewdp =driver.findElement(By.id("OptForBrandedview"));
		Select sl = new Select(BrandedViewdp);
		sl.selectByContainsVisibleText(BrandedView);
		System.out.println("clickONBrandedViewdp");
		
		}
	
	
	public static void clickAdhocSiteList(String AdhocSiteList) {
	    if (AdhocSiteList == null || AdhocSiteList.trim().isEmpty()) {
	        throw new AssertionError("❌ Recipe Category is missing or empty.");
	    }
	    try {
	    	//select2-OrgUnitProductListId-container
	        driver.findElement(By.id("select2-OrgUnitProductListId-container")).click();
	        driver.findElement(By.xpath("//input[@type='search']")).sendKeys(AdhocSiteList);
	        
	        Thread.sleep(1000);
	        
	        List<WebElement> options = driver.findElements(By.xpath("//li[contains(@class,'select2-results__option')]"));
	        
	        for (WebElement option : options) {
	            if (option.getText().equalsIgnoreCase(AdhocSiteList)) {
	                option.click();
	                
	                System.out.println("clickAdhocSiteList");
	                return;
	            }
	        }
	        
	        throw new AssertionError("❌ Recipe Category '" + AdhocSiteList + "' not found in dropdown.");
	        
	    } catch (InterruptedException e) {
	        throw new AssertionError("❌ Interrupted Exception: " + e.getMessage());
	    } catch (Exception e) {
	        throw new AssertionError("❌ Failed to select Recipe Category: " + e.getMessage());
	    }
	}
	
	public static void addSaveButton() {
		driver.findElement(By.xpath("//div//button[@type='submit']")).click();
		
		
		System.out.println("addSaveButton");
		
		
	}
	
	
}
