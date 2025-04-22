package edu.reddituigroup.reddit.tests;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.*;

public class RedditSubredditAccessTest extends BaseTest {

    private final String subreddit = "softwaretesting"; // Example subreddit
    private static final String TEST_SUBREDDIT = "r/programming";

    // Helper method to navigate to the specific subreddit and wait for basic elements
    private void navigateToSubredditAndVerify() {
        String subredditUrl = REDDIT_URL + "r/" + subreddit + "/";
        if (!driver.getCurrentUrl().startsWith(subredditUrl)) { // Use startsWith for URLs with trailing slashes/params
            System.out.println("Navigating to subreddit: " + subredditUrl);
            driver.get(subredditUrl);
        } else {
            System.out.println("Already on or navigated within subreddit: " + driver.getCurrentUrl());
        }

        try {
            // Wait for URL to contain the base subreddit path
            wait.until(ExpectedConditions.urlContains("/r/" + subreddit + "/"));
            System.out.println("Subreddit URL verified: " + driver.getCurrentUrl());
            // Wait for a key element like the header or posts to ensure page context is correct
            By headerLocator = By.cssSelector("h1, #subreddit-title"); // Primary header or specific ID
            By postLocator = By.cssSelector("shreddit-post, div[data-testid='post-container']");
            wait.until(ExpectedConditions.or(
                    ExpectedConditions.visibilityOfElementLocated(headerLocator),
                    ExpectedConditions.visibilityOfElementLocated(postLocator)
            ));
            System.out.println("Subreddit page elements (header or posts) verified.");
            sleepForPresentation(500); // Short pause after navigation/verification
        } catch (TimeoutException e) {
            System.err.println("Timeout waiting for subreddit URL or key elements on r/" + subreddit);
            Assert.fail("Setup failed: Could not navigate to or verify key elements for r/" + subreddit, e);
        }

    }

    @Test(priority = 36, description = "Navigate directly to a subreddit URL and verify URL.")
    public void navigateToSubredditDirectly() {
        navigateToSubredditAndVerify(); // Use helper method
        // The helper already contains the necessary verification wait
        Assert.assertTrue(driver.getCurrentUrl().contains("/r/" + subreddit + "/"),
                "URL should contain '/r/" + subreddit + "/'. Actual URL: " + driver.getCurrentUrl());
        System.out.println("Successfully navigated directly to r/" + subreddit + " and verified URL.");
    }

    @Test(priority = 37, description = "Verify the subreddit header/title is displayed and contains subreddit name.")
    public void verifySubredditHeader() {
        navigateToSubredditAndVerify(); // Ensure we are on the correct page

        // Use a more specific locator if possible, fallback to H1
        By headerLocator = By.cssSelector("#subreddit-title, h1"); // Check for specific ID first
        try {
            WebElement header = wait.until(ExpectedConditions.visibilityOfElementLocated(headerLocator));
            Assert.assertTrue(header.isDisplayed(), "Subreddit header element should be displayed.");

            // Verify text content case-insensitively
            String headerText = header.getText().toLowerCase();
            String expectedSubredditText = subreddit.toLowerCase();
            Assert.assertTrue(headerText.contains("r/" + expectedSubredditText) || headerText.contains(expectedSubredditText),
                    "Subreddit header text should contain '" + subreddit + "'. Actual: '" + header.getText() + "'");
            System.out.println("Verified header display and text for r/" + subreddit);
        } catch (TimeoutException e) {
            Assert.fail("Failed to find or verify the subreddit header using locator: " + headerLocator, e);
        }
    }

    @Test(priority = 38, description = "Verify posts are loaded within the subreddit feed.")
    public void verifyPostsLoadInSubreddit() {
        navigateToSubredditAndVerify(); // Ensure we are on the correct page

        // Use the standard post locator
        By postLocator = By.cssSelector("shreddit-post, div[data-testid='post-container']");
        try {
            // Wait for at least one post element to be visible
            wait.until(ExpectedConditions.visibilityOfElementLocated(postLocator));
            // Check count is greater than 0
            Assert.assertTrue(findElements(postLocator).size() > 0, "Posts should be loaded in the subreddit feed.");
            System.out.println("Verified posts loaded in r/" + subreddit);
        } catch (TimeoutException e) {
            Assert.fail("No posts became visible in the feed for r/" + subreddit + " using locator: " + postLocator, e);
        }
    }

    @Test(priority = 1, description = "Verify the presence of the 'Join' button.")
    public void verifyJoinButtonPresence() {
        try {
            driver.get(REDDIT_URL + TEST_SUBREDDIT);
            sleepForPresentation(2000); // Wait for page load

            By[] joinButtonLocators = {
                By.cssSelector("button[data-testid='join-button']"),
                By.cssSelector("shreddit-join-button button"),
                By.xpath("//button[contains(text(), 'Join')]"),
                By.xpath("//button[contains(@aria-label, 'join community')]")
            };

            WebElement joinButton = null;
            for (By locator : joinButtonLocators) {
                try {
                    joinButton = findElement(locator);
                    if (joinButton != null && joinButton.isDisplayed()) {
                        break;
                    }
                } catch (Exception e) {
                    continue;
                }
            }

            Assert.assertNotNull(joinButton, "Join button should be found using any of the locators");
            Assert.assertTrue(joinButton.isDisplayed(), "Join button should be displayed");
            System.out.println("Verified 'Join' button presence");
        } catch (Exception e) {
            Assert.fail("Failed to verify join button presence: " + e.getMessage());
        }
    }

    @Test(priority = 2, description = "Verify the presence of the subreddit information section.")
    public void verifySubredditInfoSection() {
        try {
            driver.get(REDDIT_URL + TEST_SUBREDDIT);
            sleepForPresentation(2000); // Wait for page load

            By[] infoSectionLocators = {
                By.cssSelector("div[data-testid='subreddit-sidebar']"),
                By.cssSelector("div[aria-label='Community Details']"),
                By.xpath("//div[contains(@class, 'sidebar')]//h2[contains(text(), 'About')]"),
                By.xpath("//div[contains(@class, 'sidebar')]//div[contains(text(), 'About')]")
            };

            WebElement infoSection = null;
            for (By locator : infoSectionLocators) {
                try {
                    infoSection = findElement(locator);
                    if (infoSection != null && infoSection.isDisplayed()) {
                        break;
                    }
                } catch (Exception e) {
                    continue;
                }
            }

            Assert.assertNotNull(infoSection, "Subreddit info section should be found using any of the locators");
            Assert.assertTrue(infoSection.isDisplayed(), "Subreddit info section should be displayed");
            System.out.println("Verified subreddit info section presence");
        } catch (Exception e) {
            Assert.fail("Failed to verify subreddit info section: " + e.getMessage());
        }
    }
}