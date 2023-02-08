package com.example.notebook;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.notebook.model.Note;
import com.example.notebook.repository.NoteRepository;
import com.example.notebook.repository.impl.BaseRepository;
import com.example.notebook.service.NoteService;
import com.google.android.material.textfield.TextInputEditText;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class SaveUpdateActivity extends AppCompatActivity {

    private TextInputEditText noteNameEdit;
    private TextInputEditText noteDescriptionEdit;
    private DatePicker datePicker;
    private Button saveButton;
    private Button deleteButton;
    private NoteService noteService;

    public SaveUpdateActivity() {
        noteService = NoteService.getInstance();
    }

    @RequiresApi(api = 33)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_update);

        noteNameEdit = findViewById(R.id.nameTextInputEdit);
        noteDescriptionEdit = findViewById(R.id.descriptionTextInputEdit);
        datePicker = findViewById(R.id.datePicker);
        saveButton = findViewById(R.id.saveButton);
        deleteButton = findViewById(R.id.deleteButton);
        Note currentNote = (Note) getIntent().getSerializableExtra("currentNote");

        deleteButton.setOnClickListener(v -> {
            noteService.delete(currentNote.getId());
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });
        saveButton.setOnClickListener(v -> {
            int id = currentNote == null ? -1 : currentNote.getId();
            noteService.addNote(new Note(noteNameEdit.getText().toString(),
                    noteDescriptionEdit.getText().toString(), LocalDate.of(datePicker.getYear(),
                    datePicker.getMonth(),datePicker.getDayOfMonth())), id);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });
        if (currentNote != null) {
            noteNameEdit.setText(currentNote.getName());
            noteDescriptionEdit.setText(currentNote.getDescription());
            datePicker.updateDate(currentNote.getEventTime().getYear(),
                    currentNote.getEventTime().getMonthValue(),
                    currentNote.getEventTime().getDayOfMonth());
        } else {
            deleteButton.setEnabled(false);
        }

    }
}
