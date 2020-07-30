package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Files;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FilesMapper {
    //save file
    @Insert("Insert into Files (filename, contentType, fileSize, userId,fileData) values (" +
            "#{filename},#{contentType},#{fileSize},#{userId},#{fileData,jdbcType=NULL})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    Integer saveFile(Files file);

    //get file by name
    @Select("Select * from Files where filename=#{filename}")
    Files getFileByName(String filename);

    //get files for a user
    @Select("Select * from Files where userId=#{userId}")
    List<Files> getFilesByUserId(Integer userId);

    //delete file
    @Delete("Delete from Files where filename=#{filename} and userId=#{userId}")
    Integer deleteFileByNameAndUserId(String filename, Integer userId);

    //get file by name and userid
    @Select("Select * from Files where filename=#{filename} and userId=#{userId}")
    Files getFileByNameAndUserId(String filename, Integer userId);
}
