package AutomatedScriptOfJaleh01AsPOM.pages;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MenuReportPage {

    private WebDriver driver;
    private WebDriverWait wait;

    public MenuReportPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

   

    

   

    public void openMenuReport(String menuSearchText) throws Exception {
        WebElement searchInput = wait.until(ExpectedConditions.elementToBeClickable(By.id("SearchText")));
        searchInput.clear();
        searchInput.sendKeys(menuSearchText, Keys.ENTER);
        System.out.println("🔍 Menu searched: " + menuSearchText);

        Thread.sleep(3000);

        WebElement dropdownBtn = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[contains(@class,'action-buttons btn-group')]//button")));
        dropdownBtn.click();

        WebElement editOption = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[contains(@title,'Edit')]//a")));
        editOption.click();

        System.out.println("✅ Menu edit clicked");

        driver.findElement(By.id("tabFiveTab")).click();
        System.out.println("🗂️ Switched to Menu Recipe tab");

        driver.findElement(By.id("menuRptbtn")).click();
        System.out.println("📄 Clicked on Menu Report");

        Thread.sleep(3000);
    }

    
    
    
  public void clickDownloadButton() {
	  driver.findElement(By.xpath("//button[text()='Download']")).click();
	   	System.out.println("download button");
	   	//Thread.sleep(5000); // Wait for download bar to appear
  }

   
}
