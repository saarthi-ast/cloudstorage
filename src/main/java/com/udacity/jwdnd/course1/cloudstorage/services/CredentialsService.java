package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialsMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

import static com.udacity.jwdnd.course1.cloudstorage.constants.ApplicationConstants.*;

/**
 * The type Credentials service.
 * @author Sudhir Tyagi
 */
@Service
public class CredentialsService {
    private final CredentialsMapper credentialsMapper;
    private final UserService userService;
    private final EncryptionService encryptionService;

    /**
     * Class constructor.
     *
     * @param credentialsMapper the credentials mapper
     * @param userService       the user service
     * @param encryptionService the encryption service
     */
    public CredentialsService(CredentialsMapper credentialsMapper, UserService userService, EncryptionService encryptionService) {
        this.credentialsMapper = credentialsMapper;
        this.userService = userService;
        this.encryptionService = encryptionService;
    }

    /**
     * Find credentials by username list.
     *
     * @param username the username
     * @return the list
     */
// Select All credentials for a user
    public List<Credentials> findCredentialsByUsername(String username) {
        try {
            Integer userId = userService.getUseridByName(username);
            return credentialsMapper.findCredentialsByUserId(userId);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Find credentials by id and user id credentials.
     *
     * @param credentialId the credential id
     * @param userId       the user id
     * @return the credentials
     */
// Select credentials based on credentialId and userId
    public Credentials findCredentialsByIdAndUserId(Integer credentialId, Integer userId) {
        return credentialsMapper.findCredentialsByIdAndUserId(credentialId, userId);
    }

    /**
     * Delete credentials by id and user id string.
     *
     * @param credentialId the credential id
     * @param username     the username
     * @return the string
     */
// Delete credentials based on credentialId and userId
    public String deleteCredentialsByIdAndUserId(Integer credentialId, String username) {
        try {
            Integer userId = userService.getUseridByName(username);
            Integer deleteCt = credentialsMapper.deleteCredentialsByIdAndUserId(credentialId, userId);
            if(deleteCt == 0){
                return CREDENTIAL_ZERO_DELETE;
            }
        } catch (Exception e) {
            return CREDENTIAL_DELETE_ERROR;
        }
        return SUCCESS;
    }

    /**
     * Is duplicate credential boolean.
     *
     * @param url      the url
     * @param username the username
     * @param userId   the user id
     * @return the boolean
     */
// Check if a credential is already saved for the url and username
    public Boolean isDuplicateCredential(String url, String username, Integer userId) {
        return (null != credentialsMapper.findCredentialsByUrlAndUsernameAndUserId(url, username, userId));
    }

    /**
     * Update credentials by credential id and user id string.
     *
     * @param credentials the credentials
     * @param username    the username
     * @return the string
     */
// Update credentials for a credentialId and userId
    public String updateCredentialsByCredentialIdAndUserId(Credentials credentials, String username) {
        try {
            Integer userId = userService.getUseridByName(username);
            Credentials existingCred = findCredentialsByIdAndUserId(credentials.getCredentialId(), userId);
            if (existingCred == null) {
                return CREDENTIAL_NOT_FOUND;
            }
            if(!existingCred.getUsername().equals(credentials.getUsername())
                    && isDuplicateCredential(credentials.getUrl(),credentials.getUsername(),userId)){
                return DUPLICATE_CREDENTIAL_ERROR;
            }
            String encodedKey = getEncodedKey();
            String encryptedPassword = encryptionService.encryptValue(credentials.getPassword(), encodedKey);
            credentials.setKey(encodedKey);
            credentials.setUserId(userId);
            credentials.setPassword(encryptedPassword);
            Integer updateCount = credentialsMapper.updateCredentialsByCredentialIdAndUserId(credentials);
            if (updateCount <= 0) {
                return CREDENTIAL_SAVE_ERROR;
            }
        } catch (Exception e) {
            return CREDENTIAL_SAVE_ERROR;
        }
        return SUCCESS;
    }

    /**
     * Find credentials by url and user id credentials.
     *
     * @param url    the url
     * @param userId the user id
     * @return the credentials
     */
// Select credentials based on url and userId
    public Credentials findCredentialsByUrlAndUserId(String url, Integer userId) {
        return credentialsMapper.findCredentialsByUrlAndUserId(url, userId);
    }

    /**
     * Insert credentials string.
     *
     * @param credentials the credentials
     * @param username    the username
     * @return the string
     */
// Insert credentials
    public String insertCredentials(Credentials credentials, String username) {
        try {
            Integer userId = userService.getUseridByName(username);
            if (isDuplicateCredential(credentials.getUrl(), credentials.getUsername(), userId)) {
                return DUPLICATE_CREDENTIAL_ERROR;
            }
            credentials.setUserId(userId);
            String encodedKey = getEncodedKey();
            String encryptedPassword = encryptionService.encryptValue(credentials.getPassword(), encodedKey);
            credentials.setKey(encodedKey);
            credentials.setPassword(encryptedPassword);
            Integer credId = credentialsMapper.insertCredentials(credentials);
            if (credId <= 0) {
                return CREDENTIAL_SAVE_ERROR;
            }
        } catch (Exception e) {
            return CREDENTIAL_SAVE_ERROR;
        }

        return SUCCESS;
    }

    private String getEncodedKey() {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        return Base64.getEncoder().encodeToString(key);
    }
}
