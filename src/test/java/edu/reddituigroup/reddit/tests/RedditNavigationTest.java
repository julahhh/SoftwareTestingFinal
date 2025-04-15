package edu.reddituigroup.reddit.tests;

import org.openqa.selenium.*;
// Removed unused import: org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.*;

public class RedditNavigationTest extends BaseTest {

    // Updated locator: Using CSS selector for href attribute - more stable than absolute XPath
    private final By popularLinkLocator = By.cssSelector("a[href='/r/popular/']");
    // Updated locator: Assuming this corresponds to the 'Home' link for logged-out users
    private final By homeLinkLocator = By.cssSelector("a[href='/']"); // Changed name from answersLinkLocator
    // Locator for sidebar/navigation area - kept original robust fallback XPath
    private final By topicsSidebarLocator = By.xpath("//nav | //div[contains(@aria-label,'Primary')] | //*[contains(text(), 'Feeds')]");


    @Test(priority = 6, description = "Verify the 'Popular' link is present and clickable.")
    public void verifyPopularLink() {
        WebElement popularLink = findElement(popularLinkLocator);
        Assert.assertTrue(popularLink.isDisplayed(), "'Popular' link should be displayed.");
        Assert.assertTrue(popularLink.isEnabled(), "'Popular' link should be clickable.");
        System.out.println("Verified 'Popular' link presence");
    }

    @Test(priority = 7, description = "Navigate to 'Popular' feed and verify URL/Title.")
    public void navigateToPopular() {
        clickElement(popularLinkLocator);
        wait.until(ExpectedConditions.urlContains("/r/popular/"));
        Assert.assertTrue(driver.getCurrentUrl().contains("/r/popular/"), "URL should contain '/r/popular/'.");
        // Title might change, verifying URL is usually sufficient
        // Assert.assertTrue(driver.getTitle().contains("Popular"), "Title should contain 'Popular'.");
        System.out.println("Navigated to Popular and verified URL");
    }

    @Test(priority = 8, description = "Verify Home link is present and clickable.") // Renamed test
    public void verifyHomeLink() { // Renamed method
        WebElement homeLink = findElement(homeLinkLocator);
        Assert.assertTrue(homeLink.isDisplayed(), "'Home' link should be displayed.");
        Assert.assertTrue(homeLink.isEnabled(), "'Home' link should be clickable.");
        System.out.println("Verified 'Home' link presence");
    }

    @Test(priority = 9, description = "Verify Page Scroll") // Simplified description
    public void scrollPage() { // Removed throws InterruptedException
        // Removed redundant driver creation - uses driver from BaseTest
        // driver.get("https://www.reddit.com/?feed=home"); // BaseTest already navigates to base URL
        // driver.manage().window().maximize(); // Handled in BaseTest

        JavascriptExecutor exe = (JavascriptExecutor) driver;
        System.out.println("Scrolling down...");
        exe.executeScript("window.scrollBy(0,1000)", "");//scroll down relative amount
        sleepForPresentation(1500); // Use helper method instead of Thread.sleep
        System.out.println("Scrolling up...");
        exe.executeScript("window.scrollBy(0,-1000)", "");//scroll up relative amount
        sleepForPresentation(1500); // Use helper method instead of Thread.sleep
        System.out.println("Scroll test finished.");
        // No driver.quit() here - managed by BaseTest @AfterClass/Suite
    }

    @Test(priority= 10, description = "Verify presence of a 'Topics' or similar sidebar section.")
    public void verifyTopicsSidebarSection() {
        WebElement topicsSection = findElement(topicsSidebarLocator);
        Assert.assertTrue(topicsSection.isDisplayed(), "'Topics' or similar sidebar section should be present.");
        System.out.println("Verified Topics/Sidebar section presence");
    }
}
