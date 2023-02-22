package com.example.notebook.repository.impl;

import android.content.Context;
import android.os.Build;
import androidx.annotation.RequiresApi;
import com.example.notebook.model.Note;
import com.example.notebook.repository.NoteRepository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileBasedNoteRepository extends BaseRepository {

    private final NoteRepository noteRepository;

    public FileBasedNoteRepository(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    @Override
    public List<Note> getAll() {

        List<Note> notes = new ArrayList<>();

        try (FileInputStream inputStream = context.openFileInput("notes");
             ObjectInputStream objectInputStream = new ObjectInputStream(inputStream)) {
            Note note;

            while ((note = (Note) objectInputStream.readObject()) != null) {

                notes.add(note);
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }


        return noteRepository.getAll();
    }

    private Context context;

    public void setContext(Context context) {
        this.context = context;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void delete(int id) {
        noteRepository.delete(id);
        save();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public boolean addNote(Note note, int currentId) {
        noteRepository.addNote(note, currentId);
        save();
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public List<Note> findNotes(String noteName) {
        return noteRepository.findNotes(noteName);
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void save() {
        context.deleteFile("notes");
        try (FileOutputStream outputStream = context.openFileOutput("notes", Context.MODE_PRIVATE );
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream)) {

            notes.forEach(note -> {
                try {
                    objectOutputStream.writeObject(note);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
