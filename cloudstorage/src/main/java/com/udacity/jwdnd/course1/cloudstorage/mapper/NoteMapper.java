package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {

    @Select("SELECT * FROM NOTES WHERE userid = #{userId}")
    List<Note> getAllNotes(int UserId);

    @Insert("INSERT INTO NOTES (noteTitle, noteDescription, userId) VALUES(#{noteTitle}, #{noteDescription}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    int insertNote(Note note);

    @Delete("DELETE FROM NOTES WHERE noteid = #{noteid}")
    void deleteNoteById(int noteId);

    @Select("SELECT * FROM NOTES WHERE noteid= #{noteid}")
    Note getNoteById(int noteId);

    @Update("UPDATE NOTES SET notetitle = #{noteTitle}, notedescription = #{noteDescription} WHERE noteid = #{noteId}")
    void updateNote(Note note);



}
