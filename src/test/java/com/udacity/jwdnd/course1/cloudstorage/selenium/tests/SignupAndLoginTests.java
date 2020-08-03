package com.udacity.jwdnd.course1.cloudstorage.selenium.tests;

import com.udacity.jwdnd.course1.cloudstorage.selenium.model.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.selenium.model.SignupPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.Random;

import static com.udacity.jwdnd.course1.cloudstorage.constants.ApplicationConstants.HTTP_URL_NOT_VALID;
import static com.udacity.jwdnd.course1.cloudstorage.constants.ApplicationConstants.SIGNUP_DUPLICATE_USERNAME;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SignupAndLoginTests {
    @LocalServerPort
    private Integer port;
    private static JavascriptExecutor js;
    private static WebDriver driver;
    private static String testUsr;
    private static String testPwd;
    private static String testFirstName;
    private static String testLastName;
    private SignupPage signupPage;
    private LoginPage loginPage;
    private WebDriverWait wait;

    @BeforeAll
    public static void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        js = (JavascriptExecutor) driver;
        testFirstName = "Test";
        testLastName = "User";
        Random random = new Random();
        testUsr = "test-" + random.nextInt(100000);
        // using a random generated all numeric dummy password
        testPwd = String.valueOf(random.nextInt(1000000));
    }

    @AfterAll
    public static void tearDown() {
        driver.quit();
    }

    @BeforeEach
    public void beforeEach() {
        wait = new WebDriverWait(driver, 100);
        signupPage = new SignupPage(driver);
        loginPage = new LoginPage(driver);

    }

    private void populateAndSubmitSignup() {
        signupPage.setFirstname(testFirstName);
        signupPage.setLastname(testLastName);
        signupPage.setUsername(testUsr);
        signupPage.setPassword(testPwd);
        signupPage.signup();
    }

    @Test
    @Order(1)
    public void testHomePageAccessWithoutLogin() {
        driver.get("http://localhost:8080/home");

        WebElement loginUsername = wait.until(ExpectedConditions.elementToBeClickable(By.id("inputUsername")));
        assertNotNull(loginUsername);
    }

    @Test
    @Order(2)
    public void testSignup() {
        driver.get("http://localhost:8080/signup");
        wait.until(ExpectedConditions.elementToBeClickable(By.id("submit-btn")));
        populateAndSubmitSignup();
        WebElement signupSuccess = wait.until(ExpectedConditions.elementToBeClickable(By.id("success-msg")));
        assertNotNull(signupSuccess);
        assertTrue(signupPage.getSuccessMsg().startsWith("You successfully signed up!"));
    }

    @Test
    @Order(4)
    public void testDuplicateUserNameErrorOnSignup() {
        driver.get("http://localhost:8080/signup");
        wait.until(ExpectedConditions.elementToBeClickable(By.id("submit-btn")));
        populateAndSubmitSignup();
        WebElement signupError = wait.until(ExpectedConditions.elementToBeClickable(By.id("error-msg")));
        assertNotNull(signupError);
        assertEquals(SIGNUP_DUPLICATE_USERNAME, signupPage.getErrorMessage());
    }

    @Test
    @Order(3)
    public void testLoginAndLogout() {
        try{
            driver.get("http://localhost:8080/login");
            wait.until(ExpectedConditions.elementToBeClickable(By.id("login-submit-btn")));
            loginPage.setInputUsername(testUsr);
            loginPage.setInputPassword(testPwd);
            loginPage.login();
            WebElement filesTab = wait.until(ExpectedConditions.elementToBeClickable(By.id("nav-files-tab")));
            assertNotNull(filesTab);
            WebElement logoutBtn = wait.until(ExpectedConditions.elementToBeClickable(By.id("logout-btn")));
            js.executeScript("arguments[0].click();", logoutBtn);
            wait.until(ExpectedConditions.elementToBeClickable(By.id("logout-msg")));
            assertEquals("You have been logged out", loginPage.getLogoutMsg());
            driver.get("http://localhost:8080/home");
            WebElement loginUsername = wait.until(ExpectedConditions.elementToBeClickable(By.id("inputUsername")));
            assertNotNull(loginUsername);
        }catch (Exception e){
            Assertions.fail("Error occurred while testing testLoginAndLogout");
        }
    }

    @Test
    @Order(4)
    public void testErrorForJunkUrls() {
        try{
            driver.get("http://localhost:8080/login");
            wait.until(ExpectedConditions.elementToBeClickable(By.id("login-submit-btn")));
            loginPage.setInputUsername(testUsr);
            loginPage.setInputPassword(testPwd);
            loginPage.login();
            WebElement filesTab = wait.until(ExpectedConditions.elementToBeClickable(By.id("nav-files-tab")));
            assertNotNull(filesTab);
            driver.get("http://localhost:8080/randomTest");
            WebElement errorMessage = wait.until(ExpectedConditions.elementToBeClickable(By.id("error-msg")));
            assertEquals(HTTP_URL_NOT_VALID,errorMessage.getText());
        }catch (Exception e){
            Assertions.fail("Error occurred while testing testLoginAndLogout");
        }
    }
}
