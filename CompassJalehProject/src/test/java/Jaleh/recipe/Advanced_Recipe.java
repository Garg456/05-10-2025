package Jaleh.recipe;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

public class Advanced_Recipe extends BaseTest{

	
	public static void main(String[] args) {
		

	
	Advanced_Recipe ar= new Advanced_Recipe();
	
	ar.setup();
	ar.loginToApplication("admin-path15@compass-external.com.au", "Path@0307202451");
	ar.clickHome();
	ar.clickRecipeMain();
	ar.clickSearchRecipe();
	ar.Advancedsrch();
	ar.tearDown();
	}
	
	public void Advancedsrch() {
	
	//click on add icon
	driver.findElement(By.xpath("//div[@class='search-icon collapsed']")).click();
	
	// click on search recipe list
	
	driver.findElement(By.xpath("//li[@class='select2-selection__choice']")).click();
	
	//click on Search Keyword
	
	driver.findElement(By.id("AdvanceWildSearch")).sendKeys("Pumpkin, Pea and Parmesan Risotto",Keys.ENTER);
	
	//click on SearchRecipeName
	
	driver.findElement(By.id("SearchRecipeName")).sendKeys("",Keys.ENTER);
	
	//click on SearchRecipeDisplayName
	driver.findElement(By.id("SearchRecipeDisplayName")).sendKeys("",Keys.ENTER);
	
	//click on SearchMainingredient
	driver.findElement(By.id("SearchMainingredient")).sendKeys("",Keys.ENTER);
	
	//click on Dietary Information
	driver.findElement(By.id("select2-DietaryInformationId-container")).click();
	driver.findElement(By.xpath(".//input[@class='select2-search__field']")).sendKeys("ESS",Keys.ENTER);
	
	
	//CLICK ON Refresh
	driver.findElement(By.id("select2-SearchRefreshRequired-container")).click();
	driver.findElement(By.xpath("//li[text()='Required']")).click();
	
	//click on SearchRecipeSearchTag
	
	driver.findElement(By.id("SearchRecipeSearchTag")).sendKeys("",Keys.ENTER);
	
	// click on select2-MealTypeId-container
	driver.findElement(By.id("select2-MealTypeId-container")).click();
	driver.findElement(By.xpath(".//input[@class='select2-search__field']")).sendKeys("Bakes",Keys.ENTER);
	
	//click on Cuisine
	driver.findElement(By.id("select2-CuisineTypeId-container")).click();
	driver.findElement(By.xpath("//input[@class='select2-search__field']")).sendKeys("Thai",Keys.ENTER);
	
	// click on Recipe Category
	
	driver.findElement(By.id("select2-CategoryId-container")).click();
	driver.findElement(By.xpath("//input[@class='select2-search__field']")).sendKeys("Entrees",Keys.ENTER);
	
	// click on Status
	driver.findElement(By.id("select2-SearchStatus-container")).click();
	driver.findElement(By.xpath("//span[text()='Active']")).click();
	
	// clikc on Show Recipe
	/*
	 * driver.findElement(By.id("select2-RecipeCreatedByType-container")).click();
	 * driver.findElement(By.xpath("//span[text()='Show Only Create By Me']")).click
	 * ();
	 */
	
	
	//click on search
	driver.findElement(By.id("btnAdvancedSearch")).click();
	
	System.out.println("searched completed");
	
	
	// clcik on clear filter
	driver.findElement(By.xpath("//button[text()='Clear Filter']")).click();
	
	}
	
	
	

    }


	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	



	
