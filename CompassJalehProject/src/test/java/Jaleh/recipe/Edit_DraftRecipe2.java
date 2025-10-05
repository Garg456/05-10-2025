package Jaleh.recipe;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
@Listeners(myListener2.class)
public class Edit_DraftRecipe2 extends BaseTest {
	@Test(dataProvider = "loginData")
	public  void Test_editrcp(String username, String password, String recipeName, String productDescription,
			String ingredientGroup,String recipedisplayname,String mealtype,String cuisine,String recipecategory) throws Throwable {
		//setup();
		loginToApplication(username, password);
		
		edtrcp(recipeName,  recipedisplayname,mealtype, cuisine, recipecategory);
		//tearDown();
		 // recipedisplayname,mealtype,cuisine,recipecategory
	}
	
	public void edtrcp(String rcpname,String recipedisplayname,String mealtype,String cuisine,String recipecategory ) throws Throwable {
		clickHome();
		clickRecipeMain();

		clickOnNewRecipeDraft();
		searchRecipe(rcpname);
		enterRecipeDisplayName( recipedisplayname);
		selectMealType(mealtype);
		selectCuisine( cuisine);
		selectRecipeCategory(recipecategory);
		
		
		
		
		
	}
		public void searchRecipe(String rcpname) throws Throwable {
       // driver.findElement(By.xpath("//i[@class='nav-icon fa-brands fa-firstdraft']")).click(); 
        
		driver.findElement(By.id("SearchText")).sendKeys(rcpname, Keys.ENTER);
		Thread.sleep(3000);
		
		//driver.findElement(By.xpath("//a[contains(text(), '" + rcpname + "')]")).click();
		//i[@class='fa fa-edit']
		driver.findElement(By.xpath("//i[@class='fa fa-edit']")).click();
		
		
		}
	
		
		
		
		public void enterRecipeDisplayName(String displayName) {
			if (displayName == null || displayName.trim().isEmpty()) {
				throw new AssertionError("❌ display Name is missing or empty.");
			}
			try {
				driver.findElement(By.id("RecipeDisplayName")).sendKeys(displayName, Keys.ENTER);
			} catch (Exception e) {
				throw new AssertionError("❌ Failed to enter display name: " + e.getMessage());
			}
		}

	
		public void selectMealType(String mealType) {
			if (mealType == null || mealType.trim().isEmpty()) {
				throw new AssertionError("❌ meal Type is missing or empty.");
			}
			try {
				driver.findElement(By.id("select2-MealTypeId-container")).click();
				driver.findElement(By.xpath("//input[@type='search']")).sendKeys(mealType, Keys.ENTER);
			} catch (Exception e) {
				throw new AssertionError("❌ Failed to select meal type: " + e.getMessage());
			}
		}

		public void selectCuisine(String cuisine) {
			if (cuisine == null || cuisine.trim().isEmpty()) {
				throw new AssertionError("❌ cuisine is missing or empty.");
			}
			try {

				driver.findElement(By.id("select2-CuisineId-container")).click();
				driver.findElement(By.xpath("//input[@type='search']")).sendKeys(cuisine, Keys.ENTER);
			} catch (Exception e) {
				throw new AssertionError("❌ Failed to select  cuisine: " + e.getMessage());
			}
		}

		public void selectRecipeCategory(String category) {
			if (category == null || category.trim().isEmpty()) {
				throw new AssertionError("❌ cuisine is missing or empty.");
			}
			try {
				driver.findElement(By.id("select2-RecipeCategoryId-container")).click();
				driver.findElement(By.xpath("//input[@type='search']")).sendKeys(category, Keys.ENTER);
			} catch (Exception e) {
				throw new AssertionError("❌ Failed to select category: " + e.getMessage());
			}
		}
		
	
	}
	



