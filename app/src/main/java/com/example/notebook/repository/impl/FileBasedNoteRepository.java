package com.example.notebook.repository.impl;

import android.content.Context;
import android.os.Build;
import androidx.annotation.RequiresApi;
import com.example.notebook.model.Note;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileBasedNoteRepository extends BaseRepository {

    private final Context context;

    public FileBasedNoteRepository(Context context) {
        this.context = context;
    }

    @Override
    public List<Note> getAll(boolean onStartup) {

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
        super.notes = notes;

        return super.getAll(onStartup);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void delete(int id) {
        super.delete(id);
        save();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public boolean addNote(Note note, int currentId) {
        super.addNote(note, currentId);
        save();
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public List<Note> findNotes(String noteName) {
        return super.findNotes(noteName);
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
