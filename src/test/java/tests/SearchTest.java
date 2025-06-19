package tests;

import com.aventstack.extentreports.ExtentTest;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import pages.HomePage;
import utils.ExtentReportManager;

public class SearchTest {
    WebDriver driver;
    HomePage homePage;
    ExtentTest test;

    @BeforeSuite
    public void setUpReport() {
        ExtentReportManager.initReport();
    }

    @BeforeMethod
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://magento.softwaretestingboard.com/");
        homePage = new HomePage(driver);
    }

    @Test(description = "Valid search returns relevant results")
    public void testValidSearch() {
        test = ExtentReportManager.createTest("Test: Valid Search Functionality");

        try {
            test.info("Step 1: Enter search keyword 'Hoodie'");
            enterSearchKeyword("Hoodie");

            test.info("Step 2: Submit the search");
            submitSearch();

            test.info("Step 3: Validate search results contain 'Hoodie'");
            boolean isResultVisible = driver.findElements(By.xpath("//*[contains(text(),'Hoodie')]")).size() > 0;
            Assert.assertTrue(isResultVisible, "Search results do not contain expected keyword.");
            test.pass("Search results are visible and valid");

        } catch (Exception e) {
            test.fail("Test failed due to exception: " + e.getMessage());
            saveScreenshot("Search Failure Screenshot");
            throw e;
        }
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            saveScreenshot("Failure Screenshot");
        }
        quitDriver();
    }

    @AfterSuite
    public void tearDownReport() {
        ExtentReportManager.flushReport();
    }

    // ================= Allure + Helper Methods ================= //

    @Step("Enter search keyword: {keyword}")
    public void enterSearchKeyword(String keyword) {
        homePage.enterSearchKeyword(keyword);
    }

    @Step("Submit the search")
    public void submitSearch() {
        driver.findElement(homePage.searchField).submit();
    }

    @Step("Quit browser")
    public void quitDriver() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Attachment(value = "{screenshotName}", type = "image/png")
    public byte[] saveScreenshot(String screenshotName) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }
}
