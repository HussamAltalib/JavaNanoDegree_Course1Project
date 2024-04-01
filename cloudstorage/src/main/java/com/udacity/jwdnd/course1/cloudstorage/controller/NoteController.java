package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.*;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/note")
public class NoteController {
    private NoteService noteService;
    private UserService userService;


    public NoteController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }

    @GetMapping
    public String homeView(Authentication authentication, NoteForm note, Model model){
        int userId = userService.getUserId(authentication.getName());
        model.addAttribute("notes", this.noteService.getAllNotes(userId));
        return "home";
    }

    @PostMapping
    public String addNote(Authentication authentication, NoteForm noteForm, Model model) {
        System.out.println(userService.getUserId(authentication.getName()));
        int userId = userService.getUserId(authentication.getName());
        System.out.println(userId);

        if ((Integer)userId != null) {
            noteForm.setUserId(userId);

            noteService.addNote(noteForm);

            model.addAttribute("notes", this.noteService.getAllNotes(userId));
        } else {
            // Handle the case where user is not authenticated
            // For example, you can redirect the user to a login page
            return "redirect:/login";
        }

        return "home";
    }

    @GetMapping("/deleteNote/{noteId}")
    public String deleteNote(@PathVariable("noteId") int noteId, RedirectAttributes redirectAttributes) {
        System.out.println("test");
        System.out.println(noteId);
        System.out.println("test");
        noteService.deleteNoteById(noteId);
        redirectAttributes.addFlashAttribute("successMessage", "Note deleted successfully!");
        return "redirect:/note";
    }

    @GetMapping("/editNote/{noteId}")
    public String editNote(@PathVariable("noteId") int noteId, Model model) {
        Note note = noteService.getNoteById(noteId);
        model.addAttribute("noteForm", note);
        return "editNote";
    }

    @PostMapping("/updateNote")
    public String updateNote(@ModelAttribute("noteForm") NoteForm noteForm, RedirectAttributes redirectAttributes) {
        System.out.println("in controller before");
        noteService.updateNote(noteForm);
        System.out.println("in controller after");
        redirectAttributes.addFlashAttribute("successMessage", "Note updated successfully!");
        return "redirect:/note";
    }

}

