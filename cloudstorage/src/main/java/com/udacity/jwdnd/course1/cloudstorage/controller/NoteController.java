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
    public String homeView(NoteForm note, Model model){
        model.addAttribute("notes", this.noteService.getAllNotes());
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

            model.addAttribute("notes", this.noteService.getAllNotes());
        } else {
            // Handle the case where user is not authenticated
            // For example, you can redirect the user to a login page
            return "redirect:/login";
        }

        return "home";
    }

//    @PostMapping("/deleteNote/{id}")
//    public String deleteNote(@PathVariable int id ) {
//        System.out.println(" delete id "+id);
//        // Logic to delete the note from the database using NoteService
//        noteService.deleteNoteById(id);
//        return "redirect:/note"; // Redirect to the home page or any other appropriate page
//    }

//    @DeleteMapping("/deleteNote/{id}")
//    public String deleteProduct(@PathVariable("id") int id, RedirectAttributes redirectAttributes) {
//        noteService.deleteNoteById(id);
//        redirectAttributes.addFlashAttribute("successMessage", "Note deleted successfully");
//        return "redirect:/note";
//    }

//    @RequestMapping(value = "/deleteNote", method = RequestMethod.POST)
//    public String handleDeleteUser(@ModelAttribute("note") Note note) {
//        System.out.println(note.getNoteId());
//        System.out.println("test");
//        return "redirect:/note";
//    }

    @GetMapping("/deleteNote/{noteId}")
    public String deleteNote(@PathVariable("noteId") int noteId, RedirectAttributes redirectAttributes) {
        System.out.println("test");
        System.out.println(noteId);
        System.out.println("test");
        noteService.deleteNoteById(noteId);
        redirectAttributes.addFlashAttribute("successMessage", "Note deleted successfully!");
        return "redirect:/note";
    }
}
