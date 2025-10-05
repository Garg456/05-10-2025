package Jaleh.menu;

import org.openqa.selenium.*;
import org.testng.annotations.Test;

public class MenuDelete {
	
	
@Test
    public void TC_MenuDelete() throws InterruptedException {
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
        
        

        common.driver.findElement(By.id("btnMoreAction")).click();
        common.driver.findElement(By.xpath("(//i[@class='fas fa-trash-alt'])[1]")).click();
        common.driver.findElement(By.xpath("//button[text()='OK']")).click();

        System.out.println("Menu deleted.");
        Thread.sleep(2000);

        common.tearDown();
    }
}


