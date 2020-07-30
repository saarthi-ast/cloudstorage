package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.selenium.model.HomePage;
import com.udacity.jwdnd.course1.cloudstorage.selenium.model.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.selenium.model.SignupPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.Random;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class NotesManagementTests {
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
        homePage = new HomePage(driver);
    }

    private void signupAndLogin() {
        driver.get("http://localhost:8080/signup");
        WebDriverWait wait = new WebDriverWait(driver, 3);
        wait.until(webDriver -> webDriver.findElement(By.id("submit-btn")));
        signupPage.setFirstname(testFirstName);
        signupPage.setLastname(testLastName);
        signupPage.setUsername(testUsr);
        signupPage.setPassword(testPwd);
        signupPage.signup();
        driver.get("http://localhost:8080/login");
        wait.until(webDriver -> webDriver.findElement(By.id("login-submit-btn")));
        loginPage.setInputUsername(testUsr);
        loginPage.setInputPassword(testPwd);
        loginPage.login();
        WebElement filesTab = wait.until(webDriver -> webDriver.findElement(By.id("nav-files-tab")));
    }

    @Test
    @Order(1)
    public void testNavigateToNotes(){
        signupAndLogin();
        homePage.navigateToNotes();
        System.out.println("");
    }
}
