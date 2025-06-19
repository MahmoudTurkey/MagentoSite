package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class CartPage {
    WebDriver driver;
    WebDriverWait wait;

    private By cartIcon = By.cssSelector(".showcart");
    private By viewCartLink = By.cssSelector("a.action.viewcart");
    private By removeButtons = By.cssSelector("a.action.action-delete");
    private By cartItems = By.cssSelector("div.cart.item");

    public CartPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void openCart() {
        driver.findElement(cartIcon).click();
        wait.until(ExpectedConditions.elementToBeClickable(viewCartLink)).click();
    }

    public int getCartItemCount() {
        return driver.findElements(cartItems).size();
    }

    public void removeOneItem() {
        List<WebElement> removeLinks = driver.findElements(removeButtons);

        if (!removeLinks.isEmpty()) {
            removeLinks.get(0).click(); // حذف أول عنصر
        } else {
            throw new RuntimeException("No items available to remove in the cart.");
        }
    }
}
