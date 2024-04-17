package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.FileRecord;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;


@Service
public class FileService {
    private final FileMapper fileMapper;
    private final Path fileStorageLocation;

    @Autowired
    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
        String relativePath = "src/main/resources/static/files";
        this.fileStorageLocation = Paths.get(relativePath).toAbsolutePath().normalize();
        // Ensure the directory exists
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new RuntimeException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    public List<FileRecord> getAllFiles(int userId) {
        return fileMapper.getAllFiles(userId);
    }

    public void uploadFile(MultipartFile fileUpload, Integer userId) throws IOException {
        String filename = fileUpload.getOriginalFilename();

        if (isFilenameAvailable(filename, userId)) {
            FileRecord fileRecord = new FileRecord();
            fileRecord.setFileName(filename);
            fileRecord.setContentType(fileUpload.getContentType());
            fileRecord.setFileSize(fileUpload.getSize());
            fileRecord.setUserId(userId);
            fileRecord.setFileData(fileUpload.getBytes());

            fileMapper.insertFile(fileRecord);
        } else {
            throw new IOException("A file with the same name already exists.");
        }
    }

    public boolean isFilenameAvailable(String filename, Integer userId) {
        return fileMapper.countFilesByNameAndUserId(filename, userId) == 0;
    }



    public FileRecord getFileById(int fileId){
        return fileMapper.getFileById(fileId);
    }


    public void removeFile(Integer fileId) {
        fileMapper.deleteFileById(fileId);
    }


    public FileRecord getAttachment(int fileId) throws Exception {
        FileRecord fileRecord = fileMapper.getFileById(fileId);
        if (fileRecord == null) {
            throw new Exception("File not found with Id: " + fileId);
        }
        return fileRecord;
    }


}
