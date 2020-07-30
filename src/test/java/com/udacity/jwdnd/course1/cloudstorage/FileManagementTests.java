package com.udacity.jwdnd.course1.cloudstorage;


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
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

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

    @BeforeAll
    public static void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        testFirstName = "Test";
        testLastName = "User";

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
        wait = new WebDriverWait(driver, 5);
        signupPage = new SignupPage(driver);
        loginPage = new LoginPage(driver);
        homePage = new HomePage(driver);
    }

    private void signupAndLogin() {
        driver.get("http://localhost:8080/signup");

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
    public void testFileUploadAndDelete(){
        signupAndLogin();
        wait.until(webDriver -> webDriver.findElement(By.id("nav-files-tab")));
        //create a dummy file
        File file = new File("/tmp/test_"+random.nextInt(1000)+".txt");
        try {
            if(file.createNewFile()){
                FileWriter writer = new FileWriter(file);
                writer.write("Test data for "+ file.getPath());
                writer.close();
                homePage.addFile(file.getPath());
                Thread.sleep(1000);
                homePage.uploadFile();
                WebElement filename = wait.until(webDriver -> webDriver.findElement(By.id("filename_" + file.getName())));
                assertNotNull(filename);
                assertEquals(file.getName(),filename.getText());
                WebElement deleteFile = wait.until(webDriver -> webDriver.findElement(By.id("file_del_" + file.getName())));
                deleteFile.click();
                assertThrows(TimeoutException.class, () ->  wait.until(webDriver -> webDriver.findElement(By.id("filename_" + file.getName()))));
            }

        } catch (IOException e) {
            Assertions.fail("Test failed in file handling.");
        } catch (TimeoutException timeoutException){
            Assertions.fail("Timeout occurred waiting for web element.");
        }catch (Exception e){
            Assertions.fail("Exception occurred while running file upload and delete test.");
        }finally{
            //clean up dummy files
            if(file.delete()){
                assertTrue(true); // file deleted. So explicitly passed
            }else{
                Assertions.fail(); // could not clean up disk space. Fail the test
            }
        }
    }
}
