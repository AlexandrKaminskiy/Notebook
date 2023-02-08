package com.example.notebook.repository.impl;

import com.example.notebook.model.Note;
import com.example.notebook.repository.NoteRepository;

import java.io.*;
import java.util.List;

public class FileBasedNoteRepository implements NoteRepository {

    @Override
    public List<Note> getAll() {
        return null;
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public boolean addNote(Note note, int currentId) {

        try (FileOutputStream outputStream = new FileOutputStream("notes", true);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream)) {
            objectOutputStream.writeObject(note);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public List<Note> findNotes(String noteName) {
        return null;
    }
}
