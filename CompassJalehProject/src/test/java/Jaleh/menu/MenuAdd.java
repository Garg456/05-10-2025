package Jaleh.menu;


import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;

public class MenuAdd {

	@Test
    public void TC_MenuAdd()throws Exception {
        CommonActions common = new CommonActions();
        common.setupDriver();
        common.loginToURL();
        common.clickRelogin();
        common.goToHome();
        common.goToMenuSection();
        common.goToUnpublishedMenus();

        String menuName = "maggie411";
        common.driver.findElement(By.xpath("//button[text()='Add Menu']")).click();
        common.driver.findElement(By.id("MenuName")).sendKeys(menuName, Keys.ENTER);

        Select type = new Select(common.driver.findElement(By.id("MenuTypeId")));
        type.selectByVisibleText("Template");

        Select category = new Select(common.driver.findElement(By.id("MenuCategoryId")));
        category.selectByVisibleText("Cyclic");

        common.driver.findElement(By.id("NoOfWeekPerCycle")).sendKeys("2", Keys.ENTER);
        common.driver.findElement(By.xpath("//button[text()='Add']")).click();

        System.out.println("Menu added.");
        
        
        
        common.tearDown();
    }
}

