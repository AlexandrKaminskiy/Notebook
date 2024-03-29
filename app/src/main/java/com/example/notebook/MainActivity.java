package com.example.notebook;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import com.example.notebook.adapter.NoteAdapter;
import com.example.notebook.listener.TextChangeListener;
import com.example.notebook.model.Note;
import com.example.notebook.repository.NoteRepository;
import com.example.notebook.repository.impl.FileBasedNoteRepository;
import com.example.notebook.repository.impl.SqlRepository;
import com.example.notebook.service.NoteService;
import com.google.android.material.textfield.TextInputEditText;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private Button addButton;
    private NoteService noteService;
    private TextInputEditText findTextInput;
    private FileBasedNoteRepository noteRepository;
    private SqlRepository noteSqlRepository;

    public MainActivity() {
        noteService = NoteService.getInstance();
        noteSqlRepository = new SqlRepository();
        noteRepository = new FileBasedNoteRepository(noteRepository);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listView);
        addButton = findViewById(R.id.addButton);
        findTextInput = findViewById(R.id.findTextInput);
        noteRepository.setContext(getBaseContext());
        noteSqlRepository.setDb(getBaseContext());
        noteService.setNoteRepository(noteSqlRepository);
        List<Note> list = noteService.getAll();
        ArrayAdapter<Note> adapter = new NoteAdapter(this,
                android.R.layout.simple_list_item_1, list);

        findTextInput.addTextChangedListener(new TextChangeListener(noteService, adapter));

        listView.setAdapter(adapter);
        noteService.setAdapter(adapter);

        listView.setOnItemClickListener((parent, itemClicked, position, id) -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("currentNote", adapter.getItem(position));
            Intent intent = new Intent(this, SaveUpdateActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        });

        addButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, SaveUpdateActivity.class);
            startActivity(intent);
        });
    }
}
