package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.FileRecord;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.io.File;
import java.util.List;

@Mapper
public interface FileMapper {

    @Select("SELECT * FROM FILES WHERE userid = #{userId}")
    List<FileRecord> getAllFiles(int userId);

    @Select("SELECT * FROM FILES WHERE fileId= #{fileId}")
    FileRecord getFileById(int fileId);
    @Select("SELECT * FROM NOTES WHERE noteid= #{noteid}")
    Note getNoteById(int noteId);

    @Insert("INSERT INTO FILES (fileName, contentType, fileSize, userId, fileData) " +
            "VALUES (#{fileName}, #{contentType}, #{fileSize}, #{userId}, #{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    void insertFile(FileRecord fileRecord);

    @Delete("DELETE FROM FILES WHERE fileId = #{fileId}")
    void deleteFileById(int fileId);
}
