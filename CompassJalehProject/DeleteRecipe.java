package Jaleh.recipe;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

public class DeleteRecipe extends BaseTest {

	public  void Test_DeleteRecipe() throws Exception {
		// TODO Auto-generated method stub

		DeleteRecipe dr = new DeleteRecipe();
		dr.setup();

		dr.loginToApplication("admin-path15@compass-external.com.au", "Path@0307202451");
		dr.dltrcp("maggie409");
		dr.tearDown();
	}

	public void dltrcp(String rcpname) throws Exception {

		driver.findElement(By.id("SearchText")).sendKeys(rcpname, Keys.ENTER);
		Thread.sleep(3000);

		driver.findElement(By.xpath("//i[@class='fas fa-trash-alt']")).click();

		driver.findElement(By.xpath("//button[text()='Yes']")).click();
		System.out.println("RECIPE DELETED");
	}

}
