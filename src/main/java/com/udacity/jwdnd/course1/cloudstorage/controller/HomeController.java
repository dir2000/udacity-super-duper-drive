package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

@Controller
@RequestMapping("/home")
public class HomeController {
    private UserService userService;
    private FileService fileService;

    public HomeController(UserService userService, FileService fileService) {
        this.userService = userService;
        this.fileService = fileService;
    }

    @GetMapping
    public String getHomePage(Model model, Principal principal) {
        Integer userId = userService.getUserId(principal);
        model.addAttribute("files", fileService.getUserFiles(userId));
        return "home";
    }
}
