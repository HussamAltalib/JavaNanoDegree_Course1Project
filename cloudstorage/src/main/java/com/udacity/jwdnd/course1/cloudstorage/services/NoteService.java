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
        Note newNote = new Note(noteForm.getNoteId(), noteForm.getNoteTitle(), noteForm.getNoteDescription(), noteForm.getUserId());
        noteMapper.insertNote(newNote);
    }

    public List<Note> getAllNotes(int UserId) {
        return noteMapper.getAllNotes(UserId);
    }

    public void deleteNoteById(int noteId){
        noteMapper.deleteNoteById(noteId);
    }

    public void updateNote(NoteForm noteForm){
        System.out.println("in service before");
        Note newNote = new Note(noteForm.getNoteId(), noteForm.getNoteTitle(), noteForm.getNoteDescription(), noteForm.getUserId());
        noteMapper.updateNote(newNote);
    }

    public Note getNoteById(int noteId){
        return noteMapper.getNoteById(noteId);
    }
}
