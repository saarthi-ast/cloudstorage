package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.model.Files;
import com.udacity.jwdnd.course1.cloudstorage.model.LoginForm;
import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialsService;
import com.udacity.jwdnd.course1.cloudstorage.services.FilesService;
import com.udacity.jwdnd.course1.cloudstorage.services.NotesService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.ByteArrayInputStream;
import java.util.List;

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

    public static final String HOME_MAPPING = "/home";
    public static final String UPLOAD_MAPPING = "/upload";
    public static final String HOME = "home";
    public static final String UPLOAD = "upload";

    @GetMapping(HOME_MAPPING)
    public String showHomePage(@ModelAttribute("loginForm") LoginForm loginForm, @ModelAttribute("credentialForm") Credentials credentials,
                               Model model, Authentication authentication) {
        populateModel(model, authentication);
        return HOME;
    }

    @PostMapping(UPLOAD_MAPPING)
    public String uploadFile(@RequestParam("fileUpload") MultipartFile files,
                             @ModelAttribute("credentialForm") Credentials credentials,
                             Model model, Authentication authentication) {
        filesService.saveFile(files, authentication.getName());
        populateModel(model, authentication);
        return HOME;
    }

    @PostMapping("/notes")
    public String saveNotes(@ModelAttribute("credentialForm") Credentials credentials,
                            @RequestParam("noteId") Integer noteId, @RequestParam("noteTitle") String noteTitle,
                            @RequestParam("noteDescription") String noteDescription, Model model, Authentication authentication) {
        if (noteId == null) {
            notesService.insertNote(noteTitle, noteDescription, authentication.getName());
        } else {
            notesService.updateNotes(noteId, noteTitle, noteDescription, authentication.getName());
        }
        populateModel(model, authentication);
        return HOME;
    }

    // public String saveCredentials(@RequestParam("credentialId") Integer credentialId,@RequestParam("url") String url,@RequestParam("username") String username,
    //                             @RequestParam("password") String password, Model model, Authentication authentication) {

    @PostMapping("/credentials")
    public String saveCredentials(@ModelAttribute("credentialForm") Credentials credentials,
                                  Model model, Authentication authentication) {
        if (credentials.getCredentialId() == null) {
            credentialsService.insertCredentials(credentials, authentication.getName());
        } else {
            credentialsService.updateCredentialsByCredentialIdAndUserId(credentials, authentication.getName());
        }
        populateModel(model, authentication);
        return HOME;
    }

    @GetMapping(UPLOAD_MAPPING)
    public String getFilesForUser(Model model, Authentication authentication) {
        populateModel(model, authentication);
        return HOME;
    }

    @GetMapping("/notes")
    public String getNotesForUser(Model model, Authentication authentication) {
        populateModel(model, authentication);

        return HOME;
    }

    @GetMapping("/download/{filename}")
    public StreamingResponseBody downloadFile(@PathVariable String filename, Model model, Authentication authentication) {
        Files usrFile = filesService.getFileByName(filename, authentication.getName());
        return outputStream -> {
            int nRead;
            byte[] data = usrFile.getFileData();
            ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
            while ((nRead = inputStream.read(usrFile.getFileData(), 0, data.length)) != -1) {
                System.out.println("Writing some bytes..");
                outputStream.write(data, 0, nRead);
            }
        };
    }

    @GetMapping("/delete/{filename}") //TODO: check on changing this to DeleteMapping
    public String deleteFile(@ModelAttribute("credentialForm") Credentials credentials,
                             @PathVariable String filename, Model model, Authentication authentication) {
        filesService.deleteFileByName(filename, authentication.getName());
        populateModel(model, authentication);
        return HOME;
    }

    @GetMapping("/deleteNote/{noteId}") //TODO: check on changing this to DeleteMapping
    public String deleteNote(@ModelAttribute("credentialForm") Credentials credentials,
                             @PathVariable Integer noteId, Model model, Authentication authentication) {
        notesService.deleteNotesById(noteId, authentication.getName());
        populateModel(model, authentication);
        return HOME;
    }

    @GetMapping("/deleteCredential/{credentialId}") //TODO: check on changing this to DeleteMapping
    public String deleteCredential(@PathVariable Integer credentialId,
                                   @ModelAttribute("credentialForm") Credentials credentials,
                                   Model model, Authentication authentication) {
        credentialsService.deleteCredentialsByIdAndUserId(credentialId, authentication.getName());
        populateModel(model, authentication);
        return HOME;
    }

    private Model populateModel(Model model, Authentication authentication) {
        List<String> fileNames = filesService.getFilenamesByUserName(authentication.getName());
        model.addAttribute("fileNames", fileNames);
        List<Notes> notesList = notesService.getNotesByUserName(authentication.getName());
        model.addAttribute("notesList", notesList);
        List<Credentials> credentialsList = credentialsService.findCredentialsByUsername(authentication.getName());
        model.addAttribute("credentialsList", credentialsList);
        return model;
    }
}
