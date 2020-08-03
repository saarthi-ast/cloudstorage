package com.udacity.jwdnd.course1.cloudstorage.selenium.tests;

import com.udacity.jwdnd.course1.cloudstorage.selenium.model.HomePage;
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

import static com.udacity.jwdnd.course1.cloudstorage.constants.ApplicationConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CredentialManagementTests {
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
    private HomePage homePage;
    private WebDriverWait wait;
    private static String credUrl;
    private static String credUsername;
    private static String credPassword;

    @BeforeAll
    public static void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
       js =  (JavascriptExecutor)driver;
        testFirstName = "Test";
        testLastName = "User";
        Random random = new Random();
        testUsr = "test-" + random.nextInt(100000);
        credUrl = "test-url-"+testUsr;
        credUsername = testUsr;
        credPassword = "ChangeMe";
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
        wait.until(webDriver -> webDriver.findElement(By.id("login-submit-btn")));
        loginPage.setInputUsername(testUsr);
        loginPage.setInputPassword(testPwd);
        loginPage.login();
        WebElement filesTab = wait.until(ExpectedConditions.elementToBeClickable(By.id("nav-files-tab")));
    }

    @Test
    @Order(1)
    public void testAddCredential(){
        try{
            signupAndLogin();
            WebElement navBtn =  wait.until(ExpectedConditions.elementToBeClickable(By.id("nav-credentials-tab")));
            js.executeScript("arguments[0].click();", navBtn);
            WebElement addCredBtn = wait.until(ExpectedConditions.elementToBeClickable(By.id("add-cred-btn")));
            js.executeScript("arguments[0].click();", addCredBtn);
            wait.until(ExpectedConditions.elementToBeClickable(By.id("credential-url")));
            homePage.setCredentialUrl(credUrl);
            homePage.setCredentialUsername(credUsername);
            homePage.setCredentialPassword(credPassword);
            WebElement credSaveBtn = wait.until(ExpectedConditions.elementToBeClickable(By.id("cred-save-btn")));
            js.executeScript("arguments[0].click();", credSaveBtn);
            wait.until(ExpectedConditions.elementToBeClickable(By.id("success-msg")));
            assertEquals(CREDENTIALS_SAVE_SUCCESS, homePage.getSuccessMessage());
            WebElement credText = wait.until(ExpectedConditions.elementToBeClickable(By.id("cred_url_"+credUrl)));
            assertEquals(credUrl,credText.getText());
        }catch (Exception e){
            Assertions.fail("Exception occurred while running testAddCredential test: "+e.getMessage());
        }
    }

    @Test
    @Order(2)
    public void testDuplicateCredError(){
        try{
            signupAndLogin();
            WebElement navBtn = wait.until(ExpectedConditions.elementToBeClickable(By.id("nav-credentials-tab")));
            js.executeScript("arguments[0].click();", navBtn);
            WebElement addCredBtn = wait.until(ExpectedConditions.elementToBeClickable(By.id("add-cred-btn")));
            js.executeScript("arguments[0].click();", addCredBtn);
            wait.until(ExpectedConditions.elementToBeClickable(By.id("credential-url")));
            homePage.setCredentialUrl(credUrl);
            homePage.setCredentialUsername(credUsername);
            homePage.setCredentialPassword(credPassword);
            WebElement saveCredBtn = wait.until(ExpectedConditions.elementToBeClickable(By.id("cred-save-btn")));
            js.executeScript("arguments[0].click();", saveCredBtn);
            wait.until(ExpectedConditions.elementToBeClickable(By.id("error-msg")));
            assertEquals(DUPLICATE_CREDENTIAL_ERROR, homePage.getErrorMessage());
        }catch (Exception e){
            Assertions.fail("Exception occurred while running testDuplicateCredError test: "+e.getMessage());
        }
    }

    @Test
    @Order(3)
    public void testEditCredential(){
        try{
            signupAndLogin();
            Thread.sleep(1000);
            WebElement navCredBtn = wait.until(ExpectedConditions.elementToBeClickable(By.id("nav-credentials-tab")));
            js.executeScript("arguments[0].click();", navCredBtn);
            WebElement editCredBtn = wait.until(ExpectedConditions.elementToBeClickable(By.id("cred_edit_"+credUrl)));
            js.executeScript("arguments[0].click();", editCredBtn);
            wait.until(ExpectedConditions.elementToBeClickable(By.id("credential-url")));
            //homePage.setCredentialUrl(credUrl);
            homePage.setCredentialUsername("-modified");
            homePage.setCredentialPassword(credPassword);
            WebElement credSaveBtn = wait.until(ExpectedConditions.elementToBeClickable(By.id("cred-save-btn")));
            js.executeScript("arguments[0].click();", credSaveBtn);
            wait.until(ExpectedConditions.elementToBeClickable(By.id("success-msg")));
            assertEquals(CREDENTIALS_SAVE_SUCCESS, homePage.getSuccessMessage());
            WebElement credText = wait.until(ExpectedConditions.elementToBeClickable(By.id("cred_usr_"+credUrl)));
            assertEquals(credUsername+"-modified",credText.getText());
        }catch (Exception e){
            Assertions.fail("Exception occurred while running testEditCredential test: "+e.getMessage());
        }
    }

    @Test
    @Order(4)
    public void testDeleteCredential(){
        try{
            signupAndLogin();
            WebElement navCredBtn = wait.until(ExpectedConditions.elementToBeClickable(By.id("nav-credentials-tab")));
            js.executeScript("arguments[0].click();", navCredBtn);
            WebElement deleteCredBtn = wait.until(ExpectedConditions.elementToBeClickable(By.id("cred_del_"+credUrl)));
            js.executeScript("arguments[0].click();", deleteCredBtn);
            wait.until(ExpectedConditions.elementToBeClickable(By.id("success-msg")));
            assertEquals(CREDENTIAL_DELETE_SUCCESS, homePage.getSuccessMessage());
        }catch (Exception e){
            Assertions.fail("Exception occurred while running testDeleteCredential test: "+e.getMessage());
        }
    }

}
