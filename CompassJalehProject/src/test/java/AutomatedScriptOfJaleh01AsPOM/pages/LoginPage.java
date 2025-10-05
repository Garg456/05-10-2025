package AutomatedScriptOfJaleh01AsPOM.pages;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {

    WebDriver driver;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public void login(String username, String password) {
        String url = "http://" + username + ":" + password + "@cgrrweb01t:8086/Menu/PublishedMenuList";
        driver.get(url);
        driver.findElement(By.xpath("//a[text()=' Re-Login']")).click();
    }
}
