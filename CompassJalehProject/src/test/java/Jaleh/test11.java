package Jaleh;



import java.time.Duration;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import io.github.bonigarcia.wdm.WebDriverManager;

public class test11 {

    static WebDriver driver;

    public static void main(String[] args) throws Exception {
        String menuname = "maggie911";

        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();

        String username = "admin-path15@compass-external.com.au";
        String password = "Path@0307202451";
        String URL = "http://" + username + ":" + password + "@" + "cgrrweb01t:8086/Menu/PublishedMenuList";

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        driver.get(URL);

        clickRelogin();
        goToHome();
        goToMenu();
        openUnpublishedMenus();
        clickAddMenu();
        enterMenuName(menuname);
        selectMenuType("Template");
        selectMenuCategory("Cyclic");
        enterWeeksPerCycle("2");
        clickAdd();
        editMenuRow();
        enterCycleValue("122");
        saveMenuRow();
        System.out.println("data saved");
        Thread.sleep(3000);
        openMoreActions();
        deleteMenu();
        System.out.println("menu deleted");

        driver.quit();
    }

    // Methods for each WebElement action

    public static void clickRelogin() {
        driver.findElement(By.xpath("//a[text()=' Re-Login']")).click();
    }

    public static void goToHome() {
        driver.findElement(By.xpath("//i[@class='nav-icon fas fa-home']")).click();
    }

    public static void goToMenu() {
        driver.findElement(By.xpath("(//i[@class='nav-icon fas fa-table'])[1]")).click();
    }

    public static void openUnpublishedMenus() {
        driver.findElement(By.xpath("//i[@class='nav-icon fas fa-utensils']")).click();
    }

    public static void clickAddMenu() {
        driver.findElement(By.xpath("//button[text()='Add Menu']")).click();
    }

    public static void enterMenuName(String menuname) {
        driver.findElement(By.id("MenuName")).sendKeys(menuname, Keys.ENTER);
    }

    public static void selectMenuType(String type) {
        WebElement mntype = driver.findElement(By.id("MenuTypeId"));
        Select sl = new Select(mntype);
        sl.selectByVisibleText(type);
    }

    public static void selectMenuCategory(String category) {
        WebElement mncatid = driver.findElement(By.id("MenuCategoryId"));
        Select sl = new Select(mncatid);
        sl.selectByVisibleText(category);
    }

    public static void enterWeeksPerCycle(String weeks) {
        driver.findElement(By.id("NoOfWeekPerCycle")).sendKeys(weeks, Keys.ENTER);
    }

    public static void clickAdd() {
        driver.findElement(By.xpath("//button[text()='Add']")).click();
    }

    public static void editMenuRow() {
        driver.findElement(By.xpath("//button[@id='editCustomerRow_11']")).click();
    }

    public static void enterCycleValue(String value) {
        driver.findElement(By.xpath("//tr[@id='tr_W1_Cycle1']//input[@id='editableValue7']")).sendKeys(value);
    }

    public static void saveMenuRow() {
        driver.findElement(By.xpath("//button[@id='saveCustomerRow_11']")).click();
    }

    public static void openMoreActions() {
        driver.findElement(By.id("btnMoreAction")).click();
    }

    public static void deleteMenu() {
        driver.findElement(By.xpath("(//i[@class='fas fa-trash-alt'])[1]")).click();
        driver.findElement(By.xpath("//button[text()='OK']")).click();
    }
}
