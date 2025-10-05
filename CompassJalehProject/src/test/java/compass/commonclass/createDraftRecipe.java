package compass.commonclass;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;

public class createDraftRecipe {
    WebDriver driver;

    public createDraftRecipe(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//i[@class='nav-icon fa-brands fa-firstdraft']")
    private WebElement newRecipeDraft;

    @FindBy(xpath = "//i[@class='fas fa-plus']")
    private WebElement addRecipeButton;

    @FindBy(id = "RecipeName")
    private WebElement recipeNameInput;

    @FindBy(id = "RecipeDisplayName")
    private WebElement recipeDisplayName;

    @FindBy(id = "RecipeSearchTag")
    private WebElement recipeSearchTag;

    @FindBy(id = "select2-MealTypeId-container")
    private WebElement mealTypeDropdown;

    @FindBy(id = "select2-CuisineId-container")
    private WebElement cuisineDropdown;

    @FindBy(id = "select2-RecipeCategoryId-container")
    private WebElement categoryDropdown;

    @FindBy(id = "RecomPortionPerSize")
    private WebElement portionSizeInput;

    @FindBy(xpath = "//span[text()='Dietary Information']")
    private WebElement dietaryInfoDropdown;

    @FindBy(id = "Description")
    private WebElement descriptionInput;

    @FindBy(id = "portion")
    private WebElement portionInput;

    @FindBy(xpath = "//*[@id='select2-desc-container']")
    private WebElement productDropdown;

    @FindBy(xpath = "//input[@class='select2-search__field']")
    private WebElement ingredientGroupInput;

    @FindBy(xpath = "//i[@class='fa fa-plus-circle']")
    private WebElement addIngredientButton;

    @FindBy(id = "btnSavedraft")
    private WebElement saveDraftButton;

    @FindBy(id = "SearchText")
    private WebElement searchInput;

    public void clickNewRecipeDraft() {
        newRecipeDraft.click();
    }

    public void clickAddRecipe() {
        addRecipeButton.click();
    }

    public void enterRecipeName(String name) {
        recipeNameInput.clear();
        recipeNameInput.sendKeys(name, Keys.ENTER);
    }

    public void enterRecipeDisplayName(String displayName) {
        recipeDisplayName.clear();
        recipeDisplayName.sendKeys(displayName, Keys.ENTER);
    }

    public void enterSearchTag(String tag) {
        recipeSearchTag.clear();
        recipeSearchTag.sendKeys(tag, Keys.ENTER);
    }

    public void selectDropdown(String text) {
        driver.findElement(By.xpath("//input[@type='search']")).sendKeys(text, Keys.ENTER);
    }

    public void selectMealType(String mealType) {
        mealTypeDropdown.click();
        selectDropdown(mealType);
    }

    public void selectCuisine(String cuisine) {
        cuisineDropdown.click();
        selectDropdown(cuisine);
    }

    public void selectCategory(String category) {
        categoryDropdown.click();
        selectDropdown(category);
    }

    public void enterPortionSize(String size) {
        portionSizeInput.clear();
        portionSizeInput.sendKeys(size);
    }

    public void enterDietaryInfo(String info) {
        dietaryInfoDropdown.click();
        selectDropdown(info);
    }

    public void enterDescription(String desc) {
        descriptionInput.clear();
        descriptionInput.sendKeys(desc, Keys.ENTER);
    }

    public void enterPortion(String portion) {
        portionInput.sendKeys(portion);
    }

    public void selectProductDescription(String productText) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        productDropdown.click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[text()='" + productText + "']"))).click();
    }

    public void enterIngredientGroup(String group) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(ingredientGroupInput)).sendKeys(group, Keys.ENTER);
    }

    public void clickAddIngredient() {
        addIngredientButton.click();
    }

    public void publishDraft() {
        saveDraftButton.click();
    }

    public void searchRecipe(String recipeName) {
        searchInput.clear();
        searchInput.sendKeys(recipeName, Keys.ENTER);
    }

    public boolean isRecipePresent(String recipeName) {
        try {
            return driver.findElement(By.xpath("//a[contains(text(), '" + recipeName + "')]")).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }
}
