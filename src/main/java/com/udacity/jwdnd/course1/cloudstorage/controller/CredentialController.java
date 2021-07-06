package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.form.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.service.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    public String editCredential(CredentialForm credentialForm, Principal principal, Model model) {
        Integer userId = userService.getUserId(principal);
        Integer rowCountAffected = 0;
        if (credentialService.checkIfCrenentialExists(credentialForm, userId)) {
            model.addAttribute("errorMessage", "User already available");
            return "result";
        };
        if (credentialForm.getCredentialId() == null) {
            rowCountAffected = credentialService.addCredential(credentialForm, userId);
        } else {
            rowCountAffected = credentialService.update(credentialForm);
        }
        addChangesInfoToModel(rowCountAffected, model);
        return "result";
    }

    @GetMapping("/delete/{credentialid}")
    public String deleteCredential(@PathVariable(value = "credentialid") Integer credentialid, Model model) {
        Integer rowCountAffected = credentialService.delete(credentialid);
        addChangesInfoToModel(rowCountAffected, model);
        return "result";
    }

    private void addChangesInfoToModel(Integer rowCountAffected, Model model) {
        if (rowCountAffected == 0) {
            model.addAttribute("changesNotSaved", true);
        } else {
            model.addAttribute("changesSaved", true);
        };
    }
}
