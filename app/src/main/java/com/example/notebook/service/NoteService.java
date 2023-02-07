package com.example.notebook.service;

import android.widget.ArrayAdapter;
import com.example.notebook.model.Note;
import com.example.notebook.repository.NoteRepository;
import com.example.notebook.repository.impl.BaseRepository;

import java.util.ArrayList;
import java.util.List;


public class NoteService {
    private NoteRepository noteRepository;
    private ArrayAdapter<Note> adapter;
    private static NoteService noteService;

    private NoteService() {
        noteRepository = BaseRepository.getInstance();
    }

    public static NoteService getInstance() {
        if (noteService != null) {
            return noteService;
        }
        noteService = new NoteService();
        return noteService;
    }

    public void setAdapter(ArrayAdapter<Note> adapter) {
        this.adapter = adapter;
    }

    public List<Note> getAll() {
        return new ArrayList<>(noteRepository.getAll());
    }

    public void delete(int id) {
        noteRepository.delete(id);
        adapter.notifyDataSetChanged();
    }

    public boolean addNote(Note note, Note currentNote) {
        noteRepository.addNote(note, currentNote);
        adapter.notifyDataSetChanged();
        return true;
    }

    public List<Note> findNotes(String noteName) {
        return noteRepository.findNotes(noteName);
    }
}
