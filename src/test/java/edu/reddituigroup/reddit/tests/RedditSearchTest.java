package edu.reddituigroup.reddit.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.List;

public class RedditSearchTest extends BaseTest {

    private final String searchTerm = "selenium webdriver";

    @Test(priority = 1, description = "Test searching on Reddit")
    public void testSearch() {
        driver.get(REDDIT_URL);
        waitForPageLoad();

        // Find and click search input
        By searchInput = By.cssSelector("input[type='search']");
        WebElement searchElement = findElement(searchInput);
        searchElement.clear();
        searchElement.sendKeys(searchTerm);
        searchElement.submit();

        // Wait for search results
        By searchResults = By.cssSelector("div[data-testid='search-results-container']");
        WebElement resultsContainer = wait.until(ExpectedConditions.visibilityOfElementLocated(searchResults));
        Assert.assertTrue(resultsContainer.isDisplayed(), "Search results should be displayed");
    }

    @Test(priority = 2, description = "Test clicking first post")
    public void testClickFirstPost() {
        driver.get(REDDIT_URL);
        waitForPageLoad();

        // Find and click first post
        By firstPost = By.cssSelector("shreddit-post");
        WebElement post = findElement(firstPost);
        post.click();

        // Verify navigation to post page
        wait.until(ExpectedConditions.urlContains("/comments/"));
        Assert.assertTrue(driver.getCurrentUrl().contains("/comments/"), "Should be on post page");
    }

    @Test(priority = 3, description = "Test comment section")
    public void testCommentSection() {
        driver.get(REDDIT_URL);
        waitForPageLoad();

        // Click first post
        By firstPost = By.cssSelector("shreddit-post");
        WebElement post = findElement(firstPost);
        post.click();

        // Wait for comment section
        By commentSection = By.cssSelector("div[data-testid='comment-textarea']");
        WebElement commentArea = wait.until(ExpectedConditions.visibilityOfElementLocated(commentSection));
        Assert.assertTrue(commentArea.isDisplayed(), "Comment section should be visible");
    }
}