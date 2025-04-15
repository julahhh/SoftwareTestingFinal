package edu.reddituigroup.reddit.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class RedditSidebarLinksTest extends BaseTest {

    private By getSidebarLinkLocator(String linkText) {
        return By.xpath("//a[.//div[normalize-space()='" + linkText + "']]");
    }

    @Test(priority = 21, description = "Click 'Communities' link in sidebar")
    public void clickCommunities() {
        System.out.println("Navigating to homepage for sidebar test...");
        driver.get(REDDIT_URL);
        sleepForPresentation(1000); // Pause on homepage

        System.out.println("Attempting to click on the 'Communities' component...");
        By communitiesLocator = getSidebarLinkLocator("Communities");
        try {
            clickElement(communitiesLocator); // [cite: 24]
            System.out.println("Successfully clicked on the 'Communities' component.");
            wait.until(ExpectedConditions.urlContains("/communities"));
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
        System.out.println("Navigating to homepage for sidebar test...");
        driver.get(REDDIT_URL);
        sleepForPresentation(1000);

        System.out.println("Attempting to click on the 'Help' component...");
        By helpLocator = getSidebarLinkLocator("Help"); // [cite: 27]
        try {
            clickElement(helpLocator);
            System.out.println("Successfully clicked on the 'Help' component.");
            wait.until(ExpectedConditions.urlContains("support.reddithelp.com")); // [cite: 5] (From previous context, verify URL)
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
        System.out.println("Navigating to homepage for sidebar test...");
        driver.get(REDDIT_URL);
        sleepForPresentation(1000);

        System.out.println("Attempting to click on the 'Blog' component...");
        By blogLocator = getSidebarLinkLocator("Blog");
        try {
            clickElement(blogLocator);
            System.out.println("Successfully clicked on the 'Blog' component.");
            wait.until(ExpectedConditions.urlContains("redditblog.com"));
            System.out.println("Navigated to Blog page: " + driver.getCurrentUrl());
            sleepForPresentation();
        } catch (Exception e) {
            System.err.println("Exception during clickBlog: " + e.getMessage());
            e.printStackTrace();
            Assert.fail("Failed to click 'Blog' link.", e);
        }
    }

    @Test(priority = 24, description = "Click 'Careers' link in sidebar")
    public void clickCareers() {
        System.out.println("Navigating to homepage for sidebar test...");
        driver.get(REDDIT_URL);
        sleepForPresentation(1000);

        System.out.println("Attempting to click on the 'Careers' component...");
        By careersLocator = getSidebarLinkLocator("Careers");
        try {
            clickElement(careersLocator);
            System.out.println("Successfully clicked on the 'Careers' component.");
            wait.until(ExpectedConditions.urlContains("careers"));
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
        System.out.println("Navigating to homepage for sidebar test...");
        driver.get(REDDIT_URL);
        sleepForPresentation(1000);

        System.out.println("Attempting to click on the 'Press' component...");
        By pressLocator = getSidebarLinkLocator("Press");
        try {
            clickElement(pressLocator);
            System.out.println("Successfully clicked on the 'Press' component.");
            wait.until(ExpectedConditions.urlContains("press"));
            System.out.println("Navigated to Press page: " + driver.getCurrentUrl());
            sleepForPresentation();
        } catch (Exception e) {
            System.err.println("Exception during clickPress: " + e.getMessage());
            e.printStackTrace();
            Assert.fail("Failed to click 'Press' link.", e);
        }
    }
}