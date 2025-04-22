package edu.reddituigroup.reddit.tests;

import org.openqa.selenium.*;
import org.testng.Assert;
import org.testng.annotations.*;

public class RedditHomepageTest extends BaseTest {

    @Test(priority = 1, description = "Verify the page title of the Reddit homepage.")
    public void verifyHomepageTitle() {
        String expectedTitlePart = "Reddit"; // Reddit often adds dynamic content like "Dive into anything"
        String actualTitle = driver.getTitle();
        Assert.assertTrue(actualTitle.contains(expectedTitlePart),
                "Page title should contain '" + expectedTitlePart + "'. Actual: " + actualTitle);
        System.out.println("Verified homepage title contains '" + expectedTitlePart + "'");
    }

    @Test(priority = 2, description = "Verify the presence of the Reddit logo.")
    public void verifyLogoPresence() {
        try {
            // Try multiple possible logo locators
            By[] logoLocators = {
                By.cssSelector("a[aria-label='Home']"),
                By.cssSelector("#reddit-logo"),
                By.cssSelector(".reddit-logo"),
                By.xpath("//a[contains(@href, '/')]//img[contains(@alt, 'Reddit')]")
            };
            
            WebElement logo = null;
            for (By locator : logoLocators) {
                try {
                    logo = findElement(locator);
                    if (logo != null && logo.isDisplayed()) {
                        break;
                    }
                } catch (Exception e) {
                    continue;
                }
            }
            
            Assert.assertNotNull(logo, "Reddit logo should be found using any of the locators");
            Assert.assertTrue(logo.isDisplayed(), "Reddit logo should be displayed");
            System.out.println("Verified Reddit logo presence");
        } catch (Exception e) {
            Assert.fail("Failed to verify logo presence: " + e.getMessage());
        }
    }

    @Test(priority = 3, description = "Verify the presence of the 'Log In' button.")
    public void verifyLoginButtonPresence() {
        try {
            By[] loginLocators = {
                By.cssSelector("a[href*='login']"),
                By.cssSelector("button[data-testid='login-button']"),
                By.xpath("//a[contains(text(), 'Log In')]"),
                By.xpath("//button[contains(text(), 'Log In')]")
            };
            
            WebElement loginButton = null;
            for (By locator : loginLocators) {
                try {
                    loginButton = findElement(locator);
                    if (loginButton != null && loginButton.isDisplayed()) {
                        break;
                    }
                } catch (Exception e) {
                    continue;
                }
            }
            
            Assert.assertNotNull(loginButton, "Login button should be found using any of the locators");
            Assert.assertTrue(loginButton.isDisplayed(), "Login button should be displayed");
            Assert.assertTrue(loginButton.isEnabled(), "Login button should be enabled");
            System.out.println("Verified 'Log In' button presence and enabled state");
        } catch (Exception e) {
            Assert.fail("Failed to verify login button presence: " + e.getMessage());
        }
    }

    @Test(priority = 4, description = "Verify the presence of the 'Sign Up' button.")
    public void verifySignUpButtonPresence() {
        try {
            By[] signUpLocators = {
                By.cssSelector("a[href*='register']"),
                By.cssSelector("button[data-testid='signup-button']"),
                By.xpath("//a[contains(text(), 'Sign Up')]"),
                By.xpath("//button[contains(text(), 'Sign Up')]")
            };
            
            WebElement signUpButton = null;
            for (By locator : signUpLocators) {
                try {
                    signUpButton = findElement(locator);
                    if (signUpButton != null && signUpButton.isDisplayed()) {
                        break;
                    }
                } catch (Exception e) {
                    continue;
                }
            }
            
            Assert.assertNotNull(signUpButton, "Sign Up button should be found using any of the locators");
            Assert.assertTrue(signUpButton.isDisplayed(), "Sign Up button should be displayed");
            Assert.assertTrue(signUpButton.isEnabled(), "Sign Up button should be enabled");
            System.out.println("Verified 'Sign Up' button presence and enabled state");
        } catch (Exception e) {
            Assert.fail("Failed to verify sign up button presence: " + e.getMessage());
        }
    }

    @Test(priority = 5, description = "Verify the presence of the 'Get App' button or link.")
    public void verifyGetAppButtonPresence() {
        try {
            By[] getAppLocators = {
                By.xpath("//a[contains(text(), 'Get App')]"),
                By.xpath("//button[contains(text(), 'Get App')]"),
                By.xpath("//a[contains(@aria-label, 'Get the Reddit app')]"),
                By.cssSelector("a[href*='app']")
            };
            
            WebElement getAppButton = null;
            for (By locator : getAppLocators) {
                try {
                    getAppButton = findElement(locator);
                    if (getAppButton != null && getAppButton.isDisplayed()) {
                        break;
                    }
                } catch (Exception e) {
                    continue;
                }
            }
            
            Assert.assertNotNull(getAppButton, "Get App button should be found using any of the locators");
            Assert.assertTrue(getAppButton.isDisplayed(), "Get App button should be displayed");
            System.out.println("Verified 'Get App' button presence");
        } catch (Exception e) {
            Assert.fail("Failed to verify Get App button presence: " + e.getMessage());
        }
    }
}