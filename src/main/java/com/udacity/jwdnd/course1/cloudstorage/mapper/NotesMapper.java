package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * The interface Notes mapper.
 * @author Sudhir Tyagi
 */
@Mapper
public interface NotesMapper {
    /**
     * Gets notes by user id.
     *
     * @param userId the user id
     * @return the notes by user id
     */
    @Select("Select * from Notes where userId = #{userId}")
    List<Notes> getNotesByUserId(Integer userId);

    /**
     * Find notes by note id and user id notes.
     *
     * @param noteId the note id
     * @param userId the user id
     * @return the notes
     */
    @Select("Select * from Notes where noteId=#{noteId} and userId = #{userId}")
    Notes findNotesByNoteIdAndUserId(Integer noteId, Integer userId);

    /**
     * Update notes integer.
     *
     * @param notes the notes
     * @return the integer
     */
    @Update("Update Notes set noteTitle = #{noteTitle}, noteDescription = #{noteDescription} where noteId=#{noteId}")
    Integer updateNotes(Notes notes);

    /**
     * Delete notes by id integer.
     *
     * @param noteId the note id
     * @param userId the user id
     * @return the integer
     */
    @Delete("Delete from Notes where noteId=#{noteId} and userId=#{userId}")
    Integer deleteNotesById(Integer noteId, Integer userId);

    /**
     * Gets notes by title.
     *
     * @param noteTitle the note title
     * @return the notes by title
     */
    @Select("Select * from Notes where noteTitle = #{noteTitle}")
    Notes getNotesByTitle(String noteTitle);

    /**
     * Insert note integer.
     *
     * @param notes the notes
     * @return the integer
     */
    @Insert("Insert into notes (noteTitle, noteDescription, userId) values (#{noteTitle}, #{noteDescription}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    Integer insertNote(Notes notes);
}
