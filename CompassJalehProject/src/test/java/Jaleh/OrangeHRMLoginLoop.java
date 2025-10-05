package Jaleh;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

public class OrangeHRMLoginLoop {

    public static void main(String[] args) {

        // Replace with your credentials
        String username = "Admin";
        String password = "admin123";

        // Number of times to repeat login
        int repeatCount = 50;

        for (int i = 1; i <= repeatCount; i++) {
            System.out.println("🔁 Attempt " + i + " of login");

            WebDriverManager.chromedriver().setup();
            WebDriver driver = new ChromeDriver();

            try {
                driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
                driver.manage().window().maximize();

                // Navigate to the login page
                driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");

                // Perform login
                driver.findElement(By.name("username")).sendKeys(username);
                driver.findElement(By.name("password")).sendKeys(password);
                driver.findElement(By.xpath("//button[text()=' Login ']")).click();

                System.out.println("✅ Login attempt " + i + " successful");

                // Wait for 10 seconds before next iteration
                Thread.sleep(10000); // 10,000 milliseconds = 10 seconds

            } catch (Exception e) {
                System.err.println("❌ Login attempt " + i + " failed: " + e.getMessage());
            } finally {
                // Close the browser after each run
                driver.quit();
            }
        }

        System.out.println("✅ All login attempts completed.");
    }
}
