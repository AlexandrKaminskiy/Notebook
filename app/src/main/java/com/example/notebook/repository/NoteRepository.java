package com.example.notebook.repository;

import com.example.notebook.model.Note;

import java.io.FileNotFoundException;
import java.util.List;

public interface NoteRepository {
    List<Note> getAll();
    void delete(int id);
    boolean addNote(Note note);
    List<Note> findNote(String note);
}
