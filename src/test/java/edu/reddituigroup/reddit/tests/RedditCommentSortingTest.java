package edu.reddituigroup.reddit.tests;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class RedditCommentSortingTest extends BaseTest {

    @BeforeClass
    public void navigateToPostForSorting() {
        System.out.println("Setting up for comment sorting: Navigating to a post page...");
        driver.get(REDDIT_URL); // Go to homepage first
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div[data-testid='post-container']")));

        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            Object clickResult = js.executeScript(
                    "const postLink = document.querySelector('a[slot=\"full-post-link\"].absolute');" +
                            "if (!postLink) { console.error('Post link not found for navigation'); return 'ERROR'; }" +
                            "postLink.click(); return 'OK';"
            );
            if ("ERROR".equals(clickResult)) Assert.fail("Could not navigate to post page in BeforeClass setup.");
            wait.until(ExpectedConditions.urlContains("/comments/"));
            System.out.println("Setup complete: On post page " + driver.getCurrentUrl());
            sleepForPresentation(); // Pause on post page before tests
        } catch (Exception e) {
            Assert.fail("Failed to navigate to post page in BeforeClass setup.", e);
        }
    }

    private void sortCommentsBy(String sortOption) {
        System.out.println("Attempting to sort comments by: " + sortOption);
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            Object openResult = js.executeScript(
                    "try {" +
                            "const dropdownEl = document.querySelector('shreddit-sort-dropdown');" +
                            "if (!dropdownEl) return 'ERROR: No shreddit-sort-dropdown found';" +
                            "if (!dropdownEl.shadowRoot) return 'ERROR: Dropdown has no shadow root';" +
                            "const button = dropdownEl.shadowRoot.getElementById('comment-sort-button');" + // JS Locator [cite: 17]
                            "if (!button) return 'ERROR: No button inside shadow root';" +
                            "button.click();" +
                            "return 'Opened sort dropdown';" +
                            "} catch (e) { return 'ERROR: JS Exception (open): ' + e.message; }"
            );
            System.out.println("JS Open Dropdown Result: " + openResult);
            Assert.assertFalse(openResult.toString().startsWith("ERROR:"), "JS execution failed: " + openResult);
            sleepForPresentation(1000); // Wait for dropdown animation/content

            String script =
                    "try {" +
                            "const items = document.querySelectorAll('faceplate-tracker[noun=\"sort\"] div[role=\"menuitem\"]');" + // JS Locator [cite: 19, 20] - Adjusted based on likely structure
                            "if (!items || items.length === 0) return 'ERROR: No sort menu items found';" +
                            "for (const el of items) {" +
                            "   const text = el.textContent.trim();" +
                            "   if (text === '" + sortOption + "') {" + // Match text [cite: 20]
                            "       el.click();" + // Click item [cite: 21]
                            "       return 'Clicked: ' + text;" +
                            "   }" +
                            "}" +
                            "return 'ERROR: Option not found: " + sortOption + "';" +
                            "} catch (e) { return 'ERROR: JS Exception (click): ' + e.message; }"; // [cite: 22]

            Object clickResult = js.executeScript(script);
            System.out.println("JS Click Sort Option Result: " + clickResult);
            Assert.assertFalse(clickResult.toString().startsWith("ERROR:"), "JS execution failed: " + clickResult);

            sleepForPresentation();

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