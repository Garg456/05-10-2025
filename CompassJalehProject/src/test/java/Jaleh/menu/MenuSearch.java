package Jaleh.menu;

import org.openqa.selenium.*;
import org.testng.Assert;
import org.testng.annotations.Test;

public class MenuSearch {

	@Test
	public void TC_MenuSearch()throws Exception {
        CommonActions common = new CommonActions();
        common.setupDriver();
        common.loginToURL();
        common.clickRelogin();
        common.goToHome();
        common.goToMenuSection();
        common.goToUnpublishedMenus();

        String recipeName = "maggie411";
        WebElement searchBox = common.driver.findElement(By.id("SearchText"));
        searchBox.clear();
        searchBox.sendKeys(recipeName, Keys.ENTER);
        Thread.sleep(3000);

        try {
            String result = common.driver.findElement(By.xpath("//td[text()='No record found']")).getText();
            Assert.assertNotEquals(result, recipeName, "Recipe not found.");
            System.out.println("Search result: " + result);
        } catch (NoSuchElementException e) {
            System.out.println("Recipe found: " + recipeName);
        }

        common.tearDown();
    }
}

