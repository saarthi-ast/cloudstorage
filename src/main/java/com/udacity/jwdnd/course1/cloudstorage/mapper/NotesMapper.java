package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NotesMapper {
    // Select notes for a user
    @Select("Select * from Notes where userId = #{userId}")
    public List<Notes> getNotesByUserId(Integer userId);

    // Find notes for a given noteId
    @Select("Select * from Notes where noteId=#{noteId}")
    public Notes findNotesByNoteId(Integer noteId);

    //Update notes
    @Update("Update Notes set noteTitle = #{noteTitle}, noteDescription = #{noteDescription} where noteId=#{noteId}")
    public Integer updateNotes(Notes notes);

    //Delete notes
    @Delete("Delete from Notes where noteId=#{noteId} and userId=#{userId}")
    public Integer deleteNotesById(Integer noteId, Integer userId);

    // Find notes by title
    @Select("Select * from Notes where notesTitle = #{noteTitle}")
    public Notes getNotesByTitle(String noteTitle);

    //Save notes
    @Insert("Insert into notes (noteTitle, noteDescription, userId) values (#{noteTitle}, #{noteDescription}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    public Integer insertNote(Notes notes);
}
