package edu.reddituigroup.reddit.tests;

import org.openqa.selenium.*;
// Removed unused import: org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.*;

public class RedditNavigationTest extends BaseTest {

    // Using CSS selector for href attribute - generally more stable
    private final By popularLinkLocator = By.cssSelector("a[href='/r/popular/']");
    // Using CSS selector for the main home link
    private final By homeLinkLocator = By.cssSelector("a[aria-label='Home'][href='/']");
    // Locator for a general sidebar/navigation area - kept XPath as fallback for structural identification
    private final By navSidebarLocator = By.xpath("//nav | //div[contains(@aria-label,'Primary')] | //div[contains(@aria-label,'Explore')]");


    @Test(priority = 1, description = "Verify the presence of the 'Popular' link.")
    public void verifyPopularLink() {
        try {
            driver.get(REDDIT_URL);
            sleepForPresentation(2000); // Wait for page load

            By[] popularLinkLocators = {
                By.cssSelector("a[href='/r/popular/']"),
                By.cssSelector("a[href*='popular']"),
                By.xpath("//a[contains(text(), 'Popular')]"),
                By.xpath("//a[contains(@href, 'popular')]")
            };

            WebElement popularLink = null;
            for (By locator : popularLinkLocators) {
                try {
                    popularLink = findElement(locator);
                    if (popularLink != null && popularLink.isDisplayed()) {
                        break;
                    }
                } catch (Exception e) {
                    continue;
                }
            }

            Assert.assertNotNull(popularLink, "Popular link should be found using any of the locators");
            Assert.assertTrue(popularLink.isDisplayed(), "Popular link should be displayed");
            System.out.println("Verified 'Popular' link presence");
        } catch (Exception e) {
            Assert.fail("Failed to verify Popular link presence: " + e.getMessage());
        }
    }

    @Test(priority = 2, description = "Navigate to the 'Popular' page.")
    public void navigateToPopular() {
        try {
            driver.get(REDDIT_URL);
            sleepForPresentation(2000); // Wait for page load

            By[] popularLinkLocators = {
                By.cssSelector("a[href='/r/popular/']"),
                By.cssSelector("a[href*='popular']"),
                By.xpath("//a[contains(text(), 'Popular')]"),
                By.xpath("//a[contains(@href, 'popular')]")
            };

            WebElement popularLink = null;
            for (By locator : popularLinkLocators) {
                try {
                    popularLink = findElement(locator);
                    if (popularLink != null && popularLink.isDisplayed()) {
                        popularLink.click();
                        break;
                    }
                } catch (Exception e) {
                    continue;
                }
            }

            Assert.assertNotNull(popularLink, "Popular link should be found using any of the locators");
            sleepForPresentation(2000); // Wait for navigation

            // Verify we're on the Popular page
            String currentUrl = driver.getCurrentUrl();
            Assert.assertTrue(currentUrl.contains("popular"), "Should be on the Popular page. Current URL: " + currentUrl);
            System.out.println("Successfully navigated to Popular page");
        } catch (Exception e) {
            Assert.fail("Failed to navigate to Popular page: " + e.getMessage());
        }
    }

    @Test(priority = 8, description = "Verify Home link is present and clickable.")
    public void verifyHomeLink() {
        WebElement homeLink = findElement(homeLinkLocator); // Use helper
        Assert.assertTrue(homeLink.isDisplayed(), "'Home' link should be displayed.");
        Assert.assertTrue(homeLink.isEnabled(), "'Home' link should be clickable.");
        System.out.println("Verified 'Home' link presence and enabled state");
    }

    @Test(priority = 9, description = "Perform basic page scroll down and up.")
    public void scrollPageBasic() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        long initialScroll = (Long) js.executeScript("return window.pageYOffset;");
        System.out.println("Initial scroll position: " + initialScroll);

        System.out.println("Scrolling down...");
        js.executeScript("window.scrollBy(0, 1000)"); // Scroll down relative amount
        sleepForPresentation(1000); // Use helper method for pause

        long scrollDownPosition = (Long) js.executeScript("return window.pageYOffset;");
        System.out.println("Scroll position after scrolling down: " + scrollDownPosition);
        Assert.assertTrue(scrollDownPosition > initialScroll, "Scroll position should increase after scrolling down.");

        System.out.println("Scrolling up...");
        js.executeScript("window.scrollBy(0, -500)"); // Scroll up relative amount
        sleepForPresentation(1000); // Use helper method for pause

        long scrollUpPosition = (Long) js.executeScript("return window.pageYOffset;");
        System.out.println("Scroll position after scrolling up: " + scrollUpPosition);
        Assert.assertTrue(scrollUpPosition < scrollDownPosition, "Scroll position should decrease after scrolling up.");

        System.out.println("Scroll test finished.");
    }

    @Test(priority= 10, description = "Verify presence of a navigation/sidebar section.")
    public void verifyNavSidebarSection() {
        WebElement sidebarSection = findElement(navSidebarLocator); // Use helper
        Assert.assertTrue(sidebarSection.isDisplayed(), "A primary navigation or sidebar section should be present.");
        System.out.println("Verified Navigation/Sidebar section presence");
    }
}