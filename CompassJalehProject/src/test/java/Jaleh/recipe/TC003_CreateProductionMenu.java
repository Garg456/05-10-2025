package Jaleh.recipe;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(myListener2.class)
public class TC003_CreateProductionMenu extends BaseTest2 {

    public static String convertAppDateToHtmlDate(String appDate) throws ParseException {
        String[] possibleFormats = {"dd-MM-yyyy", "M/d/yy", "MM/dd/yy"};
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
        List<Map<String, String>> dataList = readData("E:\\Automation Testing data\\Jaleh\\test1_creation_Master.xlsx", "TC_Create Production Menu");
        Object[][] data = new Object[dataList.size()][9];

        for (int i = 0; i < dataList.size(); i++) {
            Map<String, String> row = dataList.get(i);
            data[i][0] = row.get("username");
            data[i][1] = row.get("password");
            data[i][2] = row.get("recipename");
            data[i][3] = row.get("menuname");
            data[i][4] = row.get("sitename");
            data[i][5] = row.get("Menu_Type");
            data[i][6] = row.get("MenuCategory");
            data[i][7] = row.get("EffectiveStartDate").trim();
            data[i][8] = row.get("EffectiveEndDate").trim();
        }

        return data;
    }

    @Test(dataProvider = "recipeDataProvider")
    public void Test_refreshRecipe(String username, String password, String recipeName, String menuname, String sitename, String Menu_Type, String MenuCategory, String EffectiveStartDate, String EffectiveEndDate) throws Throwable {
        loginToApplication(username, password);
        refreshRecipe(menuname, sitename, recipeName, Menu_Type, MenuCategory, EffectiveStartDate, EffectiveEndDate);
    }

    public static void refreshRecipe(String menuname, String sitename, String Recipename, String Menu_Type, String MenuCategory, String EffectiveStartDate, String EffectiveEndDate) throws Exception {
        clickHome();
        addSiteGoRecipe(sitename);
        selectMenuRecipe();
        navigateToMenus();
        addmenu(menuname, Menu_Type, MenuCategory, EffectiveStartDate, EffectiveEndDate);
        addCustomer();
        addMealPeriod();
        selectMealPeriod();
        addRecipeOnMenu(Recipename);
    }

    public static void navigateToMenus() {
        driver.findElement(By.xpath("(//i[@class='nav-icon fas fa-table'])[1]")).click();
        driver.findElement(By.xpath("//i[@class='nav-icon fas fa-utensils']")).click();
    }

    public static void addmenu(String menuname, String Menu_Type, String MenuCategory, String EffectiveStartDate, String EffectiveEndDate) throws Exception {
        driver.findElement(By.xpath("//button[text()='Add Menu']")).click();
        driver.findElement(By.xpath("//input[@id='MenuName']")).sendKeys(menuname, Keys.ENTER);

        new Select(driver.findElement(By.id("MenuTypeId"))).selectByVisibleText(Menu_Type);
        new Select(driver.findElement(By.id("MenuCategoryId"))).selectByVisibleText(MenuCategory);
        
        
        
        
        

        setDateById("EffectiveStartDate", EffectiveStartDate);
        setDateById("EffectiveEndDate", EffectiveEndDate);
        
        if(MenuCategory.equals("Cyclic")) {

        WebElement weekCycle = driver.findElement(By.id("NoOfWeekPerCycle"));
        weekCycle.clear();
        weekCycle.sendKeys("2", Keys.ENTER);
        }else {
        	
        WebElement NoOfDays=	driver.findElement(By.xpath("//label[contains(text(),'Days')]/following-sibling::input[@id='txtNoOfDays']"));
        NoOfDays.clear();
        NoOfDays.sendKeys("12");
        }

        driver.findElement(By.xpath("//label[text()='Per Customer Target']/following-sibling::input")).sendKeys("12", Keys.ENTER);

        driver.findElement(By.xpath("//button[text()='Add']")).click();

        System.out.println("menu added successfully");
    }

    public static void addCustomer() throws Exception {
        driver.findElement(By.xpath("//button[@id='editCustomerRow_11']")).click();
        driver.findElement(By.xpath("//tr[@id='tr_W1_Cycle1']//input[@id='editableValue7']")).sendKeys("122");
        driver.findElement(By.xpath("//button[@id='saveCustomerRow_11']")).click();

        String msg = capturePopupMessageText();
        System.out.println("Popup after adding customer: " + msg);

        Assert.assertNotNull(msg, "Customer popup not found.");
        Assert.assertTrue(msg.toLowerCase().contains("customer") || msg.toLowerCase().contains("success"), "Unexpected customer popup: " + msg);

        Thread.sleep(3000);
    }

    public static void addMealPeriod() throws Exception {
        driver.findElement(By.id("tabThreeTab")).click();

        driver.findElement(By.xpath("//span[text()='Add Meal Period']")).click();
        driver.findElement(By.xpath("(//input[@type='search'])[5]")).sendKeys("Breakfast", Keys.ENTER);
        driver.findElement(By.id("addMealPeriod")).click();

        Thread.sleep(2000);

        driver.findElement(By.xpath("//span[text()='Add Meal Period']")).click();
        driver.findElement(By.xpath("(//input[@type='search'])[5]")).sendKeys("Crib", Keys.ENTER);
        driver.findElement(By.id("addMealPeriod")).click();

        String msg = capturePopupMessageText();
        System.out.println("Popup after adding meal period: " + msg);

        Assert.assertNotNull(msg, "Meal period popup not found.");
        Assert.assertTrue(msg.toLowerCase().contains("added") || msg.toLowerCase().contains("success"), "Unexpected meal period popup: " + msg);

        Thread.sleep(3000);
    }

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
        Assert.assertTrue(msg.toLowerCase().contains("added") || msg.toLowerCase().contains("success"), "Unexpected meal section popup: " + msg);

        Thread.sleep(3000);
    }

   



    public static void addRecipeOnMenu(String Recipename) {
        driver.findElement(By.id("tabFiveTab")).click();

        try {
            // Expand section
            driver.findElement(By.xpath("(//i[@class='fa fa-chevron-down'])[1]")).click();
            driver.findElement(By.xpath("(//i[@class='fa fa-plus'])[1]")).click();

            // Search for recipe
            WebElement srchrcptxt = driver.findElement(By.id("SearchText"));
            srchrcptxt.clear();
            srchrcptxt.sendKeys(Recipename, Keys.ENTER);

            // Add recipe
            driver.findElement(By.xpath("(//button[text()='Add'])[4]")).click();
            driver.findElement(By.id("btnAddRecipe")).click();

            // Capture popup
            String msg = capturePopupMessageText();
            System.out.println("Popup after adding recipe: " + msg);

            // Assert popup is not null and contains success message
            Assert.assertNotNull(msg, "Recipe popup message was null.");
            Assert.assertTrue(
                msg.toLowerCase().contains("recipe") || msg.toLowerCase().contains("success"),
                "Unexpected recipe popup message: " + msg
            );

            System.out.println("✅ Recipe added on menu successfully: " + Recipename);
            Thread.sleep(3000); // Optional UI sync
        } catch (Exception e) {
            Assert.fail("❌ Failed to add recipe on menu: " + Recipename + ". Error: " + e.getMessage());
        }
    }



    public static void addSiteGoRecipe(String siteName) throws Exception {
        driver.findElement(By.id("txtSearchOrgUnit")).sendKeys(siteName, Keys.ENTER);

        String xpath = "//div[@class='list-field']//label[text()='" + siteName + "']/preceding-sibling::input[@type='radio']";

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
            
            String script = 
                "var dateField = document.getElementById('" + fieldId + "');" +
                "dateField.value = '" + htmlDate + "';" +
                "dateField.dispatchEvent(new Event('change', { bubbles: true }));";
            
            js.executeScript(script);
        } catch (Exception e) {
            System.out.println("Error setting date for " + fieldId + ": " + e.getMessage());
        }
    }

}
