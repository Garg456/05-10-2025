package AutomatedScriptOfJaleh01AsPOM.Runner;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;

import AutomatedScriptOfJaleh01AsPOM.pages.SearchRecipeAfterCreation;
import AutomatedScriptOfJaleh01AsPOM.pages.SearchRecipeBeforeCreation;
import AutomatedScriptOfJaleh01AsPOM.base.BaseTest;
import AutomatedScriptOfJaleh01AsPOM.pages.BreadCrumPage;
import AutomatedScriptOfJaleh01AsPOM.pages.CreateRecipe;
import AutomatedScriptOfJaleh01AsPOM.pages.LoginPage;
import AutomatedScriptOfJaleh01AsPOM.util.ExcelTest;
import AutomatedScriptOfJaleh01AsPOM.util.Util;
import AutomatedScriptOfJaleh01AsPOM.util.myListener;

@Listeners(myListener.class)
public class TC_CreateRecipe extends BaseTest  {

    @DataProvider(name = "recipeDataProvider")
    public Object[][] recipeDataProvider() throws IOException {
        List<Map<String, String>> dataList = ExcelTest.readData(
                "E:\\Automation Testing data\\Jaleh\\test1_creation_Master.xlsx",
                "TC_Create Recipe"
        );

        Object[][] data = new Object[dataList.size()][10];
        for (int i = 0; i < dataList.size(); i++) {
            Map<String, String> row = dataList.get(i);
            data[i][0] = row.get("username");
            data[i][1] = row.get("password");
            data[i][2] = row.get("recipename");
            data[i][3] = row.get("productdescription");
            data[i][4] = row.get("ingredientgroup");
            data[i][5] = row.get("recipedisplayname");
            data[i][6] = row.get("mealtype");
            data[i][7] = row.get("cuisine");
            data[i][8] = row.get("recipecategory");
            data[i][9] = row.get("publishType");
        }
        return data;
    }

    @Test(dataProvider = "recipeDataProvider")
    public void testCreateRecipe(String username, String password, String recipeName, String productDescription,
                                 String ingredientGroup, String recipeDisplayName, String mealType,
                                 String cuisine, String recipeCategory, String publishType) throws Throwable {

        // Initialize page objects
        LoginPage loginPage = new LoginPage(driver);
        BreadCrumPage breadCrumPage = new BreadCrumPage(driver);
        Util util = new Util(driver);
        CreateRecipe cr = new CreateRecipe();

        ExtentTest testLogger = myListener.getTest();
        testLogger.info("🔰 Starting test for: <span style='color:green; font-weight:bold;'>" + recipeName + "</span>");

        // Step 1: Login
        loginPage.login(username, password);
        testLogger.info("🔐 Logged in as: " + username);

        // Step 2: Navigate to home & select site
        breadCrumPage.clickHomeIcon();
        util.selectSite("Production | 208150-South32 - Groote Eylandt | 11-Catering(Real)");
        util.selectMenuRecipe();
        
        breadCrumPage.clickHomeIcon();
        breadCrumPage.clickMainRecipe();
        
        if (publishType.equalsIgnoreCase("DRAFT")) {
        	breadCrumPage.clickNewRecipeDraft();
		} else {
			breadCrumPage.openSearchRecipe();
		}
        
        
        SearchRecipeBeforeCreation srbc = new SearchRecipeBeforeCreation(driver);
		  srbc.searchProduct(recipeName);
		  breadCrumPage.clickHomeIcon();
        // Step 3: Navigate to Recipe
        breadCrumPage.clickMainRecipe();
        breadCrumPage.clickNewRecipeDraft();
        
        cr.clickOnAddRecipe();

        // Step 4: Enter Recipe Details
        cr.enterRecipeName(recipeName);
        cr.enterRecipeDisplayName(recipeDisplayName);
        cr.enterRecipeSearchTag("tag_" + recipeName);
        testLogger.info("🔰 Enter Recipetag name: <span style='color:green; font-weight:bold;'>" + recipeName + "</span>");
        cr.selectMealType(mealType);
        testLogger.info("🔰 Enter Meal Type: <span style='color:green; font-weight:bold;'>" + mealType + "</span>");

        cr.selectCuisine(cuisine);
        testLogger.info("🔰 Enter Cuisine: <span style='color:green; font-weight:bold;'>" + cuisine + "</span>");

        cr.selectRecipeCategory(recipeCategory);
        testLogger.info("🔰 Enter Recipe Category: <span style='color:green; font-weight:bold;'>" + recipeCategory + "</span>");

        
        cr.enterPortionSize("half");
        cr.enterDietaryInfo("ESS OR - PLUS");
        cr.enterRecipeDescription("Auto-generated recipe");
        cr.enterPortion("1");

        cr.scrollDown();
        cr.selectProductDescription(productDescription);
        testLogger.info("🔰 Enter Product Description: <span style='color:green; font-weight:bold;'>" + productDescription + "</span>");

        cr.enterIngredientGroup(ingredientGroup);
        testLogger.info("🔰 Enter Ingredient Group: <span style='color:green; font-weight:bold;'>" + ingredientGroup + "</span>");

        cr.clickAddIngredientSymbol();
        cr.scrollUp();

        // Step 5: Publish Recipe
        cr.publishRecipe(publishType);
        testLogger.info("✅ Recipe published as: " + publishType);

        // Step 6: Validate Recipe Creation
        breadCrumPage.clickHomeIcon();
        breadCrumPage.clickMainRecipe();
        
        if (publishType.equalsIgnoreCase("DRAFT")) {
        	breadCrumPage.clickNewRecipeDraft();
		} else {
			breadCrumPage.openSearchRecipe();
		}
        	
        SearchRecipeAfterCreation srac = new SearchRecipeAfterCreation(driver);
		  
		  srac.searchProduct(recipeName);
        
		  testLogger.pass("✅ Recipe successfully created and verified: " + 
				    "<span style='color:green; font-weight:bold;'>" + recipeName + "</span>"+ " as "+ "<span style='color:green; font-weight:bold;'>" + publishType + "</span>");
    }
}
