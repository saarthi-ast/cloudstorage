package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Files;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * The interface Files mapper.
 * @author Sudhir Tyagi
 */
@Mapper
public interface FilesMapper {
    /**
     * Save file integer.
     *
     * @param file the file
     * @return the integer
     */
    @Insert("Insert into Files (filename, contentType, fileSize, userId,fileData) values (" +
            "#{filename},#{contentType},#{fileSize},#{userId},#{fileData,jdbcType=NULL})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    Integer saveFile(Files file);

    /**
     * Gets file by name.
     *
     * @param filename the filename
     * @return the file by name
     */
    @Select("Select * from Files where filename=#{filename}")
    Files getFileByName(String filename);

    /**
     * Gets files by user id.
     *
     * @param userId the user id
     * @return the files by user id
     */
    @Select("Select * from Files where userId=#{userId}")
    List<Files> getFilesByUserId(Integer userId);

    /**
     * Delete file by name and user id integer.
     *
     * @param filename the filename
     * @param userId   the user id
     * @return the integer
     */
    @Delete("Delete from Files where filename=#{filename} and userId=#{userId}")
    Integer deleteFileByNameAndUserId(String filename, Integer userId);

    /**
     * Gets file by name and user id.
     *
     * @param filename the filename
     * @param userId   the user id
     * @return the file by name and user id
     */
    @Select("Select * from Files where filename=#{filename} and userId=#{userId}")
    Files getFileByNameAndUserId(String filename, Integer userId);
}
