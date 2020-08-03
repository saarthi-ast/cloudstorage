package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NotesMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.udacity.jwdnd.course1.cloudstorage.constants.ApplicationConstants.*;

/**
 * The type Notes service.
 * @author Sudhir Tyagi
 */
@Service
public class NotesService {

    private final NotesMapper notesMapper;
    private final UserService userService;

    /**
     * Class constructor.
     *
     * @param notesMapper the notes mapper
     * @param userService the user service
     */
    public NotesService(NotesMapper notesMapper, UserService userService) {
        this.notesMapper = notesMapper;
        this.userService = userService;
    }

    /**
     * Gets notes by user name.
     *
     * @param username the username
     * @return the notes by user name
     */
// Select notes for a user
    public List<Notes> getNotesByUserName(String username) {
        try {
            Integer userId = userService.getUseridByName(username);
            return notesMapper.getNotesByUserId(userId);
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * Update notes string.
     *
     * @param noteId          the note id
     * @param noteTitle       the note title
     * @param noteDescription the note description
     * @param username        the username
     * @return the string
     */
//Update notes
    public String updateNotes(Integer noteId, String noteTitle, String noteDescription, String username) {
        Integer updateCount;
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

    /**
     * Delete notes by id string.
     *
     * @param noteId   the note id
     * @param username the username
     * @return the string
     */
//Delete notes by Id
    public String deleteNotesById(Integer noteId, String username) {
        Integer deleteCt;
        try {
            Integer userId = userService.getUseridByName(username);
            deleteCt = notesMapper.deleteNotesById(noteId, userId);
        } catch (Exception e) {
            return NOTE_DELETE_ERROR;
        }
        return (deleteCt > 0) ? SUCCESS : NOTE_NO_DELETE;
    }

    /**
     * Gets notes by title.
     *
     * @param notesTitle the notes title
     * @return the notes by title
     */
// Find notes by title
    public Notes getNotesByTitle(String notesTitle) {
        return notesMapper.getNotesByTitle(notesTitle);
    }

    /**
     * Is duplicate note boolean.
     *
     * @param notesTitle the notes title
     * @return the boolean
     */
// Check if note with Title exists
    public Boolean isDuplicateNote(String notesTitle) {
        return (null != getNotesByTitle(notesTitle));
    }

    /**
     * Insert note string.
     *
     * @param noteTitle       the note title
     * @param noteDescription the note description
     * @param username        the username
     * @return the string
     */
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
