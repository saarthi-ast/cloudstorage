package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.selenium.model.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.selenium.model.SignupPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SignupAndLoginTests {
    @LocalServerPort
    private Integer port;

    private static WebDriver driver;
    private static String testUsr;
    private static String testPwd;
    private static String testFirstName;
    private static String testLastName;
    private SignupPage signupPage;
    private LoginPage loginPage;
    public static final String DUP_USR_ERROR = "This username is already in use. Please try with a different username.";

    @BeforeAll
    public static void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
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
    public void testHomePageAccessWithoutLogin(){
        driver.get("http://localhost:8080/home");
        WebDriverWait wait = new WebDriverWait(driver, 3);
        WebElement loginUsername = wait.until(webDriver -> webDriver.findElement(By.id("inputUsername")));
        assertNotNull(loginUsername);
        assertThrows(TimeoutException.class, () ->  wait.until(webDriver -> webDriver.findElement(By.id("nav-files-tab"))));
    }

    @Test
    @Order(2)
    public void testSignup() {
        driver.get("http://localhost:8080/signup");
        WebDriverWait wait = new WebDriverWait(driver, 3);
        wait.until(webDriver -> webDriver.findElement(By.id("submit-btn")));
        populateAndSubmitSignup();
        WebElement signupSuccess = wait.until(webDriver -> webDriver.findElement(By.id("success-msg")));
        assertNotNull(signupSuccess);
        assertTrue(signupPage.getSuccessMsg().startsWith("You successfully signed up!"));
    }

    @Test
    @Order(4)
    public void testDuplicateUserNameErrorOnSignup(){
        driver.get("http://localhost:8080/signup");
        WebDriverWait wait = new WebDriverWait(driver, 3);
        wait.until(webDriver -> webDriver.findElement(By.id("submit-btn")));
        populateAndSubmitSignup();
        WebElement signupError = wait.until(webDriver -> webDriver.findElement(By.id("error-msg")));
        assertNotNull(signupError);
        assertEquals(DUP_USR_ERROR, signupPage.getErrorMessage());
    }

    @Test
    @Order(3)
    public void testLoginAndLogout(){
        driver.get("http://localhost:8080/login");
        WebDriverWait wait = new WebDriverWait(driver, 3);
        wait.until(webDriver -> webDriver.findElement(By.id("login-submit-btn")));
        loginPage.setInputUsername(testUsr);
        loginPage.setInputPassword(testPwd);
        loginPage.login();
        WebElement filesTab = wait.until(webDriver -> webDriver.findElement(By.id("nav-files-tab")));
        assertNotNull(filesTab);
        WebElement logoutBtn = wait.until(webDriver -> webDriver.findElement(By.id("logout-btn")));
        logoutBtn.click();
        assertEquals("You have been logged out",loginPage.getLogoutMsg());
        driver.get("http://localhost:8080/home");
        assertThrows(TimeoutException.class, () ->  wait.until(webDriver -> webDriver.findElement(By.id("nav-files-tab"))));
    }

}
