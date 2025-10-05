package Jaleh.recipe;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {

    protected static WebDriver driver;
@BeforeMethod
    protected void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
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
    protected void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
    
 // ✅ Excel Reader
 	 public static List<Map<String, String>> readdata() throws IOException {
 		    List<Map<String, String>> testData = new ArrayList<>();
 		    try (FileInputStream file = new FileInputStream("E:\\Automation Testing data\\Jaleh\\test1_creation_Master.xlsx");
 		         XSSFWorkbook workbook = new XSSFWorkbook(file)) {

 		        XSSFSheet sheet = workbook.getSheetAt(0);
 		        XSSFRow headerRow = sheet.getRow(0);

 		        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
 		            XSSFRow currentRow = sheet.getRow(i);
 		            if (currentRow == null) continue; // Skip blank rows

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


 	 // ✅ DataProvider
 	    @DataProvider(name = "loginData")
 	    public Object[][] loginDataProvider() throws IOException {
 	        List<Map<String, String>> dataList = readdata();
 	        Object[][] data = new Object[dataList.size()][10];

 	        for (int i = 0; i < dataList.size(); i++) {
 	            Map<String, String> dataMap = dataList.get(i);
 	            data[i][0] = dataMap.get("username");
 	            data[i][1] = dataMap.get("password");
 	            data[i][2] = dataMap.get("recipename");
 	            data[i][3] = dataMap.get("productdescription");
 	            data[i][4] = dataMap.get("ingredientgroup");
 	           data[i][5] = dataMap.get("recipedisplayname");
 	          data[i][6] = dataMap.get("mealtype");
 	         data[i][7] = dataMap.get("cuisine");
 	        data[i][8] = dataMap.get("recipecategory");
 	       data[i][9] = dataMap.get("publishType");
 	       
 	       //for refresh scenario
 	     
 	       
 	        
 	            
 	            
 	          // recipedisplayname,mealtype,cuisine,recipecategory
 	            
 	            
 	        }
 	        return data;
 	    }
    
   
    
    
}
