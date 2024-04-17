package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.FileRecord;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import org.springframework.core.io.Resource;
import java.io.IOException;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Controller
@RequestMapping("/files")
public class FileController {

    private final FileService fileService;
    private final UserService userService;

    @Autowired
    public FileController(FileService fileService, UserService userService) {
        this.fileService = fileService;
        this.userService = userService;
    }
    @RequestMapping
    public String showFiles(Authentication authentication, Model model){
        int userId = userService.getUserId(authentication.getName());
        model.addAttribute("files", this.fileService.getAllFiles(userId));
        return "redirect:/home";
    }

    // Endpoint to upload a file
    @PostMapping("/upload")
    public String uploadFile(@RequestParam("fileUpload") MultipartFile fileUpload,
                             Authentication authentication,
                             RedirectAttributes redirectAttributes) {

        int userId = userService.getUserId(authentication.getName());

        try {
            fileService.uploadFile(fileUpload, userId);
            redirectAttributes.addFlashAttribute("message", "File uploaded successfully!");
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }

        return "redirect:/files";
    }


    // Endpoint to delete a file
    @GetMapping("/delete/{fileId}")
    public String deleteFile(@PathVariable Integer fileId, RedirectAttributes redirectAttributes) {
        fileService.removeFile(fileId);
        redirectAttributes.addFlashAttribute("message", "File deleted successfully!");
        return "redirect:/files";
    }


    @GetMapping("/download/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable int fileId) throws Exception{
        FileRecord attachment = null;
        attachment = fileService.getAttachment(fileId);
        return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(attachment.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=\"" + attachment.getFileName()
                                + "\"")
                    .body(new ByteArrayResource(attachment.getFileData()));
    }

}
