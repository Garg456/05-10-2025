package AutomatedScriptOfJaleh01AsPOM;

import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(myListener3.class)
public class SC03_ChangeRole extends BaseTest2 {

    @DataProvider(name = "recipeDataProvider")
    public Object[][] recipeDataProvider() throws IOException {
        List<Map<String, String>> dataList = readData(
            "E:\\Automation Testing data\\Jaleh\\test1_creation_Master.xlsx",
            "TC_ChangeRole"
        );
        Object[][] data = new Object[dataList.size()][6];

        for (int i = 0; i < dataList.size(); i++) {
            Map<String, String> row = dataList.get(i);
            data[i][0] = row.get("username");
            data[i][1] = row.get("password");
            data[i][2] = row.get("sitename");
            data[i][3] = row.get("adminrole");
            data[i][4] = row.get("sectorName");
            data[i][5] = row.get("productname");
        }
        return data;
    }

    @Test(dataProvider = "recipeDataProvider")
    public void Test_SiteInactive(String username, String password, String sitename, String adminrole, String sectorName, String productname) throws Exception {
        loginToApplication(username, password);
        SiteInactive(sitename, adminrole, sectorName, productname);
    }

    public static void SiteInactive(String sitename, String adminrole, String sectorName, String productname) throws Exception {
        clickHome();
        clickConfiguration();
        clickUserSiteRoleMapping();

        searchedproduct(productname);
        scrolldown();

        changeRole(adminrole);  // Select the role from dropdown

        // Call role-specific methods
        if (adminrole.equalsIgnoreCase("Site Admin")) {
            forsiteAdmin(sitename);
        } else if (adminrole.equalsIgnoreCase("Sector Admin")) {
            forSectorAdmin(sectorName);
        } else if (adminrole.equalsIgnoreCase("Jaleh Admin")) {
            // No extra action needed
            System.out.println("Jaleh Admin selected: No additional sector/site assignment.");
        } else {
            System.out.println("Unknown admin type: " + adminrole);
        }

        savechangedRole();  // Save the role assignment
    }


    public static void scrolldown() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            By editButtonLocator = By.xpath("//i[@title='Edit']/parent::button");
            WebElement editButton = wait.until(ExpectedConditions.presenceOfElementLocated(editButtonLocator));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", editButton);
            wait.until(ExpectedConditions.visibilityOf(editButton));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void changeRole(String role) {
        try {
            driver.findElement(By.xpath("//i[@title='Edit']/parent::button")).click();
            driver.findElement(By.id("select2-ddlRole-container")).click();
            driver.findElement(By.xpath("//input[@type='search']")).sendKeys(role, Keys.ENTER);
        } catch (Exception e) {
            System.out.println("Error in changeRole: " + e.getMessage());
        }
    }

    public static void savechangedRole() {
        driver.findElement(By.xpath("//button[text()='Save']")).click();
    }

    public static void forSectorAdmin(String sectorName) {
        try {
            driver.findElement(By.xpath("(//label[text()='Available Sector']/following::input)[1]")).sendKeys(sectorName, Keys.ENTER);
            driver.findElement(By.xpath("(//label[text()='Available Sector']/following::option[text()='" + sectorName + "'])[1]")).click();
            driver.findElement(By.xpath("(//button[@title='Move selected'])[1]")).click();

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            By product_moved = By.xpath("(//label[text()='Selected Sector']/following::option[text()='" + sectorName + "'])[1]");
            wait.until(ExpectedConditions.presenceOfElementLocated(product_moved));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void forsiteAdmin(String sitename) {
        try {
            driver.findElement(By.xpath("(//label[text()='Available Site']/following::input)[1]")).sendKeys(sitename, Keys.ENTER);
            driver.findElement(By.xpath("(//label[text()='Available Site']/following::option[text()='" + sitename + "'])[1]")).click();
            driver.findElement(By.xpath("(//button[@title='Move selected'])[2]")).click();

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            By product_moved = By.xpath("(//label[text()='Selected Site']/following::option[text()='" + sitename + "'])[1]");
            wait.until(ExpectedConditions.presenceOfElementLocated(product_moved));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void searchedproduct(String productname) {
        try {
            WebElement searchProduct = driver.findElement(By.id("SearchText"));
            searchProduct.clear();
            searchProduct.sendKeys(productname, Keys.ENTER);

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement searchProductLink = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'" + productname + "')]"))
            );
            // Optionally click on product link if needed
            // searchProductLink.click();

        } catch (Exception e) {
            System.err.println("Error in searchedproduct: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void addSiteGoRecipe(String siteName) {
        try {
            driver.findElement(By.id("txtSearchOrgUnit")).sendKeys(siteName, Keys.ENTER);
            String xpath = "//div[@class='list-field']//label[text()='" + siteName + "']/preceding-sibling::input[@type='radio']";
            WebElement radioButton = driver.findElement(By.xpath(xpath));
            if (!radioButton.isSelected()) {
                radioButton.click();
            }
            System.out.println("Site selected");
            Thread.sleep(3000);
        } catch (Exception e) {
            System.err.println("Error in addSiteGoRecipe: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void selectMenuRecipe() {
        try {
            driver.findElement(By.id("spnCountRecipes")).click();
        } catch (Exception e) {
            System.err.println("Error in selectMenuRecipe: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void clickMenus() {
        try {
            driver.findElement(By.xpath("(//p[contains(text(),'Menus')] /ancestor::a[1])[1]")).click();
        } catch (Exception e) {
            System.err.println("Error in clickMenus: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void clickProductionMenus() {
        try {
            driver.findElement(By.xpath("//p[contains(text(),'Production Menus')] /ancestor::a[1]")).click();
        } catch (Exception e) {
            System.err.println("Error in clickProductionMenus: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
