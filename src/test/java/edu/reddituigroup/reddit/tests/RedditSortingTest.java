package edu.reddituigroup.reddit.tests;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.*;

public class RedditSortingTest extends BaseTest {

    // Locator for the container holding the main feed sort buttons (e.g., Hot, New, Top)
    // Using OR for flexibility with different Reddit UI versions/layouts
    private final By sortContainerLocator = By.cssSelector("div[role='tablist'][aria-label*='posts'], shreddit-feed-tab-menu, div[data-testid='feed-sort-buttons']");

    // Locators for sort options RELATIVE to the container. Using normalize-space() for resilience.
    // These assume the options are buttons or anchors within the sort container.
    private final By hotSortLocator = By.xpath(".//button[normalize-space()='Hot'] | .//a[contains(@href, '?sort=hot')]");
    private final By newSortLocator = By.xpath(".//button[normalize-space()='New'] | .//a[contains(@href, '?sort=new')]");
    private final By topSortLocator = By.xpath(".//button[normalize-space()='Top'] | .//a[contains(@href, '?sort=top')]");
    // Example locator for an active state (might need adjustment based on actual CSS classes/attributes used by Reddit)
    private final String activeStateSelectorPart = "[@aria-selected='true' or contains(@class, 'active') or contains(@class, 'selected')]";


    @BeforeMethod
    public void setUpTest() {
        String targetUrl = REDDIT_URL + "r/popular/"; // Navigate to a page where sorting is typically available
        System.out.println("Navigating to " + targetUrl + " for sorting tests...");
        if (!driver.getCurrentUrl().contains("/r/popular/")) { // Avoid reloading if already there
            driver.get(targetUrl);
        }
        try {
            // Wait for the sort container itself to be visible
            WebElement sortContainer = wait.until(ExpectedConditions.visibilityOfElementLocated(sortContainerLocator));
            System.out.println("Sort container found.");
            // Additionally, wait for at least one sort option (e.g., Hot) to be clickable within the container
            wait.until(ExpectedConditions.elementToBeClickable(sortContainer.findElement(hotSortLocator)));
            System.out.println("Navigated to r/popular and verified sort options container and 'Hot' button.");
        } catch (TimeoutException e) {
            System.err.println("Failed to find sort options container or 'Hot' button on " + targetUrl);
            Assert.fail("Setup for RedditSortingTest failed: Could not find required sort elements.", e);
        }
        sleepForPresentation(500); // Small pause after setup
    }

    // Helper to find a sort option WebElement within the container
    private WebElement findSortOption(By relativeLocator) {
        WebElement sortContainer = findElement(sortContainerLocator); // Find container first
        return sortContainer.findElement(relativeLocator); // Find button within container using the relative locator
    }

    // Helper to click a sort option within the container, waiting for clickability
    private void clickSortOption(By relativeLocator) {
        WebElement sortContainer = findElement(sortContainerLocator); // Find container first
        System.out.println("Attempting to click sort option relative locator: " + relativeLocator + " within container " + sortContainerLocator);
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(sortContainer.findElement(relativeLocator)));
        element.click();
        System.out.println("Clicked sort option element: " + relativeLocator);
        sleepForPresentation(); // Pause after click
    }

    @Test(priority = 26, description = "Verify 'Hot' sort option is present and clickable.")
    public void verifyHotSortOption() {
        WebElement hotButton = findSortOption(hotSortLocator);
        Assert.assertTrue(hotButton.isDisplayed(), "'Hot' sort button should be displayed.");
        Assert.assertTrue(hotButton.isEnabled(), "'Hot' sort button should be clickable.");
        System.out.println("Verified 'Hot' sort option is present and clickable.");
    }

    @Test(priority = 27, description = "Verify 'New' sort option is present and clickable.")
    public void verifyNewSortOption() {
        WebElement newButton = findSortOption(newSortLocator);
        Assert.assertTrue(newButton.isDisplayed(), "'New' sort button should be displayed.");
        Assert.assertTrue(newButton.isEnabled(), "'New' sort button should be clickable.");
        System.out.println("Verified 'New' sort option is present and clickable.");
    }

    @Test(priority = 28, description = "Verify 'Top' sort option is present and clickable.")
    public void verifyTopSortOption() {
        WebElement topButton = findSortOption(topSortLocator);
        Assert.assertTrue(topButton.isDisplayed(), "'Top' sort button should be displayed.");
        Assert.assertTrue(topButton.isEnabled(), "'Top' sort button should be clickable.");
        System.out.println("Verified 'Top' sort option is present and clickable.");
    }

    @Test(priority = 29, description = "Click 'New' sort and verify URL change or active state.")
    public void clickNewSortOption() {
        clickSortOption(newSortLocator); // Use helper

        // Define locator for the 'New' button specifically in an active state
        By activeNewLocator = By.xpath(".//button[normalize-space()='New' and " + activeStateSelectorPart + "] | .//a[contains(@href, '?sort=new') and " + activeStateSelectorPart + "]");

        try {
            // Wait for EITHER the URL to contain 'sort=new' OR the 'New' button to gain an active state indicator
            boolean conditionMet = wait.until(ExpectedConditions.or(
                    ExpectedConditions.urlContains("sort=new"),
                    ExpectedConditions.visibilityOfNestedElementsLocatedBy(sortContainerLocator, activeNewLocator)
            ));
            Assert.assertTrue(conditionMet, "Clicking 'New' should update URL OR set button state to active.");

            // Optional secondary check after wait
            boolean urlHasNew = driver.getCurrentUrl().contains("sort=new");
            boolean buttonIsActive = findElements(sortContainerLocator).stream()
                    .anyMatch(c -> !c.findElements(activeNewLocator).isEmpty());

            Assert.assertTrue(urlHasNew || buttonIsActive, "Verification failed: Neither URL contains 'sort=new' nor 'New' button appears active.");
            System.out.println("Clicked 'New' sort and verified state/URL update.");

        } catch (TimeoutException e) {
            System.err.println("Timeout waiting for 'New' sort activation (URL or active state). Current URL: " + driver.getCurrentUrl());
            Assert.fail("Verification for 'New' sort activation failed.", e);
        }
    }

    @Test(priority = 30, description = "Click 'Top' sort, verify activation, and check for timeframe dropdown.")
    public void clickTopSortOption() {
        clickSortOption(topSortLocator); // Use helper

        // Define locator for the 'Top' button specifically in an active state
        By activeTopLocator = By.xpath(".//button[normalize-space()='Top' and " + activeStateSelectorPart + "] | .//a[contains(@href, '?sort=top') and " + activeStateSelectorPart + "]");

        try {
            // Wait for EITHER the URL to contain 'sort=top' OR the 'Top' button to gain an active state indicator
            boolean conditionMet = wait.until(ExpectedConditions.or(
                    ExpectedConditions.urlContains("sort=top"),
                    ExpectedConditions.visibilityOfNestedElementsLocatedBy(sortContainerLocator, activeTopLocator)
            ));
            Assert.assertTrue(conditionMet, "Clicking 'Top' should update URL OR set button state to active.");

            // Optional secondary check after wait
            boolean urlHasTop = driver.getCurrentUrl().contains("sort=top");
            boolean buttonIsActive = findElements(sortContainerLocator).stream()
                    .anyMatch(c -> !c.findElements(activeTopLocator).isEmpty());
            Assert.assertTrue(urlHasTop || buttonIsActive, "Verification failed: Neither URL contains 'sort=top' nor 'Top' button appears active.");
            System.out.println("Clicked 'Top' sort and verified state/URL update.");

            // Now, verify the timeframe dropdown appears when 'Top' is selected
            // **VERIFY SELECTOR** for the timeframe dropdown trigger button
            By topTimeFrameDropdownButtonLocator = By.cssSelector("button[id*='top-level-sort-time-picker'], faceplate-dropdown-trigger[aria-label*='Sort by time'], button:has(span:contains('Today'))"); // Example selectors
            WebElement topTimeFrameButton = wait.until(ExpectedConditions.visibilityOfElementLocated(topTimeFrameDropdownButtonLocator));
            Assert.assertTrue(topTimeFrameButton.isDisplayed(), "Timeframe dropdown button for 'Top' sort should appear.");
            Assert.assertTrue(topTimeFrameButton.isEnabled(), "Timeframe dropdown button for 'Top' sort should be enabled.");
            System.out.println("Verified timeframe dropdown button presence for 'Top' sort.");

        } catch (TimeoutException e) {
            System.err.println("Timeout waiting for 'Top' sort activation (URL or active state) or timeframe button. Current URL: " + driver.getCurrentUrl());
            Assert.fail("Verification for 'Top' sort failed.", e);
        }
    }
}