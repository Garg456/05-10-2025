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

public class popup_massage {
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

		// click to home 

		driver.findElement(By.xpath("//i[@class='nav-icon fas fa-home']")).click();
		
		driver.findElement(By.xpath("(//i[@class='nav-icon fas fa-bowl-rice'])[1]")).click();
		
		driver.findElement(By.xpath("//div//i[@class='nav-icon fa-solid fa-utensils']")).click();
		
		  driver.findElement(By.id("SearchText")).clear();
	        driver.findElement(By.id("SearchText")).sendKeys("Recipe899889", Keys.ENTER);


		 driver.findElement(By.xpath("//a//i[@class='fas fa-undo']")).click();
		 
		 driver.findElement(By.xpath("//button[text()='Yes']")).click();
		 
		 String data=  driver.findElement(By.xpath("//table//td[@class='dt-empty']")).getText();
		 if(data.contains("No record found")) {
			 
		 }
		 
		 
		//addSiteGoRecipe(sitename2);
		
		
		
	
	}
	

}
