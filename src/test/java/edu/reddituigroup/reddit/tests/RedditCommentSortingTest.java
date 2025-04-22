package edu.reddituigroup.reddit.tests;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.testng.Assert;
import org.testng.annotations.*;

public class RedditCommentSortingTest extends BaseTest {

    private static final String TEST_POST_URL = "https://www.reddit.com/r/programming/comments/1bq1234/test_post/";

    // Locator for the comment sort dropdown component
    private final By commentSortDropdownLocator = By.cssSelector("shreddit-sort-dropdown");
    // Locator for any comment element to confirm comments are loaded
    private final By commentLocator = By.cssSelector("shreddit-comment");

    @BeforeMethod
    public void navigateToPostForSorting() {
        System.out.println("Setting up for comment sorting: Navigating to a post page...");
        // Start from home page to ensure a fresh state
        driver.get(REDDIT_URL);
        wait.until(ExpectedConditions.urlToBe(REDDIT_URL));
        System.out.println("On homepage, waiting for posts...");

        // Wait for posts to appear
        By postLocator = By.cssSelector("shreddit-post, div[data-testid='post-container']");
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(postLocator));
            System.out.println("Feed posts loaded on homepage.");
        } catch (TimeoutException e){
            Assert.fail("Homepage feed posts did not load in BeforeMethod.", e);
        }

        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            // Click the first post link using JS (similar logic to RedditSearchTest)
            System.out.println("Clicking the first feed post using JS...");
            Object clickResult = js.executeScript(
                    "try {" +
                            "const postContainer = document.querySelector('shreddit-post, div[data-testid=\"post-container\"]');" +
                            "if (!postContainer) return 'ERROR: Could not find the first post container';" +
                            "const postLink = postContainer.querySelector('a[slot=\"full-post-link\"], a[data-testid=\"post-title\"], h3 > a');" +
                            "if (!postLink) return 'ERROR: Could not find the post link within the container';" +
                            "postLink.click(); return 'OK: Post link clicked';" +
                            "} catch (e) { return 'ERROR: JS Exception (click post): ' + e.message; }"
            );
            System.out.println("JS Click Post Result: " + clickResult);
            Assert.assertTrue(clickResult.toString().startsWith("OK:"), "Could not click post link in BeforeMethod setup using JS: " + clickResult);

            // Wait for navigation to the comments page and key elements
            wait.until(ExpectedConditions.urlContains("/comments/"));
            System.out.println("Navigated to comments page URL: " + driver.getCurrentUrl());
            wait.until(ExpectedConditions.visibilityOfElementLocated(commentSortDropdownLocator));
            System.out.println("Comment sort dropdown found.");
            wait.until(ExpectedConditions.visibilityOfElementLocated(commentLocator)); // Ensure comments themselves start loading
            System.out.println("Initial comments loaded.");
            System.out.println("Setup complete: Ready for comment sorting tests.");
            sleepForPresentation(); // Pause on post page before tests run

        } catch (Exception e) {
            System.err.println("Error during BeforeMethod setup for Comment Sorting: " + e.getMessage());
            Assert.fail("Failed to navigate to post page and verify elements in BeforeMethod setup.", e);
        }
    }

    @Test(priority = 1, description = "Sort comments by 'Best'.")
    public void sortCommentsByBest() {
        sortCommentsBy("Best");
    }

    @Test(priority = 2, description = "Sort comments by 'Top'.")
    public void sortCommentsByTop() {
        sortCommentsBy("Top");
    }

    @Test(priority = 3, description = "Sort comments by 'New'.")
    public void sortCommentsByNew() {
        sortCommentsBy("New");
    }

    @Test(priority = 4, description = "Sort comments by 'Controversial'.")
    public void sortCommentsByControversial() {
        sortCommentsBy("Controversial");
    }

    @Test(priority = 5, description = "Sort comments by 'Old'.")
    public void sortCommentsByOld() {
        sortCommentsBy("Old");
    }

    @Test(priority = 6, description = "Sort comments by 'Q&A'.")
    public void sortCommentsByQnA() {
        sortCommentsBy("Q&A");
    }

    private void sortCommentsBy(String sortOption) {
        try {
            driver.get(TEST_POST_URL);
            sleepForPresentation(2000); // Wait for page load

            // Try to find the sort button
            By[] sortButtonLocators = {
                By.cssSelector("button[aria-label*='sort']"),
                By.cssSelector("button[data-testid='sort-button']"),
                By.xpath("//button[contains(text(), 'Sort')]"),
                By.xpath("//button[contains(@aria-label, 'sort')]")
            };

            WebElement sortButton = null;
            for (By locator : sortButtonLocators) {
                try {
                    sortButton = findElement(locator);
                    if (sortButton != null && sortButton.isDisplayed()) {
                        sortButton.click();
                        break;
                    }
                } catch (Exception e) {
                    continue;
                }
            }

            Assert.assertNotNull(sortButton, "Sort button should be found using any of the locators");
            sleepForPresentation(1000); // Wait for menu to appear

            // Try to find the sort option
            By[] sortOptionLocators = {
                By.xpath("//button[contains(text(), '" + sortOption + "')]"),
                By.xpath("//div[contains(@role, 'menuitem')][contains(text(), '" + sortOption + "')]"),
                By.cssSelector("button[data-testid='sort-" + sortOption.toLowerCase() + "']"),
                By.xpath("//*[contains(@aria-label, '" + sortOption + "')]")
            };

            WebElement sortOptionElement = null;
            for (By locator : sortOptionLocators) {
                try {
                    sortOptionElement = findElement(locator);
                    if (sortOptionElement != null && sortOptionElement.isDisplayed()) {
                        sortOptionElement.click();
                        break;
                    }
                } catch (Exception e) {
                    continue;
                }
            }

            Assert.assertNotNull(sortOptionElement, "Sort option '" + sortOption + "' should be found using any of the locators");
            sleepForPresentation(2000); // Wait for sorting to take effect

            // Verify the sort option was selected
            String currentUrl = driver.getCurrentUrl();
            Assert.assertTrue(currentUrl.contains("sort=" + sortOption.toLowerCase()), 
                "URL should contain sort parameter for " + sortOption + ". Current URL: " + currentUrl);
            System.out.println("Successfully sorted comments by " + sortOption);
        } catch (Exception e) {
            Assert.fail("Failed to sort comments by " + sortOption + ": " + e.getMessage());
        }
    }
}