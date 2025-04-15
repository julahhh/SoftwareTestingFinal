package edu.reddituigroup.reddit.tests;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.*;

public class RedditSortingTest extends BaseTest {

    // Refined locators to be slightly more specific within common containers (e.g., role='tablist')
    // Still might need adjustment based on exact Reddit structure.
    private final By sortContainerLocator = By.cssSelector("div[role='tablist'], div[data-testid='feed-sort-buttons']"); // Locator for the container of sort buttons
    private final By hotSortLocator = By.xpath(".//button[normalize-space()='Hot'] | .//a[contains(@href, '?sort=hot')]"); // Relative XPath within container
    private final By newSortLocator = By.xpath(".//button[normalize-space()='New'] | .//a[contains(@href, '?sort=new')]"); // Relative XPath within container
    private final By topSortLocator = By.xpath(".//button[normalize-space()='Top'] | .//a[contains(@href, '?sort=top')]"); // Relative XPath within container


    @BeforeMethod // Changed from @BeforeMethod @Override to just @BeforeMethod
    public void setUpTest() { // Renamed to avoid conflict if BaseTest uses @BeforeMethod
        // super.setUp(); // Call BaseTest setup if it's @BeforeMethod
        // Navigate specifically to popular for these tests
        driver.get(REDDIT_URL + "r/popular/");
        wait.until(ExpectedConditions.urlContains("/r/popular/"));
        // Wait for the sort button container first, then the specific button
        try {
            WebElement sortContainer = wait.until(ExpectedConditions.visibilityOfElementLocated(sortContainerLocator));
            // Now wait for the 'Hot' button within the container
            wait.until(ExpectedConditions.elementToBeClickable(sortContainer.findElement(hotSortLocator)));
            System.out.println("Navigated to r/popular and found sort options for sorting tests");
        } catch (TimeoutException e) {
            System.err.println("Failed to find sort options container or 'Hot' button on r/popular.");
            Assert.fail("Setup for RedditSortingTest failed: Could not find sort options.", e);
        }
    }

    // Helper to find sort option within the container
    private WebElement findSortOption(By relativeLocator) {
        WebElement sortContainer = findElement(sortContainerLocator); // Find container first
        return sortContainer.findElement(relativeLocator); // Find button within container
    }

    // Helper to click sort option within the container
    private void clickSortOption(By relativeLocator) {
        WebElement sortContainer = findElement(sortContainerLocator); // Find container first
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(sortContainer.findElement(relativeLocator)));
        element.click();
        System.out.println("Clicked sort option element: " + relativeLocator);
        sleepForPresentation();
    }


    @Test(priority = 26, description = "Verify 'Hot' sort option is present and clickable.")
    public void verifyHotSortOption() {
        WebElement hotButton = findSortOption(hotSortLocator);
        Assert.assertTrue(hotButton.isDisplayed(), "'Hot' sort button should be displayed.");
        Assert.assertTrue(hotButton.isEnabled(), "'Hot' sort button should be clickable.");
        System.out.println("Verified 'Hot' sort option");
    }

    @Test(priority = 27, description = "Verify 'New' sort option is present and clickable.")
    public void verifyNewSortOption() {
        WebElement newButton = findSortOption(newSortLocator);
        Assert.assertTrue(newButton.isDisplayed(), "'New' sort button should be displayed.");
        Assert.assertTrue(newButton.isEnabled(), "'New' sort button should be clickable.");
        System.out.println("Verified 'New' sort option");
    }

    @Test(priority = 28, description = "Verify 'Top' sort option is present and clickable.")
    public void verifyTopSortOption() {
        WebElement topButton = findSortOption(topSortLocator);
        Assert.assertTrue(topButton.isDisplayed(), "'Top' sort button should be displayed.");
        Assert.assertTrue(topButton.isEnabled(), "'Top' sort button should be clickable.");
        System.out.println("Verified 'Top' sort option");
    }

    @Test(priority = 29, description = "Click 'New' sort and verify URL change or active state.")
    public void clickNewSortOption() {
        clickSortOption(newSortLocator); // Use helper to click within container
        // Locator for active state might need adjustment based on current classes/attributes
        By activeNewLocator = By.xpath(".//button[normalize-space()='New' and (@aria-selected='true' or contains(@class, 'active') or contains(@class, 'selected'))] | .//a[contains(@href, '?sort=new') and (contains(@class, 'active') or contains(@class, 'selected'))]");
        try {
            wait.until(ExpectedConditions.or(
                    ExpectedConditions.urlContains("sort=new"),
                    // Wait for the active state within the sort container
                    ExpectedConditions.visibilityOfNestedElementsLocatedBy(sortContainerLocator, activeNewLocator)
            ));
            boolean isActive = driver.getCurrentUrl().contains("sort=new") || findSortOption(activeNewLocator).isDisplayed();
            Assert.assertTrue(isActive, "Clicking 'New' should update URL or set button state to active.");
            System.out.println("Clicked 'New' sort and verified state/URL");
        } catch (TimeoutException e) {
            System.err.println("Failed to verify 'New' sort activation (URL or active state).");
            Assert.fail("Verification for 'New' sort failed.", e);
        }
    }

    @Test(priority = 30, description = "Click 'Top' sort and verify URL change or active state and timeframe options.")
    public void clickTopSortOption() {
        clickSortOption(topSortLocator); // Use helper to click within container
        // Locator for active state might need adjustment
        By activeTopLocator = By.xpath(".//button[normalize-space()='Top' and (@aria-selected='true' or contains(@class, 'active') or contains(@class, 'selected'))] | .//a[contains(@href, '?sort=top') and (contains(@class, 'active') or contains(@class, 'selected'))]");
        try {
            wait.until(ExpectedConditions.or(
                    ExpectedConditions.urlContains("sort=top"),
                    // Wait for the active state within the sort container
                    ExpectedConditions.visibilityOfNestedElementsLocatedBy(sortContainerLocator, activeTopLocator)
            ));
            boolean isActive = driver.getCurrentUrl().contains("sort=top") || findSortOption(activeTopLocator).isDisplayed();
            Assert.assertTrue(isActive, "Clicking 'Top' should update URL or set button state to active.");

            // Locator for the timeframe dropdown button (might appear next to 'Top')
            // This structure varies greatly, adjust locator as needed.
            By topTimeFrameDropdownButtonLocator = By.cssSelector("button[id*='top-level-sort-time-picker'], button:has(span:contains('Today'))"); // Example
            WebElement topTimeFrameButton = findElement(topTimeFrameDropdownButtonLocator);
            Assert.assertTrue(topTimeFrameButton.isDisplayed(), "Timeframe dropdown button for 'Top' should appear.");
            System.out.println("Clicked 'Top' sort and verified state/URL and timeframe button presence");

        } catch (TimeoutException e) {
            System.err.println("Failed to verify 'Top' sort activation (URL or active state or timeframe button).");
            Assert.fail("Verification for 'Top' sort failed.", e);
        }
    }
}
