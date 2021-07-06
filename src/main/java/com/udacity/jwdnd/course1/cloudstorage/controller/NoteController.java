package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.form.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.service.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    public String editNote(NoteForm noteForm, Principal principal, Model model) {
        Integer userId = userService.getUserId(principal);
        Integer rowCountAffected = 0;
        if (noteForm.getNoteid() == null) {
            rowCountAffected = noteService.addNote(noteForm, userId);
        } else {
            rowCountAffected = noteService.update(noteForm);
        }
        addChangesInfoToModel(rowCountAffected, model);
        return "result";
    }

    @GetMapping("/delete/{noteid}")
    public String deleteNote(@PathVariable(value = "noteid") Integer noteid, Model model) {
        Integer rowCountAffected = noteService.delete(noteid);
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
