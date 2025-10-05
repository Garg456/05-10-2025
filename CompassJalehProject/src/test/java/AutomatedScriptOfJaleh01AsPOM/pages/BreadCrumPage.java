package AutomatedScriptOfJaleh01AsPOM.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class BreadCrumPage {
	
	 WebDriver driver;

	    public BreadCrumPage(WebDriver driver) {
	        this.driver = driver;
	    }
	    
	    public  void clickRelogin() {
	        driver.findElement(By.xpath("//a[text()=' Re-Login']")).click();
	    }
	    public void clickHomeIcon() {
	        driver.findElement(By.xpath("//i[@class='nav-icon fas fa-home']")).click();
	    }
	    
	    public void clickMainRecipe() {
	        driver.findElement(By.xpath("(//i[@class='nav-icon fas fa-bowl-rice'])[1]")).click();
	    }

	    public void openSearchRecipe() {
	        driver.findElement(By.xpath("(//i[@class='nav-icon fas fa-bowl-rice'])[2]")).click();
	    }
	    
	    public void clickNewRecipeDraft() {
	        driver.findElement(By.xpath("//i[@class='nav-icon fa-brands fa-firstdraft']")).click();
	    }
	    public void clickMenus() {
	        driver.findElement(By.xpath("(//p[contains(text(),'Menus')] /ancestor::a[1])[1]")).click();
	    }

	    public void clickProductionMenus() {
	        driver.findElement(By.xpath("//p[contains(text(),'Production Menus')] /ancestor::a[1]")).click();
	    }


		public  void clickTemplateMenus() {
			driver.findElement(By.xpath("//p[contains(text(),'Template Menus')] /ancestor::a[1]")).click();
		}
		public void clickproducts() {
			driver.findElement(By.xpath("//p[text()='Products '] /preceding-sibling::i")).click();
			
			
			}
		public void clickproductIntegration() {
			driver.findElement(By.xpath("//p[text()='Product Integration'] /preceding-sibling::i")).click();
			
			}
		public void Products_APL_MOG() {
			driver.findElement(By.xpath("//p[text()='Products (APL/MOG)'] /preceding-sibling::i")).click();
			
			}
}
