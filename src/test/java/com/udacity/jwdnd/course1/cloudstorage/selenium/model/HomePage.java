package com.udacity.jwdnd.course1.cloudstorage.selenium.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {

    //constructor
    public HomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    //define elements

    @FindBy(id = "logout-btn")
    private WebElement logoutBtn;

    @FindBy(id = "nav-files-tab")
    private WebElement navFilesTab;

    @FindBy(id = "nav-notes-tab")
    private WebElement navNotesTab;

    @FindBy(id = "nav-credentials-tab")
    private WebElement navCredentialsTab;

    @FindBy(id = "fileUpload")
    private WebElement fileUpload;

    @FindBy(id = "file-submit-btn")
    private WebElement fileSubmit;

    @FindBy(id = "file_view_test.txt")
    private WebElement fileView;

    @FindBy(id = "file_del_test.txt")
    private WebElement fileDelete;

    @FindBy(id = "filename_test.txt")
    private WebElement filename;

    @FindBy(id = "add-note-btn")
    private WebElement addNoteBtn;

    @FindBy(id = "note_del_Test")
    private WebElement noteDelete;

    @FindBy(id = "note_edit_Test")
    private WebElement noteEdit;

    @FindBy(id = "noteTitle_Test")
    private WebElement noteTitleText;

    @FindBy(id = "noteDesc_Test")
    private WebElement noteDescText;

    @FindBy(id = "note-id")
    private WebElement noteId;

    @FindBy(id = "note-title")
    private WebElement noteTitle;

    @FindBy(id = "note-description")
    private WebElement noteDescription;

    @FindBy(id = "note-close-btn")
    private WebElement noteCloseBtn;

    @FindBy(id = "note-submit-btn")
    private WebElement noteSubmitBtn;

    @FindBy(id = "note-save-btn")
    private WebElement noteSaveBtn;

    @FindBy(id = "add-cred-btn")
    private WebElement addCredBtn;

    @FindBy(id = "cred_edit_abc.test.com")
    private WebElement credEditText;

    @FindBy(id = "cred_del_abc.test.com")
    private WebElement credDelText;

    @FindBy(id = "cred_url_abc.test.com")
    private WebElement credUrlText;

    @FindBy(id = "cred_pwd_abc.test.com")
    private WebElement credPwdText;

    @FindBy(id = "credential-id")
    private WebElement credentialId;

    @FindBy(id = "credential-url")
    private WebElement credentialUrl;

    @FindBy(id = "credential-username")
    private WebElement credentialUsername;

    @FindBy(id = "credential-password")
    private WebElement credentialPassword;

    @FindBy(id = "cred-submit-btn")
    private WebElement credSubmitBtn;

    @FindBy(id = "cred-close-btn")
    private WebElement credCloseBtn;

    @FindBy(id = "cred-save-btn")
    private WebElement credSaveBtn;

    //Custom methods
    // Logout
    public void logout(){
        logoutBtn.click();
    }

    //File upload


    // Notes
    public void navigateToNotes(){
        navNotesTab.click();
    }

    public void navigateToFiles(){
        navFilesTab.click();
    }

    public void addFile(String filePath){
        fileUpload.sendKeys(filePath);
    }

    public void uploadFile(){
        fileSubmit.click();
    }
    // Credentials

}
