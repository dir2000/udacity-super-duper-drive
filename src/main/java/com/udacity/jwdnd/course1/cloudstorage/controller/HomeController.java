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
public class HomeController {
    private UserService userService;
    private FileService fileService;

    public HomeController(UserService userService, FileService fileService) {
        this.userService = userService;
        this.fileService = fileService;
    }

    @GetMapping("/home")
    public String getHomePage(Model model, Principal principal) {
        //model.addAttribute("messages", this.messageListService.getMessages());
        populateModel(model, principal);
        return "home";
    }

    @PostMapping("/file-upload")
    public String uploadFile(@RequestParam MultipartFile fileUpload, Model model, Principal principal) throws IOException {
        int userId = userService.getUserId(principal);
        File file = new File(null, fileUpload.getOriginalFilename(), fileUpload.getContentType(),
                String.valueOf(fileUpload.getSize()), userId, fileUpload.getBytes());
        fileService.addFile(file);
        populateModel(model, principal);
        return "home";
    }

    private void populateModel(Model model, Principal principal) {
        int userId = userService.getUserId(principal);
        model.addAttribute("files", fileService.getUserFiles(userId));
    }
}
