package edu.reddituigroup.reddit.tests;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.*;

public class RedditTrendingCommunitiesTest extends BaseTest {

    @Test(priority = 1, description = "Verify the trending section is visible.")
    public void verifyTrendingSectionVisible() {
        try {
            driver.get(REDDIT_URL);
            sleepForPresentation(2000); // Wait for page load

            By[] trendingSectionLocators = {
                By.xpath("//h2[contains(text(), 'Trending')]"),
                By.xpath("//h3[contains(text(), 'Trending')]"),
                By.xpath("//div[contains(@aria-label, 'Trending')]"),
                By.cssSelector("div[data-testid='trending-communities']"),
                By.cssSelector("div[data-testid='popular-communities']")
            };

            WebElement trendingSection = null;
            for (By locator : trendingSectionLocators) {
                try {
                    trendingSection = findElement(locator);
                    if (trendingSection != null && trendingSection.isDisplayed()) {
                        break;
                    }
                } catch (Exception e) {
                    continue;
                }
            }

            Assert.assertNotNull(trendingSection, "Trending section should be found using any of the locators");
            Assert.assertTrue(trendingSection.isDisplayed(), "Trending section should be displayed");
            System.out.println("Verified trending section presence");
        } catch (Exception e) {
            Assert.fail("Failed to verify trending section: " + e.getMessage());
        }
    }

    @Test(priority = 2, description = "Click the first trending community link.")
    public void clickFirstTrendingCommunityLink() {
        try {
            driver.get(REDDIT_URL);
            sleepForPresentation(2000); // Wait for page load

            By[] communityLinkLocators = {
                By.cssSelector("a[href^='/r/']"),
                By.xpath("//a[contains(@href, '/r/')]"),
                By.cssSelector("div[data-testid='trending-communities'] a"),
                By.cssSelector("div[data-testid='popular-communities'] a")
            };

            WebElement communityLink = null;
            for (By locator : communityLinkLocators) {
                try {
                    communityLink = findElement(locator);
                    if (communityLink != null && communityLink.isDisplayed()) {
                        communityLink.click();
                        break;
                    }
                } catch (Exception e) {
                    continue;
                }
            }

            Assert.assertNotNull(communityLink, "Community link should be found using any of the locators");
            sleepForPresentation(2000); // Wait for navigation

            // Verify we're on a subreddit page
            String currentUrl = driver.getCurrentUrl();
            Assert.assertTrue(currentUrl.contains("/r/"), "Should be on a subreddit page. Current URL: " + currentUrl);
            System.out.println("Successfully clicked first trending community link");
        } catch (Exception e) {
            Assert.fail("Failed to click first trending community link: " + e.getMessage());
        }
    }

    @Test(priority = 3, description = "Verify trending section is visible after refresh.")
    public void verifyTrendingVisibleAfterRefresh() {
        try {
            driver.get(REDDIT_URL);
            sleepForPresentation(2000); // Wait for page load
            driver.navigate().refresh();
            sleepForPresentation(2000); // Wait for refresh

            By[] trendingSectionLocators = {
                By.xpath("//h2[contains(text(), 'Trending')]"),
                By.xpath("//h3[contains(text(), 'Trending')]"),
                By.xpath("//div[contains(@aria-label, 'Trending')]"),
                By.cssSelector("div[data-testid='trending-communities']"),
                By.cssSelector("div[data-testid='popular-communities']")
            };

            WebElement trendingSection = null;
            for (By locator : trendingSectionLocators) {
                try {
                    trendingSection = findElement(locator);
                    if (trendingSection != null && trendingSection.isDisplayed()) {
                        break;
                    }
                } catch (Exception e) {
                    continue;
                }
            }

            Assert.assertNotNull(trendingSection, "Trending section should be found using any of the locators");
            Assert.assertTrue(trendingSection.isDisplayed(), "Trending section should be displayed");
            System.out.println("Verified trending section presence after refresh");
        } catch (Exception e) {
            Assert.fail("Failed to verify trending section after refresh: " + e.getMessage());
        }
    }

    @Test(priority = 4, description = "Verify the 'Help' link in the footer.")
    public void verifyFooterHelpLink() {
        try {
            driver.get(REDDIT_URL);
            sleepForPresentation(2000); // Wait for page load
            scrollToFooter();

            By[] helpLinkLocators = {
                By.xpath("//footer//a[contains(text(), 'Help')]"),
                By.xpath("//footer//a[contains(text(), 'Help Center')]"),
                By.cssSelector("footer a[href*='help']"),
                By.xpath("//a[contains(@href, 'help')]")
            };

            WebElement helpLink = null;
            for (By locator : helpLinkLocators) {
                try {
                    helpLink = findElement(locator);
                    if (helpLink != null && helpLink.isDisplayed()) {
                        break;
                    }
                } catch (Exception e) {
                    continue;
                }
            }

            Assert.assertNotNull(helpLink, "Help link should be found using any of the locators");
            Assert.assertTrue(helpLink.isDisplayed(), "Help link should be displayed");
            System.out.println("Verified Help link presence in footer");
        } catch (Exception e) {
            Assert.fail("Failed to verify Help link: " + e.getMessage());
        }
    }

    @Test(priority = 5, description = "Verify the 'User Agreement' link in the footer.")
    public void verifyUserAgreementLink() {
        try {
            driver.get(REDDIT_URL);
            sleepForPresentation(2000); // Wait for page load
            scrollToFooter();

            By[] agreementLinkLocators = {
                By.xpath("//footer//a[contains(text(), 'User Agreement')]"),
                By.xpath("//footer//a[contains(text(), 'Terms')]"),
                By.cssSelector("footer a[href*='terms']"),
                By.xpath("//a[contains(@href, 'terms')]")
            };

            WebElement agreementLink = null;
            for (By locator : agreementLinkLocators) {
                try {
                    agreementLink = findElement(locator);
                    if (agreementLink != null && agreementLink.isDisplayed()) {
                        break;
                    }
                } catch (Exception e) {
                    continue;
                }
            }

            Assert.assertNotNull(agreementLink, "User Agreement link should be found using any of the locators");
            Assert.assertTrue(agreementLink.isDisplayed(), "User Agreement link should be displayed");
            System.out.println("Verified User Agreement link presence in footer");
        } catch (Exception e) {
            Assert.fail("Failed to verify User Agreement link: " + e.getMessage());
        }
    }
}