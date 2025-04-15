package edu.reddituigroup.reddit.tests;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.*;
import org.openqa.selenium.TimeoutException; // Import explicitly


public class RedditFeedViewOptionsTest extends BaseTest {

    private final By cardViewButtonLocator = By.cssSelector("button[aria-label='Card view']");
    private final By classicViewButtonLocator = By.cssSelector("button[aria-label='Classic view']");
    private final By compactViewButtonLocator = By.cssSelector("button[aria-label='Compact view']");


    @BeforeMethod
    @Override
    public void setUp() {
        super.setUp();
        driver.get(REDDIT_URL + "r/popular/");
        wait.until(ExpectedConditions.urlContains("/r/popular/"));
        wait.until(ExpectedConditions.presenceOfElementLocated(cardViewButtonLocator));
        System.out.println("Navigated to r/popular for feed view tests");
    }

    @Test(priority = 31, description = "Verify 'Card' view option button is present.")
    public void verifyCardViewOptionPresence() {
        WebElement cardViewButton = findElement(cardViewButtonLocator);
        Assert.assertTrue(cardViewButton.isDisplayed(), "'Card' view button should be displayed.");
        System.out.println("Verified 'Card' view option presence");
    }

    @Test(priority = 32, description = "Verify 'Classic' view option button is present.")
    public void verifyClassicViewOptionPresence() {
        WebElement classicViewButton = findElement(classicViewButtonLocator);
        Assert.assertTrue(classicViewButton.isDisplayed(), "'Classic' view button should be displayed.");
        System.out.println("Verified 'Classic' view option presence");
    }

    @Test(priority = 33, description = "Verify 'Compact' view option button is present.")
    public void verifyCompactViewOptionPresence() {
        WebElement compactViewButton = findElement(compactViewButtonLocator);
        Assert.assertTrue(compactViewButton.isDisplayed(), "'Compact' view button should be displayed.");
        System.out.println("Verified 'Compact' view option presence");
    }

    @Test(priority = 34, description = "Switch to 'Classic' view and verify change.")
    public void switchToClassicView() {
        clickElement(classicViewButtonLocator);
        By classicButtonSelectedLocator = By.cssSelector("button[aria-label='Classic view'][aria-selected='true']");
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(classicButtonSelectedLocator));
            Assert.assertTrue(findElement(classicButtonSelectedLocator).isDisplayed(), "'Classic' view button should appear selected after clicking.");
            System.out.println("Switched to 'Classic' view and verified selection state");
        } catch (TimeoutException e) {
            // Alternative check: Look for a class change on the main feed container
            By feedContainerLocator = By.cssSelector("div[data-testid='feed-container'].classic-view");
            wait.until(ExpectedConditions.presenceOfElementLocated(feedContainerLocator));
            Assert.assertTrue(findElement(feedContainerLocator).isDisplayed(), "Feed container should have classic view style.");
            Assert.fail("Could not confirm switch to Classic view via button state or container class. VERIFY LOCATORS/STATE.");
        }
    }

    @Test(priority = 35, description = "Switch to 'Compact' view and verify change.")
    public void switchToCompactView() {
        clickElement(compactViewButtonLocator);
        By compactButtonSelectedLocator = By.cssSelector("button[aria-label='Compact view'][aria-selected='true']");
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(compactButtonSelectedLocator));
            Assert.assertTrue(findElement(compactButtonSelectedLocator).isDisplayed(), "'Compact' view button should appear selected after clicking.");
            System.out.println("Switched to 'Compact' view and verified selection state");
        } catch (TimeoutException e) {
            Assert.fail("Could not confirm switch to Compact view via button state or container class. VERIFY LOCATORS/STATE.");
        }
    }
}