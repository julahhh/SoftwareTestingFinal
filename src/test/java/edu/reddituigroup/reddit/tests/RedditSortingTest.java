package edu.reddituigroup.reddit.tests;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.*;

public class RedditSortingTest extends BaseTest {

    private final By hotSortLocator = By.xpath("//button[normalize-space()='Hot'] | //a[contains(@href, '?sort=hot')]");
    private final By newSortLocator = By.xpath("//button[normalize-space()='New'] | //a[contains(@href, '?sort=new')]");
    private final By topSortLocator = By.xpath("//button[normalize-space()='Top'] | //a[contains(@href, '?sort=top')]");


    @BeforeMethod
    @Override
    public void setUp() {
        super.setUp();
        driver.get(REDDIT_URL + "r/popular/");
        wait.until(ExpectedConditions.urlContains("/r/popular/"));
        wait.until(ExpectedConditions.presenceOfElementLocated(hotSortLocator));
        System.out.println("Navigated to r/popular for sorting tests");
    }

    @Test(priority = 26, description = "Verify 'Hot' sort option is present and clickable.")
    public void verifyHotSortOption() {
        WebElement hotButton = findElement(hotSortLocator);
        Assert.assertTrue(hotButton.isDisplayed(), "'Hot' sort button should be displayed.");
        Assert.assertTrue(hotButton.isEnabled(), "'Hot' sort button should be clickable.");
        System.out.println("Verified 'Hot' sort option");
    }

    @Test(priority = 27, description = "Verify 'New' sort option is present and clickable.")
    public void verifyNewSortOption() {
        WebElement newButton = findElement(newSortLocator);
        Assert.assertTrue(newButton.isDisplayed(), "'New' sort button should be displayed.");
        Assert.assertTrue(newButton.isEnabled(), "'New' sort button should be clickable.");
        System.out.println("Verified 'New' sort option");
    }

    @Test(priority = 28, description = "Verify 'Top' sort option is present and clickable.")
    public void verifyTopSortOption() {
        WebElement topButton = findElement(topSortLocator);
        Assert.assertTrue(topButton.isDisplayed(), "'Top' sort button should be displayed.");
        Assert.assertTrue(topButton.isEnabled(), "'Top' sort button should be clickable.");
        System.out.println("Verified 'Top' sort option");
    }

    @Test(priority = 29, description = "Click 'New' sort and verify URL change or active state.")
    public void clickNewSortOption() {
        clickElement(newSortLocator);
        By activeNewLocator = By.xpath("//button[normalize-space()='New' and (@aria-selected='true' or contains(@class, 'active'))] | //a[contains(@href, '?sort=new') and contains(@class, 'active')]");
        wait.until(ExpectedConditions.or(
                ExpectedConditions.urlContains("sort=new"),
                ExpectedConditions.presenceOfElementLocated(activeNewLocator)
        ));
        boolean isActive = driver.getCurrentUrl().contains("sort=new") || findElement(activeNewLocator).isDisplayed();
        Assert.assertTrue(isActive, "Clicking 'New' should update URL or set button state to active.");
        System.out.println("Clicked 'New' sort and verified state/URL");
    }

    @Test(priority = 30, description = "Click 'Top' sort and verify URL change or active state.")
    public void clickTopSortOption() {
        clickElement(topSortLocator);
        By activeTopLocator = By.xpath("//button[normalize-space()='Top' and (@aria-selected='true' or contains(@class, 'active'))] | //a[contains(@href, '?sort=top') and contains(@class, 'active')]");
        wait.until(ExpectedConditions.or(
                ExpectedConditions.urlContains("sort=top"),
                ExpectedConditions.presenceOfElementLocated(activeTopLocator)
        ));
        boolean isActive = driver.getCurrentUrl().contains("sort=top") || findElement(activeTopLocator).isDisplayed();
        Assert.assertTrue(isActive, "Clicking 'Top' should update URL or set button state to active.");

        By topTimeFrameLocator = By.xpath("//button[contains(., 'Today')] | //div[contains(@role,'menu')]//button[normalize-space()='Today']");
        WebElement topTimeFrame = findElement(topTimeFrameLocator);
        Assert.assertTrue(topTimeFrame.isDisplayed(), "Timeframe options for 'Top' should appear.");
        System.out.println("Clicked 'Top' sort and verified state/URL");
    }
}