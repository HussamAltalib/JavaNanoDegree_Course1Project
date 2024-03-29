package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {

    NoteService noteService;

    public HomeController(NoteService noteService) {
        this.noteService = noteService;
    }
    @GetMapping
    public String homeView(NoteForm note, Model model){
        model.addAttribute("notes", this.noteService.getAllNotes());
        return "home";
    }

//    @GetMapping("/home")
//    public String getHomePage(Model model, Authentication authentication) {
//        Integer userId = getUserId(authentication);
//        model.addAttribute("notes", noteService.getAllNotesForUser(userId));
//        model.addAttribute("credentials", credentialService.getCredentialsForUser(userId));
//        model.addAttribute("files", fileService.getFilesForUser(userId));
//        return "home"; // The Thymeleaf template that displays the dashboard
//    }

}
