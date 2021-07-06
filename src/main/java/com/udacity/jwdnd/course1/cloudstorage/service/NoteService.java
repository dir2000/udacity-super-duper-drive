package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.form.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {
    private NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public Integer addNote(NoteForm noteForm, Integer userId) {
        Note note = new Note(null, noteForm.getNoteTitle(), noteForm.getNoteDescription(), userId);
        return noteMapper.insert(note);
    }

    public List<Note> getNotes(Integer userId) {
        return noteMapper.getNotes(userId);
    }

    public Integer delete(Integer noteid) {
        return noteMapper.delete(noteid);
    }

    public Integer update(NoteForm noteForm) {
        return noteMapper.update(noteForm);
    }
}
