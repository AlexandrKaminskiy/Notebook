package com.example.notebook.repository.impl;

import android.os.Build;
import androidx.annotation.RequiresApi;
import com.example.notebook.model.Note;
import com.example.notebook.repository.NoteRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BaseRepository implements NoteRepository {

    protected List<Note> notes;
    private static int id;

    public BaseRepository() {
        this.notes = new ArrayList<>();
    }

    @Override
    public List<Note> getAll(boolean onStartup) {
        return notes;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void delete(int id) {
        notes.removeIf((note -> note.getId() == id));
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public boolean addNote(Note note, int currentId) {

        if (currentId == -1) {
            note.setId(++id);
            notes.add(note);
        } else {
            notes.stream()
                    .filter(n -> n.getId() == currentId)
                    .forEach(n -> {
                        n.setName(note.getName());
                        n.setDescription(note.getDescription());
                        n.setEventTime(note.getEventTime());
                    });
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
