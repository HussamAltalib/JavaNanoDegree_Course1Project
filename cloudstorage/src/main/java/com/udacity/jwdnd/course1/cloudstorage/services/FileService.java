package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.FileRecord;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

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

    public void addFile(FileRecord fileRecord) {

            fileMapper.insertFile(fileRecord);

    }


    public void removeFile(Integer fileId) {
        fileMapper.deleteFileById(fileId);
    }
}
