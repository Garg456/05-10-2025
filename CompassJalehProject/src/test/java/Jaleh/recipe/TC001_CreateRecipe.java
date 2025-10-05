package Jaleh.recipe;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;

@Listeners(myListener2.class)
public class TC001_CreateRecipe extends BaseTest2 {
public static ExtentTest test;
	@Test(dataProvider = "loginData")
	public void Test_createDraftRecipe(String username, String password, String recipeName, String productDescription,
			String ingredientGroup, String recipedisplayname, String mealtype, String cuisine, String recipecategory, String publishType)
			throws Throwable {
		// setup();
		loginToApplication(username, password);
		createRecipe(recipeName, productDescription, ingredientGroup, recipedisplayname, publishType);
		// tearDown();
		// recipedisplayname,mealtype,cuisine,recipecategory
	}

	public void createRecipe(String recipeName, String prdctDcrption, String Ingredientgroup, String dspname, String publishType)
			throws Throwable {
		clickHome();
		addSiteGoRecipe("Production | 208150-South32 - Groote Eylandt | 11-Catering(Real)");
		clickHome();
		clickRecipeMain();

		clickOnNewRecipeDraft();
		clickOnAddRecipe();
		enterRecipeName(recipeName);
		enterRecipeDisplayName("maggie2.0_updated");// maggie2.0_updated
		enterRecipeSearchTag("maggie2.0_tag");
		selectMealType("Bakes");
		selectCuisine("Indian");
		selectRecipeCategory("Entrees");
		enterPortionSize("half");
		enterDietaryInfo("ESS OR - PLUS");
		enterRecipeDescription("item added");
		enterPortion("1");
		scrollDown();
		selectProductDescription(prdctDcrption);
		enterIngredientGroup(Ingredientgroup);
		// selectProductDescription("Vinegar White 4Lt Edlyn - Superior Food Melbourne -
		// GWV5");
		// enterIngredientGroup("VINEGAR, WHITE");
		clickAddIngredientSymbol();
		scrollUp();
		//clickPublishDraft();
		
		publishRecipe(publishType);
		// validation part
//		clickHome();
//		clickRecipeMain();
//
//		clickOnNewRecipeDraft();
//		validationOfCreateRecipe(recipeName);

	}

	// ==================== Reusable Methods ====================

	public void clickOnNewRecipeDraft() {
		driver.findElement(By.xpath("//i[@class='nav-icon fa-brands fa-firstdraft']")).click();
	}

	public void clickOnAddRecipe() {
		driver.findElement(By.xpath("//i[@class='fas fa-plus']")).click();
	}

	public void enterRecipeName(String name) {
		if (name == null || name.trim().isEmpty()) {
			throw new AssertionError("❌ Recipe name is missing. Please provide a valid name in the Excel sheet.");
		}

		try {
			driver.findElement(By.id("RecipeName")).sendKeys(name, Keys.ENTER);
			System.out.println("✅ Recipe name entered: " + name);
		} catch (Exception e) {
			throw new AssertionError("❌ Failed to enter recipe name: " + e.getMessage());
		}
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

	public void enterRecipeSearchTag(String tag) {

		try {
			driver.findElement(By.id("RecipeSearchTag")).sendKeys(tag, Keys.ENTER);
		} catch (Exception e) {
			throw new AssertionError("❌ Failed to enter search tag: " + e.getMessage());
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
			throw new AssertionError("❌ Failed to select  category: " + e.getMessage());
		}
	}

	public void enterPortionSize(String portionSize) {
		driver.findElement(By.id("RecomPortionPerSize")).sendKeys(portionSize, Keys.ENTER);
	}

	public void enterDietaryInfo(String dietaryInfo) {
		driver.findElement(By.xpath("//span[text()='Dietary Information']")).click();
		driver.findElement(By.xpath("//input[@type='search']")).sendKeys(dietaryInfo, Keys.ENTER);
	}

	public void enterRecipeDescription(String description) {
		driver.findElement(By.id("Description")).sendKeys(description, Keys.ENTER);
	}

	public void enterPortion(String portion) {
		driver.findElement(By.id("portion")).sendKeys(portion);
	}

	public void scrollDown() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,500)");
	}

	public void scrollUp() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollTo(0, 0);");
	}

	/*
	 * public void selectProductDescription(String productText) throws
	 * InterruptedException { Thread.sleep(5000);
	 * driver.findElement(By.xpath("//*[@id='select2-desc-container']")).click();
	 * WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	 * wait.until(ExpectedConditions.elementToBeClickable(By.
	 * xpath("//li[contains(@class,'select2-results__option') and text()='" +
	 * productText + "']"))).click(); }
	 * 
	 * public void enterIngredientGroup(String ingredientGroup) throws
	 * InterruptedException { Thread.sleep(5000);
	 * driver.findElement(By.xpath("//input[@class='select2-search__field']")).
	 * sendKeys(ingredientGroup, Keys.ENTER); }
	 */

	public void selectProductDescription(String productText) {
		if (productText == null || productText.trim().isEmpty()) {
			throw new AssertionError("❌ product Text is missing or empty.");
		}

		try {
			Thread.sleep(5000); // Replace with WebDriverWait if needed
			driver.findElement(By.xpath("//*[@id='select2-desc-container']")).click();
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.elementToBeClickable(
					By.xpath("//li[contains(@class,'select2-results__option') and text()='" + productText + "']")))
					.click();
			System.out.println("✅ Product description selected: " + productText);
		} catch (Exception e) {
			throw new AssertionError("❌ Failed to select product description '" + productText + "': " + e.getMessage());
		}
	}

	public void enterIngredientGroup(String ingredientGroup) {
		if (ingredientGroup == null || ingredientGroup.trim().isEmpty()) {
			throw new AssertionError("❌ Ingredient group is missing or empty.");
		}

		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			By inputLocator = By.xpath("//input[@class='select2-search__field']");
			wait.until(ExpectedConditions.visibilityOfElementLocated(inputLocator)).sendKeys(ingredientGroup,
					Keys.ENTER);
			
			System.out.println("✅ Ingredient group entered: " + ingredientGroup);
		} catch (Exception e) {
			throw new AssertionError("❌ Failed to enter ingredient group '" + ingredientGroup + "': " + e.getMessage());
		}
	}

	public void clickAddIngredientSymbol() {
		driver.findElement(By.xpath("//i[@class='fa fa-plus-circle']")).click();
	}

	public void publishRecipe(String publishTypeFromExcel) throws Throwable {
		 if (publishTypeFromExcel == null || publishTypeFromExcel.trim().isEmpty()) {
		        throw new IllegalArgumentException("❌ Publish type is null or empty. Please provide a valid value in the Excel sheet.");
		
		 }
		 
		 switch (publishTypeFromExcel.trim().toUpperCase()) {
		case "MASTER":
			clickPublishMaster();
			break;
		case "SITE":
			clickPublishSite();
			break;
		case "SECTOR":
			clickPublishSector();
			break;
		case "DRAFT":
			clickPublishDraft();
			break;
		default:
			throw new IllegalArgumentException("❌ Unknown publish type: " + publishTypeFromExcel);
		}

	}

	public void clickPublishDraft() {
		try {
			Thread.sleep(4000); // Prefer WebDriverWait if the button appears dynamically
			driver.findElement(By.id("btnSavedraft")).click();
			
		} catch (Exception e) {
			throw new AssertionError("❌ Failed to click 'Save Draft' button: " + e.getMessage());
		}
		System.out.println("✅ Draft published successfully.");
	}

	public void clickPublishSector() throws Throwable {
		Thread.sleep(4000);
		driver.findElement(By.xpath("//button[text()='Publish to...']")).click();
		driver.findElement(By.id("btnPublishTosector")).click();
		driver.findElement(By.xpath("//button[text()='OK']")).click();
		System.out.println("✅ Sector published successfully.");
		
	}

	//
	public void clickPublishSite() throws Throwable {
		Thread.sleep(4000);
		driver.findElement(By.xpath("//button[text()='Publish to...']")).click();
		driver.findElement(By.id("btnPublishSiteLib")).click();
		driver.findElement(By.xpath("//button[text()='OK']")).click();
		System.out.println("✅ Site published successfully.");
	}

	//
	 public void clickPublishMaster() throws Throwable {
	    	Thread.sleep(4000); 
	    	driver.findElement(By.xpath("//button[text()='Publish to...']")).click();
	   // driver.findElement(By.xpath("//button[text()='Publish to...']")).click();
		  driver.findElement(By.xpath("//button[text()='Publish as Master']")).click();
		  driver.findElement(By.xpath("//button[text()='OK']")).click();
		  System.out.println("✅ Master published successfully.");
		  
		 
	    
	}

	public void validationOfCreateRecipe(String recipeName) throws Exception {
		// Enter recipe name and search
		driver.findElement(By.id("SearchText")).clear();
		driver.findElement(By.id("SearchText")).sendKeys(recipeName, Keys.ENTER);

		Thread.sleep(3000); // Replace with wait if needed

		try {
			// Try to find recipe link
			String actualRecipeName = driver.findElement(By.xpath("//a[contains(text(), '" + recipeName + "')]"))
					.getText();
			Assert.assertEquals(actualRecipeName, recipeName, "Recipe name found matches searched name.");
			System.out.println("✅ Recipe found: " + actualRecipeName);

		} catch (NoSuchElementException e) {
			// If recipe not found, fail test with message
			Assert.fail("❌ Recipe '" + recipeName + "' not found.");
		}

	}

	// Read data from Excel and return a List of Maps
	public static void addSiteGoRecipe(String siteName) throws Exception {
	    // Enter site name in the search field and press Enter
	    driver.findElement(By.id("txtSearchOrgUnit")).sendKeys(siteName, Keys.ENTER);

	    // Build dynamic XPath for the radio button based on the site name
	    String xpath = "//div[@class='list-field']//label[text()='" + siteName + "']/preceding-sibling::input[@type='radio']";

	    // Locate the radio button
	    WebElement radioButton = driver.findElement(By.xpath(xpath));

	    // Click the radio button if it's not already selected
	    if (!radioButton.isSelected()) {
	        radioButton.click();
	        
	    }
	    System.out.println("site selected");
	    Thread.sleep(3000);
	    driver.findElement(By.id("spnCountRecipes")).click();
	}

}
