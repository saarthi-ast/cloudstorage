package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NotesMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.udacity.jwdnd.course1.cloudstorage.constants.ApplicationConstants.*;

@Service
public class NotesService {

    private NotesMapper notesMapper;

    private UserService userService;

    public NotesService(NotesMapper notesMapper, UserService userService) {
        this.notesMapper = notesMapper;
        this.userService = userService;
    }

    // Select notes for a user
    public List<Notes> getNotesByUserName(String username) {
        try {
            Integer userId = userService.getUseridByName(username);
            return notesMapper.getNotesByUserId(userId);
        } catch (Exception ex) {
            return null;
        }
    }

    //Update notes
    public String updateNotes(Integer noteId, String noteTitle, String noteDescription, String username) {
        Integer updateCount = 0;
        try {
            Integer userId = userService.getUseridByName(username);
            Notes notes = notesMapper.findNotesByNoteIdAndUserId(noteId,userId);
            if (notes == null) {
                return NOTE_NOT_FOUND;
            } else {
                if (!notes.getNoteTitle().equals(noteTitle) && isDuplicateNote(noteTitle)) {
                    return NOTE_DUPLICATE_ERROR;
                }
                notes.setNoteTitle(noteTitle);
                notes.setNoteDescription(noteDescription);
                updateCount = notesMapper.updateNotes(notes);
            }
        } catch (Exception ex) {
            return NOTE_UPDATE_ERROR_GENERIC;
        }
        return (updateCount > 0) ? SUCCESS : NOTE_NO_UPDATE;
    }

    //Delete notes by Id
    public String deleteNotesById(Integer noteId, String username) {
        Integer deleteCt = 0;
        try {
            Integer userId = userService.getUseridByName(username);
            deleteCt = notesMapper.deleteNotesById(noteId, userId);
        } catch (Exception e) {
            return NOTE_DELETE_ERROR;
        }
        return (deleteCt > 0) ? SUCCESS : NOTE_NO_DELETE;
    }

//    //Delete notes by Title
//    public Integer deleteNotesByTitle(String title, String username){
//        Integer userId = userService.getUseridByName(username);
//        Notes notes = notesMapper.getNotesByTitle(title);
//        return notesMapper.deleteNotesById(notes.getNoteId(),userId);
//    }

    // Find notes by title
    public Notes getNotesByTitle(String notesTitle) {
        return notesMapper.getNotesByTitle(notesTitle);
    }

    // Check if note with Title exists
    public Boolean isDuplicateNote(String notesTitle) {
        return (null != getNotesByTitle(notesTitle));
    }

    //Save notes
    public String insertNote(String noteTitle, String noteDescription, String username) {
        try {
            if (isDuplicateNote(noteTitle)) {
                return NOTE_DUPLICATE_ERROR;
            }
            Integer userId = userService.getUseridByName(username);
            Notes notes = new Notes(null, noteTitle, noteDescription, userId);
            Integer noteId = notesMapper.insertNote(notes);
        } catch (Exception ex) {
            return NOTE_INSERT_ERROR_GENERIC;
        }
        return SUCCESS;
    }
}
