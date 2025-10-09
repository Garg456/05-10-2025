package AutomatedScriptOfJaleh01AsPOM;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.io.FileUtils;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v134.page.Page;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest2 {

    protected static WebDriver driver;
    protected DevTools devTools;

    @BeforeMethod
    public void setup() {
        WebDriverManager.chromedriver().setup();

        String downloadFilepath = "E:\\AutomationTestingData\\Jaleh\\SeleniumDownloads";

        Map<String, Object> prefs = new HashMap<>();
        prefs.put("download.default_directory", downloadFilepath);
        prefs.put("download.prompt_for_download", false);
        prefs.put("download.directory_upgrade", true);
        prefs.put("safebrowsing.enabled", true);
        prefs.put("profile.default_content_settings.popups", 0);  // Disable popups

        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", prefs);
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-popup-blocking");

        driver = new ChromeDriver(options);

        devTools = ((ChromeDriver) driver).getDevTools();
        devTools.createSession();
        devTools.send(Page.setDownloadBehavior(
            Page.SetDownloadBehaviorBehavior.ALLOW,
            Optional.of(downloadFilepath)
        ));

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        System.out.println("✅ Setup complete. Download directory set to: " + downloadFilepath);
    }

    protected void loginToApplication(String username, String password) {
        String URL = "http://" + username + ":" + password + "@" + "cgrrweb01t:8086/Menu/PublishedMenuList";
        driver.get(URL);
        clickRelogin();
    }

    protected static void clickRelogin() {
        driver.findElement(By.xpath("//a[text()=' Re-Login']")).click();
    }

    protected static void clickHome() {
        driver.findElement(By.xpath("//i[@class='nav-icon fas fa-home']")).click();
    }

    protected static void clickRecipeMain() {
        driver.findElement(By.xpath("(//i[@class='nav-icon fas fa-bowl-rice'])[1]")).click();
    }

    protected static void clickSearchRecipe() {
        driver.findElement(By.xpath("(//i[@class='nav-icon fas fa-bowl-rice'])[2]")).click();
    }

    public void clickOnNewRecipeDraft() {
        driver.findElement(By.xpath("//i[@class='nav-icon fa-brands fa-firstdraft']")).click();
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            System.out.println("Browser closed.");
        }
    }

    // Excel Reader
    public static List<Map<String, String>> readData(String filePath, String sheetName) throws IOException {
        List<Map<String, String>> testData = new ArrayList<>();
        try (FileInputStream file = new FileInputStream(filePath);
             XSSFWorkbook workbook = new XSSFWorkbook(file)) {

            XSSFSheet sheet = workbook.getSheet(sheetName);
            XSSFRow headerRow = sheet.getRow(0);

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                XSSFRow currentRow = sheet.getRow(i);
                if (currentRow == null) continue;

                Map<String, String> dataMap = new HashMap<>();
                for (int j = 0; j < headerRow.getLastCellNum(); j++) {
                    String key = headerRow.getCell(j).getStringCellValue();
                    String value = "";
                    if (currentRow.getCell(j) != null) {
                        value = currentRow.getCell(j).toString();
                    }
                    dataMap.put(key, value);
                }

                testData.add(dataMap);
            }
        }
        return testData;
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
    
    public static void clickConfiguration() {
    	driver.findElement(By.xpath("//p[normalize-space(text()) = 'Configuration']/preceding-sibling::i")).click();
    	
    }

    public static void clickSiteManagement() {
    	driver.findElement(By.xpath("//p[normalize-space(text()) = 'Site Management']/preceding-sibling::i")).click();
    	
    }
    
    
    public static void clickUserSiteRoleMapping() {
    	driver.findElement(By.xpath("//p[normalize-space(text()) = 'User Site Role Mapping']/preceding-sibling::i")).click();
    	
    }
    public static void clickproducts() {
		driver.findElement(By.xpath("//p[text()='Products '] /preceding-sibling::i")).click();
		
		
		}
	public void clickproductIntegration() {
		driver.findElement(By.xpath("//p[text()='Product Integration'] /preceding-sibling::i")).click();
		
		}
	public static void Products_APL_MOG() {
		driver.findElement(By.xpath("//p[text()='Products (APL/MOG)'] /preceding-sibling::i")).click();
		
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
	public static void clickTemplateMenus() {
		driver.findElement(By.xpath("//p[contains(text(),'Template Menus')] /ancestor::a[1]")).click();
	}
	public static void clickNewORUnpublishedMenus() {
		driver.findElement(By.xpath("//p[contains(text(),'New or Unpublished Menus')] /ancestor::a[1]")).click();
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
	
public static void searchProductOnSearchbar(String productName) throws Exception {
    
	 
  	 WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
       
       // Wait for the search input box to be clickable and ready
       WebElement srchRecipe = wait.until(ExpectedConditions.elementToBeClickable(By.id("SearchText")));
       
       // Clear any existing text in the search box
       srchRecipe.clear();
       
       // Enter the recipe name and press ENTER
       srchRecipe.sendKeys(productName, Keys.ENTER);
       
       wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'"+ productName +"')]")));
   }


public static String takeScreenshot(String name) {
    File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
    String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
    
    // Create a full (absolute) path
    String dir = System.getProperty("user.dir") + "/screenshots";
    new File(dir).mkdirs(); // Make sure directory exists
    String filePath = dir + "/" + name + "_" + timestamp + ".png";

    try {
        FileUtils.copyFile(src, new File(filePath));
        return filePath; // return full path
    } catch (IOException e) {
        System.out.println("Failed to save screenshot: " + e.getMessage());
        return null;
    }
}


}
	
	
	
	
	
	





