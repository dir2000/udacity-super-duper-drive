package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.form.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.form.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {
    private CredentialMapper credentialMapper;
    private EncryptionService encryptionService;

    public CredentialService(CredentialMapper credentialMapper) {
        this.credentialMapper = credentialMapper;
    }

    public void addCredential(CredentialForm credentialForm, Integer userId) {
        SecureRandom random = new SecureRandom();
        byte[] byteKey = new byte[32];
        random.nextBytes(byteKey);
        String key = Base64.getEncoder().encodeToString(byteKey);
        String encryptedPassword = encryptionService.encryptValue(credentialForm.getPassword(), key);
        Credential credential = new Credential(null, credentialForm.getUrl(), credentialForm.getUserName(),
                key, encryptedPassword, userId);
        credentialMapper.insert(credential);
    }

    public List<Credential> getCredentials(Integer userId) {
        return credentialMapper.getCredentials(userId);
    }

    public void delete(Integer credentialId) {
        credentialMapper.delete(credentialId);
    }

    public void update(CredentialForm credentialForm) {
        credentialMapper.update(credentialForm);
    }
}
