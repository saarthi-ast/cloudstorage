package com.udacity.jwdnd.course1.cloudstorage.model;

public class LoginForm {
    private String inputUsername;
    private String inputPassword;

    public LoginForm(String inputUsername, String inputPassword) {
        this.inputUsername = inputUsername;
        this.inputPassword = inputPassword;
    }

    public String getInputUsername() {
        return inputUsername;
    }

    public void setInputUsername(String inputUsername) {
        this.inputUsername = inputUsername;
    }

    public String getInputPassword() {
        return inputPassword;
    }

    public void setInputPassword(String inputPassword) {
        this.inputPassword = inputPassword;
    }
}
