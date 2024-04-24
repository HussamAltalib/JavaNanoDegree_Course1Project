package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {

    FileService fileService;
    NoteService noteService;
    UserService userService;
    CredentialService credentialService;


    public HomeController(FileService fileService, NoteService noteService, UserService userService, CredentialService credentialService) {
        this.fileService = fileService;
        this.noteService = noteService;
        this.userService = userService;
        this.credentialService = credentialService;
    }
    @GetMapping
    public String homeView(Authentication authentication, NoteForm note, CredentialForm credentialForm, Model model){
        int userId = userService.getUserId(authentication.getName());
        model.addAttribute("files", this.fileService.getAllFiles(userId));
        model.addAttribute("notes", this.noteService.getAllNotes(userId));
        model.addAttribute("credentials", credentialService.getAllCredentials(userId));

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
