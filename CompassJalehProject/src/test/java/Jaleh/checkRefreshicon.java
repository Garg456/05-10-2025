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

public class checkRefreshicon {
	public static WebDriver driver;
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
String menuname = "Menuuumm1nn3";
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
		
		driver.findElement(By.xpath("//input[@id='MenuName']")).sendKeys("akio90",Keys.ENTER);
		
		
		//enter menutype
		
		WebElement mntype =driver.findElement(By.id("MenuTypeId"));
		
		Select sl1 =new Select(mntype);
		sl1.selectByVisibleText("Production");
		
		
		//enter MenuCategoryId
		
        WebElement mncatid =driver.findElement(By.id("MenuCategoryId"));
		
		Select sl2 =new Select(mncatid);
		sl2.selectByVisibleText("Cyclic");
		
		
		//date
		WebElement dateInput = driver.findElement(By.id("EffectiveStartDate"));
		dateInput.sendKeys("2025-09-15");

		
		
		
		
	//click of Weeks/Cycle
		
		driver.findElement(By.id("NoOfWeekPerCycle")).sendKeys("2",Keys.ENTER);
		
		
		
		//click on add button
		
	//	driver.findElement(By.xpath("//button[text()='Add']")).click();
		//capturePopupMessageText();
		
		
		
		
						
						
						
						
						
//				
		
	}
		