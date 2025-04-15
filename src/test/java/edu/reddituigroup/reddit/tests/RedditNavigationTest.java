package edu.reddituigroup.reddit.tests;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.*;

public class RedditNavigationTest extends BaseTest {

    private final By popularLinkLocator = By.xpath("/html/body/shreddit-app/div[2]/reddit-sidebar-nav/nav/left-nav-top-section//div/faceplate-tracker[2]/li/a");
    private final By answersLinkLocator = By.xpath("/html/body/shreddit-app/div[2]/reddit-sidebar-nav/nav/left-nav-top-section//div/faceplate-tracker[3]/li/a");
    private final By topicsSidebarLocator = By.xpath("//nav | //div[contains(@aria-label,'Primary')] | //*[contains(text(), 'Feeds')]");


    @Test(priority = 6, description = "Verify the 'Popular' link is present and clickable.")
    public void verifyPopularLink() {
        WebElement popularLink = findElement(popularLinkLocator);
        Assert.assertTrue(popularLink.isDisplayed(), "'Popular' link should be displayed.");
        Assert.assertTrue(popularLink.isEnabled(), "'Popular' link should be clickable.");
        System.out.println("Verified 'Popular' link presence");
    }

    @Test(priority = 7, description = "Navigate to 'Popular' feed and verify URL/Title.")
    public void navigateToPopular() {
        clickElement(popularLinkLocator);
        wait.until(ExpectedConditions.urlContains("/r/popular/"));
        Assert.assertTrue(driver.getCurrentUrl().contains("/r/popular/"), "URL should contain '/r/popular/'.");
        Assert.assertTrue(driver.getTitle().contains("Popular"), "Title should contain 'Popular'.");
        System.out.println("Navigated to Popular and verified URL/Title");
    }

    @Test(priority = 8, description = "Verify Answers link")
    public void verifyAnswersLink() {
        WebElement allLink = findElement(answersLinkLocator);
        Assert.assertTrue(allLink.isDisplayed(), "'Answers' link should be displayed.");
        Assert.assertTrue(allLink.isEnabled(), "'Answers' link should be clickable.");
        System.out.println("Verified 'Answers' link presence");
    }

    @Test(priority = 9, description = "Verify Scroll Page")
    public void scrollPage() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.reddit.com/?feed=home");
        driver.manage().window().maximize();
        JavascriptExecutor exe = (JavascriptExecutor) driver;
        exe.executeScript("window.scroll(0,1000)", "");//scroll up
        Thread.sleep(3000);
        exe.executeScript("window.scroll(0,-1000)", "");//scroll down
        Thread.sleep(3000);
    }

    @Test(priority= 10, description = "Verify presence of a 'Topics' or similar sidebar section.")
    public void verifyTopicsSidebarSection() {
        WebElement topicsSection = findElement(topicsSidebarLocator);
        Assert.assertTrue(topicsSection.isDisplayed(), "'Topics' or similar sidebar section should be present.");
        System.out.println("Verified Topics/Sidebar section presence");
    }
}