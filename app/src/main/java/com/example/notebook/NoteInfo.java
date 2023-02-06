package com.example.notebook;

import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class NoteInfo extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_info);
        String currentNote = getIntent().getExtras().getString("currentNote");
        textView = findViewById(R.id.textView2);
        textView.setText(currentNote);
    }
}