package Jaleh;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class refreshRecipe {
    public static WebDriver driver;

    public static void main(String[] args) throws Exception {
        String menuname = "Menu150096";
        String sitename2 = "Production | 180800-AFP Majura | 31-Retail 1(Real)";
        String sitename1 = "Production | 208150-South32 - Groote Eylandt | 11-Catering(Real)";
        String Recipename = "maggie5AA5";

        setupDriverWithLogin();
        reLoginAndHome();

        addSiteGoRecipe(sitename2);
        createNewMenu(menuname);
        addMealPeriods();
        addSectionsToMealPeriods();
        addRecipeToMenu(Recipename);
        publishRecipe(Recipename);

        switchToSiteAndRefreshRecipe(sitename1, Recipename);
        publishRecipe(Recipename);

        addSiteGoRecipe(sitename2);
        refreshRecipeInSite(Recipename);

        driver.quit();
    }

    // Setup ChromeDriver and open application with login
    public static void setupDriverWithLogin() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();

        String username = "admin-path22@compass-external.com.au";
        String password = "Pathinfotech@01";
        String URL = "http://" + username + ":" + password + "@cgrrweb01t:8086/Menu/PublishedMenuList";

        driver.get(URL);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    // Perform re-login and go to homepage
    public static void reLoginAndHome() {
        driver.findElement(By.xpath("//a[text()=' Re-Login']")).click();
        driver.findElement(By.xpath("//i[@class='nav-icon fas fa-home']")).click();
    }

    // Switch to a specific site and go to the recipe section
    public static void addSiteGoRecipe(String siteName) throws InterruptedException {
        driver.findElement(By.id("txtSearchOrgUnit")).sendKeys(siteName, Keys.ENTER);
        String xpath = "//div[@class='list-field']//label[text()='" + siteName + "']/preceding-sibling::input[@type='radio']";
        WebElement radioButton = driver.findElement(By.xpath(xpath));
        if (!radioButton.isSelected()) radioButton.click();
        Thread.sleep(3000);
        driver.findElement(By.id("spnCountRecipes")).click();
        System.out.println("Site selected: " + siteName);
    }

    // Create a new menu with required details
    public static void createNewMenu(String menuname) throws InterruptedException {
        driver.findElement(By.xpath("(//i[@class='nav-icon fas fa-table'])[1]")).click();
        driver.findElement(By.xpath("//i[@class='nav-icon fas fa-utensils']")).click();
        driver.findElement(By.xpath("//button[text()='Add Menu']")).click();
        driver.findElement(By.id("MenuName")).sendKeys(menuname, Keys.ENTER);

        Select type = new Select(driver.findElement(By.id("MenuTypeId")));
        type.selectByVisibleText("Template");

        Select category = new Select(driver.findElement(By.id("MenuCategoryId")));
        category.selectByVisibleText("Cyclic");

        driver.findElement(By.id("NoOfWeekPerCycle")).sendKeys("2", Keys.ENTER);
        driver.findElement(By.xpath("//button[text()='Add']")).click();

        driver.findElement(By.xpath("//button[@id='editCustomerRow_11']")).click();
        driver.findElement(By.xpath("//tr[@id='tr_W1_Cycle1']//input[@id='editableValue7']")).sendKeys("122");
        driver.findElement(By.id("saveCustomerRow_11")).click();
        System.out.println("Menu created and saved.");
        Thread.sleep(3000);
    }

    // Add meal periods like Breakfast and Crib
    public static void addMealPeriods() {
        driver.findElement(By.id("tabThreeTab")).click();
        addMealPeriod("Breakfast");
        addMealPeriod("Crib");
    }

    // Helper method to add a meal period
    public static void addMealPeriod(String period) {
        driver.findElement(By.xpath("//span[text()='Add Meal Period']")).click();
        driver.findElement(By.xpath("(//input[@type='search'])[5]")).sendKeys(period, Keys.ENTER);
        driver.findElement(By.id("addMealPeriod")).click();
    }

    // Add sections like Bakery, Carvery to meal periods
    public static void addSectionsToMealPeriods() {
        driver.findElement(By.id("tabFourTab")).click();
        selectMealPeriodAndSection("Breakfast", "Bakery");
        selectMealPeriodAndSection("Crib", "Carvery");
    }

    // Helper method to assign a section to a meal period
    public static void selectMealPeriodAndSection(String mealPeriod, String section) {
        driver.findElement(By.xpath("//span[text()='Select Meal Period']")).click();
        driver.findElement(By.xpath("(//input[@type='search'])[5]")).sendKeys(mealPeriod, Keys.ENTER);

        driver.findElement(By.xpath("//span[text()='Select Section']")).click();
        driver.findElement(By.xpath("(//input[@type='search'])[5]")).sendKeys(section, Keys.ENTER);

        driver.findElement(By.id("MealPeriodSectionAddId")).click();
    }

    // Add a recipe to the menu for a meal period
    public static void addRecipeToMenu(String recipeName) {
        driver.findElement(By.id("tabFiveTab")).click();
        try {
            driver.findElement(By.xpath("(//i[@class='fa fa-chevron-down'])[1]")).click();
            driver.findElement(By.xpath("(//i[@class='fa fa-plus'])[1]")).click();
            WebElement search = driver.findElement(By.id("SearchText"));
            search.clear();
            search.sendKeys(recipeName, Keys.ENTER);
            driver.findElement(By.xpath("(//button[text()='Add'])[4]")).click();
            driver.findElement(By.id("btnAddRecipe")).click();
            System.out.println("Recipe added to menu: " + recipeName);
        } catch (Exception e) {
            System.out.println("Error adding recipe: " + e.getMessage());
        }
    }

    // Publish the recipe to base from current site
    public static void publishRecipe(String recipeName) throws InterruptedException {
        goToRecipePage();
        searchAndClickRecipe(recipeName);
        driver.findElement(By.xpath("//button[text()=' Edit Source Recipe ']")).click();
        driver.findElement(By.xpath("//button[text()='OK']")).click();
        Thread.sleep(3000);
        driver.findElement(By.id("btnPublishtoBase")).click();
        driver.findElement(By.xpath("//button[text()='OK']")).click();
        Thread.sleep(3000);
        System.out.println("Recipe published to base: " + recipeName);
    }

    // Navigate to Recipe page
    public static void goToRecipePage() {
        driver.findElement(By.xpath("(//i[@class='nav-icon fas fa-bowl-rice'])[1]")).click(); // Main recipe
        driver.findElement(By.xpath("(//i[@class='nav-icon fas fa-bowl-rice'])[2]")).click(); // Search recipe
    }

    // Search recipe and click it
    public static void searchAndClickRecipe(String recipeName) {
        WebElement search = driver.findElement(By.id("SearchText"));
        search.clear();
        search.sendKeys(recipeName, Keys.ENTER);
        driver.findElement(By.xpath("//a[contains(text(),'" + recipeName + "')]")).click();
    }

    // Switch to another site and refresh the recipe
    public static void switchToSiteAndRefreshRecipe(String siteName, String recipeName) throws Exception {
        driver.findElement(By.xpath("//i[@class='nav-icon fas fa-home']")).click();
        addSiteGoRecipe(siteName);

        WebElement search = driver.findElement(By.id("SearchText"));
        search.clear();
        search.sendKeys(recipeName, Keys.ENTER);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        WebElement refreshBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(@class, 'refresh2')]")));
        refreshBtn.click();

        System.out.println("Recipe refreshed on site: " + siteName);
    }

    // Final refresh of recipe on main site
    public static void refreshRecipeInSite(String recipeName) {
        WebElement search = driver.findElement(By.id("SearchText"));
        search.clear();
        search.sendKeys(recipeName, Keys.ENTER);

        driver.findElement(By.xpath("//a[contains(text(),'" + recipeName + "')]")).click();
        driver.findElement(By.xpath("//button[normalize-space(text())='Refresh']")).click();
        System.out.println("Recipe manually refreshed in current site.");
    }
}
