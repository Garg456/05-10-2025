package AutomatedScriptOfJaleh01AsPOM;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

public class productIntegration extends BaseTest2 {
	
	public WebDriver driver;
	
	
	public void tc_productIntegration() {
		
		
		clickHome();
		clickproducts();
		
		
		
	}
	public void clickproducts() {
		driver.findElement(By.xpath("//p[text()='Products '] /preceding-sibling::i")).click();
		
		
		}
	public void clickproductIntegration() {
		driver.findElement(By.xpath("//p[text()='Product Integration'] /preceding-sibling::i")).click();
		
		}
	
	public void selectDropdownproductIntegration(String dpvalue) {
		
		Select select = new Select(driver.findElement(By.id("SearchIntegrationTypeId")));
		select.selectByVisibleText(dpvalue);
		
	}
	
	
	

}
