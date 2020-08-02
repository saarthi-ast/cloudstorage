package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;

import java.util.List;

import static com.udacity.jwdnd.course1.cloudstorage.constants.ApplicationConstants.*;

@Service
public class ApplicationUtils {
    private FilesService filesService;
    private NotesService notesService;
    private CredentialsService credentialsService;
    private EncryptionService encryptionService;

    public ApplicationUtils(FilesService filesService, NotesService notesService, CredentialsService credentialsService, EncryptionService encryptionService) {
        this.filesService = filesService;
        this.notesService = notesService;
        this.credentialsService = credentialsService;
        this.encryptionService = encryptionService;
    }

    public Model populateModel(Model model, Authentication authentication) {
        List<String> fileNames = filesService.getFilenamesByUserName(authentication.getName());
        model.addAttribute(FILE_NAMES, fileNames);
        List<Notes> notesList = notesService.getNotesByUserName(authentication.getName());
        model.addAttribute(NOTES_LIST, notesList);
        List<Credentials> credentialsList = credentialsService.findCredentialsByUsername(authentication.getName());
        model.addAttribute(CREDENTIALS_LIST, credentialsList);
        if(null == fileNames || null == notesList || null == credentialsList){
            model.addAttribute(ERROR_MESSAGE,GET_DETAILS_ERROR_GENERIC);
        }
        model.addAttribute("encryptionService",encryptionService);
        return model;
    }
    public ModelMap populateModelMap(ModelMap model, Authentication authentication) {
        List<String> fileNames = filesService.getFilenamesByUserName(authentication.getName());
        model.addAttribute(FILE_NAMES, fileNames);
        List<Notes> notesList = notesService.getNotesByUserName(authentication.getName());
        model.addAttribute(NOTES_LIST, notesList);
        List<Credentials> credentialsList = credentialsService.findCredentialsByUsername(authentication.getName());
        model.addAttribute(CREDENTIALS_LIST, credentialsList);
        if(null == fileNames || null == notesList || null == credentialsList){
            model.addAttribute(ERROR_MESSAGE,GET_DETAILS_ERROR_GENERIC);
        }
        model.addAttribute("encryptionService",encryptionService);
        return model;
    }
}
