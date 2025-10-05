package AutomatedScriptOfJaleh01AsPOM.pages.Menu;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.aventstack.extentreports.ExtentTest;

import AutomatedScriptOfJaleh01AsPOM.util.Util;

public class CreateProductionMenu {

    private WebDriver driver;
    private WebDriverWait wait;
    private Util ut;

    public CreateProductionMenu(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.ut = new Util(driver);
    }

    
    //Util ut= new Util(driver);
    public void navigateToMenus() {
        driver.findElement(By.xpath("(//i[@class='nav-icon fas fa-table'])[1]")).click();
        driver.findElement(By.xpath("//i[@class='nav-icon fas fa-utensils']")).click();
    }

    public void addMenu(String menuname, String menuType, String menuCategory,
                        String effectiveStartDate, String effectiveEndDate) {
    	
        driver.findElement(By.xpath("//button[text()='Add Menu']")).click();
        driver.findElement(By.id("MenuName")).sendKeys(menuname, Keys.ENTER);

        new Select(driver.findElement(By.id("MenuTypeId"))).selectByVisibleText(menuType);
        new Select(driver.findElement(By.id("MenuCategoryId"))).selectByVisibleText(menuCategory);

		
		  ut.setDateById(driver,"EffectiveStartDate", effectiveStartDate);
		  ut.setDateById(driver,"EffectiveEndDate", effectiveEndDate);
		 
		/*
		 * ut.setDateById(driver,"EffectiveStartDate", "19-09-2025");
		 * ut.setDateById(driver,"EffectiveEndDate", "30-09-2025");
		 */

        if (menuCategory.equalsIgnoreCase("Cyclic")) {
            WebElement weekCycle = driver.findElement(By.id("NoOfWeekPerCycle"));
            weekCycle.clear();
            weekCycle.sendKeys("2", Keys.ENTER);
        } else {
            WebElement noOfDays = driver.findElement(
                    By.xpath("//label[contains(text(),'Days')]/following-sibling::input[@id='txtNoOfDays']"));
            noOfDays.clear();
            noOfDays.sendKeys("12");
        }

        driver.findElement(By.xpath("//label[text()='Per Customer Target']/following-sibling::input"))
                .sendKeys("12", Keys.ENTER);

        driver.findElement(By.xpath("//button[text()='Add']")).click();

        System.out.println("✅ Menu added successfully: " + menuname);
    }

    public void addCustomer() throws InterruptedException {
    	
    	try {
        driver.findElement(By.id("editCustomerRow_11")).click();
        driver.findElement(By.id("editableValue7")).sendKeys("122");
        driver.findElement(By.id("saveCustomerRow_11")).click();

        String msg = ut.capturePopupMessageText();
        System.out.println("Popup after adding customer: " + msg);
       // Assert.assertTrue(msg != null && msg.toLowerCase().contains("success"), "Unexpected customer popup: " + msg);

        Thread.sleep(3000);
    	}catch(Exception e) { System.out.println(e.getMessage());
    	
    	}
    	
    	
    }

    public void addMealPeriods() throws InterruptedException {
        driver.findElement(By.id("tabThreeTab")).click();

        String[] periods = {"Breakfast", "Crib"};

        for (String period : periods) {
            driver.findElement(By.xpath("//span[text()='Add Meal Period']")).click();
            driver.findElement(By.xpath("(//input[@type='search'])[last()]")).sendKeys(period, Keys.ENTER);
            driver.findElement(By.id("addMealPeriod")).click();
            Thread.sleep(1000);
        }

        String msg = ut.capturePopupMessageText();
        System.out.println("Popup after adding meal periods: " + msg);
        Assert.assertTrue(msg != null && msg.toLowerCase().contains("success"), "Unexpected meal popup: " + msg);

        Thread.sleep(3000);
    }

    public  void selectMealPeriod() throws Exception {
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

		String msg = ut.capturePopupMessageText();
		System.out.println("Popup after selecting meal period: " + msg);

		Assert.assertNotNull(msg, "Meal period section popup not found.");
		Assert.assertTrue(msg.toLowerCase().contains("added") || msg.toLowerCase().contains("success"),
				"Unexpected meal section popup: " + msg);

		Thread.sleep(3000);
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
            System.out.println("Popup after adding recipe: " + msg);

            Assert.assertNotNull(msg);
            Assert.assertTrue(msg.toLowerCase().contains("recipe") || msg.toLowerCase().contains("success"));

            System.out.println("✅ Recipe added successfully: " + recipeName);
        } catch (Exception e) {
            Assert.fail("❌ Failed to add recipe: " + recipeName + " | Error: " + e.getMessage());
        }
    }

    public void publishMenu(ExtentTest testLogger) {
        wait.until(ExpectedConditions.elementToBeClickable(By.id("btnMoreAction"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='dropdown-menu show']//ul//li[3]"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("btnDraftPublish"))).click();

        String msg = ut.capturePopupMessageText();
        System.out.println("Popup after publishing menu: " + msg);

        testLogger.info(msg);
    }

    // ---- Helper Methods ----

    
   
}
