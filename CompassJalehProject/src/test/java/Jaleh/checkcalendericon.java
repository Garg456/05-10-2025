package Jaleh;

import java.time.Duration;
import java.util.concurrent.TimeoutException;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class checkcalendericon {
	public static WebDriver driver;
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
String menuname = "Menuuumm13";
String sitename2 ="Production | 180800-AFP Majura | 31-Retail 1(Real)";
String sitename1 ="Production | 208150-South32 - Groote Eylandt | 11-Catering(Real)";
String Recipename="test46";
		
		WebDriverManager.chromedriver().setup();
		 driver = new ChromeDriver();
		String username = "admin-path22@compass-external.com.au";
		// Set the password
		String password = "Pathinfotech@01";

		String URL = "http://" + username + ":" + password + "@" + "cgrrweb01t:8086/Menu/PublishedMenuList";
		// driver.get(config.getProperty("URL"));
		driver.get(URL);
		// driver.get("http://admin-path15@compass-external.com.au:Path@0307202451:8086/Menu/PublishedMenuList");

		// http://admin-path15@compass-external.com.au:Path@0307202451:8086/Menu/PublishedMenuList
		// "http://" +username +":" +password +"@"+
		// "cgrrweb01t:8086/Menu/PublishedMenuList"
		// admin-path15@compass-external.com.au
		// Path@0307202451
		// Goodlife@123456

		// http://cgrrweb01t:8086/Recipe/List
		// http://cgrrweb01t:8086/Recipe/List
		driver.manage().window().maximize();

		// driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		driver.findElement(By.xpath("//a[text()=' Re-Login']")).click();

		// click to home button

		driver.findElement(By.xpath("//i[@class='nav-icon fas fa-home']")).click();
		//addSiteGoRecipe(sitename2);
		
		//click on menu
		
		driver.findElement(By.xpath("(//i[@class='nav-icon fas fa-table'])[1]")).click();
		

		// click to New or Unpublished Menus button
		driver.findElement(By.xpath("//i[@class='nav-icon fas fa-utensils']")).click();
		
		
		//click on Addmenu button
		
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
		//capturePopupMessageText();
		//click on edit 
				driver.findElement(By.xpath("//button[@id='editCustomerRow_11']")).click();
				
				//clickon add data
				driver.findElement(By.xpath("//tr[@id='tr_W1_Cycle1']//input[@id='editableValue7']")).sendKeys("122");
				
				//click on save 
						driver.findElement(By.xpath("//button[@id='saveCustomerRow_11']")).click();
						capturePopupMessageText();
						
						System.out.println("data saved");
						//capturePopupMessageText();
						
						
						
						
						
//						WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
//						wait.until(ExpectedConditions.alertIsPresent());
//						Alert alert = driver.switchTo().alert();
//						String alertText = alert.getText();
//						System.out.println("Alert found: " + alertText);
//						
//						messagebyjavascript();
//						
						
						

						driver.quit();
						
	}
	
	public static String capturePopupMessageText() {
	    try {
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
	        WebElement popup = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("swal2-title")));
	        String message = popup.getText().trim();
	        System.out.println("Popup message: " + message);
	        return message;
	    } catch (Exception e) {
	        System.out.println("Popup not found or disappeared too fast.");
	        return null;
	    }
	}

	
	public static void messagebyjavascript() throws TimeoutException {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		WebElement popup = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("swal2-title")));
		popup.click();

	}

		
	}
		