package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.form.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.service.FileService;
import com.udacity.jwdnd.course1.cloudstorage.service.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/home")
public class HomeController {
    private UserService userService;
    private FileService fileService;
    private NoteService noteService;
    //private Cred

    public HomeController(UserService userService, FileService fileService, NoteService noteService) {
        this.userService = userService;
        this.fileService = fileService;
        this.noteService = noteService;
    }

    @GetMapping
    public String getHomePage(NoteForm NoteForm, Model model, Principal principal) {
        Integer userId = userService.getUserId(principal);
        model.addAttribute("files", fileService.getFiles(userId));
        model.addAttribute("notes", noteService.getNotes(userId));
        return "home";
    }
}
