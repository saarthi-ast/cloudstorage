package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.ApplicationForm;
import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.model.Files;
import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;

import static com.udacity.jwdnd.course1.cloudstorage.constants.ApplicationConstants.*;

@Controller
public class HomeController implements HandlerExceptionResolver {
    private static final Logger LOG = LoggerFactory.getLogger(HomeController.class);
    private FilesService filesService;
    private NotesService notesService;
    private CredentialsService credentialsService;
    private EncryptionService encryptionService;
    private ApplicationUtils utils;

    public HomeController(FilesService filesService, NotesService notesService,
                          CredentialsService credentialsService, EncryptionService encryptionService,
                          ApplicationUtils utils) {
        this.filesService = filesService;
        this.notesService = notesService;
        this.credentialsService = credentialsService;
        this.encryptionService = encryptionService;
        this.utils = utils;
    }

    @GetMapping(HOME_MAPPING)
    public String showHomePage(@ModelAttribute(APPLICATION_FORM) ApplicationForm applicationForm,
                               Model model, Authentication authentication) {
        LOG.debug("home page invoked");
        utils.populateModel(model, authentication);
        model.addAttribute(ACTIVE_TAB,FILES_TAB);
        return HOME;
    }

    @GetMapping(CREDENTIAL_MAPPING)
    public String getCredentialsForUser(@ModelAttribute(APPLICATION_FORM) ApplicationForm applicationForm,Model model, Authentication authentication) {
        LOG.debug("credential page invoked");
        utils.populateModel(model, authentication);
        model.addAttribute(ACTIVE_TAB,CREDENTIALS_TAB);
        return HOME;
    }

    @GetMapping(NOTES_MAPPING)
    public String getNotesForUser(@ModelAttribute(APPLICATION_FORM) ApplicationForm applicationForm,Model model, Authentication authentication) {
        LOG.debug("note page invoked");
        utils.populateModel(model, authentication);
        model.addAttribute(ACTIVE_TAB,NOTES_TAB);
        return HOME;
    }

    @PostMapping(UPLOAD_MAPPING)
    public String uploadFile(@RequestParam("fileUpload") MultipartFile files,
                             @ModelAttribute(APPLICATION_FORM) ApplicationForm applicationForm,
                             Model model, Authentication authentication) {
        LOG.debug("file upload requested");
        String uploadStatus = filesService.saveFile(files, authentication.getName());
        if(SUCCESS.equals(uploadStatus)){
            model.addAttribute(SUCCESS_MESSAGE,FILE_UPLOAD_SUCCESS);
        }else {
            model.addAttribute(ERROR_MESSAGE,uploadStatus);
        }
        utils.populateModel(model, authentication);
        model.addAttribute(ACTIVE_TAB,FILES_TAB);
        return HOME;
    }

    @PostMapping(NOTES_MAPPING)
    public String saveNotes(@ModelAttribute(APPLICATION_FORM) ApplicationForm applicationForm,
                            Model model, Authentication authentication) {
        LOG.debug("note addition requested");
        Notes notes = applicationForm.getNotes();
        String status;
        if (notes.getNoteId() == null) {
            LOG.debug("note addition requested - insert");
            status  = notesService.insertNote(notes.getNoteTitle(), notes.getNoteDescription(), authentication.getName());
        } else {
            LOG.debug("note addition requested - update");
            status = notesService.updateNotes(notes.getNoteId(), notes.getNoteTitle(), notes.getNoteDescription(), authentication.getName());
        }
        if(SUCCESS.equals(status)){
            LOG.debug("note addition requested - success:"+status);
            model.addAttribute(SUCCESS_MESSAGE,NOTES_SAVE_SUCCESS);
        }else {
            LOG.debug("note addition requested - failure:"+status);
            model.addAttribute(ERROR_MESSAGE,status);
        }
        utils.populateModel(model, authentication);
        model.addAttribute(ACTIVE_TAB,NOTES_TAB);
        return HOME;
    }

    @PostMapping(CREDENTIAL_MAPPING)
    public String saveCredentials(@ModelAttribute(APPLICATION_FORM) ApplicationForm applicationForm,
                                  Model model, Authentication authentication) {
        LOG.debug("credential addition requested");
        Credentials credentials = applicationForm.getCredentials();
        String status = null;
        if (credentials.getCredentialId() == null) {
            LOG.debug("credential addition requested - insert");
            status  = credentialsService.insertCredentials(credentials, authentication.getName());
        } else {
            LOG.debug("credential addition requested - update");
            status  = credentialsService.updateCredentialsByCredentialIdAndUserId(credentials, authentication.getName());
        }
        if(SUCCESS.equals(status)){
            LOG.debug("credential addition requested - success");
            model.addAttribute(SUCCESS_MESSAGE,CREDENTIALS_SAVE_SUCCESS);
        }else {
            LOG.debug("credential addition requested - failure"+status);
            model.addAttribute(ERROR_MESSAGE,status);
        }
        utils.populateModel(model, authentication);
        model.addAttribute(ACTIVE_TAB,CREDENTIALS_TAB);
        return HOME;
    }

    @GetMapping(DOWNLOAD_FILE_MAPPING)
    public ResponseEntity<Object> downloadFile(@PathVariable String filename, Model model, Authentication authentication) {
        LOG.info("File download requested - "+filename);
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
        LOG.debug("File download success");
        return responseEntity;
    }

    @GetMapping(DELETE_FILE_MAPPING)
    public String deleteFile(@ModelAttribute(APPLICATION_FORM) ApplicationForm applicationForm,
                             @PathVariable String filename, Model model, Authentication authentication) {
        LOG.debug("File delete requested - "+filename);
        String status = filesService.deleteFileByName(filename, authentication.getName());
        utils.populateModel(model, authentication);
        if(SUCCESS.equals(status)){
            LOG.debug("File delete requested - success - "+filename);
            model.addAttribute(SUCCESS_MESSAGE,FILE_DELETE_SUCCESS);
        }else {
            LOG.info("File delete requested - failure: "+status+" name - "+filename);
            model.addAttribute(ERROR_MESSAGE,status);
        }
        model.addAttribute(ACTIVE_TAB,FILES_TAB);
        return HOME;
    }

    @GetMapping(DELETE_NOTE_MAPPING)
    public String deleteNote(@ModelAttribute(APPLICATION_FORM) ApplicationForm applicationForm,
                             @PathVariable Integer noteId, Model model, Authentication authentication) {
        LOG.debug("Note delete requested - "+noteId);
        String status = notesService.deleteNotesById(noteId, authentication.getName());
        utils.populateModel(model, authentication);
        if(SUCCESS.equals(status)){
            LOG.debug("Note delete requested - success."+noteId);
            model.addAttribute(SUCCESS_MESSAGE,NOTE_DELETE_SUCCESS);
        }else {
            LOG.info("Note delete requested - failure:"+status+" Id - "+ noteId);
            model.addAttribute(ERROR_MESSAGE,status);
        }
        model.addAttribute(ACTIVE_TAB,NOTES_TAB);
        return HOME;
    }

    @GetMapping(DELETE_CREDENTIAL_MAPPING)
    public String deleteCredential(@PathVariable Integer credentialId,
                                   @ModelAttribute(APPLICATION_FORM) ApplicationForm applicationForm ,
                                   Model model, Authentication authentication) {
        LOG.debug("Credential delete requested -"+credentialId);
        String status = credentialsService.deleteCredentialsByIdAndUserId(credentialId, authentication.getName());
        utils.populateModel(model, authentication);
        model.addAttribute(ACTIVE_TAB,CREDENTIALS_TAB);
        if(SUCCESS.equals(status)){
            LOG.debug("Credential delete requested - success."+credentialId);
            model.addAttribute(SUCCESS_MESSAGE,CREDENTIAL_DELETE_SUCCESS);
        }else {
            LOG.info("Credential delete requested - failure."+status+" Id-"+credentialId);
            model.addAttribute(ERROR_MESSAGE,status);
        }
        return HOME;
    }

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object object, Exception exc) {
        LOG.error("Exception resolver: "+exc.getMessage());
        ModelAndView modelAndView = new ModelAndView("home");
        if (exc instanceof MaxUploadSizeExceededException) {
            modelAndView.getModel().put(ERROR_MESSAGE, FILE_UPLOAD_SIZE_ERROR);
        }else {
            modelAndView.getModel().put(ERROR_MESSAGE, GENERIC_ERROR);
        }
        ModelMap model= (ModelMap) modelAndView.getModel();
        utils.populateModelMap(model, SecurityContextHolder.getContext().getAuthentication());
        model.addAttribute(ACTIVE_TAB,FILES_TAB);
        return modelAndView;
    }
}
