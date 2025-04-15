package edu.reddituigroup.reddit.tests;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.*;

public class RedditSubredditAccessTest extends BaseTest {

    private final String subreddit = "softwaretesting";

    // Helper method to navigate to the specific subreddit for tests in this class
    private void navigateToSubreddit() {
        String subredditUrl = REDDIT_URL + "r/" + subreddit + "/";
        if (!driver.getCurrentUrl().equals(subredditUrl)) {
            driver.get(subredditUrl);
            wait.until(ExpectedConditions.urlContains("/r/" + subreddit + "/"));
        }
    }

    @Test(priority = 36, description = "Navigate directly to a subreddit URL.")
    public void navigateToSubredditDirectly() {
        navigateToSubreddit(); // Use helper method
        Assert.assertTrue(driver.getCurrentUrl().contains("/r/" + subreddit + "/"), "URL should contain '/r/" + subreddit + "/'.");
        System.out.println("Navigated directly to r/" + subreddit);
    }

    @Test(priority = 37, description = "Verify the subreddit header/title is displayed.")
    public void verifySubredditHeader() {
        navigateToSubreddit(); // Ensure we are on the correct page
        // Using CSS selector for potentially more stability
        By headerLocator = By.cssSelector("h1"); // Find the main H1
        WebElement header = findElement(headerLocator);
        Assert.assertTrue(header.isDisplayed(), "Subreddit header (h1) should be displayed.");
        // Verify text content more flexibly
        String headerTextLower = header.getText().toLowerCase();
        Assert.assertTrue(headerTextLower.contains("r/" + subreddit.toLowerCase()) || headerTextLower.contains(subreddit.toLowerCase()),
                "Subreddit header text should contain 'r/" + subreddit + "' or '" + subreddit + "'. Actual: " + header.getText());
        System.out.println("Verified header for r/" + subreddit);
    }

    @Test(priority = 38, description = "Verify posts are loaded within the subreddit feed.")
    public void verifyPostsLoadInSubreddit() {
        navigateToSubreddit(); // Ensure we are on the correct page
        // Updated locator: General selector for posts
        By postLocator = By.cssSelector("shreddit-post, div[data-testid='post-container']");
        // Wait for at least one post element to be visible
        wait.until(ExpectedConditions.visibilityOfElementLocated(postLocator));
        // Check count is greater than 0
        Assert.assertTrue(findElements(postLocator).size() > 0, "Posts should be loaded in the subreddit feed.");
        System.out.println("Verified posts loaded in r/" + subreddit);
    }

    @Test(priority = 39, description = "Verify the 'Join' button is present on the subreddit page (when logged out).")
    public void verifyJoinButtonPresence() {
        navigateToSubreddit(); // Ensure we are on the correct page
        // Updated locator: CSS selector targeting the custom element and button inside
        // This is still potentially fragile due to custom elements. Verify structure in browser dev tools.
        By joinButtonLocator = By.cssSelector("shreddit-join-button button");
        try {
            WebElement joinButton = findElement(joinButtonLocator);
            Assert.assertTrue(joinButton.isDisplayed(), "'Join' button should be displayed on the subreddit page.");
            System.out.println("Verified 'Join' button presence in r/" + subreddit);
        } catch (TimeoutException e) {
            System.err.println("Could not find Join button using locator: " + joinButtonLocator);
            System.err.println("Current URL: " + driver.getCurrentUrl());
            System.err.println("Check if the user is logged in or if the button structure/selector has changed.");
            Assert.fail("Failed to find 'Join' button.", e);
        }
    }

    @Test(priority = 40, description = "Verify subreddit rules or info section is present.")
    public void verifySubredditInfoSection() {
        navigateToSubreddit(); // Ensure we are on the correct page
        // Kept original robust fallback XPath locator
        By infoSectionLocator = By.xpath("//*[contains(@aria-label, 'Community details')] | //div[contains(., 'About Community')] | //div[contains(., 'Rules')]");
        try {
            WebElement infoSection = findElement(infoSectionLocator);
            scrollToElement(infoSectionLocator); // Scroll to make sure it's in view if needed
            Assert.assertTrue(infoSection.isDisplayed(), "Subreddit info/rules section should be displayed.");
            System.out.println("Verified info/rules section presence in r/" + subreddit);
        } catch (TimeoutException e) {
            System.err.println("Could not find Info/Rules section using locator: " + infoSectionLocator);
            System.err.println("Current URL: " + driver.getCurrentUrl());
            Assert.fail("Failed to find info/rules section.", e);
        }
    }
}
