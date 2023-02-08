package com.example.notebook.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.notebook.model.Note;
import java.util.List;


public class NoteAdapter extends ArrayAdapter<Note> {
    public NoteAdapter(@NonNull Context context, int resource, List<Note> list) {
        super(context, resource, list);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Note note = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(android.R.layout.simple_list_item_1, null);
        }
        ((TextView) convertView.findViewById(android.R.id.text1))
                .setText(note.getName());

        return convertView;
    }
}
