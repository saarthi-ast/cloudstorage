package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialsMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

import static com.udacity.jwdnd.course1.cloudstorage.constants.ApplicationConstants.*;

@Service
public class CredentialsService {
    private CredentialsMapper credentialsMapper;
    private UserService userService;
    private EncryptionService encryptionService;

    public CredentialsService(CredentialsMapper credentialsMapper, UserService userService, EncryptionService encryptionService) {
        this.credentialsMapper = credentialsMapper;
        this.userService = userService;
        this.encryptionService = encryptionService;
    }

    // Select All credentials for a user
    public List<Credentials> findCredentialsByUsername(String username) {
        try {
            Integer userId = userService.getUseridByName(username);
            List<Credentials> credentialsList = credentialsMapper.findCredentialsByUserId(userId);
            return credentialsList;
        } catch (Exception e) {
            return null;
        }
    }

    // Select credentials based on credentialId and userId
    public Credentials findCredentialsByIdAndUserId(Integer credentialId, Integer userId) {
        return credentialsMapper.findCredentialsByIdAndUserId(credentialId, userId);
    }

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

    // Check if a credential is already saved for the url and username
    public Boolean isDuplicateCredential(String url, String username, Integer userId) {
        return (null != credentialsMapper.findCredentialsByUrlAndUsernameAndUserId(url, username, userId));
    }

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

    // Select credentials based on url and userId
    public Credentials findCredentialsByUrlAndUserId(String url, Integer userId) {
        return credentialsMapper.findCredentialsByUrlAndUserId(url, userId);
    }

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
