package AutomatedScriptOfJaleh01AsPOM.pages;



import java.time.Duration;
import java.util.List;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import AutomatedScriptOfJaleh01AsPOM.base.BaseTest;

public class CreateRecipe extends BaseTest {

    // Constructor (if needed)
    public CreateRecipe() {}

    public void clickOnNewRecipeDraft() {
        driver.findElement(By.xpath("//i[@class='nav-icon fa-brands fa-firstdraft']")).click();
    }

    public void clickOnAddRecipe() {
        driver.findElement(By.xpath("//i[@class='fas fa-plus']")).click();
    }

    public void enterRecipeName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new AssertionError("❌ Recipe name is missing.");
        }
        driver.findElement(By.id("RecipeName")).sendKeys(name, Keys.ENTER);
    }

    public void enterRecipeDisplayName(String displayName) {
        if (displayName == null || displayName.trim().isEmpty()) {
            throw new AssertionError("❌ Display name is missing.");
        }
        driver.findElement(By.id("RecipeDisplayName")).sendKeys(displayName, Keys.ENTER);
    }

    public void enterRecipeSearchTag(String tag) {
        driver.findElement(By.id("RecipeSearchTag")).sendKeys(tag, Keys.ENTER);
    }

    public void selectDropdown(String containerId, String value) throws InterruptedException {
        driver.findElement(By.id(containerId)).click();
        driver.findElement(By.xpath("//input[@type='search']")).sendKeys(value);
        Thread.sleep(1000); // consider WebDriverWait
        List<WebElement> options = driver.findElements(By.xpath("//li[contains(@class,'select2-results__option')]"));
        for (WebElement option : options) {
            if (option.getText().equalsIgnoreCase(value)) {
                option.click();
                return;
            }
        }
        throw new AssertionError("❌ Option '" + value + "' not found in dropdown.");
    }

    public void selectMealType(String value) throws InterruptedException {
        selectDropdown("select2-MealTypeId-container", value);
    }

    public void selectCuisine(String value) throws InterruptedException {
        selectDropdown("select2-CuisineId-container", value);
    }

    public void selectRecipeCategory(String value) throws InterruptedException {
        selectDropdown("select2-RecipeCategoryId-container", value);
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

    public void selectProductDescription(String productText) throws Exception {
        if (productText == null || productText.trim().isEmpty()) {
            throw new AssertionError("❌ Product description is missing.");
        }
        Thread.sleep(5000);
        driver.findElement(By.id("select2-desc-container")).click();
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//li[contains(@class,'select2-results__option') and text()='" + productText + "']")))
                .click();
    }

    public void enterIngredientGroup(String ingredientGroup) {
        if (ingredientGroup == null || ingredientGroup.trim().isEmpty()) {
            throw new AssertionError("❌ Ingredient group is missing.");
        }
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//input[@class='select2-search__field']")))
                .sendKeys(ingredientGroup, Keys.ENTER);
    }

    public void clickAddIngredientSymbol() {
        driver.findElement(By.xpath("//i[@class='fa fa-plus-circle']")).click();
    }

    public void clickPublishDraft() {
        driver.findElement(By.id("btnSavedraft")).click();
    }

    public void publishRecipe(String type) throws Throwable {
        Thread.sleep(4000);
        switch (type.toUpperCase()) {
            case "DRAFT": clickPublishDraft(); break;
            case "SECTOR": clickPublishType("btnPublishTosector"); break;
            case "SITE": clickPublishType("btnPublishSiteLib"); break;
            case "MASTER": clickPublishAsMaster(); break;
            default: throw new IllegalArgumentException("Invalid publish type: " + type);
        }
    }

    private void clickPublishType(String buttonId) {
        driver.findElement(By.xpath("//button[text()='Publish to...']")).click();
        driver.findElement(By.id(buttonId)).click();
        driver.findElement(By.xpath("//button[text()='OK']")).click();
    }

    private void clickPublishAsMaster() {
        driver.findElement(By.xpath("//button[text()='Publish to...']")).click();
        driver.findElement(By.xpath("//button[text()='Publish as Master']")).click();
        driver.findElement(By.xpath("//button[text()='OK']")).click();
    }

    public void scrollDown() {
        ((JavascriptExecutor) driver).executeScript("window.scrollBy(0,500)");
    }

    public void scrollUp() {
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0,0)");
    }

    public void validateRecipeCreated(String recipeName) throws InterruptedException {
        driver.findElement(By.id("SearchText")).clear();
        driver.findElement(By.id("SearchText")).sendKeys(recipeName, Keys.ENTER);
        Thread.sleep(3000);
        boolean found = driver.findElements(By.xpath("//a[contains(text(),'" + recipeName + "')]")).size() > 0;
        if (!found) throw new AssertionError("❌ Recipe not found after creation: " + recipeName);
    }
}
