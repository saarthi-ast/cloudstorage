package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NotesMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Service;

import java.util.List;

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
        Integer userId = userService.getUseridByName(username);
        return notesMapper.getNotesByUserId(userId);
    }

    //Update notes
    public Integer updateNotes(Integer noteId, String noteTitle, String noteDescription, String username){
        Notes notes = notesMapper.findNotesByNoteId(noteId);
        if(notes == null){
            return -1;
        }else{
            Integer userId = userService.getUseridByName(username);
            if(notes.getUserId() != userId){
                return -2;
            }
            notes.setNoteTitle(noteTitle);
            notes.setNoteDescription(noteDescription);
        }
        return notesMapper.updateNotes(notes);
    }

    //Delete notes by Id
    public Integer deleteNotesById(Integer noteId, String username){
        Integer userId = userService.getUseridByName(username);
        return notesMapper.deleteNotesById(noteId,userId);
    }

    //Delete notes by Title
    public Integer deleteNotesByTitle(String title, String username){
        Integer userId = userService.getUseridByName(username);
        Notes notes = notesMapper.getNotesByTitle(title);
        return notesMapper.deleteNotesById(notes.getNoteId(),userId);
    }

    // Find notes by title
    public Notes getNotesByTitle(String notesTitle){
        return notesMapper.getNotesByTitle(notesTitle);
    }

    //Save notes
    public Integer insertNote(String noteTitle, String noteDescription, String username){
        Integer userId = userService.getUseridByName(username);
        Notes notes = new Notes(null,noteTitle,noteDescription,userId);
        return notesMapper.insertNote(notes);
    }
}
