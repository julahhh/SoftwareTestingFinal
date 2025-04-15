import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;
import org.openqa.selenium.interactions.Actions;

import java.time.Duration;
import java.util.Scanner;

public class PostManagement {
    static WebDriver driver;
    WebDriverWait wait;
    Scanner scanner;

    @BeforeSuite
    public void beforeSuite() {
        System.out.println("Setting up testing suite");
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\julia\\OneDrive - Florida Gulf Coast University\\..Software Testing Folder\\.Drivers\\chromedriver.exe");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    //Runs before every test
    @BeforeTest
    public void BeforeTest() {
        System.out.println("==--Testing Reddit Post Management--==");
    }

    //Runs before the first method in the class
    @BeforeClass
    public void BeforeClass() throws InterruptedException {
        System.out.println("==--Before Class: Logging In--==");
        driver.get("https://www.reddit.com/login"); //navigate to testing site
        driver.manage().window().maximize();

        //login using testing Profile
        driver.findElement(By.name("username")).sendKeys("SW-Tester-2025");
        driver.findElement(By.name("password")).sendKeys("SWtesting2025");
        driver.findElement(By.cssSelector("#login > auth-flow-modal > div.w-100 > faceplate-tracker > button")).click();

        Thread.sleep(5000);
    }

    //Runs before each test method
    @BeforeMethod
    public void BeforeMethod() {
        System.out.println("==--Before Method: Beginning Test--==");
    }

    // Test 1: Create a text Post
    @Test(priority = 1)
    public void Test1() throws InterruptedException {
        System.out.println("Test 1 is happening: ");
        //navigate to profile
        driver.findElement(new By.ByCssSelector("#expand-user-drawer-button")).click();
        Thread.sleep(500);
        driver.findElement(new By.ByCssSelector("html > body > shreddit-app > reddit-header-large > reddit-header-action-items > header > nav > div:nth-of-type(3) > div:nth-of-type(2) > shreddit-async-loader > faceplate-dropdown-menu > div > ul:nth-of-type(1) > faceplate-tracker:nth-of-type(1) > li > a")).click();

        //click the create a post button
        driver.findElement(new By.ByCssSelector("a[data-testid='create-post']")).click();
        Thread.sleep(500);

        //enter information to create a text post
        driver.findElement(By.cssSelector("#post-composer__title > faceplate-textarea-input")).sendKeys("Test Text Title");
        System.out.println("Title has been entered.");
        Thread.sleep(500);
        driver.findElement(By.cssSelector("#post-composer_bodytext")).sendKeys("This is a test post to test the function of creating a text post.");
        System.out.println("Body has been entered.");
        Thread.sleep(500);
        driver.findElement(By.id("submit-post-button")).click();
        Thread.sleep(1500);
    }

    // Test 2: Create an Image/Video Post
    @Test(priority = 2)
    public static void Test2() throws InterruptedException {
        System.out.println("test two is happening");
        //click the create a post button
        driver.findElement(new By.ByCssSelector("a[data-testid='create-post']")).click();
        Thread.sleep(500);

        //click on images and video tab
        driver.findElement(By.cssSelector("/div/faceplate-tracker[2]/button/span/span/span/span/span/span")).click();
        Thread.sleep(500);

        //enter information to create a text post
        driver.findElement(By.cssSelector("#post-composer__title > faceplate-textarea-input")).sendKeys("Test Text Title");
        System.out.println("Title has been entered.");
        Thread.sleep(500);
        driver.findElement(By.cssSelector("#post-composer_bodytext")).sendKeys("This is a test post to test the function of creating a text post.");
        System.out.println("Body has been entered.");
        Thread.sleep(500);
        driver.findElement(By.id("submit-post-button")).click();
        Thread.sleep(1500);
    }

    // Test 3: Create a Link Post
    @Test(priority = 3)
    public static void Test3() throws InterruptedException {
        System.out.println("test three is happening");
        //click the create a post button
        driver.findElement(new By.ByCssSelector("#subgrid-container > div.masthead.w-full.relative > section > div > div.flex.w-100.xs\\:w-auto.mt-xs.xs\\:mt-0.items-center > span > create-post-entry-point-wrapper > faceplate-tracker > a")).click();
        Thread.sleep(500);

        //enter information to create a text post
        driver.findElement(By.cssSelector("#post-composer__title > faceplate-textarea-input")).sendKeys("Test Text Title");
        System.out.println("Title has been entered.");
        Thread.sleep(500);
        driver.findElement(By.cssSelector("#post-composer_bodytext")).sendKeys("This is a test post to test the function of creating a text post.");
        System.out.println("Body has been entered.");
        Thread.sleep(500);
        driver.findElement(By.id("submit-post-button")).click();
        Thread.sleep(1500);
    }

    // Test 4: Create a Poll Post
    @Test(priority = 4)
    public static void Test4() throws InterruptedException {
        System.out.println("test four is happening");
        //click the create a post button
        driver.findElement(new By.ByCssSelector("#subgrid-container > div.masthead.w-full.relative > section > div > div.flex.w-100.xs\\:w-auto.mt-xs.xs\\:mt-0.items-center > span > create-post-entry-point-wrapper > faceplate-tracker > a")).click();
        Thread.sleep(500);

        //enter information to create a text post
        driver.findElement(By.cssSelector("#post-composer__title > faceplate-textarea-input")).sendKeys("Test Text Title");
        System.out.println("Title has been entered.");
        Thread.sleep(500);
        driver.findElement(By.cssSelector("#post-composer_bodytext")).sendKeys("This is a test post to test the function of creating a text post.");
        System.out.println("Body has been entered.");
        Thread.sleep(500);
        driver.findElement(By.id("submit-post-button")).click();
        Thread.sleep(1500);
    }

    // Test 5: Comment on a post
    @Test(priority = 5)
    public static void Test5() throws InterruptedException {
        System.out.println("test five is happening");
    }

    // Test 6: Edit a Post
    @Test(priority = 6)
    public static void Test6() throws InterruptedException {
        System.out.println("Test 2 is happening: ");
    }

    // Test 7: Delete a Post
    @Test(priority = 7)
    public static void Test7() throws InterruptedException {
        System.out.println("test three is happening");
    }

    // Test 8: Upvote and downvote a post
    @Test(priority = 8)
    public static void Test8() throws InterruptedException {
        System.out.println("test four is happening");
    }

    // Test 9: Comment on a post
    @Test(priority = 9)
    public static void Test9() throws InterruptedException {
        System.out.println("test five is happening");
    }
}

