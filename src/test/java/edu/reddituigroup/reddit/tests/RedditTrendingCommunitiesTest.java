package edu.reddituigroup.reddit.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
// Removed unused import: org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*; // Keep annotations import

import java.time.Duration;
import java.util.List;

public class RedditTrendingCommunitiesTest extends BaseTest {

    // Removed the local @BeforeClass setUp - this class should use BaseTest setup/teardown
    // @BeforeClass
    // public void setUp() {
    //     driver = new ChromeDriver(); // This was causing issues
    //     driver.manage().window().maximize();
    //     driver.get("https://www.reddit.com/");
    // }

    // Ensure navigation to homepage before each test in this class if needed
    @BeforeMethod
    public void navigateToHomeIfNeeded() {
        if (!driver.getCurrentUrl().equals(REDDIT_URL)) {
            System.out.println("Navigating to homepage for Trending Communities test...");
            driver.get(REDDIT_URL);
            wait.until(ExpectedConditions.urlToBe(REDDIT_URL));
        }
        // Wait for a known element to ensure page is ready
        // Updated Wait: Wait for PRESENCE of search input OR feed container, making it more robust
        // than waiting for VISIBILITY of only the search input.
        System.out.println("Waiting for homepage elements to be present...");
        try {
            wait.until(ExpectedConditions.or(
                    ExpectedConditions.presenceOfElementLocated(By.cssSelector("faceplate-search-input")),
                    ExpectedConditions.presenceOfElementLocated(By.cssSelector("shreddit-feed")), // Added alternative check for feed
                    ExpectedConditions.presenceOfElementLocated(By.cssSelector("div[data-testid='post-container']")) // Added check for post container
            ));
            System.out.println("Homepage elements found.");
        } catch (TimeoutException e) {
            System.err.println("Timeout waiting for key homepage elements (search input or feed). Page might not have loaded correctly.");
            // Capture current URL for debugging
            System.err.println("Current URL: " + driver.getCurrentUrl());
            // Optionally capture page source or screenshot here for deeper debugging
            Assert.fail("Setup failed: Key homepage elements not found.", e);
        }
    }


    @Test(priority = 31, description = "Verify Trending/Popular communities section is visible")
    public void testTrendingCommunitiesSectionVisible() {
        // Updated locator: Target section header text - more robust than structure-based XPath
        By trendingHeaderLocator = By.xpath("//h3[contains(text(),'Trending Today') or contains(text(),'Popular Communities')] | //*[contains(@aria-label,'communities')]/h2"); // Added fallback aria-label
        try {
            WebElement trendingSectionHeader = wait.until(ExpectedConditions.visibilityOfElementLocated(trendingHeaderLocator));
            Assert.assertTrue(trendingSectionHeader.isDisplayed(), "Trending/Popular communities header is not visible.");
            System.out.println("Trending/Popular communities section header found.");
        } catch (TimeoutException e) {
            System.err.println("Could not find Trending/Popular header using locator: " + trendingHeaderLocator);
            Assert.fail("Trending/Popular communities header is not visible.", e);
        }
    }

    @Test(priority = 32, description = "Open the first link in the Trending/Popular section")
    public void testOpenFirstTrendingCommunity() {
        // Updated locator: Find the first link (<a> tag with href containing /r/) within the likely container
        // This assumes the container can be identified by aria-label or contains the header found above.
        By firstCommunityLinkLocator = By.xpath("( (//div[contains(@aria-label,'communities')] | //div[.//h3[contains(text(),'Trending Today') or contains(text(),'Popular Communities')]])//a[contains(@href,'/r/')] )[1]");
        try {
            WebElement firstLink = wait.until(ExpectedConditions.elementToBeClickable(firstCommunityLinkLocator));
            String linkText = firstLink.getText(); // Get text for logging
            System.out.println("Clicking first trending/popular community link: " + linkText);
            firstLink.click();

            // Check we navigated to the community page (URL has /r/ in path)
            wait.until(ExpectedConditions.urlContains("/r/"));
            String currentUrl = driver.getCurrentUrl();
            Assert.assertTrue(currentUrl.contains("/r/"), "Not navigated to a community page. Current URL: " + currentUrl);
            System.out.println("Successfully navigated to: " + currentUrl);
            // Navigate back for other tests if necessary (BaseTest @AfterClass handles cleanup)
            // driver.navigate().back(); // Removed, let BaseTest handle state between tests/classes

        } catch (TimeoutException e) {
            System.err.println("Could not find or click the first community link using locator: " + firstCommunityLinkLocator);
            Assert.fail("Failed to open first trending/popular community link.", e);
        }
    }

    @Test(priority = 33, description = "Verify Trending/Popular section is visible after refresh")
    public void testTrendingRefresh() {
        System.out.println("Refreshing page...");
        driver.navigate().refresh();
        // Use the same robust locator as the visibility test
        By trendingHeaderLocator = By.xpath("//h3[contains(text(),'Trending Today') or contains(text(),'Popular Communities')] | //*[contains(@aria-label,'communities')]/h2");
        try {
            // Also ensure page is generally ready after refresh before checking specific header
            wait.until(ExpectedConditions.or(
                    ExpectedConditions.presenceOfElementLocated(By.cssSelector("faceplate-search-input")),
                    ExpectedConditions.presenceOfElementLocated(By.cssSelector("shreddit-feed"))
            ));
            WebElement trendingSectionHeader = wait.until(ExpectedConditions.visibilityOfElementLocated(trendingHeaderLocator));
            Assert.assertTrue(trendingSectionHeader.isDisplayed(), "Trending/Popular section should be visible after refresh.");
            System.out.println("Trending/Popular communities section header found after refresh.");
        } catch (TimeoutException e) {
            System.err.println("Could not find Trending/Popular header after refresh using locator: " + trendingHeaderLocator);
            Assert.fail("Trending/Popular section not visible after refresh.", e);
        }
    }

    @Test(priority = 34, description = "Verify Help link is present in the footer")
    public void testFooterHelpLink() {
        scrollToFooter(); // Ensure footer is scrolled into view
        // Kept original locator - seems reasonable
        By helpLinkLocator = By.xpath("//a[contains(text(),'Help Center') or contains(text(),'Help')]");
        try {
            WebElement helpLink = wait.until(ExpectedConditions.visibilityOfElementLocated(helpLinkLocator));
            Assert.assertTrue(helpLink.isDisplayed(), "Help link not visible in footer.");
            System.out.println("Footer Help link found.");
        } catch (TimeoutException e) {
            System.err.println("Could not find Help link in footer using locator: " + helpLinkLocator);
            Assert.fail("Help link not visible in footer.", e);
        }
    }

    @Test(priority = 35, description = "Verify User Agreement link is present in the footer")
    public void testUserAgreementLink() {
        scrollToFooter(); // Ensure footer is scrolled into view
        // Kept original locator - seems reasonable
        By userAgreementLinkLocator = By.xpath("//a[contains(text(),'User Agreement') or contains(text(),'Terms')]");
        try {
            WebElement userAgreementLink = wait.until(ExpectedConditions.visibilityOfElementLocated(userAgreementLinkLocator));
            Assert.assertTrue(userAgreementLink.isDisplayed(), "User Agreement / Terms link not visible in footer.");
            System.out.println("Footer User Agreement link found.");
        } catch (TimeoutException e) {
            System.err.println("Could not find User Agreement link in footer using locator: " + userAgreementLinkLocator);
            Assert.fail("User Agreement / Terms link not visible in footer.", e);
        }
    }

    // Removed @AfterClass - BaseTest should handle teardown
    // @AfterClass
    // public void tearDown() {
    //     if (driver != null) {
    //         driver.quit();
    //     }
    // }
}
