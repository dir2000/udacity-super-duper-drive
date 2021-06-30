package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.form.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.service.ErrorService;
import com.udacity.jwdnd.course1.cloudstorage.service.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/notes")
public class NoteController {
    private UserService userService;
    private NoteService noteService;

    public NoteController(UserService userService, NoteService noteService) {
        this.userService = userService;
        this.noteService = noteService;
    }

    @PostMapping("/edit")
    public String editNote(NoteForm noteForm, Principal principal) {
        Integer userId = userService.getUserId(principal);
        if (noteForm.getNoteid() == null) {
            noteService.addNote(noteForm, userId);
        } else {
            noteService.update(noteForm);
        }
        return "redirect:/home";
    }

    @GetMapping("/delete/{noteid}")
    public String deleteNote(@PathVariable(value = "noteid") Integer noteid) {
        noteService.delete(noteid);
        return "redirect:/home";
    }
}
