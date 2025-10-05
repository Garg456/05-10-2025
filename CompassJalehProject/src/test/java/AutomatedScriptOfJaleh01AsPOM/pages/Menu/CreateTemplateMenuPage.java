package AutomatedScriptOfJaleh01AsPOM.pages.Menu;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;

import com.aventstack.extentreports.ExtentTest;

import java.time.Duration;

import AutomatedScriptOfJaleh01AsPOM.util.Util;

public class CreateTemplateMenuPage {

    private WebDriver driver;
    private WebDriverWait wait;
    private Util ut;

    public CreateTemplateMenuPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.ut = new Util(driver);
    }

    public void navigateToMenus() {
        driver.findElement(By.xpath("(//i[@class='nav-icon fas fa-table'])[1]")).click();
        driver.findElement(By.xpath("//i[@class='nav-icon fas fa-utensils']")).click();
    }

    public void addMenu(String menuName, String menuType, String menuCategory) {
        if (menuName == null || menuName.trim().isEmpty())
            throw new AssertionError("❌ Menu name is missing.");

        driver.findElement(By.xpath("//button[text()='Add Menu']")).click();
        driver.findElement(By.id("MenuName")).sendKeys(menuName, Keys.ENTER);

        new Select(driver.findElement(By.id("MenuTypeId"))).selectByVisibleText(menuType);
        new Select(driver.findElement(By.id("MenuCategoryId"))).selectByVisibleText(menuCategory);

        if (menuCategory.equalsIgnoreCase("Cyclic")) {
            driver.findElement(By.id("NoOfWeekPerCycle")).sendKeys("2", Keys.ENTER);
        } else {
            driver.findElement(By.xpath("//label[contains(text(),'# of Days')]/following-sibling::input[@id='txtNoOfDays']"))
                  .sendKeys("7", Keys.ENTER);
        }

        driver.findElement(By.xpath("//button[text()='Add']")).click();
        System.out.println("✅ Template Menu added successfully.");
    }

    public void addCustomer(String count) throws Exception {
        driver.findElement(By.id("editCustomerRow_11")).click();
        driver.findElement(By.id("editableValue7")).sendKeys(count);
        driver.findElement(By.id("saveCustomerRow_11")).click();
        Thread.sleep(2000);
    }

    public void addMealPeriods(String meals) throws Exception {
        driver.findElement(By.id("tabThreeTab")).click();
        if (meals == null || meals.trim().isEmpty())
            throw new AssertionError("❌ Meal periods are missing.");

        for (String meal : meals.split(",")) {
            driver.findElement(By.xpath("//span[text()='Add Meal Period']")).click();
            driver.findElement(By.xpath("(//input[@type='search'])[5]")).sendKeys(meal.trim(), Keys.ENTER);
            driver.findElement(By.id("addMealPeriod")).click();
            Thread.sleep(2000);
        }

        String msg = ut.capturePopupMessageText();
        Assert.assertTrue(msg.toLowerCase().contains("added") || msg.toLowerCase().contains("success"));
    }

    public void selectMealPeriod() throws Exception {
        driver.findElement(By.id("tabFourTab")).click();

        // Add "Breakfast > Bakery"
        driver.findElement(By.xpath("//span[text()='Select Meal Period']")).click();
        driver.findElement(By.xpath("(//input[@type='search'])[5]")).sendKeys("Breakfast", Keys.ENTER);
        driver.findElement(By.xpath("//span[text()='Select Section']")).click();
        driver.findElement(By.xpath("(//input[@type='search'])[5]")).sendKeys("Bakery", Keys.ENTER);
        driver.findElement(By.id("MealPeriodSectionAddId")).click();

        // Add "Crib > Carvery"
        driver.findElement(By.xpath("//span[@id='select2-ddlMealPeriodForSec-container']")).click();
        driver.findElement(By.xpath("(//input[@type='search'])[5]")).sendKeys("Crib", Keys.ENTER);
        driver.findElement(By.xpath("//span[text()='Select Section']")).click();
        driver.findElement(By.xpath("(//input[@type='search'])[5]")).sendKeys("Carvery", Keys.ENTER);
        driver.findElement(By.id("MealPeriodSectionAddId")).click();

        Thread.sleep(2000);
    }

    public void addRecipeToMenu(String recipeName) {
        driver.findElement(By.id("tabFiveTab")).click();

        try {
            driver.findElement(By.xpath("(//i[@class='fa fa-chevron-down'])[1]")).click();
            driver.findElement(By.xpath("(//i[@class='fa fa-plus'])[1]")).click();

            WebElement searchField = driver.findElement(By.id("SearchText"));
            searchField.clear();
            searchField.sendKeys(recipeName, Keys.ENTER);

            driver.findElement(By.xpath("(//button[text()='Add'])[4]")).click();
            driver.findElement(By.id("btnAddRecipe")).click();

            String msg = ut.capturePopupMessageText();
            Assert.assertTrue(msg.toLowerCase().contains("menu") || msg.toLowerCase().contains("success"));
        } catch (Exception e) {
            System.out.println("❌ Recipe addition failed: " + e.getMessage());
        }
    }

    public void publishMenu(ExtentTest logger) throws Exception {
        wait.until(ExpectedConditions.elementToBeClickable(By.id("btnMoreAction"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='dropdown-menu show']//ul//li[3]"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("btnDraftPublish"))).click();

        String msg = ut.capturePopupMessageText();
        logger.info(msg);

        Thread.sleep(2000);
    }
}
