package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.form.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.service.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/credentials")
public class CredentialController {
    private UserService userService;
    private CredentialService credentialService;

    public CredentialController(UserService userService, CredentialService credentialService) {
        this.userService = userService;
        this.credentialService = credentialService;
    }

    @PostMapping("/edit")
    public String editCredential(CredentialForm credentialForm, Principal principal) {
        Integer userId = userService.getUserId(principal);
        if (credentialForm.getCredentialId() == null) {
            credentialService.addCredential(credentialForm, userId);
        } else {
            credentialService.update(credentialForm);
        }
        return "redirect:/home";
    }

    @GetMapping("/delete/{credentialid}")
    public String deleteCredential(@PathVariable(value = "credentialid") Integer credentialid) {
        credentialService.delete(credentialid);
        return "redirect:/home";
    }
}
