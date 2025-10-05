package AutomatedScriptOfJaleh01AsPOM.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Download_APL_MOG {
	
	 private WebDriver driver;
	    private WebDriverWait wait;

	    public Download_APL_MOG(WebDriver driver) {
	        this.driver = driver;
	        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
	    }
	
	
	
	    public  void validationPriceOrWithoutPriceOnMenuRecipe() {

		    // Click on the "Print" button
		    driver.findElement(By.xpath("//button[text()='Download Mapping']")).click();

		    // Create a WebDriverWait to wait for the new buttons to appear
		    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		    // Wait for both buttons to be visible
		    WebElement withPriceBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(
		        By.xpath("//button[contains(text(),'With Price')]")
		    ));
		   
		    WebElement withoutPriceBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(
		        By.xpath("//button[contains(text(),'Without Price')]")
		    ));

		    // Validate both buttons are displayed
		    if (withPriceBtn.isDisplayed() && withoutPriceBtn.isDisplayed()) {
		        System.out.println("✅ Both 'With Price' and 'Without Price' buttons are visible.");
		    } else {
		        System.out.println("❌ One or both buttons are not visible.");
		    }
		    withPriceBtn.click();
		}
}
