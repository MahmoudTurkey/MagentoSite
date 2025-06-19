package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CheckoutPage {
    WebDriver driver;
    WebDriverWait wait;

    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    public void fillShippingInformation() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("customer-email"))).sendKeys("Mahmoudtorki78@gmail.com");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("firstname"))).sendKeys("Mahmoud");
        driver.findElement(By.name("lastname")).sendKeys("Tester");
        driver.findElement(By.name("street[0]")).sendKeys("Test Street");
        driver.findElement(By.name("city")).sendKeys("Cairo");
        driver.findElement(By.name("postcode")).sendKeys("12345");
        driver.findElement(By.name("telephone")).sendKeys("0123456789");

        WebElement country = driver.findElement(By.name("country_id"));
        new Select(country).selectByVisibleText("Egypt");

        WebElement region = driver.findElement(By.name("region_id"));
        if (region.isDisplayed()) {
            new Select(region).selectByIndex(1);
        }
    }

    public void selectShippingMethod() {
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("input[name='ko_unique_1']"))).click();
    }

    public void clickNext() {
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".button.action.continue.primary"))).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.payment-group")));
    }

    public void placeOrder() {
        By placeOrderButton = By.cssSelector("button.action.primary.checkout");

        wait.until(ExpectedConditions.presenceOfElementLocated(placeOrderButton));
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(placeOrderButton));

        // استخدم JavaScript لتجنب حجب العنصر
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", button);

        // انتظر حتى يتم التوجيه لصفحة النجاح
        wait.until(ExpectedConditions.urlContains("checkout/onepage/success"));
    }
}
