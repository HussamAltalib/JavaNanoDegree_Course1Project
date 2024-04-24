package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.apache.ibatis.annotations.Update;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @GetMapping("/deleteCredential/{credentialid}")
    public String deleteCredential(@PathVariable("credentialid") int credentialid, RedirectAttributes redirectAttributes) {
        credentialService.deleteCredentialById(credentialid);
        redirectAttributes.addFlashAttribute("successMessage", "Credential deleted successfully!");
        return "redirect:/home";
    }

    @GetMapping("/editCredential/{credentialId}")
    public String editCredential(@PathVariable("credentialId") int credentialId, Model model){
        Credential credential = credentialService.getCredentialById(credentialId);
        model.addAttribute("credentialForm", credential);
        return "editCredential";
    }

    @PostMapping("/updateCredential")
    public String updateCredential(@ModelAttribute("credentialForm") CredentialForm credentialForm, RedirectAttributes redirectAttributes){
        System.out.println("in update in the controller");
        credentialService.updateCredential(credentialForm);
        return "redirect:/credential";
    }


}
