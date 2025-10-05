package AutomatedScriptOfJaleh01AsPOM;




	import java.io.IOException;
	import java.text.ParseException;
	import java.text.SimpleDateFormat;
	import java.time.Duration;
	import java.util.Date;
	import java.util.List;
	import java.util.Map;

	import org.openqa.selenium.By;
	import org.openqa.selenium.JavascriptExecutor;
	import org.openqa.selenium.Keys;
	import org.openqa.selenium.WebElement;
	import org.openqa.selenium.support.ui.ExpectedConditions;
	import org.openqa.selenium.support.ui.WebDriverWait;
	import org.testng.annotations.DataProvider;
	import org.testng.annotations.Test;

	public class std_menureport extends BaseTest2  {

		
		
		
		public static void menureport(String sitename ) throws Exception {
			
			clickHome();
			addSiteGoRecipe(sitename);
			selectMenuRecipe();
			
			clickHome();
			clickMenus();
			clickProductionMenus();
			clickONMenuReport();
		}
			
			
			
			
			
			
		 protected static void clickHome() {
		        driver.findElement(By.xpath("//i[@class='nav-icon fas fa-home']")).click();
		    }
			
			
		
		
		 public static void clickONMenuReport( ) throws Exception {
			 
			 
			 WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		        
		        // Wait for the search input box to be clickable and ready
		        WebElement srchRecipe = wait.until(ExpectedConditions.elementToBeClickable(By.id("SearchText")));
		        
		        //WebElement srchRecipe=driver.findElement(By.xpath("//div[contains(@class, 'search-button')]//input[2]"));
		        // Clear any existing text in the search box
		        srchRecipe.clear();
		        
		        // Enter the recipe name and press ENTER
		        System.out.println("Typing in search box: ");
		        srchRecipe.sendKeys("ESS OR - Spring 2025 - FINAL",Keys.ENTER);
		        System.out.println("Search submitted.");
		        Thread.sleep(3000);
//		        WebElement recipeLink = wait.until(
//		        	    ExpectedConditions.visibilityOfElementLocated(
//		        	        By.xpath("//a[contains(normalize-space(text()), '" + "ESS OR - Autumn -Winter 2025 " + "')]")
//		        	    )
//		        	);
		        
		      //  recipeLink.click();
		        

	            // Click dropdown
	            WebElement dropdownBtn = wait.until(ExpectedConditions.elementToBeClickable(
	                    By.xpath("//div[contains(@class,'action-buttons btn-group')]//button")));
	            dropdownBtn.click();

	            System.out.println("drop down clicked");
	         // Click 'edit button'
	            WebElement editOption = wait.until(ExpectedConditions.elementToBeClickable(
	                    By.xpath("//div[@title='Edit']//a")));
	            editOption.click();
	Thread.sleep(3000);
	            // Click 'OK' on confirmation
	            WebElement okButton = wait.until(ExpectedConditions.elementToBeClickable(
	                    By.xpath("//button[text()='OK']")));
	            okButton.click();
	            
	            Thread.sleep(3000);
	            System.out.println("click ok button");
	            
	            
	            
	            
		        System.out.println("click on menu link");
		        
		        
		        driver.findElement(By.id("tabFiveTab")).click();
		        
		        System.out.println("click on menu recipe");
		        
		        driver.findElement(By.xpath("//li[@id='menuRptbtn']")).click();
		        System.out.println("report button");
		        Thread.sleep(3000);
		        
		        setDateById("Rpt_RecpIng_Menu_startDate","1-09-2025");
		   	  System.out.println("print start date"); 
		   	  Thread.sleep(3000);
		   	  setDateById("Rpt_RecpIng_Menu_endDate","12-09-2025");
		   	  System.out.println("print end date");
		   	  
		   	  
		   	  
		   	  
				/*
				 * driver.findElement(By.xpath("//button[text()='Download']")).click();
				 * System.out.println("download button"); Thread.sleep(5000);
				 */
		   	  
		   	driver.findElement(By.xpath("//button[text()='Download']")).click();
		   	System.out.println("download button");
		   	Thread.sleep(5000); // Wait for download bar to appear

		   	try {
		   	    Runtime.getRuntime().exec("C:\\Users\\pradeep.garg\\Downloads\\clickKeep.exe");
		   	    System.out.println("Triggered AutoIt script to click 'Keep'");
		   	} catch (IOException e) {
		   	    e.printStackTrace();
		   	}

		   	// Optionally wait more for download completion here
		   	Thread.sleep(10000);

		        
		        
		        
		        
		        

		            
		 }
		
		
		
		
		 
		 
		 public static void checkMenuRefresh() {
			 
			 driver.findElement(By.xpath("//a[contains(text(),' Prod_Menu01')]/preceding-sibling::a"));	
			 
			
		 }
		 
		 
		 public static void checkforUnderModification() {
			 
			 driver.findElement(By.xpath("//a[contains(text(),'RecipeAuotmated035C')]/preceding-sibling::i[contains(@class,'yellow')]"));	
			 
			
		 }
		
		
		
		
		
		
		
		
		
		
		/*
		 * public static void clickONMenuReport() throws Exception {
		 * 
		 * driver.findElement(By.xpath("//ul//li//button[@data-bs-target='#menuReport']"
		 * )).click(); System.out.println("click ON Menu Report"); Thread.sleep(3000);
		 * 
		 * setDateById("Rpt_RecpIng_Menu_startDate","1-09-2025");
		 * System.out.println("print start date"); Thread.sleep(3000);
		 * setDateById("Rpt_RecpIng_Menu_endDate","12-09-2025");
		 * System.out.println("print end date");
		 * 
		 * Thread.sleep(3000);
		 * 
		 * WebElement linkreport=
		 * driver.findElement(By.xpath("//a[contains(text(),' Show Multi Menu Report')]"
		 * ));
		 * 
		 * 
		 * 
		 * System.out.println(linkreport.getAttribute("title"));
		 * 
		 * 
		 * 
		 * }
		 */
		
		
		
		
		
		
		
		
		
		public static void addSiteGoRecipe(String siteName) throws Exception {
			driver.findElement(By.id("txtSearchOrgUnit")).sendKeys(siteName, Keys.ENTER);

			String xpath = "//div[@class='list-field']//label[text()='" + siteName
					+ "']/preceding-sibling::input[@type='radio']";

			WebElement radioButton = driver.findElement(By.xpath(xpath));

			if (!radioButton.isSelected()) {
				radioButton.click();
			}
			System.out.println("site selected");

			Thread.sleep(3000);
		}

		public static void selectMenuRecipe() {
			driver.findElement(By.id("spnCountRecipes")).click();
		}
		public static void clickMenus() {
			driver.findElement(By.xpath("(//p[contains(text(),'Menus')] /ancestor::a[1])[1]")).click();
		}

		public static void clickProductionMenus() {
			driver.findElement(By.xpath("//p[contains(text(),'Production Menus')] /ancestor::a[1]")).click();
		}
		
		
		
		
		

		public static void setDateById(String fieldId, String dateValue) {
			try {
				JavascriptExecutor js = (JavascriptExecutor) driver;
				String htmlDate = convertAppDateToHtmlDate(dateValue);
				System.out.println(fieldId + " (converted): " + htmlDate);

				String script = "var dateField = document.getElementById('" + fieldId + "');" + "dateField.value = '"
						+ htmlDate + "';" + "dateField.dispatchEvent(new Event('change', { bubbles: true }));";

				js.executeScript(script);
			} catch (Exception e) {
				System.out.println("Error setting date for " + fieldId + ": " + e.getMessage());
			}
		}



	}


	

}
