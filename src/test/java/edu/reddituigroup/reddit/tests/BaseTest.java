package edu.reddituigroup.reddit.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import java.time.Duration;

/**
 * Base class for Reddit base.tests to handle WebDriver setup and teardown.
 */
public class BaseTest {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected final String REDDIT_URL = "https://www.reddit.com/";

    //for presentation
    private static final long PRESENTATION_SLEEP_MS = 1500; // 1.5 seconds, adjust as needed

    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        // Explicit wait - Increased timeout slightly
        wait = new WebDriverWait(driver, Duration.ofSeconds(15)); // Wait up to 15 seconds
        driver.get(REDDIT_URL);
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            // final state
            sleepForPresentation(2000);
            driver.quit();
        }
    }

   //helper method
    protected void sleepForPresentation(long durationMs) {
        try {
            Thread.sleep(durationMs);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Thread sleep interrupted: " + e.getMessage());
        }
    }

    /**
     * Overload for default presentation sleep duration.
     */
    protected void sleepForPresentation() {
        sleepForPresentation(PRESENTATION_SLEEP_MS);
    }


    // Helper method to safely find elements
    protected WebElement findElement(By locator) {
        // No sleep here usually, wait handles finding
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    // Helper method to find multiple elements
    protected java.util.List<WebElement> findElements(By locator) {
        // Wait for at least one element to be present before returning the list
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
    }

    // Helper method to safely click elements
    protected void clickElement(By locator) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        element.click();
        System.out.println("Clicked element: " + locator); // Logging
        sleepForPresentation(); // Pause after click for presentation
    }

    // Helper method to safely send keys
    protected void sendKeysToElement(By locator, String text) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator)); // Ensure visible before typing
        element.clear();
        element.sendKeys(text);
        System.out.println("Sent keys '" + text + "' to element: " + locator); // Logging
        sleepForPresentation(); // Pause after sendKeys for presentation
    }

    // Helper method to scroll to element
    protected void scrollToElement(By locator) {
        WebElement element = findElement(locator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        sleepForPresentation(500); // Small pause after scroll
    }

    // Helper to scroll to the bottom of the page
    protected void scrollToFooter() {
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
        // Wait for a known footer element to be visible (adjust selector)
        By footerElementLocator = By.linkText("User Agreement"); // Example locator
        wait.until(ExpectedConditions.visibilityOfElementLocated(footerElementLocator));
        sleepForPresentation(500); // Small pause after scroll
    }
}