package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.form.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.form.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.*;

@Service
public class CredentialService {
    private CredentialMapper credentialMapper;
    private EncryptionService encryptionService;

    public CredentialService(CredentialMapper credentialMapper, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
    }

    public void addCredential(CredentialForm credentialForm, Integer userId) {
        SecureRandom random = new SecureRandom();
        byte[] byteKey = new byte[16];
        random.nextBytes(byteKey);
        String key = Base64.getEncoder().encodeToString(byteKey);
        String encryptedPassword = encryptionService.encryptValue(credentialForm.getPassword(), key);
        Credential credential = new Credential(null, credentialForm.getUrl(), credentialForm.getUserName(),
                key, encryptedPassword, userId);
        credentialMapper.insert(credential);
    }

    public List<Map<String, String>> getCredentials(Integer userId) {
        List<Credential> credentials = credentialMapper.getCredentials(userId);
        List<Map<String, String>> result = new LinkedList<>();
        for (Credential credential:credentials) {
            Map<String, String> map = new HashMap<>();
            map.put("credentialId", String.valueOf(credential.getCredentialId()));
            map.put("url", credential.getUrl());
            map.put("userName", credential.getUserName());
            String password = encryptionService.decryptValue(credential.getPassword(), credential.getKey());
            map.put("password", password);
            map.put("encryptedPassword", credential.getPassword());
            result.add(map);
        }
        return result;
    }

    public void delete(Integer credentialId) {
        credentialMapper.delete(credentialId);
    }

    public void update(CredentialForm credentialForm) {
        Credential old = credentialMapper.getCredential(credentialForm.getCredentialId());
        String encryptedPassword = encryptionService.encryptValue(credentialForm.getPassword(), old.getKey());
        credentialForm.setPassword(encryptedPassword);
        credentialMapper.update(credentialForm);
    }
}
