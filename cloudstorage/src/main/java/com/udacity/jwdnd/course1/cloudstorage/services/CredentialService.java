package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import org.springframework.stereotype.Service;


import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {
    private CredentialMapper credentialMapper;
    private EncryptionService encryptionService;
    private UserService userService;

    public CredentialService(CredentialMapper credentialMapper, EncryptionService encryptionService, UserService userService) {
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
        this.userService = userService;
    }

    public void addCredential(CredentialForm credentialForm){
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(credentialForm.getPassword(), encodedKey);

        Credential newCredential = new Credential(null, credentialForm.getUrl(), credentialForm.getUsername(), encodedKey, encryptedPassword, credentialForm.getUserId());
        System.out.println(newCredential);
        credentialMapper.insertCredential(newCredential);
    }

    public List<Credential> getAllCredentials(int userId) {
        List<Credential> credentials = credentialMapper.getAllCredentials(userId);

        return credentials;
    }

    public void deleteCredentialById(int credentialId){
        credentialMapper.deleteCredentialById(credentialId);
    }


    public Credential getCredentialById(int credenttialId){
        Credential credential = credentialMapper.getCrednetialById(credenttialId);
        String decryptedPassword = encryptionService.decryptValue(credential.getPassword(), credential.getKey());
        credential.setPassword(decryptedPassword);
        return credential;
    }
     public void updateCredential(CredentialForm credentialForm){
        System.out.println("in update in the service");
        Credential credential = credentialMapper.getCrednetialById(credentialForm.getCredentialid());

        credential.setUrl(credentialForm.getUrl());
        credential.setUsername(credentialForm.getUsername());

        String encryptedPassword = encryptionService.encryptValue(credentialForm.getPassword(), credential.getKey());

        credential.setPassword(encryptedPassword);
        credential.setUserId(credentialForm.getUserId());

        credentialMapper.updateCredential(credential);
     }


}
