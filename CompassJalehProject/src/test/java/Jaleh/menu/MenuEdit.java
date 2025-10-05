package Jaleh.menu;

import org.openqa.selenium.*;
import org.testng.annotations.Test;

public class MenuEdit {

	@Test
    public void TC_MenuEdit()throws Exception {
        CommonActions common = new CommonActions();
        common.setupDriver();
        common.loginToURL();
        common.clickRelogin();
        common.goToHome();
        common.goToMenuSection();
        common.goToUnpublishedMenus();
        String menuName = "maggie411";
        
        common.searchMenuByName(menuName);
        common.clickMenuByName(menuName);
        

        // NOTE: Make sure to filter/select the menu before this if required

        common.driver.findElement(By.xpath("//button[@id='editCustomerRow_11']")).click();
        common.driver.findElement(By.xpath("//tr[@id='tr_W1_Cycle1']//input[@id='editableValue7']")).sendKeys("122");
        common.driver.findElement(By.xpath("//button[@id='saveCustomerRow_11']")).click();

        System.out.println("Menu edited.");
        Thread.sleep(2000);

        common.tearDown();
    }
}
