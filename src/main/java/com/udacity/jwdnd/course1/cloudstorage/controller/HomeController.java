package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.ApplicationForm;
import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.model.Files;
import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialsService;
import com.udacity.jwdnd.course1.cloudstorage.services.FilesService;
import com.udacity.jwdnd.course1.cloudstorage.services.NotesService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.util.List;

import static com.udacity.jwdnd.course1.cloudstorage.constants.ApplicationConstants.*;

@Controller
public class HomeController {

    private FilesService filesService;
    private NotesService notesService;
    private CredentialsService credentialsService;

    public HomeController(FilesService filesService, NotesService notesService, CredentialsService credentialsService) {
        this.filesService = filesService;
        this.notesService = notesService;
        this.credentialsService = credentialsService;
    }

    @GetMapping(HOME_MAPPING)
    public String showHomePage(@ModelAttribute(APPLICATION_FORM) ApplicationForm applicationForm,
                               Model model, Authentication authentication) {
        populateModel(model, authentication);
        applicationForm.setActiveTab(FILES_TAB);
        return HOME;
    }

    @PostMapping(UPLOAD_MAPPING)
    public String uploadFile(@RequestParam("fileUpload") MultipartFile files,
                             @ModelAttribute(APPLICATION_FORM) ApplicationForm applicationForm,
                             Model model, Authentication authentication) {
        String uploadStatus = filesService.saveFile(files, authentication.getName());
        if(SUCCESS.equals(uploadStatus)){
            model.addAttribute(SUCCESS_MESSAGE,FILE_UPLOAD_SUCCESS);
        }else {
            model.addAttribute(ERROR_MESSAGE,uploadStatus);
        }
        populateModel(model, authentication);
        applicationForm.setActiveTab(FILES_TAB);
        return HOME;
    }

    @PostMapping(NOTES_MAPPING)
    public String saveNotes(@ModelAttribute(APPLICATION_FORM) ApplicationForm applicationForm,
                            Model model, Authentication authentication) {
        Notes notes = applicationForm.getNotes();
        String status;
        if (notes.getNoteId() == null) {
            status  = notesService.insertNote(notes.getNoteTitle(), notes.getNoteDescription(), authentication.getName());
        } else {
            status = notesService.updateNotes(notes.getNoteId(), notes.getNoteTitle(), notes.getNoteDescription(), authentication.getName());
        }
        if(SUCCESS.equals(status)){
            model.addAttribute(SUCCESS_MESSAGE,NOTES_SAVE_SUCCESS);
        }else {
            model.addAttribute(ERROR_MESSAGE,status);
        }
        populateModel(model, authentication);
        applicationForm.setActiveTab(NOTES_TAB);
        return HOME;
    }

    // public String saveCredentials(@RequestParam("credentialId") Integer credentialId,@RequestParam("url") String url,@RequestParam("username") String username,
    //                             @RequestParam("password") String password, Model model, Authentication authentication) {

    @PostMapping(CREDENTIAL_MAPPING)
    public String saveCredentials(@ModelAttribute(APPLICATION_FORM) ApplicationForm applicationForm,
                                  Model model, Authentication authentication) {
        Credentials credentials = applicationForm.getCredentials();
        String status = null;
        if (credentials.getCredentialId() == null) {
            status  = credentialsService.insertCredentials(credentials, authentication.getName());
        } else {
            status  = credentialsService.updateCredentialsByCredentialIdAndUserId(credentials, authentication.getName());
        }
        if(SUCCESS.equals(status)){
            model.addAttribute(SUCCESS_MESSAGE,CREDENTIALS_SAVE_SUCCESS);
        }else {
            model.addAttribute(ERROR_MESSAGE,status);
        }
        populateModel(model, authentication);
        applicationForm.setActiveTab(CREDENTIALS_TAB);
        return HOME;
    }

//    @GetMapping(UPLOAD_MAPPING)
//    public String getFilesForUser(Model model, Authentication authentication) {
//        populateModel(model, authentication);
//        return HOME;
//    }

//    @GetMapping("/notes")
//    public String getNotesForUser(Model model, Authentication authentication) {
//        populateModel(model, authentication);
//
//        return HOME;
//    }

    @GetMapping(DOWNLOAD_FILE_MAPPING)
    public ResponseEntity<Object> downloadFile(@PathVariable String filename, Model model, Authentication authentication) {
        Files usrFile = filesService.getFileByName(filename, authentication.getName());
        byte[] data = usrFile.getFileData();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
        InputStreamResource resource = new InputStreamResource(inputStream);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", usrFile.getFilename()));
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        ResponseEntity<Object>
                responseEntity = ResponseEntity.ok().headers(headers).contentLength(data.length).contentType(
                MediaType.parseMediaType("application/txt")).body(resource);

        return responseEntity;
    }

    @GetMapping(DELETE_FILE_MAPPING)
    public String deleteFile(@ModelAttribute(APPLICATION_FORM) ApplicationForm applicationForm,
                             @PathVariable String filename, Model model, Authentication authentication) {
        filesService.deleteFileByName(filename, authentication.getName());
        populateModel(model, authentication);
        applicationForm.setActiveTab(FILES_TAB);
        return HOME;
    }

    @GetMapping(DELETE_NOTE_MAPPING)
    public String deleteNote(@ModelAttribute(APPLICATION_FORM) ApplicationForm applicationForm,
                             @PathVariable Integer noteId, Model model, Authentication authentication) {
        notesService.deleteNotesById(noteId, authentication.getName());
        populateModel(model, authentication);
        applicationForm.setActiveTab(NOTES_TAB);
        return HOME;
    }

    @GetMapping(DELETE_CREDENTIAL_MAPPING)
    public String deleteCredential(@PathVariable Integer credentialId,
                                   @ModelAttribute(APPLICATION_FORM) ApplicationForm applicationForm ,
                                   Model model, Authentication authentication) {
        credentialsService.deleteCredentialsByIdAndUserId(credentialId, authentication.getName());
        populateModel(model, authentication);
        applicationForm.setActiveTab(CREDENTIALS_TAB);
        return HOME;
    }

    private Model populateModel(Model model, Authentication authentication) {
        List<String> fileNames = filesService.getFilenamesByUserName(authentication.getName());
        model.addAttribute(FILE_NAMES, fileNames);
        List<Notes> notesList = notesService.getNotesByUserName(authentication.getName());
        model.addAttribute(NOTES_LIST, notesList);
        List<Credentials> credentialsList = credentialsService.findCredentialsByUsername(authentication.getName());
        model.addAttribute(CREDENTIALS_LIST, credentialsList);
        if(null == fileNames || null == notesList || null == credentialsList){
            model.addAttribute(ERROR_MESSAGE,GET_DETAILS_ERROR_GENERIC);
        }

        return model;
    }
}
