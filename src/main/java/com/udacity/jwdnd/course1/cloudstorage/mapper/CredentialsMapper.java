package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialsMapper {

    // Select All credentials for a user
    @Select("Select * from Credentials where userId=#{userId}")
    List<Credentials> findCredentialsByUserId(Integer userId);

    // Select credentials based on credentialId and userId
    @Select("Select * from Credentials where credentialId=#{credentialId} and userId=#{userId}")
    Credentials findCredentialsByIdAndUserId(Integer credentialId, Integer userId);

    // Delete credentials based on credentialId and userId
    @Delete("Delete from Credentials where credentialId=#{credentialId} and  userId=#{userId}")
    Integer deleteCredentialsByIdAndUserId(Integer credentialId, Integer userId);

    // Update credentials for a credentialId and userId
    @Update("Update Credentials set url=#{url}, username=#{username}, key=#{key}, password=#{password}" +
            " where credentialId=#{credentialId} and userId=#{userId}")
    Integer updateCredentialsByCredentialIdAndUserId(Credentials credentials);

    // Select credentials based on url and userId
    @Select("Select * from Credentials where url=#{url} and userId=#{userId}")
    Credentials findCredentialsByUrlAndUserId(String url, Integer userId);

    // Insert credentials
    @Insert("Insert into Credentials (url, username, key, password, userId) " +
            "values (#{url}, #{username}, #{key}, #{password}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    Integer insertCredentials(Credentials credentials);
}
