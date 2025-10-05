package Jaleh.recipe;


import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(myListener2.class)
public class SC001_RefreshRecipe_D extends BaseTest2 {

    @DataProvider(name = "recipeDataProvider")
    public Object[][] recipeDataProvider() throws IOException {
        List<Map<String, String>> dataList = readData("E:\\Automation Testing data\\Jaleh\\test1_creation_Master.xlsx", 1);
        Object[][] data = new Object[dataList.size()][6];
        for (int i = 0; i < dataList.size(); i++) {
            Map<String, String> row = dataList.get(i);
            data[i][0] = row.get("username");
            data[i][1] = row.get("password");
            data[i][2] = row.get("recipename");
            data[i][3] = row.get("menuname");
            data[i][4] = row.get("sitename2");
            data[i][5] = row.get("sitename1");
        }
        return data;
    }

    @Test(dataProvider = "recipeDataProvider")
    public void Test_refreshRecipe(String username, String password, String recipeName, String menuname, String sitename2, String sitename1) throws Throwable {
        try {
            loginToApplication(username, password);
            refreshRecipe(menuname, sitename2, sitename1, recipeName);
        } catch (Exception e) {
            System.out.println("Error in test method: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void refreshRecipe(String menuname, String sitename2, String sitename1, String Recipename) {
        try {
            clickHome();
            addSiteGoRecipe(sitename2);
            clickOnMenuRecipe();
            clickOnSearchRecipe(Recipename);
            clickOnlink(Recipename);
            editRecipeonsite();
            publishRecipeonsite();

            clickHome();
            addSiteGoRecipe(sitename1);
            clickOnMenuRecipe();
            clickOnSearchRecipe(Recipename);
            refreshRecipefromList();

            clickHome();
            addSiteGoRecipe(sitename1);
            clickOnMenuRecipe();
            clickOnSearchRecipe(Recipename);
            clickOnlink(Recipename);
            editRecipeonsite();
            publishRecipeonsite();

            clickHome();
            addSiteGoRecipe(sitename2);
            clickOnMenuRecipe();
            clickOnSearchRecipe(Recipename);
            clickOnlink(Recipename);
            refreshRecipefrominside();

        } catch (Exception e) {
            System.out.println("Exception in refreshRecipe: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void addSiteGoRecipe(String siteName) {
        try {
            driver.findElement(By.id("txtSearchOrgUnit")).sendKeys(siteName, Keys.ENTER);
            String xpath = "//div[@class='list-field']//label[text()='" + siteName + "']/preceding-sibling::input[@type='radio']";
            WebElement radioButton = driver.findElement(By.xpath(xpath));
            if (!radioButton.isSelected()) {
                radioButton.click();
            }
            System.out.println("site selected: " + siteName);
            Thread.sleep(3000);
        } catch (Exception e) {
            System.out.println("Error in addSiteGoRecipe: " + e.getMessage());
        }
    }

    public static void clickOnMenuRecipe() {
        try {
            driver.findElement(By.id("spnCountRecipes")).click();
            System.out.println("Clicked on menu recipe");
        } catch (Exception e) {
            System.out.println("Error in clickOnMenuRecipe: " + e.getMessage());
        }
    }

    public static void clickOnSearchRecipe(String Recipename) {
        try {
            WebElement searchBox = driver.findElement(By.id("SearchText"));
            searchBox.clear();
            searchBox.sendKeys(Recipename, Keys.ENTER);
            System.out.println("Recipe searched: " + Recipename);
        } catch (Exception e) {
            System.out.println("Error in clickOnSearchRecipe: " + e.getMessage());
        }
    }

    public static void clickOnlink(String Recipename) {
        try {
        	Thread.sleep(4000);
            driver.findElement(By.xpath("//a[contains(text(),'" + Recipename + "')]")).click();
            System.out.println("Clicked on recipe link: " + Recipename);
        } catch (Exception e) {
            System.out.println("Error in clickOnlink: " + e.getMessage());
        }
    }

    public static void editRecipeonsite() {
        try {
            driver.findElement(By.xpath("//button[normalize-space(text())='Edit Source Recipe']")).click();
            driver.findElement(By.xpath("//button[text()='OK']")).click();
            Thread.sleep(5000);
            System.out.println("Recipe edited on site");
        } catch (Exception e) {
            System.out.println("Error in editRecipeonsite: " + e.getMessage());
        }
    }

    public static void publishRecipeonsite() {
        try {
            driver.findElement(By.xpath("//button[contains(text(),'Publish Changes to Source')]")).click();
            driver.findElement(By.xpath("//button[text()='OK']")).click();
            Thread.sleep(5000);
            System.out.println("Recipe published on site");
        } catch (Exception e) {
            System.out.println("Error in publishRecipeonsite: " + e.getMessage());
        }
    }

    public static void refreshRecipefromList() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            WebElement refreshBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(@class, 'refresh2')]")));
            refreshBtn.click();
            System.out.println("Refreshed recipe from list");
        } catch (Exception e) {
            System.out.println("Error in refreshRecipefromList: " + e.getMessage());
        }
    }

    public static void refreshRecipefrominside() {
        try {
            driver.findElement(By.xpath("//button[normalize-space(text())='Refresh']")).click();
            System.out.println("Refreshed recipe from inside");
        } catch (Exception e) {
            System.out.println("Error in refreshRecipefrominside: " + e.getMessage());
        }
    }

    public static void clickHome() {
        try {
            driver.findElement(By.xpath("//i[@class='nav-icon fas fa-home']")).click();
            System.out.println("Clicked home");
        } catch (Exception e) {
            System.out.println("Error in clickHome: " + e.getMessage());
        }
    }
}
