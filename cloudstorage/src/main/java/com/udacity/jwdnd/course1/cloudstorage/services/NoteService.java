package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {
    private NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public void addNote(NoteForm noteForm){
        Note newNote = new Note(null, noteForm.getNoteTitle(), noteForm.getNoteDescription(), noteForm.getUserId());
        noteMapper.insertNote(newNote);
    }

    public List<Note> getAllNotes() {
        return noteMapper.getAllNotes();
    }

    public void deleteNoteById(int noteId){
        noteMapper.deleteNoteById(noteId);
    }
}
