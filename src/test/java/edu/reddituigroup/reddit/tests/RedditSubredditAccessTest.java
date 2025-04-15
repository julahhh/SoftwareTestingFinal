package edu.reddituigroup.reddit.tests;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.*;

public class RedditSubredditAccessTest extends BaseTest {

    private final String subreddit = "softwaretesting";

    @Test(priority = 36, description = "Navigate directly to a subreddit URL.")
    public void navigateToSubredditDirectly() {
        String subredditUrl = REDDIT_URL + "r/" + subreddit + "/";
        driver.get(subredditUrl);
        wait.until(ExpectedConditions.urlContains("/r/" + subreddit + "/"));
        Assert.assertTrue(driver.getCurrentUrl().contains("/r/" + subreddit + "/"), "URL should contain '/r/" + subreddit + "/'.");
        System.out.println("Navigated directly to r/" + subreddit);
    }

    @Test(priority = 37, description = "Verify the subreddit header/title is displayed.")
    public void verifySubredditHeader() {
        driver.get(REDDIT_URL + "r/" + subreddit + "/");
        By headerLocator = By.xpath(String.format("//h1[contains(text(), 'r/%s')]", subreddit));
        WebElement header = findElement(headerLocator);
        Assert.assertTrue(header.isDisplayed(), "Subreddit header should be displayed.");
        Assert.assertTrue(header.getText().toLowerCase().contains("r/" + subreddit.toLowerCase()), "Subreddit header text should contain 'r/" + subreddit + "'.");
        System.out.println("Verified header for r/" + subreddit);
    }

    @Test(priority = 38, description = "Verify posts are loaded within the subreddit feed.")
    public void verifyPostsLoadInSubreddit() {
        driver.get(REDDIT_URL + "r/" + subreddit + "/");
        By postLocator = By.cssSelector("#t3_1jyz8cc > a.absolute.inset-0");
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(postLocator, 0)); // Wait for at least one post

        Assert.assertTrue(findElements(postLocator).size() > 0, "Posts should be loaded in the subreddit feed.");
        System.out.println("Verified posts loaded in r/" + subreddit);
    }

    @Test(priority = 39, description = "Verify the 'Join' button is present on the subreddit page (when logged out).")
    public void verifyJoinButtonPresence() {
        driver.get(REDDIT_URL + "r/" + subreddit + "/");
        By joinButtonLocator = By.xpath("//*[@id=\"subgrid-container\"]/div[1]/section/div/div[2]/shreddit-subreddit-header-buttons//div/faceplate-tracker/shreddit-join-button//button");
        WebElement joinButton = findElement(joinButtonLocator);
        Assert.assertTrue(joinButton.isDisplayed(), "'Join' button should be displayed on the subreddit page.");
        System.out.println("Verified 'Join' button presence in r/" + subreddit);
    }

    @Test(priority = 40, description = "Verify subreddit rules or info section is present.")
    public void verifySubredditInfoSection() {
        driver.get(REDDIT_URL + "r/" + subreddit + "/");
        By infoSectionLocator = By.xpath("//*[contains(@aria-label, 'Community details')] | //div[contains(., 'About Community')] | //div[contains(., 'Rules')]");
        WebElement infoSection = findElement(infoSectionLocator);
        scrollToElement(infoSectionLocator); // Scroll to make sure it's in view if needed
        Assert.assertTrue(infoSection.isDisplayed(), "Subreddit info/rules section should be displayed.");
        System.out.println("Verified info/rules section presence in r/" + subreddit);
    }
}