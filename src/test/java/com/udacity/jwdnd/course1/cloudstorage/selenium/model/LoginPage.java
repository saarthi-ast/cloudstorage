package com.udacity.jwdnd.course1.cloudstorage.selenium.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

    //constructor
    public LoginPage(WebDriver driver){
        PageFactory.initElements(driver, this);
    }

    //define elements
    @FindBy(id="error-msg")
    private WebElement errorMsg;

    @FindBy(id="logout-msg")
    private WebElement logoutMsg;

    @FindBy(id="inputUsername")
    private WebElement inputUsername;

    @FindBy(id="inputPassword")
    private WebElement inputPassword;

    @FindBy(id="login-submit-btn")
    private WebElement loginSubmitBtn;

    @FindBy(id="signup-link")
    private WebElement signupLink;

    //Custom methods

    public void setInputUsername(String username){
        inputUsername.sendKeys(username);
    }

    public void setInputPassword(String password){
        inputPassword.sendKeys(password);
    }

    public void login(){
        loginSubmitBtn.click();
    }

    public String getLogoutMsg(){
        return logoutMsg.getText();
    }

}
