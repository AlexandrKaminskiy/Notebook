package com.example.notebook.repository.impl;

import android.os.Build;
import com.example.notebook.model.Note;
import com.example.notebook.repository.NoteRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BaseRepository implements NoteRepository {

    private static BaseRepository baseRepository;
    private List<Note> notes;
    private int id;

    private BaseRepository() {
        notes = new ArrayList<>();
    }

    public static BaseRepository getInstance() {
        if (baseRepository != null) {
            return baseRepository;
        }
        return new BaseRepository();
    }


    @Override
    public List<Note> getAll() {
        return new ArrayList<>(notes);
    }

    @Override
    public void delete(int id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            notes.removeIf((note -> note.getId() == id));
        }
    }

    @Override
    public boolean addNote(Note note) {
        note.setId(id++);
        notes.add(note);
        return true;
    }

    @Override
    public List<Note> findNote(String noteName) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return notes.stream()
                    .filter(note -> note.getName().equals(noteName))
                    .collect(Collectors.toList());
        }
        return null;
    }
}
