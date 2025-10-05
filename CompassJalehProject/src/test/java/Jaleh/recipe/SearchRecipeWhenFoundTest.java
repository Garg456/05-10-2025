
package Jaleh.recipe;


import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.testng.Assert;

public class SearchRecipeWhenFoundTest extends BaseTest {

    public  void Test_srch_recipe_after_create ()throws Exception {
        SearchRecipeWhenFoundTest test = new SearchRecipeWhenFoundTest();
        test.setup();
        test.loginToApplication("admin-path15@compass-external.com.au", "Path@0307202451");
        test.searchRecipe("maggie409");
        test.tearDown();
    }

    public void searchRecipe(String recipeName) throws Exception {
        clickHome();
        clickRecipeMain();
        //clickSearchRecipe();
        driver.findElement(By.xpath("//i[@class='nav-icon fa-brands fa-firstdraft']")).click(); 

        driver.findElement(By.id("SearchText")).sendKeys(recipeName, Keys.ENTER);
        Thread.sleep(3000);

        String actualRecipeName = driver.findElement(By.xpath("//a[contains(text(), '" + recipeName + "')]")).getText();
        Assert.assertEquals(actualRecipeName, recipeName, "Recipe name does not match");
        System.out.println("Recipe found: " + actualRecipeName);
    }
}
