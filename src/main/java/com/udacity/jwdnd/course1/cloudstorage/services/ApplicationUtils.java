package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;

import java.util.List;

import static com.udacity.jwdnd.course1.cloudstorage.constants.ApplicationConstants.*;

/**
 * The type Application utils.
 * @author Sudhir Tyagi
 */
@Service
public class ApplicationUtils {
    private final FilesService filesService;
    private final NotesService notesService;
    private final CredentialsService credentialsService;
    private final EncryptionService encryptionService;

    /**
     * Class constructor.
     *
     * @param filesService       the files service
     * @param notesService       the notes service
     * @param credentialsService the credentials service
     * @param encryptionService  the encryption service
     */
    public ApplicationUtils(FilesService filesService, NotesService notesService, CredentialsService credentialsService, EncryptionService encryptionService) {
        this.filesService = filesService;
        this.notesService = notesService;
        this.credentialsService = credentialsService;
        this.encryptionService = encryptionService;
    }

    /**
     * Populate model model.
     *
     * @param model          the model
     * @param authentication the authentication
     * @return the model
     */
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

    /**
     * Populate model map model map.
     *
     * @param model          the model
     * @param authentication the authentication
     * @return the model map
     */
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
