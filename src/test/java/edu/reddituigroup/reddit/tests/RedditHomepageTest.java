package edu.reddituigroup.reddit.tests;

import org.openqa.selenium.*;
import org.testng.Assert;
import org.testng.annotations.*;

public class RedditHomepageTest extends BaseTest {

    @Test(priority = 1, description = "Verify the page title of the Reddit homepage.")
    public void verifyHomepageTitle() {
        String expectedTitlePart = "Reddit";
        Assert.assertTrue(driver.getTitle().contains(expectedTitlePart), "Page title should contain '" + expectedTitlePart + "'. Actual: " + driver.getTitle());
        System.out.println("Verified homepage title contains 'Reddit'");
    }

    @Test(priority = 2, description = "Verify the presence of the Reddit logo.")
    public void verifyLogoPresence() {
        By logoLocator = By.cssSelector("a[aria-label='Home'], #reddit-logo");
        WebElement logo = findElement(logoLocator);
        Assert.assertTrue(logo.isDisplayed(), "Reddit logo should be displayed.");
        System.out.println("Verified Reddit logo presence");
    }

    @Test(priority = 3, description = "Verify the presence of the 'Log In' button.")
    public void verifyLoginButtonPresence() {
        By loginButtonLocator = By.cssSelector("a[href*='login'], button[data-testid='login-button'], #login-button");
        WebElement loginButton = findElement(loginButtonLocator);
        Assert.assertTrue(loginButton.isDisplayed(), "'Log In' button should be displayed.");
        System.out.println("Verified 'Log In' button presence");
    }

    @Test(priority = 4, description = "Verify the presence of the 'Sign Up' button.")
    public void verifySignUpButtonPresence() {
        By signUpButtonLocator = By.cssSelector("a[href*='register'], button[data-testid='signup-button']");
        WebElement signUpButton = findElement(signUpButtonLocator);
        Assert.assertTrue(signUpButton.isDisplayed(), "'Sign Up' button should be displayed.");
        System.out.println("Verified 'Sign Up' button presence");
    }

    @Test(priority = 5, description = "Verify the presence of the 'Get App' button or link.")
    public void verifyGetAppButtonPresence() {
        By getAppButtonLocator = By.xpath("//*[contains(text(), 'Get App') or contains(@aria-label, 'Get the Reddit app')]");
        WebElement getAppButton = findElement(getAppButtonLocator);
        Assert.assertTrue(getAppButton.isDisplayed(), "'Get App' button or link should be displayed.");
        System.out.println("Verified 'Get App' button presence");
    }
}