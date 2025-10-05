package Jaleh.recipe;

import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;





public class SC001_RefreshRecipe2 extends BaseTest2 {
	/*
	 * String menuname = "MenuA19H"; String sitename2
	 * ="Production | 180800-AFP Majura | 31-Retail 1(Real)"; String sitename1
	 * ="Production | 208150-South32 - Groote Eylandt | 11-Catering(Real)"; String
	 * Recipename="Rec98TY";
	 */
			
	@DataProvider(name = "recipeDataProvider") // ✅ Rename here

	public Object[][] recipeDataProvider() throws IOException {
	    List<Map<String, String>> dataList = readData("E:\\Automation Testing data\\Jaleh\\test1_creation_Master.xlsx", 1);
	    Object[][] data = new Object[dataList.size()][6]; // expects 10 columns

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
		//setup();
		loginToApplication(username, password);
		refreshRecipe(menuname, sitename2, sitename1,recipeName);
		//tearDown();
		 // recipedisplayname,mealtype,cuisine,recipecategory
	}

	
	
	
	
	public static void refreshRecipe (String menuname,String sitename2,String sitename1,String Recipename ) throws Exception {
		
		
		
		clickHome();
		addSiteGoRecipe(sitename2);
		navigateToMenus();
		addmenu(menuname);
		addCustomer();
		addMealPeriod();
		selectMealPeriod();
		addRecipeOnMenu(Recipename);
		
		navigateToSearchRecipe();
		clickOnSearchRecipe(Recipename);
		clickOnlink(Recipename);
		
		//editandPublishRecipe(true);();
		editRecipeonsite();
		publishRecipeonsite();
		clickHome();
		addSiteGoRecipe(sitename1);
		refreshRecipefromList(Recipename);
		clickHome();
		navigateToSearchRecipe();
		clickOnSearchRecipe(Recipename);
		clickOnlink(Recipename);
		//editandPublishRecipe(true);
		editRecipeonsite();
		publishRecipeonsite();
		clickHome();
		addSiteGoRecipe(sitename2);
		clickOnSearchRecipe(Recipename);
		clickOnlink(Recipename);
		refreshRecipefrominside();
		
		
		
		
		
		
		
		
		
		
		
		
	}
	
	
		public static void navigateToMenus() {
			driver.findElement(By.xpath("(//i[@class='nav-icon fas fa-table'])[1]")).click();
			driver.findElement(By.xpath("//i[@class='nav-icon fas fa-utensils']")).click();
		}
		
		//click on Addmenu button
		public static void addmenu(String  menuname) {
		driver.findElement(By.xpath("//button[text()='Add Menu']")).click();
		
		//enter menu name
		
		driver.findElement(By.xpath("//input[@id='MenuName']")).sendKeys(menuname,Keys.ENTER);
		
		
		//enter menutype
		
		WebElement mntype =driver.findElement(By.id("MenuTypeId"));
		
		Select sl1 =new Select(mntype);
		sl1.selectByVisibleText("Template");
		
		
		//enter MenuCategoryId
		
        WebElement mncatid =driver.findElement(By.id("MenuCategoryId"));
		
		Select sl2 =new Select(mncatid);
		sl2.selectByVisibleText("Cyclic");
		
		
		
		
	//click of Weeks/Cycle
		
		driver.findElement(By.id("NoOfWeekPerCycle")).sendKeys("2",Keys.ENTER);
		
		
		
		//click on add button
		
		driver.findElement(By.xpath("//button[text()='Add']")).click();
		
		System.out.println("menu added successfully");
		}
		
		//click on edit 
		
		public static void addCustomer() throws Exception {
		driver.findElement(By.xpath("//button[@id='editCustomerRow_11']")).click();
		
		//clickon add data
		driver.findElement(By.xpath("//tr[@id='tr_W1_Cycle1']//input[@id='editableValue7']")).sendKeys("122");
		
		//click on save 
				driver.findElement(By.xpath("//button[@id='saveCustomerRow_11']")).click();
				
				System.out.println(" Customer added");
				
				Thread.sleep(3000);
		}
		
		
		
//		
//		//click onmore action
//		
//		driver.findElement(By.id("btnMoreAction")).click();
//		
//		//click on delete on button
//		
//		driver.findElement(By.xpath("(//i[@class='fas fa-trash-alt'])[1]")).click();
//		
//		driver.findElement(By.xpath("//button[text()='OK']")).click();
//		
//		System.out.println("menu deleted");
				
				
				
				
				
//click on meal period tab
		public static void addMealPeriod() throws Exception {
				driver.findElement(By.id("tabThreeTab")).click();
				
				  driver.findElement(By.xpath("//span[text()='Add Meal Period']")).click();
			        driver.findElement(By.xpath("(//input[@type='search'])[5]")).sendKeys("Breakfast", Keys.ENTER);
			        driver.findElement(By.id("addMealPeriod")).click();
			        
			        Thread.sleep(3000);
			        driver.findElement(By.xpath("//span[text()='Add Meal Period']")).click();
			        driver.findElement(By.xpath("(//input[@type='search'])[5]")).sendKeys("Crib", Keys.ENTER);
			        driver.findElement(By.id("addMealPeriod")).click();
		}
			        
			        // click on section tab
		public static void selectMealPeriod() {
			        driver.findElement(By.id("tabFourTab")).click();
			        
			        driver.findElement(By.xpath("//span[text()='Select Meal Period']")).click();
			        driver.findElement(By.xpath("(//input[@type='search'])[5]")).sendKeys("Breakfast", Keys.ENTER);
			        
			        // click on Select Section 
			        
			        driver.findElement(By.xpath("//span[text()='Select Section']")).click();
			        driver.findElement(By.xpath("(//input[@type='search'])[5]")).sendKeys("Bakery", Keys.ENTER);
			        
			        driver.findElement(By.id("MealPeriodSectionAddId")).click();
			        
			        
			        driver.findElement(By.xpath("//span[@id='select2-ddlMealPeriodForSec-container']")).click();
			        driver.findElement(By.xpath("(//input[@type='search'])[5]")).sendKeys("Crib", Keys.ENTER);
			        
			        // click on Select Section 
			        
			        driver.findElement(By.xpath("//span[text()='Select Section']")).click();
			        driver.findElement(By.xpath("(//input[@type='search'])[5]")).sendKeys("Carvery", Keys.ENTER);
			        
			        driver.findElement(By.id("MealPeriodSectionAddId")).click();
			        
			        System.out.println("meal period section added successfully");
		}
			        
			        
			        //click on menu Recipe
		public static void addRecipeOnMenu(String Recipename) {
			        driver.findElement(By.id("tabFiveTab")).click();
			      try {  
			        //for breakfast
			    	  
			    	  
			        driver.findElement(By.xpath("(//i[@class='fa fa-chevron-down'])[1]")).click();
			        
			        // click on plus icon
			        
			        driver.findElement(By.xpath("(//i[@class='fa fa-plus'])[1]")).click();
			        
			        // recipe creach an add activiy
			        WebElement srchrcptxt =driver.findElement(By.id("SearchText"));
			        srchrcptxt.clear();
			        
			        srchrcptxt.sendKeys(Recipename,Keys.ENTER);
			        
			        driver.findElement(By.xpath("(//button[text()='Add'])[4]")).click();
			        
			        driver.findElement(By.id("btnAddRecipe")).click();
			      }
			      catch(Exception e){System.out.println(e.getMessage());}
			      System.out.println("Recipe added on menu");
			      
		}
//			     try {   
//			      //for crib  
//                    driver.findElement(By.xpath("(//i[@class='fa fa-chevron-down'])[2]")).click();
//			        
//			        // click on plus icon
//			        
//			        driver.findElement(By.xpath("(//i[@class='fa fa-plus'])[1]")).click();
//			        
//			        // recipe creach an add activiy
//			        
//			        driver.findElement(By.id("SearchText")).sendKeys("maggie743abc",Keys.ENTER);
//			        
//			        driver.findElement(By.xpath("(//button[text()='Add'])[4]")).click();
//			        
//			        driver.findElement(By.id("btnAddRecipe")).click();
//			     }catch(Exception e){System.out.println(e.getMessage());}
//			        // click on moreaction button
//			        
//			        driver.findElement(By.id("btnMoreAction")).click();
//			        driver.findElement(By.xpath("//a[text()='Publish']")).click();
			        
			     // System.out.println("RECIPE ADD IN MENU");
			      //search  main recipe
		public static void navigateToSearchRecipe() {
			      driver.findElement(By.xpath("(//i[@class='nav-icon fas fa-bowl-rice'])[1]")).click();
			      
			      //search recipe
			      driver.findElement(By.xpath("(//i[@class='nav-icon fas fa-bowl-rice'])[2]")).click();
		}
			    
		public static void clickOnSearchRecipe(String Recipename) {
			
			      WebElement searchBox = driver.findElement(By.id("SearchText"));

				    searchBox.clear();
				    searchBox.sendKeys(Recipename, Keys.ENTER);
				    System.out.println("Recipe searched on search bar");
			}
			public static void clickOnlink(String Recipename) {    
				    driver.findElement(By.xpath("//a[contains(text(),'" + Recipename + "')]")).click();
				    
				    System.out.println("click on link");
			
			}
			
			
			
			public static void clickONrefreshicon() {
				
				driver.findElement(By.xpath("//div//a/descendant::i[@class='fas fa-undo']")).click();
				System.out.println("click on undo icon");
				
				driver.findElement(By.xpath("//button[text()='Yes']")).click();
				System.out.println("recipe go out from under edit  section");
				
			}
			
				    
			public static void editRecipeonsite() {
				/*
				 * WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
				 * WebElement editButton = wait.until(ExpectedConditions.elementToBeClickable(
				 * By.xpath("//button[normalize-space(text())='Edit Source Recipe']")));
				 * editButton.click();
				 */
				        driver.findElement(By.xpath("//button[normalize-space(text())='Edit Source Recipe']")).click();
				        // If workflow B, click OK after editing
				       
						/*
						 * WebElement okAfterEdit = wait.until(ExpectedConditions.elementToBeClickable(
						 * By.xpath("//button[text()='OK']"))); okAfterEdit.click();
						 */
				            driver.findElement(By.xpath("//button[text()='OK']")).click();

				        System.out.println("Recipe edited on site");
				
			}
			
			public static void publishRecipeonsite() {
				/*
				 * WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
				 * WebElement publishButton =
				 * wait.until(ExpectedConditions.elementToBeClickable(
				 * By.xpath("//button[contains(text(),'Publish Changes to Source')]")));
				 * publishButton.click();
				 */
				        driver.findElement(By.xpath("//button[contains(text(),'Publish Changes to Source')]")).click();

				        // Wait and click "OK"
//				        WebElement okAfterPublish = wait.until(ExpectedConditions.elementToBeClickable(
//				            By.xpath("//button[text()='OK']")));
//				        okAfterPublish.click();
				        driver.findElement(By.xpath("//button[text()='OK']")).click();
				        System.out.println("Recipe published on site");
			}
		
//		public static void editandPublishRecipe(boolean clickOKAfterEdit) throws Exception {
//		    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//
//		    try {
//		        // Click "Edit Source Recipe"
//		        WebElement editButton = wait.until(ExpectedConditions.elementToBeClickable(
//		            By.xpath("//button[normalize-space(text())='Edit Source Recipe']")));
//		        editButton.click();
//
//		        // If workflow B, click OK after editing
//		        if (clickOKAfterEdit) {
//		            WebElement okAfterEdit = wait.until(ExpectedConditions.elementToBeClickable(
//		                By.xpath("//button[text()='OK']")));
//		            okAfterEdit.click();
//		        }
//
//		        System.out.println("Recipe edited");
//
//		        // Wait and click "Publish Changes to Source"
//		        WebElement publishButton = wait.until(ExpectedConditions.elementToBeClickable(
//		            By.xpath("//button[contains(text(),'Publish Changes to Source')]")));
//		        publishButton.click();
//
//		        // Wait and click "OK"
//		        WebElement okAfterPublish = wait.until(ExpectedConditions.elementToBeClickable(
//		            By.xpath("//button[text()='OK']")));
//		        okAfterPublish.click();
//
//		        System.out.println("Recipe published");
//
//		    } catch (Exception e) {
//		        System.err.println("Error in editandPublishRecipe: " + e.getMessage());
//		    }
//
//		    System.out.println("RECPED UPDATED from site2");
//		}

				    
				    
					/*
					 * //search main recipe
					 * 
					 * driver.findElement(By.xpath("(//i[@class='nav-icon fas fa-bowl-rice'])[1]")).
					 * click();
					 * 
					 * //search recipe
					 * driver.findElement(By.xpath("(//i[@class='nav-icon fas fa-bowl-rice'])[2]")).
					 * click();
					 */
				    
				   // driver.findElement(By.xpath("//i[@class='nav-icon fas fa-home']")).click();
				  //  addSiteGoRecipe(sitename1);
				   // System.out.println("SEARCH IN GROOT");
				    
				    public  static void refreshRecipefromList(String Recipename ) throws Exception {
				   WebElement  searchBox = driver.findElement(By.id("SearchText"));

				    searchBox.clear();
				    searchBox.sendKeys(Recipename, Keys.ENTER);
				    
				    //click to refresh button
				    
				    Thread.sleep(5000);
				    
				   
				    	
				    
				    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
				    WebElement refreshBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(@class, 'refresh2')]")));
				    refreshBtn.click();
				    
				    System.out.println("refreshed by sitename1");
				    }

				    
				    //next process
				    //search  main recipe
				    
				  //search main recipe
				   // driver.findElement(By.xpath("//i[@class='nav-icon fas fa-home']")).click();
					
					  
					/*
					 * driver.findElement(By.xpath("(//i[@class='nav-icon fas fa-bowl-rice'])[1]")).
					 * click();
					 * 
					 * //search recipe
					 * driver.findElement(By.xpath("(//i[@class='nav-icon fas fa-bowl-rice'])[2]")).
					 * click();
					 */
					// System.out.println("search recipe page");
				      
				   
						/*
						 * searchBox = driver.findElement(By.id("SearchText"));
						 * 
						 * searchBox.clear(); searchBox.sendKeys(Recipename, Keys.ENTER);
						 * 
						 * driver.findElement(By.xpath("//a[contains(text(),'" + Recipename +
						 * "')]")).click();
						 */
				    
//				    driver.findElement(By.xpath("//button[text()=' Edit Source Recipe ']")).click();
//				    driver.findElement(By.xpath("//button[text()='OK']")).click();
//				    
//				    Thread.sleep(3000);
//				    driver.findElement(By.id("btnPublishtoBase")).click();
//				    driver.findElement(By.xpath("//button[text()='OK']")).click();
//				    Thread.sleep(3000);
//				    driver.findElement(By.xpath("//i[@class='nav-icon fas fa-home']")).click();
//				    addSiteGoRecipe(sitename2);
				    
					/*
					 * //search main recipe
					 * 
					 * driver.findElement(By.xpath("(//i[@class='nav-icon fas fa-bowl-rice'])[1]")).
					 * click();
					 * 
					 * //search recipe
					 * driver.findElement(By.xpath("(//i[@class='nav-icon fas fa-bowl-rice'])[2]")).
					 * click();
					 */
					/*
					 * searchBox = driver.findElement(By.id("SearchText")); searchBox.clear();
					 * searchBox.sendKeys(Recipename, Keys.ENTER);
					 * 
					 * driver.findElement(By.xpath("//a[contains(text(),'" + Recipename +
					 * "')]")).click();
					 * 
					 * Thread.sleep(5000);
					 */
				    
				    public static void refreshRecipefrominside() {
					    driver.findElement(By.xpath("//button[normalize-space(text())='Refresh']")).click();
					    System.out.println("refreshed by site2");
				    }
					    
				    
				    
			
	
	public static void addSiteGoRecipe(String siteName) throws Exception {
	    // Enter site name in the search field and press Enter
	    driver.findElement(By.id("txtSearchOrgUnit")).sendKeys(siteName, Keys.ENTER);

	    // Build dynamic XPath for the radio button based on the site name
	    String xpath = "//div[@class='list-field']//label[text()='" + siteName + "']/preceding-sibling::input[@type='radio']";

	    // Locate the radio button
	    WebElement radioButton = driver.findElement(By.xpath(xpath));

	    // Click the radio button if it's not already selected
	    if (!radioButton.isSelected()) {
	        radioButton.click();
	        
	    }
	    System.out.println("site selected");
	    Thread.sleep(3000);
	    driver.findElement(By.id("spnCountRecipes")).click();
	}
	
	

}
