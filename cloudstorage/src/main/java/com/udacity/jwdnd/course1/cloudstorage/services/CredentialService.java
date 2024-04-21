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
        String encryptedPassword = encryptionService.encryptValue(credentialForm.getCredentialPassword(), encodedKey);
//        String decryptedPassword = encryptionService.decryptValue(encryptedPassword, encodedKey);

        Credential newCredential = new Credential(null, credentialForm.getCredentialUrl(), credentialForm.getCredentialUsername(), encodedKey, encryptedPassword, credentialForm.getUserId());
        System.out.println(newCredential);
        credentialMapper.insertCredential(newCredential);
    }

    public List<Credential> getAllCredentials(int userId) {
        List<Credential> credentials = credentialMapper.getAllCredentials(userId);
//        for(Credential credential : credentials){
//            String decryptedPassword = encryptionService.decryptValue(credential.getCredentialPassword(), credential.getKey());
//            credential.setCredentialPassword(decryptedPassword);
//        }
        return credentials;
    }
}
