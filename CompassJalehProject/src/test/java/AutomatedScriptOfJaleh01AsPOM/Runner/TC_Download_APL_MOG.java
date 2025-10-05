package AutomatedScriptOfJaleh01AsPOM.Runner;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import AutomatedScriptOfJaleh01AsPOM.base.BaseTest;
import AutomatedScriptOfJaleh01AsPOM.pages.BreadCrumPage;
import AutomatedScriptOfJaleh01AsPOM.pages.Download_APL_MOG;
import AutomatedScriptOfJaleh01AsPOM.pages.LoginPage;
import AutomatedScriptOfJaleh01AsPOM.util.ExcelTest;
import AutomatedScriptOfJaleh01AsPOM.util.Util;

public class TC_Download_APL_MOG extends BaseTest {
	
	 @DataProvider(name = "recipeDataProvider")
	    public Object[][] recipeDataProvider() throws IOException {
	        List<Map<String, String>> dataList = ExcelTest.readData(
	            "E:\\Automation Testing data\\Jaleh\\test1_creation_Master.xlsx",
	            "TC_Create Menu Report"
	        );

	        Object[][] data = new Object[dataList.size()][3];

	        for (int i = 0; i < dataList.size(); i++) {
	            Map<String, String> row = dataList.get(i);
	            data[i][0] = row.get("username");
	            data[i][1] = row.get("password");
	            data[i][2] = row.get("sitename");
	        }

	        return data;
	    }

	    @Test(dataProvider = "recipeDataProvider")
	    public void testMenuReportDownload(String username, String password, String sitename) throws Exception {
	        // Page Object Instances
	        LoginPage loginPage = new LoginPage(driver);
	        BreadCrumPage breadCrumPage = new BreadCrumPage(driver);
	        Download_APL_MOG D_APL_MOG = new Download_APL_MOG(driver);
	        Util util = new Util(driver);

	        // Step 1: Login
	        loginPage.login(username, password);

	        // Step 2: Home > Site > Menu Recipes
	        breadCrumPage.clickHomeIcon();
	        util.selectSite(sitename);
	        util.selectMenuRecipe();

	        // Step 3: Navigate to Menu Report
	        breadCrumPage.clickHomeIcon();
	        breadCrumPage.clickproducts();
	        breadCrumPage.Products_APL_MOG();
	        D_APL_MOG.validationPriceOrWithoutPriceOnMenuRecipe();

	     
	    }
	}



