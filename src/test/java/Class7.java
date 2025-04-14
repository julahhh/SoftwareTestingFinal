import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

public class Class7 {
    // Test 1
    @Test(priority = 1)
    public static void Test1(String[] args) throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\julia\\OneDrive - Florida Gulf Coast University\\..Software Testing Folder\\.Drivers\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.reddit.com/login/");
        driver.manage().window().maximize();

        //login using testing Profile
        driver.findElement(By.name("username")).sendKeys("MaintenanceOk2822");
        driver.findElement(By.name("password")).sendKeys("SWtesting2025");
        driver.findElement(By.xpath("/html/body/shreddit-app/auth-flow-manager/span[1]/faceplate-partial/auth-flow-login/faceplate-tabpanel/faceplate-form[1]/auth-flow-modal/div[2]/faceplate-tracker/button")).click();
    }

    // Test 2
    @Test(priority = 2)
    public static void Test2(String[] args) throws InterruptedException {

    }

    // Test 3
    @Test(priority = 3)
    public static void Test3(String[] args) throws InterruptedException {

    }

    // Test 4
    @Test(priority = 4)
    public static void Test4(String[] args) throws InterruptedException {

    }

    // Test 5
    @Test(priority = 5)
    public static void Test5(String[] args) throws InterruptedException {

    }
}

