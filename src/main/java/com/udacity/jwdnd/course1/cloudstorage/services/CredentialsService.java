package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialsMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

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
        Integer userId = userService.getUseridByName(username);
        List<Credentials> credentialsList = credentialsMapper.findCredentialsByUserId(userId);
        for(Credentials cred:credentialsList){
            cred.setEditPwd(encryptionService.decryptValue(cred.getPassword(),cred.getKey()));
        }
        return credentialsList;
    }

    // Select credentials based on credentialId and userId
    public Credentials findCredentialsByIdAndUserId(Integer credentialId, Integer userId) {
        return credentialsMapper.findCredentialsByIdAndUserId(credentialId, userId);
    }

    // Delete credentials based on credentialId and userId
    public Integer deleteCredentialsByIdAndUserId(Integer credentialId, String username) {
        Integer userId = userService.getUseridByName(username);
        return credentialsMapper.deleteCredentialsByIdAndUserId(credentialId, userId);
    }

    // Update credentials for a credentialId and userId
    public Credentials updateCredentialsByCredentialIdAndUserId(Credentials credentials, String username) {
        Integer userId = userService.getUseridByName(username);
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(credentials.getPassword(),encodedKey);
        credentials.setKey(encodedKey);
        credentials.setUserId(userId);
        credentials.setPassword(encryptedPassword);
        return credentialsMapper.updateCredentialsByCredentialIdAndUserId(credentials);
    }

    // Select credentials based on url and userId
    public Credentials findCredentialsByUrlAndUserId(String url, Integer userId) {
        return credentialsMapper.findCredentialsByUrlAndUserId(url, userId);
    }

    // Insert credentials
    public Integer insertCredentials(Credentials credentials, String username) {
        Integer userId = userService.getUseridByName(username);
        credentials.setUserId(userId);
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(credentials.getPassword(),encodedKey);
        credentials.setKey(encodedKey);
        credentials.setPassword(encryptedPassword);
        return credentialsMapper.insertCredentials(credentials);
    }
}
