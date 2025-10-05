package AutomatedScriptOfJaleh01AsPOM;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ActionOnMenuList extends BaseTest2{
	
	
	public static void clickONAction() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(@class,'dropdown-toggle')]/parent::div[contains(@class,'action-buttons ')]"))).click();
		
	}
	
	public static void clickONEditButton() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//i[contains(@title,'Edit')]/parent::a"))).click();
		
	}
	public static void clickONCountinueEditButton() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//i[contains(@title,'Continue Edit')]/parent::a"))).click();
		
	}
	
	public static void clickONInactive() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//i[contains(@title,'Inactive')]/parent::a"))).click();
		
	}
	
	public static void clickONCopy() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//i[contains(@title,'Create a copy within the same site')]/parent::a"))).click();
		
	}
	
	public static void clickONPublish() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//i[contains(@title,'Publish')]/parent::a"))).click();
		
	}
	
	public static void clickONCopyANDSaveinEditMode() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//i[contains(@title,'Copy and Save Production Menu in Edit Mode')]/parent::a"))).click();
		
	}
	
	

}
