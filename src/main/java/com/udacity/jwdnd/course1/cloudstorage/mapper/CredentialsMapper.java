package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * The interface Credentials mapper.
 * @author Sudhir Tyagi
 */
@Mapper
public interface CredentialsMapper {

    /**
     * Find credentials by user id list.
     *
     * @param userId the user id
     * @return the list
     */
    @Select("Select * from Credentials where userId=#{userId}")
    List<Credentials> findCredentialsByUserId(Integer userId);

    /**
     * Find credentials by id and user id credentials.
     *
     * @param credentialId the credential id
     * @param userId       the user id
     * @return the credentials
     */
    @Select("Select * from Credentials where credentialId=#{credentialId} and userId=#{userId}")
    Credentials findCredentialsByIdAndUserId(Integer credentialId, Integer userId);

    /**
     * Delete credentials by id and user id integer.
     *
     * @param credentialId the credential id
     * @param userId       the user id
     * @return the integer
     */
    @Delete("Delete from Credentials where credentialId=#{credentialId} and  userId=#{userId}")
    Integer deleteCredentialsByIdAndUserId(Integer credentialId, Integer userId);

    /**
     * Update credentials by credential id and user id integer.
     *
     * @param credentials the credentials
     * @return the integer
     */
    @Update("Update Credentials set url=#{url}, username=#{username}, key=#{key}, password=#{password}" +
            " where credentialId=#{credentialId} and userId=#{userId}")
    Integer updateCredentialsByCredentialIdAndUserId(Credentials credentials);

    /**
     * Find credentials by url and user id credentials.
     *
     * @param url    the url
     * @param userId the user id
     * @return the credentials
     */
    @Select("Select * from Credentials where url=#{url} and userId=#{userId}")
    Credentials findCredentialsByUrlAndUserId(String url, Integer userId);

    /**
     * Find credentials by url and username and user id credentials.
     *
     * @param url      the url
     * @param username the username
     * @param userId   the user id
     * @return the credentials
     */
    @Select("Select * from Credentials where url=#{url} and username=#{username} and userId=#{userId}")
    Credentials findCredentialsByUrlAndUsernameAndUserId(String url,String username, Integer userId);

    /**
     * Insert credentials integer.
     *
     * @param credentials the credentials
     * @return the integer
     */
    @Insert("Insert into Credentials (url, username, key, password, userId) " +
            "values (#{url}, #{username}, #{key}, #{password}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    Integer insertCredentials(Credentials credentials);
}
