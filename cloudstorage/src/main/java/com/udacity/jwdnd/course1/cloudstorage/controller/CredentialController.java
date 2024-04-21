package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/credential")
public class CredentialController {
    private CredentialService credentialService;
    private UserService userService;

    public CredentialController(CredentialService credentialService, UserService userService) {
        this.credentialService = credentialService;
        this.userService = userService;
    }

    @GetMapping
    public String homeView(Authentication authentication, CredentialForm credentialForm, Model model){
        int userId = userService.getUserId(authentication.getName());
        model.addAttribute("credentials", this.credentialService.getAllCredentials(userId));
        return "redirect:/home";
    }

    @PostMapping
    public String addCredential(Authentication authentication, CredentialForm credentialForm, Model model) {
        int userId = userService.getUserId(authentication.getName());
        System.out.println(userId);

        if ((Integer)userId != null) {
            credentialForm.setUserId(userId);

            credentialService.addCredential(credentialForm);

            model.addAttribute("credentials", this.credentialService.getAllCredentials(userId));
        } else {
            // Handle the case where user is not authenticated
            // For example, you can redirect the user to a login page
            return "redirect:/login";
        }

        return "redirect:/home";
    }
}
