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
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;

@Listeners(myListener3.class)
public class TC_DeleteProductionMenu extends BaseTest2 {

    @DataProvider(name = "recipeDataProvider")
    public Object[][] recipeDataProvider() throws IOException {
        List<Map<String, String>> dataList = readData("E:\\Automation Testing data\\Jaleh\\test1_creation_Master.xlsx", "TC_Delete Menu");
        Object[][] data = new Object[dataList.size()][3];

        for (int i = 0; i < dataList.size(); i++) {
            Map<String, String> row = dataList.get(i);
            data[i][0] = row.get("username");
            data[i][1] = row.get("password");
            data[i][2] = row.get("menuname");
        }
        return data;
    }

    @Test(dataProvider = "recipeDataProvider")
    public void Test_DeleteMenu(String username, String password, String menuname) throws Throwable {
        loginToApplication(username, password);
        deleteMenu(menuname);
    }

    public void deleteMenu(String menuname) throws Exception {
        clickHome();
        clickMenus();
        clickProductionMenus();
        deleteTemplateMenu(menuname);

        // Post-delete verification
        clickHome();
        clickMenus();
        clickProductionMenus();
        SearchRecipeBeforeCreation srbc = new SearchRecipeBeforeCreation();
        srbc.searchProduct(menuname);
    }

    public void deleteTemplateMenu(String menuname) {
        ExtentTest testLogger = myListener3.getTest();

        if (menuname == null || menuname.trim().isEmpty()) {
            Assert.fail("❌ Menu name is missing or empty.");
        }

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            // Search menu
            WebElement searchBox = wait.until(ExpectedConditions.elementToBeClickable(By.id("SearchText")));
            searchBox.clear();
            searchBox.sendKeys(menuname, Keys.ENTER);
            Thread.sleep(2000); // Optional — consider replacing with smarter wait

            // Click dropdown
            WebElement dropdownBtn = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//div[contains(@class,'action-buttons btn-group')]//button")));
            dropdownBtn.click();

            // Click 'Inactive'
            WebElement inactiveOption = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//div[@title='Inactive']//a")));
            inactiveOption.click();

            // Click 'OK' on confirmation
            WebElement okButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[text()='OK']")));
            okButton.click();

            wait.until(ExpectedConditions.invisibilityOf(okButton));
            System.out.println("✅ Menu '" + menuname + "' deleted successfully.");
            testLogger.info("<span style='color:green; font-weight:bold;'>✅ Menu deleted:</span> " + menuname);

        } catch (Exception e) {
            e.printStackTrace();
            testLogger.fail("❌ Failed to delete menu '" + menuname + "': " + e.getMessage());
            Assert.fail("❌ Exception occurred while deleting menu: " + e.getMessage());
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
}
