package com.udacity.jwdnd.course1.cloudstorage.selenium.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignupPage {

    //constructor
    public SignupPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    //define elements
    @FindBy(id = "login-link")
    private WebElement loginLink;

    @FindBy(id = "success-msg")
    private WebElement successMsg;

    @FindBy(id = "error-msg")
    private WebElement errorMsg;

    @FindBy(id = "firstname")
    private WebElement firstname;

    @FindBy(id = "lastname")
    private WebElement lastname;

    @FindBy(id = "username")
    private WebElement username;

    @FindBy(id = "password")
    private WebElement password;

    @FindBy(id = "submit-btn")
    private WebElement submitBtn;

    //Custom methods

    public void setFirstname(String firstname){
        this.firstname.sendKeys(firstname);
    }

    public void setLastname(String lastname){
        this.lastname.sendKeys(lastname);
    }

    public void setUsername(String username){
        this.username.sendKeys(username);
    }

    public void setPassword(String password){
        this.password.sendKeys(password);
    }

    public String getErrorMessage(){
        return errorMsg.getText();
    }

    public String getSuccessMsg(){
        return successMsg.getText();
    }

    public void signup(){
        submitBtn.click();
    }

    public void goToLoginPage(){
        loginLink.click();
    }
}
