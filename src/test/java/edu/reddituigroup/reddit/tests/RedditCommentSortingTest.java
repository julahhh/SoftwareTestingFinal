package edu.reddituigroup.reddit.tests;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod; // Changed from BeforeClass
import org.testng.annotations.Test;

public class RedditCommentSortingTest extends BaseTest {

    // Changed from @BeforeClass to @BeforeMethod to ensure driver is initialized by BaseTest.setUp() first
    @BeforeMethod
    public void navigateToPostForSorting() {
        // super.setUp(); // Call BaseTest setup if it's @BeforeMethod - Assuming BaseTest uses @BeforeClass now

        System.out.println("Setting up for comment sorting: Navigating to a post page...");
        // Ensure we start from homepage if BaseTest didn't already navigate
        if (!driver.getCurrentUrl().equals(REDDIT_URL)) {
            driver.get(REDDIT_URL);
        }
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("shreddit-post, div[data-testid='post-container']"))); // Wait for posts

        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            // Click the first post link using JS (similar to RedditSearchTest)
            Object clickResult = js.executeScript(
                    "const postContainer = document.querySelector('shreddit-post, div[data-testid=\"post-container\"]');" +
                            "if (!postContainer) return 'ERROR: Could not find the first post container';" +
                            "const postLink = postContainer.querySelector('a[slot=\"full-post-link\"], a[data-testid=\"post-title\"]');" +
                            "if (!postLink) return 'ERROR: Could not find the post link within the container';" +
                            "postLink.click(); return 'OK';"
            );
            Assert.assertEquals(clickResult, "OK", "Could not navigate to post page in BeforeMethod setup using JS.");

            // Wait for navigation to the comments page
            wait.until(ExpectedConditions.urlContains("/comments/"));
            // Wait for the comment sort dropdown to be present as an indicator the page is ready
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("shreddit-sort-dropdown")));
            System.out.println("Setup complete: On post page " + driver.getCurrentUrl());
            sleepForPresentation(); // Pause on post page before tests
        } catch (Exception e) {
            Assert.fail("Failed to navigate to post page in BeforeMethod setup.", e);
        }
    }

    private void sortCommentsBy(String sortOption) {
        System.out.println("Attempting to sort comments by: " + sortOption);
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;

            // --- Step 1: Open the Sort Dropdown using JS ---
            // Locator needs to target the button *inside* the shadow DOM of shreddit-sort-dropdown
            // ** VERIFY THESE JS SELECTORS against the live site **
            Object openResult = js.executeScript(
                    "try {" +
                            "const dropdownEl = document.querySelector('shreddit-sort-dropdown');" +
                            "if (!dropdownEl) return 'ERROR: No shreddit-sort-dropdown found';" +
                            "if (!dropdownEl.shadowRoot) return 'ERROR: Dropdown has no shadow root';" +
                            // Find button within shadow root (ID might change, use attributes if possible)
                            "const button = dropdownEl.shadowRoot.querySelector('button');" + // Simpler selector, might need refinement
                            "if (!button) return 'ERROR: No button inside shadow root (Verify JS selector)'; " +
                            "button.click();" +
                            "return 'Opened sort dropdown';" +
                            "} catch (e) { return 'ERROR: JS Exception (open): ' + e.message; }"
            );
            System.out.println("JS Open Dropdown Result: " + openResult);
            Assert.assertTrue(openResult.toString().startsWith("Opened"), "JS execution failed to open dropdown: " + openResult);
            sleepForPresentation(1000); // Wait for dropdown animation/content

            // --- Step 2: Click the Desired Sort Option ---
            // The sort options might be regular DOM elements *outside* the shadow root,
            // contained within a menu structure linked by aria attributes.
            // ** VERIFY THIS JS SELECTOR against the live site **
            String clickScript =
                    "try {" +
                            // Find the menu items (adjust selector based on actual structure)
                            // Common pattern: Look for elements with role='menuitemradio' or similar inside a container linked by aria-controls
                            "const items = document.querySelectorAll('div[role=\"menu\"] [role=\"menuitemradio\"], faceplate-tracker[noun=\"sort\"] div[role=\"menuitem\"]');" +
                            "if (!items || items.length === 0) return 'ERROR: No sort menu items found (Verify JS selector)'; " +
                            "for (const el of items) {" +
                            // Get text content, trim whitespace
                            "   const text = el.textContent.trim();" +
                            "   if (text === '" + sortOption + "') {" +
                            "       el.click();" +
                            "       return 'Clicked: ' + text;" +
                            "   }" +
                            "}" +
                            "return 'ERROR: Option not found: " + sortOption + "';" +
                            "} catch (e) { return 'ERROR: JS Exception (click): ' + e.message; }";

            Object clickResult = js.executeScript(clickScript);
            System.out.println("JS Click Sort Option Result: " + clickResult);
            Assert.assertTrue(clickResult.toString().startsWith("Clicked:"), "JS execution failed to click sort option: " + clickResult);

            // Wait for comments to potentially reload/reorder after sorting
            // We can wait for a brief moment or for a specific element change if predictable
            sleepForPresentation(1500); // Simple pause after sorting
            System.out.println("Successfully sorted by " + sortOption);

        } catch (Exception e) {
            System.err.println("Exception while sorting by " + sortOption + ": " + e.getMessage());
            e.printStackTrace();
            Assert.fail("Failed to sort comments by " + sortOption, e);
        }
    }

    @Test(priority = 16, description = "Sort comments by 'Top'")
    public void sortCommentsByTop() {
        sortCommentsBy("Top");
    }

    @Test(priority = 17, description = "Sort comments by 'New'")
    public void sortCommentsByNew() {
        sortCommentsBy("New");
    }

    @Test(priority = 18, description = "Sort comments by 'Controversial'")
    public void sortCommentsByControversial() {
        sortCommentsBy("Controversial");
    }

    @Test(priority = 19, description = "Sort comments by 'Old'")
    public void sortCommentsByOld() {
        sortCommentsBy("Old");
    }

    @Test(priority = 20, description = "Sort comments by 'Q&A'")
    public void sortCommentsByQnA() {
        sortCommentsBy("Q&A");
    }
}
