package com.example.notebook.listener;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import com.example.notebook.model.Note;
import com.example.notebook.service.NoteService;
import java.util.List;


public class TextChangeListener implements TextWatcher {

    private final NoteService noteService;
    private final ArrayAdapter<Note> adapter;

    public TextChangeListener(NoteService noteService, ArrayAdapter<Note> adapter) {
        this.noteService = noteService;
        this.adapter = adapter;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        List<Note> notes = noteService.findNotes(s.toString());
        adapter.clear();
        adapter.addAll(notes);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
