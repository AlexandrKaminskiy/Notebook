package com.example.notebook.repository.impl;

import android.os.Build;
import androidx.annotation.RequiresApi;
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
        return notes;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void delete(int id) {
        notes.removeIf((note -> note.getId() == id));
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public boolean addNote(Note note, Note currentNote) {
        if (currentNote != null) {
            int id = currentNote.getId();
            notes.stream()
                    .filter(n -> n.getId() == currentNote.getId())
                    .forEach(n -> {
                        n.setName(note.getName());
                        n.setDescription(note.getDescription());
                        n.setEventTime(note.getEventTime());
                    });
            note.setId(id);
        } else {
            note.setId(id++);
            notes.add(note);
        }
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public List<Note> findNotes(String noteName) {

        return notes.stream()
                .filter(note -> note.getName().contains(noteName))
                .collect(Collectors.toList());

    }
}
