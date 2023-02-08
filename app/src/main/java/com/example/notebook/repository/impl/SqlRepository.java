package com.example.notebook.repository.impl;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import androidx.annotation.RequiresApi;
import com.example.notebook.model.Note;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class SqlRepository extends BaseRepository {

    private SQLiteDatabase db;

    public SqlRepository(Context context) {
        db = context.openOrCreateDatabase("notebookdb.db", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS notes " +
                "(id INTEGER PRIMARY KEY, " +
                "name TEXT, " +
                "description TEXT, " +
                "eventTime DATE)");

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public List<Note> getAll(boolean onStartup) {
        List<Note> notes = new ArrayList<>();
        Cursor query = db.rawQuery("SELECT * FROM notes;", null);
        while (query.moveToNext()) {
            int id = Integer.parseInt(query.getString(0));
            String name = query.getString(1);
            String description = query.getString(2);
            LocalDate date;
            try {
                date = LocalDate.parse(query.getString(3));
            } catch (Exception e) {
                date = null;
            }
            Note note = new Note(name, description, date);
            note.setId(id);
            notes.add(note);
        }
        super.notes = notes;
        return super.getAll(onStartup);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void delete(int id) {
        db.execSQL("DELETE FROM notes WHERE id = ?;", new Object[]{id});
        super.delete(id);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public boolean addNote(Note note, int currentId) {
        if (currentId != -1) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("name", note.getName());
            contentValues.put("description", note.getDescription());
            contentValues.put("eventTime", String.valueOf(note.getEventTime()));
            db.update("notes", contentValues, "id=" + currentId, null);
        } else {
            ContentValues contentValues = new ContentValues();
            contentValues.put("name", note.getName());
            contentValues.put("description", note.getDescription());
            contentValues.put("eventTime", String.valueOf(note.getEventTime()));
            currentId = (int) db.insert("notes", "id", contentValues);
        }
        return super.addNote(note, currentId);
    }

    @SuppressLint("NewApi")
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public List<Note> findNotes(String noteName) {
        return super.findNotes(noteName);
    }
}