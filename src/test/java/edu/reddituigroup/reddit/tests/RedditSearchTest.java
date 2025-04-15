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
            driver.navigate().to("https://www.reddit.com/");
            Thread.sleep(3000); // Wait for feed to load

            try {
                JavascriptExecutor js = (JavascriptExecutor) driver;

                System.out.println("🔍 Trying to click the first feed post...");

                Object clickResult = js.executeScript(
                        "try {" +
                                "const postLink = document.querySelector('a[slot=\"full-post-link\"].absolute');" +
                                "if (!postLink) return '❌ Could not find the post link';" +
                                "postLink.click();" +
                                "return '✅ First post clicked!';" +
                                "} catch (e) { return '❌ JS Exception: ' + e.message; }"
                );

                System.out.println("JS Result: " + clickResult);
                Thread.sleep(3000); // Wait for the post page to load

            } catch (Exception e) {
                System.out.println("❌ Exception in clickFirstFeedPost: " + e.getMessage());
                e.printStackTrace();
            }
        }


    @Test(priority = 14, description = "Click the comments button")
    public void clickCommentsButton() throws InterruptedException {
        System.out.println("Attempting to click the comments button...");
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            Object result = js.executeScript(
                    "const postEl = document.querySelector('shreddit-post');" +
                            "if (!postEl) return '❌ No <shreddit-post> element found';" +
                            "if (!postEl.shadowRoot) return '❌ <shreddit-post> has no shadow root';" +
                            "const commentButton = postEl.shadowRoot.querySelector(\"button[data-post-click-location='comments-button']\");" +
                            "if (!commentButton) return '❌ Comments button not found inside shadow root';" +
                            "commentButton.click();" +
                            "return '✅ Comments button clicked!';"
            );
            System.out.println(result);
        } catch (Exception e) {
            System.out.println("❌ Exception during clickCommentsButton: " + e.getMessage());
            e.printStackTrace();
        }

        Thread.sleep(3000); // Allow time for any navigation or modal to open
    }

    @Test(priority = 15, description = "Verify comment input area is present after clicking comments.")
    public void verifyCommentInputPresenceAfterClick() {
        System.out.println("Verifying comment input area presence...");
        By commentInputLocator = By.cssSelector("div[data-testid='comment-textarea'], #CommentSort--SortPicker"); // Example locators

        try {
            WebElement commentInputArea = wait.until(ExpectedConditions.presenceOfElementLocated(commentInputLocator));

            scrollToElement(commentInputLocator);
            Assert.assertTrue(commentInputArea.isDisplayed(), "Comment input area should be displayed.");
            System.out.println("Comment input area found and displayed.");
            sleepForPresentation();

        } catch (Exception e) {

            System.err.println("Could not find comment input area using standard locator: " + e.getMessage());
            e.printStackTrace();
            Assert.fail("Failed to verify comment input area presence.", e);
        }
    }
}