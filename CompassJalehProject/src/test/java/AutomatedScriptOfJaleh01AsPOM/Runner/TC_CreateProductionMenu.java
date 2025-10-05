package AutomatedScriptOfJaleh01AsPOM.Runner;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;

import AutomatedScriptOfJaleh01AsPOM.base.BaseTest;
import AutomatedScriptOfJaleh01AsPOM.pages.BreadCrumPage;
import AutomatedScriptOfJaleh01AsPOM.pages.LoginPage;
import AutomatedScriptOfJaleh01AsPOM.pages.SearchRecipeAfterCreation;
import AutomatedScriptOfJaleh01AsPOM.pages.SearchRecipeBeforeCreation;
import AutomatedScriptOfJaleh01AsPOM.pages.Menu.CreateProductionMenu;
import AutomatedScriptOfJaleh01AsPOM.util.ExcelTest;
import AutomatedScriptOfJaleh01AsPOM.util.Util;
import AutomatedScriptOfJaleh01AsPOM.util.myListener;

@Listeners(myListener.class)
public class TC_CreateProductionMenu extends BaseTest {

    @DataProvider(name = "productionMenuDataProvider")
    public Object[][] productionMenuDataProvider() throws IOException {
        List<Map<String, String>> dataList = ExcelTest.readData(
                "E:\\Automation Testing data\\Jaleh\\test1_creation_Master.xlsx",
                "TC_Create Production Menu"
        );

        Object[][] data = new Object[dataList.size()][9];
        for (int i = 0; i < dataList.size(); i++) {
            Map<String, String> row = dataList.get(i);
            data[i][0] = row.get("username");
            data[i][1] = row.get("password");
            data[i][2] = row.get("recipename");
            data[i][3] = row.get("menuname");
            data[i][4] = row.get("sitename");
            data[i][5] = row.get("Menu_Type");
            data[i][6] = row.get("MenuCategory");
            data[i][7] = row.get("EffectiveStartDate").trim();
            data[i][8] = row.get("EffectiveEndDate").trim();
        }
        return data;
    }

    @Test(dataProvider = "productionMenuDataProvider")
    public void testCreateProductionMenu(String username, String password, String recipeName, String menuName,
                                         String siteName, String menuType, String menuCategory,
                                         String effectiveStartDate, String effectiveEndDate) throws Exception {

        // Initialize Page Objects
        LoginPage loginPage = new LoginPage(driver);
        BreadCrumPage breadCrum = new BreadCrumPage(driver);
        CreateProductionMenu productionMenu = new CreateProductionMenu(driver);
        Util util = new Util(driver);
        SearchRecipeBeforeCreation srbc = new SearchRecipeBeforeCreation(driver);
        SearchRecipeAfterCreation srac = new SearchRecipeAfterCreation(driver);
        ExtentTest testLogger = myListener.getTest();

        testLogger.info("🟢 Starting Production Menu creation for: <span style='color:green; font-weight:bold;'>" + menuName + "</span>");

        // Step 1: Login
        loginPage.login(username, password);
        testLogger.info("🔐 Logged in as: " + username);

        // Step 2: Select Site & Navigate
        breadCrum.clickHomeIcon();
        util.selectSite(siteName);
        util.selectMenuRecipe();
        breadCrum.clickHomeIcon();

        // Step 3: Search for menu before creation (to validate absence)
        breadCrum.clickMenus();
        breadCrum.clickProductionMenus();
        srbc.searchProduct(menuName);
        breadCrum.clickHomeIcon();

        // Step 4: Navigate to Menu Creation
        productionMenu.navigateToMenus();
        productionMenu.addMenu(menuName, menuType, menuCategory, effectiveStartDate, effectiveEndDate);
        
        testLogger.pass("✅ Enter MENU TYPE : <span style='color:green; font-weight:bold;'>" + menuType + "</span>");
        testLogger.pass("✅ Enter MENU Category : <span style='color:green; font-weight:bold;'>" + menuCategory + "</span>");
  

        productionMenu.addCustomer();
        productionMenu.addMealPeriods();
        productionMenu.selectMealPeriod();
        productionMenu.addRecipeToMenu(recipeName);
        productionMenu.publishMenu(testLogger);

        testLogger.pass("✅ Menu created and published: <span style='color:green; font-weight:bold;'>" + menuName + "</span>");

        // Step 5: Validate Menu Created
        breadCrum.clickHomeIcon();
        breadCrum.clickMenus();
        breadCrum.clickProductionMenus();
        srac.searchProduct(menuName);
        testLogger.pass("🎯 Menu successfully verified after creation: " + menuName);
    }
}
