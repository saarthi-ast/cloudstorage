package com.udacity.jwdnd.course1.cloudstorage.selenium.tests;

import com.udacity.jwdnd.course1.cloudstorage.selenium.model.HomePage;
import com.udacity.jwdnd.course1.cloudstorage.selenium.model.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.selenium.model.SignupPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.Random;

import static com.udacity.jwdnd.course1.cloudstorage.constants.ApplicationConstants.*;
import static org.junit.jupiter.api.Assertions.*;

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
    private WebDriverWait wait;
    private static String noteTitle;

    @BeforeAll
    public static void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        testFirstName = "Test";
        testLastName = "User";
        Random random = new Random();
        testUsr = "test-" + random.nextInt(100000);
        noteTitle = testUsr;
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
    public void testAddNote(){
        try{
            signupAndLogin();
            Thread.sleep(1000);
            wait.until(ExpectedConditions.elementToBeClickable(By.id("nav-notes-tab")));
            homePage.navigateToNotes();
            WebElement addNoteBtn = wait.until(ExpectedConditions.elementToBeClickable(By.id("add-note-btn")));
            Thread.sleep(1000);
            homePage.clickAddNoteBtn();
            wait.until(ExpectedConditions.elementToBeClickable(By.id("note-title")));
            homePage.setNoteTitle(noteTitle);
            homePage.setNoteDescription("THis is a test.");
            wait.until(ExpectedConditions.elementToBeClickable(By.id("note-save-btn"))).click();
            wait.until(ExpectedConditions.elementToBeClickable(By.id("success-msg")));
            assertEquals(NOTES_SAVE_SUCCESS, homePage.getSuccessMessage());
            WebElement noteText = wait.until(ExpectedConditions.elementToBeClickable(By.id("noteTitle_"+noteTitle)));
            assertEquals(noteTitle,noteText.getText());
        }catch (Exception e){
            Assertions.fail("Exception occurred while running testAddNote test: "+e.getMessage());
        }
    }

    @Test
    @Order(2)
    public void testDuplicateNoteError(){
        try{
            signupAndLogin();
            Thread.sleep(1000);
            wait.until(ExpectedConditions.elementToBeClickable(By.id("nav-notes-tab")));
            homePage.navigateToNotes();
            WebElement addNoteBtn = wait.until(ExpectedConditions.elementToBeClickable(By.id("add-note-btn")));
            Thread.sleep(1000);
            homePage.clickAddNoteBtn();
            wait.until(ExpectedConditions.elementToBeClickable(By.id("note-title")));
            homePage.setNoteTitle(noteTitle);
            homePage.setNoteDescription("THis is a test.");
            wait.until(ExpectedConditions.elementToBeClickable(By.id("note-save-btn"))).click();
            wait.until(ExpectedConditions.elementToBeClickable(By.id("error-msg")));
            assertEquals(NOTE_DUPLICATE_ERROR, homePage.getErrorMessage());
        }catch (Exception e){
            Assertions.fail("Exception occurred while running testDuplicateNoteError test: "+e.getMessage());
        }
    }

    @Test
    @Order(3)
    public void testEditNote(){
        try{
            signupAndLogin();
            Thread.sleep(1000);
            wait.until(ExpectedConditions.elementToBeClickable(By.id("nav-notes-tab")));
            homePage.navigateToNotes();
            WebElement editNote = wait.until(ExpectedConditions.elementToBeClickable(By.id("note_edit_"+noteTitle)));
            Thread.sleep(1000);
            editNote.click();
            wait.until(ExpectedConditions.elementToBeClickable(By.id("note-title")));
            //homePage.setNoteTitle(noteTitle);
            homePage.setNoteDescription(" Modified");
            wait.until(ExpectedConditions.elementToBeClickable(By.id("note-save-btn"))).click();
            wait.until(ExpectedConditions.elementToBeClickable(By.id("success-msg")));
            WebElement noteDesc = wait.until(ExpectedConditions.elementToBeClickable(By.id("noteDesc_"+noteTitle)));
            assertEquals("THis is a test. Modified", noteDesc.getText());
        }catch (Exception e){
            Assertions.fail("Exception occurred while running testEditNote test: "+e.getMessage());
        }
    }

    @Test
    @Order(4)
    public void testDeleteNote(){
        try{
            signupAndLogin();
            Thread.sleep(1000);
            wait.until(ExpectedConditions.elementToBeClickable(By.id("nav-notes-tab")));
            homePage.navigateToNotes();
            WebElement deleteNote = wait.until(ExpectedConditions.elementToBeClickable(By.id("note_del_"+noteTitle)));
            Thread.sleep(1000);
            deleteNote.click();
            wait.until(ExpectedConditions.elementToBeClickable(By.id("success-msg")));
            assertEquals(NOTE_DELETE_SUCCESS, homePage.getSuccessMessage());
        }catch (Exception e){
            Assertions.fail("Exception occurred while running testDeleteNote test: "+e.getMessage());
        }
    }

}
