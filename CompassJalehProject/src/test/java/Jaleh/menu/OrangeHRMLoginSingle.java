package Jaleh.menu;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(myListener2.class)
public class OrangeHRMLoginSingle {

    @Test
    public void loginTest() {
        String username = "Admin";
        String password = "admin123";

        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();

        try {
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(java.time.Duration.ofSeconds(10));

            driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");

            driver.findElement(By.name("username")).sendKeys(username);
            driver.findElement(By.name("password")).sendKeys(password);
            driver.findElement(By.xpath("//button[text()=' Login ']")).click();

            Thread.sleep(3000);

            System.out.println("✅ Login attempt successful");

        } catch (Exception e) {
            System.err.println("❌ Exception during login: " + e.getMessage());
            e.printStackTrace();
//        } finally {
//            if (driver != null) {
//                driver.quit();
//            }
//        }
//        System.out.println("✅ Login script finished.");
//    }
}}}
