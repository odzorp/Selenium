import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class LoginTests {

    private WebDriver driver;


    @BeforeClass
    public void setUp() {
        // Set path to your chromedriver.exe
      //  System.setProperty("webdriver.chrome.driver", "path/to/chromedriver");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }
    @Test
    public void executeTests() {
        try {
            positiveLoginTest();
            negativeUsernameTest();
            negativePasswordTest();
            negativeCredentialsTest();
            System.out.println("All tests passed successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("One or more tests failed!");
        } finally {
            driver.quit();
        }
    }
   // @Test
    public void positiveLoginTest() {
        driver.get("http://practicetestautomation.com/practice-test-login/");

        driver.findElement(By.id("username")).sendKeys("student");
        driver.findElement(By.id("password")).sendKeys("Password123");
        driver.findElement(By.id("submit")).click();

        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("https://practicetestautomation.com/logged-in-successfully/"));

        WebElement successMessage = driver.findElement(By.xpath("//*[@id=\"loop-container\"]/div/article/div[2]/p[1]/strong"));
        Assert.assertTrue(successMessage.getText().contains("Congratulations") || successMessage.getText().contains("successfully logged in"));

        Assert.assertTrue(driver.findElement(By.linkText("Log out")).isDisplayed());
    }

   // @Test
    public void negativeUsernameTest() throws InterruptedException {
        driver.get("http://practicetestautomation.com/practice-test-login/");

        driver.findElement(By.id("username")).sendKeys("incorrectUser");
        driver.findElement(By.id("password")).sendKeys("Password123");
        driver.findElement(By.id("submit")).click();

        WebElement errorMessage = driver.findElement(By.id("error"));
        Thread.sleep(5000);
        Assert.assertTrue(errorMessage.isDisplayed());

        Assert.assertEquals(errorMessage.getText(), "Your username is invalid!");
    }

  //  @Test
    public void negativePasswordTest() throws InterruptedException {
        driver.get("http://practicetestautomation.com/practice-test-login/");

        driver.findElement(By.id("username")).sendKeys("student");
        driver.findElement(By.id("password")).sendKeys("incorrectPassword");
        driver.findElement(By.id("submit")).click();

        WebElement errorMessage = driver.findElement(By.id("error"));
        Thread.sleep(5000);

        Assert.assertTrue(errorMessage.isDisplayed());
        Assert.assertEquals(errorMessage.getText(), "Your password is invalid!");
    }

  //  @Test
    public void negativeCredentialsTest() throws InterruptedException {
        driver.get("http://practicetestautomation.com/practice-test-login/");

        driver.findElement(By.id("username")).sendKeys("incorrectuser");
        driver.findElement(By.id("password")).sendKeys("incorrectPassword");
        driver.findElement(By.id("submit")).click();

        WebElement errorMessage = driver.findElement(By.id("error"));
        Thread.sleep(5000);

        Assert.assertTrue(errorMessage.isDisplayed());
        Assert.assertEquals(errorMessage.getText(), "Your username is invalid!");
    }

   @AfterClass
   public void tearDown() {
           driver.quit();
      }
}


