package com.udacity.jwdnd.course1.cloudstorage.constants;

/**
 * The interface Application constants.
 * @author Sudhir Tyagi
 */
//File to declare application constants
public interface ApplicationConstants {
    /**
     * The constant HOME_MAPPING.
     */
    String HOME_MAPPING = "/home";
    /**
     * The constant UPLOAD_MAPPING.
     */
    String UPLOAD_MAPPING = "/upload";
    /**
     * The constant SIGNUP_MAPPING.
     */
    String SIGNUP_MAPPING = "/signup";
    /**
     * The constant LOGIN.
     */
    String LOGIN = "login";
    /**
     * The constant HOME.
     */
    String HOME = "home";
    /**
     * The constant UPLOAD.
     */
    String UPLOAD = "upload";
    /**
     * The constant NOTES_MAPPING.
     */
    String NOTES_MAPPING = "/notes";
    /**
     * The constant CREDENTIAL_MAPPING.
     */
    String CREDENTIAL_MAPPING = "/credentials";
    /**
     * The constant DOWNLOAD_FILE_MAPPING.
     */
    String DOWNLOAD_FILE_MAPPING = "/download/{filename}";
    /**
     * The constant DELETE_FILE_MAPPING.
     */
    String DELETE_FILE_MAPPING = "/delete/{filename}";
    /**
     * The constant DELETE_NOTE_MAPPING.
     */
    String DELETE_NOTE_MAPPING = "/deleteNote/{noteId}";
    /**
     * The constant DELETE_CREDENTIAL_MAPPING.
     */
    String DELETE_CREDENTIAL_MAPPING = "/deleteCredential/{credentialId}";
    /**
     * The constant FILE_NAMES.
     */
    String FILE_NAMES = "fileNames" ;
    /**
     * The constant NOTES_LIST.
     */
    String NOTES_LIST = "notesList";
    /**
     * The constant CREDENTIALS_LIST.
     */
    String CREDENTIALS_LIST = "credentialsList";
    /**
     * The constant FILES_TAB.
     */
    String FILES_TAB = "nav-files-tab";
    /**
     * The constant NOTES_TAB.
     */
    String NOTES_TAB = "nav-notes-tab";
    /**
     * The constant CREDENTIALS_TAB.
     */
    String CREDENTIALS_TAB = "nav-credentials-tab";
    /**
     * The constant SUCCESS.
     */
    String SUCCESS = "SUCCESS";
    /**
     * The constant MAX_FILE_SIZE.
     */
    Integer MAX_FILE_SIZE = 2097152;
    /**
     * The constant FILE_UPLOAD_SUCCESS.
     */
    String FILE_UPLOAD_SUCCESS = "Your file was uploaded successfully.";
    /**
     * The constant FILE_UPLOAD_FAILURE.
     */
    String FILE_UPLOAD_FAILURE= "An error occurred while saving your file. Please try again after sometime.";
    /**
     * The constant FILE_UPLOAD_ERROR_GENERIC.
     */
    String FILE_UPLOAD_ERROR_GENERIC = "An error occurred while saving your file. Please try again after sometime.";
    /**
     * The constant FILE_UPLOAD_SIZE_ERROR.
     */
    String FILE_UPLOAD_SIZE_ERROR = "The uploaded file exceeds the allowed file size of "+(MAX_FILE_SIZE /1048576)+" MB. Please upload a smaller file";
    /**
     * The constant FILE_UPLOAD_DUPLICATE_ERROR.
     */
    String FILE_UPLOAD_DUPLICATE_ERROR = "Filename is already used. Please use a different file.";
    /**
     * The constant NOTE_INSERT_ERROR_GENERIC.
     */
    String NOTE_INSERT_ERROR_GENERIC = "An error occurred while saving your note. Please try again after sometime.";
    /**
     * The constant NOTE_DUPLICATE_ERROR.
     */
    String NOTE_DUPLICATE_ERROR = "A note with that Title already exists. Please use a different Title";
    /**
     * The constant NOTE_NOT_FOUND.
     */
    String NOTE_NOT_FOUND = "Could not find a note corresponding to your request.";
    /**
     * The constant GENERIC_ERROR.
     */
    String GENERIC_ERROR = "An error occurred while processing your request. Please try again later.";
    /**
     * The constant NOTE_UPDATE_ERROR_GENERIC.
     */
    String NOTE_UPDATE_ERROR_GENERIC = "An error occurred while saving your note. Please try again after sometime.";
    /**
     * The constant NOTE_NO_UPDATE.
     */
    String NOTE_NO_UPDATE = "There were no changes done to your note.";
    /**
     * The constant CREDENTIAL_SAVE_ERROR.
     */
    String CREDENTIAL_SAVE_ERROR = "An error occurred while saving credentials. Please try again after sometime.";
    /**
     * The constant NOTES_SAVE_SUCCESS.
     */
    String NOTES_SAVE_SUCCESS = "Your note was saved successfully.";
    /**
     * The constant CREDENTIALS_SAVE_SUCCESS.
     */
    String CREDENTIALS_SAVE_SUCCESS = "Your credentials were saved successfully.";
    /**
     * The constant CREDENTIAL_NOT_FOUND.
     */
    String CREDENTIAL_NOT_FOUND = "Could not find a credential corresponding to your request.";
    /**
     * The constant GET_DETAILS_ERROR_GENERIC.
     */
    String GET_DETAILS_ERROR_GENERIC = "An error occurred while fetching your details. Please try again after sometime.";
    /**
     * The constant ERROR_MESSAGE.
     */
    String ERROR_MESSAGE = "errorMessage";
    /**
     * The constant SUCCESS_MESSAGE.
     */
    String SUCCESS_MESSAGE = "successMessage";
    /**
     * The constant APPLICATION_FORM.
     */
    String APPLICATION_FORM = "applicationForm";
    /**
     * The constant ACTIVE_TAB.
     */
    String ACTIVE_TAB = "activeTab";
    /**
     * The constant FILE_DELETE_ERROR.
     */
    String FILE_DELETE_ERROR = "An error occurred while deleting the file. Please try again later.";
    /**
     * The constant FILE_DELETE_SUCCESS.
     */
    Object FILE_DELETE_SUCCESS = "File deleted successfully.";
    /**
     * The constant NOTE_DELETE_SUCCESS.
     */
    Object NOTE_DELETE_SUCCESS = "Note deleted successfully.";
    /**
     * The constant CREDENTIAL_DELETE_SUCCESS.
     */
    Object CREDENTIAL_DELETE_SUCCESS = "Credential deleted successfully.";
    /**
     * The constant NOTE_DELETE_ERROR.
     */
    String NOTE_DELETE_ERROR = "An error occurred while deleting the note. Please try again later.";
    /**
     * The constant CREDENTIAL_DELETE_ERROR.
     */
    String CREDENTIAL_DELETE_ERROR = "An error occurred while deleting the credential. Please try again later.";
    /**
     * The constant DUPLICATE_CREDENTIAL_ERROR.
     */
    String DUPLICATE_CREDENTIAL_ERROR = "A credential for that URL and username already exists.";
    /**
     * The constant CREDENTIAL_ZERO_DELETE.
     */
    String CREDENTIAL_ZERO_DELETE = "No credential was deleted.";
    /**
     * The constant NOTE_NO_DELETE.
     */
    String NOTE_NO_DELETE = "No note was deleted.";
    /**
     * The constant SIGNUP.
     */
    String SIGNUP = "signup";
    /**
     * The constant SIGNUP_DUPLICATE_USERNAME.
     */
    String SIGNUP_DUPLICATE_USERNAME = "This username is already in use. Please try with a different username.";
    /**
     * The constant SIGNUP_GENERIC_ERROR.
     */
    String SIGNUP_GENERIC_ERROR = "The signup process could not be completed. Please try again in sometime.";
    /**
     * The constant SIGNUP_ERROR.
     */
    String SIGNUP_ERROR = "signupError";
    /**
     * The constant SIGNUP_SUCCESS.
     */
    String SIGNUP_SUCCESS = "signupSuccess";
    /**
     * The constant SIGNUP_SUCCESS_MSG.
     */
    String SIGNUP_SUCCESS_MSG = "You successfully signed up! Please login to continue.";
    /**
     * The constant USER_FORM.
     */
    String USER_FORM = "userForm";
    /**
     * The constant FIRSTNAME.
     */
    String FIRSTNAME = "firstname";
    /**
     * The constant LASTNAME.
     */
    String LASTNAME = "lastname";
    /**
     * The constant LOGIN_MAPPING.
     */
    String LOGIN_MAPPING = "/login";
    /**
     * The constant HTTP_METHOD_NOT_SUPPORTED.
     */
    String HTTP_METHOD_NOT_SUPPORTED = "This http method is not supported for the given operation.";
    /**
     * The constant NEGATIVE_USER_ID.
     */
    String NEGATIVE_USER_ID = "Negative userid returned by insert";
    /**
     * The constant HTTP_URL_NOT_VALID.
     */
    String HTTP_URL_NOT_VALID = "The operation requested is not supported.";
}
