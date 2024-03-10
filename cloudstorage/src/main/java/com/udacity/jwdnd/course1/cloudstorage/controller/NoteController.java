package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.*;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.boot.Banner;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.security.core.context.SecurityContextHolder;

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
}
