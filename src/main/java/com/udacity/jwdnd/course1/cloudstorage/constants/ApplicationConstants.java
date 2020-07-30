package com.udacity.jwdnd.course1.cloudstorage.constants;

//File to declare application constants
public interface ApplicationConstants {
    String HOME_MAPPING = "/home";
    String UPLOAD_MAPPING = "/upload";
    String HOME = "home";
    String UPLOAD = "upload";
    String NOTES_MAPPING = "/notes";
    String CREDENTIAL_MAPPING = "/credentials";
    String DOWNLOAD_FILE_MAPPING = "/download/{filename}";
    String DELETE_FILE_MAPPING = "/delete/{filename}";
    String DELETE_NOTE_MAPPING = "/deleteNote/{noteId}";
    String DELETE_CREDENTIAL_MAPPING = "/deleteCredential/{credentialId}";
    String FILE_NAMES = "fileNames" ;
    String NOTES_LIST = "notesList";
    String CREDENTIALS_LIST = "credentialsList";
    String FILES_TAB = "nav-files-tab";
    String NOTES_TAB = "nav-notes-tab";
    String CREDENTIALS_TAB = "nav-credentials-tab";
    String SUCCESS = "SUCCESS";
    String FILE_UPLOAD_SUCCESS = "Your file was uploaded successfully.";
    String FILE_UPLOAD_FAILURE= "An error occurred while saving your file. Please try again after sometime.";
    String FILE_UPLOAD_ERROR_GENERIC = "An error occurred while saving your file. Please try again after sometime.";
    String FILE_UPLOAD_SIZE_ERROR = "The uploaded file exceeds the allowed file size. Please upload a smaller file";
    String FILE_UPLOAD_DUPLICATE_ERROR = "Filename is already used. Please use a different file.";
    String NOTE_INSERT_ERROR_GENERIC = "An error occurred while saving your note. Please try again after sometime.";
    String NOTE_DUPLICATE_ERROR = "A note with that Title already exists. Please use a different Title";
    String NOTE_NOT_FOUND = "Could not find a note corresponding to your request.";
    String NOTE_NOT_FOUND_FOR_USER = "Could not find any existing note corresponding to your request.";
    String NOTE_UPDATE_ERROR_GENERIC = "An error occurred while saving your note. Please try again after sometime.";
    String NOTE_NO_UPDATE = "There were no changes done to your note.";
    String CREDENTIAL_SAVE_ERROR = "An error occurred while saving credentials. Please try again after sometime.";
    String NOTES_SAVE_SUCCESS = "Your note was saved successfully.";
    String CREDENTIALS_SAVE_SUCCESS = "Your credentials were saved successfully.";
    String CREDENTIAL_NOT_FOUND = "Could not find a credential corresponding to your request.";
    String GET_DETAILS_ERROR_GENERIC = "An error occurred while fetching your details. Please try again after sometime.";
    String ERROR_MESSAGE = "errorMessage";
    String SUCCESS_MESSAGE = "successMessage";
    String APPLICATION_FORM = "applicationForm";
}
