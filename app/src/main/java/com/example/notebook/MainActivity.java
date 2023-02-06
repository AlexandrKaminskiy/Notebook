package com.example.notebook;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private ListView listView;
    private Button addButton;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button);
        listView = findViewById(R.id.listView);
        addButton = findViewById(R.id.addButton);


        button.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("currentNote", "hello from fst act");
            Intent intent = new Intent(this, SaveUpdateActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        });

        List<String> list = new ArrayList<>();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, itemClicked, position, id) -> Toast.makeText(getApplicationContext(), ((TextView) itemClicked).getText(),
                Toast.LENGTH_SHORT).show());

        addButton.setOnClickListener(v -> {
//            list.add("123");
            adapter.add("qwe");
            adapter.notifyDataSetChanged();
        });
    }


}