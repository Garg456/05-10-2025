package compass.Test;

import org.testng.Assert;
import org.testng.annotations.Test;

import compass.commonclass.BaseTest;
import compass.commonclass.CommonMethods;
import compass.pages.LoginPage;
import compass.pages.RecipePage;

public class CreateRecipeTest extends BaseTest {

	@Test(dataProvider = "loginData")
	public void testCreateRecipe(Map<String, String> data) throws Exception {

	    String username = data.get("username");
	    String password = data.get("password");
	    String recipeName = data.get("recipeName");
	    String productDescription = data.get("productDescription");
	    String ingredientGroup = data.get("ingredientGroup");
	    String displayName = data.get("displayName");
	    String mealType = data.get("mealType");
	    String cuisine = data.get("cuisine");
	    String category = data.get("category");

	    CommonMethods cm = new CommonMethods(driver);
	   // RecipePage recipePage = new RecipePage(driver);

	    cm.login(username, password);

	    cm.clickOnNewRecipeDraft();
	    cm.clickSearchRecipe();
	    cm.enterRecipeName(recipeName);
	    recipePage.enterRecipeDisplayName(displayName);
	    recipePage.enterSearchTag("tag_" + recipeName);
	    recipePage.selectMealType(mealType);
	    recipePage.selectCuisine(cuisine);
	    recipePage.selectCategory(category);
	    recipePage.enterPortionSize("1");
	    recipePage.enterDietaryInfo("ESS OR - PLUS");
	    recipePage.enterDescription("This is a description");
	    recipePage.enterPortion("1");
	    recipePage.selectProductDescription(productDescription);
	    recipePage.enterIngredientGroup(ingredientGroup);
	    recipePage.clickAddIngredient();
	    recipePage.publishDraft();

	    // Validation
	    recipePage.clickNewRecipeDraft();
	    recipePage.searchRecipe(recipeName);
	    Assert.assertTrue(recipePage.isRecipePresent(recipeName), "Recipe not found after creation.");
	}

}
