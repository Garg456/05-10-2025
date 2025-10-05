
package Jaleh.recipe;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.testng.Assert;

public class SearchRecipeWhenNotFoundTest extends BaseTest {

    public  void  Test_srch_recipe_before_create() throws Exception {
        SearchRecipeWhenNotFoundTest test = new SearchRecipeWhenNotFoundTest();
        test.setup();
        test.loginToApplication("admin-path15@compass-external.com.au", "Path@0307202451");
        test.searchRecipe("maggie409");
        test.tearDown();
    }

    public void searchRecipe(String recipeName) throws Exception {
        clickHome();
        clickRecipeMain();
        clickSearchRecipe();

        driver.findElement(By.id("SearchText")).sendKeys(recipeName, Keys.ENTER);
        Thread.sleep(3000);

        String message = driver.findElement(By.xpath("//td[text()='No record found']")).getText();
        Assert.assertEquals(message, "No record found", "Expected 'No record found' message not displayed.");
        System.out.println("Recipe not found as expected.");
    }
}
