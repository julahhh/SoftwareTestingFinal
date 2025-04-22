package edu.reddituigroup.reddit.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test; // Keep TestNG annotations import

public class RedditSidebarLinksTest extends BaseTest {

    // More specific locator for sidebar container (adjust if needed)
    private final By sidebarContainerLocator = By.cssSelector("div[aria-label='Explore']"); // Example, might need adjustment

    // Locator for sidebar links based on the text inside the anchor tag or a descendant div/span
    private By getSidebarLinkLocator(String linkText) {
        // Use normalize-space() for resilience against extra whitespace
        // Search within the specific sidebar container if possible
        return By.xpath(
                "//div[@aria-label='Explore']//a[normalize-space()='" + linkText + "'] | " + // Direct text match
                        "//div[@aria-label='Explore']//a[.//div[normalize-space()='" + linkText + "']] | " + // Text in child div
                        "//div[@aria-label='Explore']//a[.//span[normalize-space()='" + linkText + "']] | " + // Text in child span
                        "//a[normalize-space()='" + linkText + "']" // Fallback if not in specific container
        );
    }

    // Helper method to navigate home and ensure the sidebar (or a key link) is ready
    private void navigateHomeAndEnsureSidebar(String specificLinkTextToWaitFor) {
        System.out.println("Navigating to homepage for sidebar test...");
        if (!driver.getCurrentUrl().equals(REDDIT_URL)) { // Avoid reload if already home
            driver.get(REDDIT_URL);
        }
        try {
            wait.until(ExpectedConditions.urlToBe(REDDIT_URL));
            // Wait for the specific sidebar link needed for the test to be visible and clickable
            By specificLinkLocator = getSidebarLinkLocator(specificLinkTextToWaitFor);
            System.out.println("Waiting for sidebar link: '" + specificLinkTextToWaitFor + "' using locator: " + specificLinkLocator);
            wait.until(ExpectedConditions.elementToBeClickable(specificLinkLocator));
            System.out.println("Homepage loaded and '" + specificLinkTextToWaitFor + "' link is ready.");
            sleepForPresentation(500); // Short pause on homepage
        } catch (TimeoutException e) {
            System.err.println("Timeout waiting for homepage and/or sidebar link '" + specificLinkTextToWaitFor + "'.");
            Assert.fail("Setup failed: Could not ensure homepage and sidebar link '" + specificLinkTextToWaitFor + "' were ready.", e);
        }
    }


    @Test(priority = 22, description = "Click 'Communities' link in sidebar and verify navigation")
    public void clickCommunities() {
        String linkText = "Communities";
        navigateHomeAndEnsureSidebar(linkText); // Ensure this link is ready

        System.out.println("Attempting to click on the '" + linkText + "' link...");
        By linkLocator = getSidebarLinkLocator(linkText);
        try {
            clickElement(linkLocator);
            System.out.println("Successfully clicked on the '" + linkText + "' link.");
            // Wait for URL containing '/best/communities' or a similar expected pattern
            boolean navigated = wait.until(ExpectedConditions.urlContains("/communities")); // More general check
            Assert.assertTrue(navigated, "URL should contain '/communities' after clicking link.");
            System.out.println("Navigated successfully to: " + driver.getCurrentUrl());
            sleepForPresentation(); // Pause on new page
        } catch (TimeoutException e) {
            Assert.fail("Failed to verify navigation after clicking '" + linkText + "' link (URL check timeout). Current URL: " + driver.getCurrentUrl(), e);
        } catch (Exception e) {
            Assert.fail("Failed to click '" + linkText + "' link or verify navigation.", e);
        }
    }

    @Test(priority = 23, description = "Click 'Help' link in sidebar and verify navigation")
    public void clickHelp() {
        String linkText = "Help"; // Or "Help Center" if text changes
        navigateHomeAndEnsureSidebar(linkText);

        System.out.println("Attempting to click on the '" + linkText + "' link...");
        By linkLocator = getSidebarLinkLocator(linkText);
        try {
            clickElement(linkLocator);
            System.out.println("Successfully clicked on the '" + linkText + "' link.");
            // Expect navigation to the help domain
            boolean navigated = wait.until(ExpectedConditions.urlContains("support.reddithelp.com"));
            Assert.assertTrue(navigated, "URL should contain 'support.reddithelp.com' after clicking Help.");
            System.out.println("Navigated successfully to Help page: " + driver.getCurrentUrl());
            sleepForPresentation();
        } catch (TimeoutException e) {
            Assert.fail("Failed to verify navigation after clicking '" + linkText + "' link (URL check timeout). Current URL: " + driver.getCurrentUrl(), e);
        } catch (Exception e) {
            Assert.fail("Failed to click '" + linkText + "' link or verify navigation.", e);
        }
    }

    @Test(priority = 24, description = "Click 'Blog' link in sidebar and verify navigation")
    public void clickBlog() {
        String linkText = "Blog";
        navigateHomeAndEnsureSidebar(linkText);

        System.out.println("Attempting to click on the '" + linkText + "' link...");
        By linkLocator = getSidebarLinkLocator(linkText);
        try {
            clickElement(linkLocator);
            System.out.println("Successfully clicked on the '" + linkText + "' link.");
            // Expect navigation to the company blog domain/path
            boolean navigated = wait.until(ExpectedConditions.urlContains("redditinc.com/blog"));
            Assert.assertTrue(navigated, "URL should contain 'redditinc.com/blog' after clicking Blog.");
            System.out.println("Navigated successfully to Blog page: " + driver.getCurrentUrl());
            sleepForPresentation();
        } catch (TimeoutException e) {
            Assert.fail("Failed to verify navigation after clicking '" + linkText + "' link (URL check timeout). Current URL: " + driver.getCurrentUrl(), e);
        } catch (Exception e) {
            Assert.fail("Failed to click '" + linkText + "' link or verify navigation.", e);
        }
    }

    @Test(priority = 25, description = "Click 'Careers' link in sidebar and verify navigation")
    public void clickCareers() {
        String linkText = "Careers";
        navigateHomeAndEnsureSidebar(linkText);

        System.out.println("Attempting to click on the '" + linkText + "' link...");
        By linkLocator = getSidebarLinkLocator(linkText);
        try {
            clickElement(linkLocator);
            System.out.println("Successfully clicked on the '" + linkText + "' link.");
            // Expect navigation to the company careers domain/path
            boolean navigated = wait.until(ExpectedConditions.urlContains("redditinc.com/careers"));
            Assert.assertTrue(navigated, "URL should contain 'redditinc.com/careers' after clicking Careers.");
            System.out.println("Navigated successfully to Careers page: " + driver.getCurrentUrl());
            sleepForPresentation();
        } catch (TimeoutException e) {
            Assert.fail("Failed to verify navigation after clicking '" + linkText + "' link (URL check timeout). Current URL: " + driver.getCurrentUrl(), e);
        } catch (Exception e) {
            Assert.fail("Failed to click '" + linkText + "' link or verify navigation.", e);
        }
    }

    @Test(priority = 26, description = "Click 'Press' link in sidebar and verify navigation")
    public void clickPress() {
        String linkText = "Press";
        navigateHomeAndEnsureSidebar(linkText);

        System.out.println("Attempting to click on the '" + linkText + "' link...");
        By linkLocator = getSidebarLinkLocator(linkText);
        try {
            clickElement(linkLocator);
            System.out.println("Successfully clicked on the '" + linkText + "' link.");
            // Expect navigation to the company press domain/path
            boolean navigated = wait.until(ExpectedConditions.urlContains("redditinc.com/press"));
            Assert.assertTrue(navigated, "URL should contain 'redditinc.com/press' after clicking Press.");
            System.out.println("Navigated successfully to Press page: " + driver.getCurrentUrl());
            sleepForPresentation();
        } catch (TimeoutException e) {
            Assert.fail("Failed to verify navigation after clicking '" + linkText + "' link (URL check timeout). Current URL: " + driver.getCurrentUrl(), e);
        } catch (Exception e) {
            Assert.fail("Failed to click '" + linkText + "' link or verify navigation.", e);
        }
    }
}