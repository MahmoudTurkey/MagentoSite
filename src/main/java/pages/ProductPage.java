package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class ProductPage {
    WebDriver driver;

    private By sizeOption = By.cssSelector("div.swatch-option.text");
    private By colorOption = By.cssSelector("div.swatch-option.color");
    private By addToCartButton = By.id("product-addtocart-button");
    private By successMessage = By.cssSelector("div.message-success");

    public ProductPage(WebDriver driver) {
        this.driver = driver;
    }

    public void selectFirstAvailableSize() {
        List<WebElement> sizes = driver.findElements(sizeOption);
        if (!sizes.isEmpty()) sizes.get(0).click();
    }

    public void selectFirstAvailableColor() {
        List<WebElement> colors = driver.findElements(colorOption);
        if (!colors.isEmpty()) colors.get(0).click();
    }

    public void clickAddToCart(WebDriverWait wait) {
        try {
            WebElement addToCart = wait.until(ExpectedConditions.elementToBeClickable(addToCartButton));
            addToCart.click();
        } catch (ElementClickInterceptedException e) {
            WebElement addToCart = driver.findElement(addToCartButton);
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click();", addToCart);
        }
    }

    public boolean waitForSuccessMessage(WebDriverWait wait) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(successMessage));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }
}
