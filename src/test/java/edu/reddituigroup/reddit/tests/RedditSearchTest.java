package edu.reddituigroup.reddit.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class RedditSearchTest extends BaseTest {

    @Test(priority = 11, description = "Test searching using Shadow DOM JS execution and direct navigation.")
    public void searchOnRedditWithJSAndNav() {
        System.out.println("Setting search input via JS...");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        // Using JS to interact with Shadow DOM search input
        Object result = js.executeScript(
                "const faceplateInput = document.querySelector('faceplate-search-input');" +
                        "if (!faceplateInput || !faceplateInput.shadowRoot) return 'ERROR: No shadow root for faceplate-search-input';" +
                        "const input = faceplateInput.shadowRoot.querySelector('input');" +
                        "if (!input) return 'ERROR: No input found in shadow root';" +
                        "input.focus();" +
                        "input.value = 'computer science';" + // Sets input via JS [cite: 1]
                        "input.dispatchEvent(new Event('input', { bubbles: true }));" +
                        "return 'Input set via JS to: ' + input.value;"
        );
        System.out.println("JS Execution Result: " + result);
        sleepForPresentation(1000); // Short pause after JS execution

        String directSearchTerm = "dog pictures";
        String searchUrl = REDDIT_URL + "search/?q=" + directSearchTerm.replace(" ", "+");
        System.out.println("Navigating directly to search URL: " + searchUrl);
        driver.get(searchUrl);
        sleepForPresentation();

        wait.until(ExpectedConditions.urlContains("/search/?q="));
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("/search/?q="), "Should be on search results page. Current URL: " + currentUrl);
        System.out.println("Search page loaded: " + currentUrl);
    }

    @Test(priority = 12, description = "Click the first search result link (standard XPath).")
    public void clickFirstSearchResult() {
        String searchTerm = "cats";
        String searchUrl = REDDIT_URL + "search/?q=" + searchTerm;
        System.out.println("Navigating to search URL for clicking results: " + searchUrl);
        driver.get(searchUrl);
        wait.until(ExpectedConditions.urlContains("/search/?q="));
        sleepForPresentation(); // Pause on results page

        try {
            By firstResultLocator = By.xpath("(//a[contains(@href, '/r/')])[1]");
            System.out.println("Waiting for the first search result link...");
            WebElement firstResult = wait.until(ExpectedConditions.elementToBeClickable(firstResultLocator));

            System.out.println("First search result element found: " + firstResult.getText());
            System.out.println("Attempting to click the first search result...");
            clickElement(firstResultLocator);
            System.out.println("Successfully clicked the first search result.");
            wait.until(ExpectedConditions.not(ExpectedConditions.urlContains("/search/?q=")));
            System.out.println("Navigated away from search results page to: " + driver.getCurrentUrl());

        } catch (Exception e) {
            System.err.println("Exception during clickFirstResult: " + e.getMessage());
            e.printStackTrace();
            Assert.fail("Failed to click the first search result.", e);
        }
    }

    @Test(priority = 13, description = "Click the first feed post using JS.")
    public void clickFirstFeedPost() throws InterruptedException {
        System.out.println("Navigating to Reddit homepage...");
        driver.navigate().to(REDDIT_URL); // Use BaseTest driver
        wait.until(ExpectedConditions.urlToBe(REDDIT_URL)); // Wait for homepage URL
        // Wait for posts to appear
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("shreddit-post, div[data-testid='post-container']")));

        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;

            System.out.println("🔍 Trying to click the first feed post...");

            // Updated JS selector for robustness
            Object clickResult = js.executeScript(
                    "try {" +
                            // Look for the link within the first post container
                            "const postContainer = document.querySelector('shreddit-post, div[data-testid=\"post-container\"]');" +
                            "if (!postContainer) return '❌ Could not find the first post container';" +
                            // Find the specific link element within the container
                            // Common patterns: a[slot='full-post-link'], a[data-testid='post-title']
                            "const postLink = postContainer.querySelector('a[slot=\"full-post-link\"], a[data-testid=\"post-title\"]');" +
                            "if (!postLink) return '❌ Could not find the post link within the container';" +
                            "postLink.click();" +
                            "return '✅ First post clicked!';" +
                            "} catch (e) { return '❌ JS Exception: ' + e.message; }"
            );

            System.out.println("JS Result: " + clickResult);
            Assert.assertTrue(clickResult.toString().startsWith("✅"), "JS failed to click first post: " + clickResult);

            // Wait for navigation to a post page (usually contains /comments/)
            wait.until(ExpectedConditions.urlContains("/comments/"));
            System.out.println("Navigated to post page: " + driver.getCurrentUrl());

        } catch (Exception e) {
            System.out.println("❌ Exception in clickFirstFeedPost: " + e.getMessage());
            e.printStackTrace();
            Assert.fail("Exception occurred while clicking first feed post.", e);
        }
    }


    @Test(priority = 14, description = "Click the comments button (dependent on clickFirstFeedPost)")
    public void clickCommentsButton() throws InterruptedException {
        // This test implicitly depends on clickFirstFeedPost having navigated to a post page.
        // Consider adding navigation logic here if tests can run independently.
        System.out.println("Attempting to click the comments button...");
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            // JS to click the comments button within shreddit-post's shadow DOM
            // Verify the selector `button[data-post-click-location='comments-button']` is still correct in Reddit's current UI
            Object result = js.executeScript(
                    "const postEl = document.querySelector('shreddit-post');" +
                            "if (!postEl) return '❌ No <shreddit-post> element found';" +
                            "if (!postEl.shadowRoot) return '❌ <shreddit-post> has no shadow root';" +
                            // Selector for the comments button inside the shadow root - **VERIFY THIS SELECTOR**
                            "const commentButton = postEl.shadowRoot.querySelector(\"button[data-post-click-location='comments-button']\");" +
                            "if (!commentButton) return '❌ Comments button not found inside shadow root (Verify JS selector)'; " +
                            "commentButton.click();" +
                            "return '✅ Comments button clicked!'; "
            );
            System.out.println(result);
            Assert.assertTrue(result.toString().startsWith("✅"), "JS failed to click comments button: " + result);
            // Clicking comments usually navigates or reveals comments, wait for an indicator
            // Example: Wait for the comment input area or comment sort dropdown
            By commentIndicator = By.cssSelector("div[data-testid='comment-textarea'], shreddit-sort-dropdown");
            wait.until(ExpectedConditions.presenceOfElementLocated(commentIndicator));
            System.out.println("Comment section likely loaded after clicking comments button.");

        } catch (Exception e) {
            System.out.println("❌ Exception during clickCommentsButton: " + e.getMessage());
            e.printStackTrace();
            Assert.fail("Failed to click comments button.", e);
        }

        sleepForPresentation(); // Allow time for UI changes
    }

    @Test(priority = 15, description = "Verify comment input area is present after clicking comments (dependent on clickCommentsButton).")
    public void verifyCommentInputPresenceAfterClick() {
        // This test implicitly depends on clickCommentsButton having run successfully.
        System.out.println("Verifying comment input area presence...");
        // Updated locator: Prioritize data-testid if available, fallback to other structures
        // Reddit might lazy-load this, so waiting for visibility is better.
        // The #CommentSort--SortPicker might appear *before* the text area is ready.
        By commentInputLocator = By.cssSelector("div[data-testid='comment-textarea']");
        // Alternative/Fallback: By.cssSelector("textarea[placeholder='Add a comment']"); // Check actual placeholder text

        try {
            // Wait for the specific text area to be visible
            WebElement commentInputArea = wait.until(ExpectedConditions.visibilityOfElementLocated(commentInputLocator));

            scrollToElement(commentInputLocator); // Scroll to it
            Assert.assertTrue(commentInputArea.isDisplayed(), "Comment input area should be displayed.");
            System.out.println("Comment input area found and displayed.");
            sleepForPresentation();

        } catch (Exception e) {
            // If the primary locator fails, provide more context
            System.err.println("Could not find comment input area using locator: " + commentInputLocator);
            System.err.println("Current URL: " + driver.getCurrentUrl());
            System.err.println("Consider checking if the element is inside a Shadow DOM or if the data-testid/structure has changed.");
            e.printStackTrace();
            Assert.fail("Failed to verify comment input area presence.", e);
        }
    }
}
