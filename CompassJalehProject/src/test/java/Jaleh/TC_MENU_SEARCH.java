package Jaleh;

import java.time.Duration;

import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.openqa.selenium.*;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TC_MENU_SEARCH {
public static WebDriver driver;
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		

String rcpname = "burger65";
		
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		String username = "admin-path15@compass-external.com.au";
		// Set the password
		String password = "Path@0307202451";

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
				
				
				
				
				//click on menu
				
				driver.findElement(By.xpath("(//i[@class='nav-icon fas fa-table'])[1]")).click();
				

				// click to New or Unpublished Menus button
				driver.findElement(By.xpath("//i[@class='nav-icon fas fa-utensils']")).click();
				
				
				
				//click on search bar text
				
				driver.findElement(By.id("SearchText")).sendKeys(rcpname,Keys.ENTER);
				
				Thread.sleep(3000);
				
				
				
				String result =driver.findElement(By.xpath("//td[text()='No record found']")).getText();
				

				Thread.sleep(4000);
				
				//Assert.assertEquals(rcpname, Recipe_name);
				Assert.assertNotEquals(result, rcpname, "no record found");
				
				System.out.println(result);
				
				driver.quit();
				
				
			}

		
	public static void addSiteGoRecipe(String siteName) {
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
	}
	

	
}
