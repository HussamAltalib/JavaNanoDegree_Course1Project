package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.FileRecord;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

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
                             RedirectAttributes redirectAttributes)throws IOException {
            System.out.println("in the uploadfile method");
            int userId = userService.getUserId(authentication.getName());
            FileRecord fileRecord = new FileRecord();
            fileRecord.setFileName(fileUpload.getOriginalFilename());
            fileRecord.setContentType(fileUpload.getContentType());
            fileRecord.setFileSize(String.valueOf(fileUpload.getSize()));
            fileRecord.setUserId(userId);
            fileRecord.setFileData(fileUpload.getBytes());

            fileService.addFile(fileRecord);
            redirectAttributes.addFlashAttribute("message", "File uploaded successfully!");

        return "redirect:/files";
    }

    // Endpoint to delete a file
    @GetMapping("/delete/{fileId}")
    public String deleteFile(@PathVariable Integer fileId, RedirectAttributes redirectAttributes) {
        fileService.removeFile(fileId);
        redirectAttributes.addFlashAttribute("message", "File deleted successfully!");
        return "redirect:/files";
    }

//    @GetMapping("/{fileId}")
//    public ResponseEntity<Resource> viewFile(@PathVariable String fileId) {
//        try {
//            // Assuming files are stored in a directory and fileId corresponds to the filename
//            Path fileLocation = Paths.get("path/to/your/files/directory", fileId);
//            Resource resource = new UrlResource(fileLocation.toUri());
//
//            if (resource.exists() || resource.isReadable()) {
//                return ResponseEntity.ok()
//                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
//                        .body(resource);
//            } else {
//                return ResponseEntity.notFound().build();
//            }
//        } catch (Exception e) {
//            return ResponseEntity.internalServerError().build();
//        }
//    }
}
