package edu.reddituigroup.reddit.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class RedditSidebarLinksTest extends BaseTest {

    // Locator for sidebar links based on the text inside a div within the anchor tag
    private By getSidebarLinkLocator(String linkText) {
        // This XPath assumes a structure like <a><div>Link Text</div></a>
        // Adjust if the structure is different (e.g., span, or text directly in <a>)
        return By.xpath("//a[.//div[normalize-space()='" + linkText + "']] | //a[normalize-space()='" + linkText + "']");
    }

    // Helper method to navigate home and handle potential stale elements
    private void navigateHomeAndWait() {
        System.out.println("Navigating to homepage for sidebar test...");
        driver.get(REDDIT_URL);
        wait.until(ExpectedConditions.urlToBe(REDDIT_URL));
        // Wait for a stable element on the homepage sidebar to ensure it's loaded
        wait.until(ExpectedConditions.visibilityOfElementLocated(getSidebarLinkLocator("Communities"))); // Wait for a known link
        sleepForPresentation(1000); // Pause on homepage
    }


    @Test(priority = 21, description = "Click 'Communities' link in sidebar")
    public void clickCommunities() {
        navigateHomeAndWait();

        System.out.println("Attempting to click on the 'Communities' component...");
        By communitiesLocator = getSidebarLinkLocator("Communities");
        try {
            clickElement(communitiesLocator); // [cite: 24]
            System.out.println("Successfully clicked on the 'Communities' component.");
            // Wait for URL containing '/best/communities' or similar pattern
            wait.until(ExpectedConditions.urlContains("/communities")); // More general check
            System.out.println("Navigated to Communities page: " + driver.getCurrentUrl());
            sleepForPresentation(); // Pause on new page
        } catch (Exception e) {
            System.err.println("Exception during clickCommunities: " + e.getMessage());
            e.printStackTrace();
            Assert.fail("Failed to click 'Communities' link.", e);
        }
    }

    @Test(priority = 22, description = "Click 'Help' link in sidebar")
    public void clickHelp() {
        navigateHomeAndWait();

        System.out.println("Attempting to click on the 'Help' component...");
        By helpLocator = getSidebarLinkLocator("Help"); // [cite: 27]
        try {
            clickElement(helpLocator);
            System.out.println("Successfully clicked on the 'Help' component.");
            // Updated Expected URL based on logs
            wait.until(ExpectedConditions.urlContains("support.reddithelp.com")); // [cite: 5]
            System.out.println("Navigated to Help page: " + driver.getCurrentUrl());
            sleepForPresentation();
        } catch (Exception e) {
            System.err.println("Exception during clickHelp: " + e.getMessage()); // [cite: 28]
            e.printStackTrace();
            Assert.fail("Failed to click 'Help' link.", e);
        }
    }

    @Test(priority = 23, description = "Click 'Blog' link in sidebar")
    public void clickBlog() {
        navigateHomeAndWait();

        System.out.println("Attempting to click on the 'Blog' component...");
        By blogLocator = getSidebarLinkLocator("Blog");
        try {
            clickElement(blogLocator);
            System.out.println("Successfully clicked on the 'Blog' component.");
            // Updated Expected URL based on the failure log in the analysis
            wait.until(ExpectedConditions.urlContains("redditinc.com/blog")); // Corrected expected URL
            System.out.println("Navigated to Blog page: " + driver.getCurrentUrl());
            sleepForPresentation();
        } catch (TimeoutException e) {
            // Provide more context on timeout
            System.err.println("Timeout waiting for URL to contain 'redditinc.com/blog'. Current URL: " + driver.getCurrentUrl());
            Assert.fail("Failed to verify navigation to 'Blog' link (URL check failed).", e);
        }
        catch (Exception e) {
            System.err.println("Exception during clickBlog: " + e.getMessage());
            e.printStackTrace();
            Assert.fail("Failed to click 'Blog' link.", e);
        }
    }

    @Test(priority = 24, description = "Click 'Careers' link in sidebar")
    public void clickCareers() {
        navigateHomeAndWait();

        System.out.println("Attempting to click on the 'Careers' component...");
        By careersLocator = getSidebarLinkLocator("Careers");
        try {
            clickElement(careersLocator);
            System.out.println("Successfully clicked on the 'Careers' component.");
            // Wait for URL containing 'redditinc.com/careers' or similar
            wait.until(ExpectedConditions.urlContains("redditinc.com/careers"));
            System.out.println("Navigated to Careers page: " + driver.getCurrentUrl());
            sleepForPresentation();
        } catch (Exception e) {
            System.err.println("Exception during clickCareers: " + e.getMessage());
            e.printStackTrace();
            Assert.fail("Failed to click 'Careers' link.", e);
        }
    }

    @Test(priority = 25, description = "Click 'Press' link in sidebar")
    public void clickPress() {
        navigateHomeAndWait();

        System.out.println("Attempting to click on the 'Press' component...");
        By pressLocator = getSidebarLinkLocator("Press");
        try {
            clickElement(pressLocator);
            System.out.println("Successfully clicked on the 'Press' component.");
            // Wait for URL containing 'redditinc.com/press' or similar
            wait.until(ExpectedConditions.urlContains("redditinc.com/press"));
            System.out.println("Navigated to Press page: " + driver.getCurrentUrl());
            sleepForPresentation();
        } catch (Exception e) {
            System.err.println("Exception during clickPress: " + e.getMessage());
            e.printStackTrace();
            Assert.fail("Failed to click 'Press' link.", e);
        }
    }
}
