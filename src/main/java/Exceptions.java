import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.time.Duration;
import java.util.List;

public class Exceptions {
    private WebDriver driver;

    @BeforeClass
    public void setUp() {
        // Set path to your chromedriver.exe
        //System.setProperty("webdriver.chrome.driver", "path/to/chromedriver");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }


//
//    @Test(expectedExceptions = org.openqa.selenium.NoSuchElementException.class)
//    public void testNoSuchElementException() {
//        driver.get("https://practicetestautomation.com/practice-test-exceptions/");
//        driver.findElement(By.id("add_btn")).click();
//        // This will throw NoSuchElementException which is expected
//        WebElement row2 = driver.findElement(By.xpath("//*[@id=\"row2\"]/label"));
//    }
//

    @Test
    public void testNoSuchElementException() {
        driver.get("https://practicetestautomation.com/practice-test-exceptions/");
        driver.findElement(By.id("add_btn")).click();
        boolean caughtException = false;

        try {
            // Attempt to find an element that may not exist
            WebElement row2 = driver.findElement(By.xpath("//*[@id=\"row2\"]/label"));
        } catch (org.openqa.selenium.NoSuchElementException e) {
            caughtException = true;
        }

        // Assert that the NoSuchElementException was caught
        Assert.assertTrue(caughtException, "NoSuchElementException was expected but not encountered");
    }



    @Test
    public void testElementNotInteractableException() {
        driver.get("https://practicetestautomation.com/practice-test-exceptions/");
        driver.findElement(By.id("add_btn")).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        // Correctly wait for the element
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='row2']/input")));

        WebElement row2Input = driver.findElement(By.xpath("//*[@id='row2']/input"));
        row2Input.sendKeys("Test Text");
         boolean caughtException = false;

        try {
            driver.findElement(By.name("Save")).click();


        } catch (org.openqa.selenium.ElementNotInteractableException e) {
            caughtException = true;
            }



    }



    @Test
    public void testInvalidElementStateException() {
        driver.get("https://practicetestautomation.com/practice-test-exceptions/");
        WebElement inputField = driver.findElement(By.xpath("//*[@id='row1']/input"));

        // Directly check if the element is enabled or not.
        boolean isFieldEnabled = inputField.isEnabled();

        // Assert that the field is indeed disabled.
        Assert.assertFalse(isFieldEnabled, "Expected the input field to be disabled.");

        // Optionally, if you're specifically interested in seeing the attempt to interact with the element:
        if (!isFieldEnabled) {
            System.out.println("Confirmed: The input field is disabled as expected. No interaction attempt will be made.");
        } else {
            // This block should not be reached for a disabled input, but is here for completeness.
            try {
                inputField.clear();
                inputField.sendKeys("Hot Dog");
                Assert.fail("Interaction was unexpectedly successful on a field expected to be disabled.");
            } catch (ElementNotInteractableException e) {
                System.out.println("Caught expected ElementNotInteractableException due to disabled input field.");
            }
        }
    }


    @Test
    public void testStaleElementReferenceExceptionIsThrown() {
        driver.get("https://practicetestautomation.com/practice-test-exceptions/");
        WebElement instructions = driver.findElement(By.id("instructions"));

        // Perform the action that is expected to make the element no longer attached to the DOM
        driver.findElement(By.id("add_btn")).click();

        // Now, trying to interact with the element should throw a StaleElementReferenceException
        try {
            instructions.isDisplayed(); // Attempt to interact with the element
            Assert.fail("Expected StaleElementReferenceException was not thrown."); // Fail the test if no exception is thrown
        } catch (StaleElementReferenceException e) {
            // Expected exception was caught, indicating the element is indeed stale
            // Optionally, you can include an assertion here to confirm the test passes upon catching the exception
            Assert.assertTrue(true, "StaleElementReferenceException was correctly thrown when interacting with a stale element.");
        }
    }

    @Test
    public void testTimeoutExceptionForSecondInputField() {
        driver.get("https://practicetestautomation.com/practice-test-exceptions/");

        // Click the "Add" button to start the process of displaying the second input field.
        driver.findElement(By.id("add_btn")).click();

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3)); // Wait with a 3-second timeout
            // Attempt to wait for the second input field to become visible
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("second_input_field_id"))); // Replace with actual ID
            Assert.fail("Expected TimeoutException was not thrown."); // If no exception is thrown, force the test to fail
        } catch (TimeoutException e) {
            // TimeoutException is expected; you can optionally assert something about the exception if needed
            Assert.assertTrue(true, "TimeoutException was correctly thrown when waiting for the second input field.");
        }
    }


//    @AfterClass
//    public void tearDown() {
//        if (driver != null) {
//            driver.quit();
//        }
//    }
}
