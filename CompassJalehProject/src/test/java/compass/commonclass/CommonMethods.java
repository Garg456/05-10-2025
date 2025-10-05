package compass.commonclass;

import java.time.Duration;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class CommonMethods {

    protected WebDriver driver;

    // ✅ Constructor
    public CommonMethods(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);  // initialize all @FindBy elements
        this.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    // ===========================
    // ✅ Web Elements Using @FindBy
    // ===========================

    @FindBy(xpath = "//a[text()=' Re-Login']")
    private WebElement reloginButton;

    @FindBy(xpath = "//i[@class='nav-icon fas fa-home']")
    private WebElement homeIcon;

    @FindBy(xpath = "(//i[@class='nav-icon fas fa-table'])[1]")
    private WebElement menuSectionIcon;

    @FindBy(xpath = "//i[@class='nav-icon fas fa-utensils']")
    private WebElement unpublishedMenusIcon;

    @FindBy(xpath = "(//i[@class='nav-icon fas fa-bowl-rice'])[1]")
    private WebElement recipeMainIcon;

    @FindBy(xpath = "(//i[@class='nav-icon fas fa-bowl-rice'])[2]")
    private WebElement searchRecipeIcon;

    @FindBy(xpath = "//i[@class='nav-icon fa-brands fa-firstdraft']")
    private WebElement newRecipeDraftIcon;

    // ===========================
    // ✅ Actions/Methods
    // ===========================

    public void loginToApplication(String username, String password) {
        String URL = "http://" + username + ":" + password + "@" + "cgrrweb01t:8086/Menu/PublishedMenuList";
        driver.get(URL);
        clickRelogin();
    }

    public void clickRelogin() {
        try {
            reloginButton.click();
        } catch (Exception e) {
            System.out.println("Re-login not present. Skipping...");
        }
    }

    public void clickHome() {
        homeIcon.click();
    }

    public void clickMenuSection() {
        menuSectionIcon.click();
    }

    public void clickUnpublishedMenus() {
        unpublishedMenusIcon.click();
    }

    public void clickRecipeMain() {
        recipeMainIcon.click();
    }

    public void clickSearchRecipe() {
        searchRecipeIcon.click();
    }

    public void clickOnNewRecipeDraft() {
        newRecipeDraftIcon.click();
    }

    public void searchById(String id, String text) {
        WebElement searchBox = driver.findElement(By.id(id));
        searchBox.clear();
        searchBox.sendKeys(text, Keys.ENTER);
    }

    public void selectFromDropdownById(String id, String visibleText) {
        WebElement dropdown = driver.findElement(By.id(id));
        new Select(dropdown).selectByVisibleText(visibleText);
    }

    public void wait(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void clickElement(By locator) {
        try {
            driver.findElement(locator).click();
        } catch (NoSuchElementException e) {
            System.out.println("Element not found: " + locator.toString());
        }
    }

    public void sendKeys(By locator, String text) {
        try {
            WebElement element = driver.findElement(locator);
            element.clear();
            element.sendKeys(text, Keys.ENTER);
        } catch (Exception e) {
            System.out.println("Failed to send keys to element: " + locator.toString());
        }
    }

    public void clickByText(String text) {
        driver.findElement(By.xpath("//*[text()='" + text + "']")).click();
    }

    public void clickLinkContainingText(String partialText) {
        driver.findElement(By.xpath("//a[contains(text(),'" + partialText + "')]")).click();
    }

    public boolean isElementVisible(By locator) {
        try {
            return driver.findElement(locator).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }
}
