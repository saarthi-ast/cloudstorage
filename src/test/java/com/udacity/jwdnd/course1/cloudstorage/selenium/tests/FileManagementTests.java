package com.udacity.jwdnd.course1.cloudstorage.selenium.tests;


import com.udacity.jwdnd.course1.cloudstorage.selenium.model.HomePage;
import com.udacity.jwdnd.course1.cloudstorage.selenium.model.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.selenium.model.SignupPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import static com.udacity.jwdnd.course1.cloudstorage.constants.ApplicationConstants.*;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FileManagementTests {
    @LocalServerPort
    private Integer port;

    private static WebDriver driver;
    private static String testUsr;
    private static String testPwd;
    private static String testFirstName;
    private static String testLastName;
    private SignupPage signupPage;
    private LoginPage loginPage;
    private HomePage homePage;
    private WebDriverWait wait;
    private static Random random = new Random();
    private static File file;

    @BeforeAll
    public static void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        testFirstName = "Test";
        testLastName = "User";
        //create a dummy file
        file = new File("/tmp/test_" + random.nextInt(1000) + ".txt");
        try {
            if (file.createNewFile()) {
                FileWriter writer = new FileWriter(file);
                writer.write("Test data for " + file.getPath());
                writer.close();
            }
        } catch (IOException ex) {
            Assertions.fail("Error occurred while creating file");
        }

        testUsr = "test-" + random.nextInt(100000);
        // using a random generated all numeric dummy password
        testPwd = String.valueOf(random.nextInt(1000000));
    }

    @AfterAll
    public static void tearDown() {
        driver.quit();

        //clean up dummy files
        if (file.delete()) {
            assertTrue(true); // file deleted. So explicitly passed
        } else {
            Assertions.fail(); // could not clean up disk space. Fail the test
        }

    }

    @BeforeEach
    public void beforeEach() {
        wait = new WebDriverWait(driver, 100);
        signupPage = new SignupPage(driver);
        loginPage = new LoginPage(driver);
        homePage = new HomePage(driver);
    }

    private void signupAndLogin() {
        driver.get("http://localhost:8080/signup");
        wait.until(ExpectedConditions.elementToBeClickable(By.id("submit-btn")));
        signupPage.setFirstname(testFirstName);
        signupPage.setLastname(testLastName);
        signupPage.setUsername(testUsr);
        signupPage.setPassword(testPwd);
        signupPage.signup();
        driver.get("http://localhost:8080/login");
        wait.until(ExpectedConditions.elementToBeClickable(By.id("login-submit-btn")));
        loginPage.setInputUsername(testUsr);
        loginPage.setInputPassword(testPwd);
        loginPage.login();
        WebElement filesTab = wait.until(ExpectedConditions.elementToBeClickable(By.id("nav-files-tab")));
    }

    @Test
    @Order(1)
    public void testFileUpload() {
        signupAndLogin();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("nav-files-tab")));
        try {
            homePage.addFile(file.getPath());
            Thread.sleep(1000);
            homePage.uploadFile();
            WebElement filename = wait.until(ExpectedConditions.elementToBeClickable(By.id("filename_" + file.getName())));
            assertNotNull(filename);
            assertEquals(file.getName(), filename.getText());
        } catch (TimeoutException timeoutException) {
            Assertions.fail("Timeout occurred waiting for web element.");
        } catch (Exception e) {
            Assertions.fail("Exception occurred while running file upload and delete test.");
        }
    }

    @Test
    @Order(2)
    public void testDuplicateFileUploadError() {
        signupAndLogin();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("nav-files-tab")));
        try {
            homePage.addFile(file.getPath());
            Thread.sleep(1000);
            wait.until(ExpectedConditions.elementToBeClickable(By.id("file-submit-btn")));
            homePage.uploadFile();
            WebElement errorMessage = wait.until(ExpectedConditions.elementToBeClickable(By.id("error-msg")));
            assertNotNull(errorMessage);
            assertEquals(FILE_UPLOAD_DUPLICATE_ERROR, homePage.getErrorMessage());
        } catch (TimeoutException timeoutException) {
            Assertions.fail("Timeout occurred waiting for web element.");
        } catch (Exception e) {
            Assertions.fail("Exception occurred while running duplicate file upload test.");
        }
    }

    @Test
    @Order(3)
    public void testFileDelete() {
        signupAndLogin();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("nav-files-tab")));
        try {
            WebElement filename = wait.until(ExpectedConditions.elementToBeClickable(By.id("filename_" + file.getName())));
            WebElement deleteFile = wait.until(ExpectedConditions.elementToBeClickable(By.id("file_del_" + file.getName())));
           Thread.sleep(1000);
            deleteFile.click();
            WebElement successMessage = wait.until(ExpectedConditions.elementToBeClickable(By.id("success-msg")));
            assertNotNull(successMessage);
            assertEquals(FILE_DELETE_SUCCESS, homePage.getSuccessMessage());
        } catch (TimeoutException timeoutException) {
            Assertions.fail("Timeout occurred waiting for web element.");
        } catch (Exception e) {
            Assertions.fail("Exception occurred while running file upload and delete test.");
        }
    }

    @Test
    @Order(4)
    public void testLargeFileUploadError() {
        signupAndLogin();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("nav-files-tab")));
        try {
            try {
                FileWriter writer = new FileWriter(file);
                StringBuffer buffer = new StringBuffer("Test data for " + file.getPath());
                while (buffer.length() < MAX_FILE_SIZE) {
                    buffer = buffer.append(buffer);
                }
                writer.write(buffer.toString());
                writer.close();
            } catch (IOException ex) {
                Assertions.fail("Error occurred while creating file");
            }
            homePage.addFile(file.getPath());
            WebElement submitBtn = wait.until(ExpectedConditions.elementToBeClickable(By.id("file-submit-btn")));
            assertNotNull(submitBtn);
            Thread.sleep(1000);
            homePage.uploadFile();
            WebElement errorMessage = wait.until(ExpectedConditions.elementToBeClickable(By.id("error-msg")));
            assertNotNull(errorMessage);
            assertEquals(FILE_UPLOAD_SIZE_ERROR, homePage.getErrorMessage());
        } catch (TimeoutException timeoutException) {
            Assertions.fail("Timeout occurred waiting for web element.");
        } catch (Exception e) {
            Assertions.fail("Exception occurred while running Large file upload test.");
        }
    }
}
