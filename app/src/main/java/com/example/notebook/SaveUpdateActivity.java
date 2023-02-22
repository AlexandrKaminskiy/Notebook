package com.example.notebook;

import android.annotation.SuppressLint;
import android.app.*;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;
import com.example.notebook.model.Note;
import com.example.notebook.service.NoteService;
import com.example.notebook.service.ReminderBroadcast;
import com.google.android.material.textfield.TextInputEditText;
import java.time.LocalDate;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Calendar;
import java.util.Locale;



public class SaveUpdateActivity extends AppCompatActivity {

    private TextInputEditText noteNameEdit;
    private TextInputEditText noteDescriptionEdit;
    private TextInputEditText datePicker;
    private Button saveButton;
    private Button deleteButton;
    private NoteService noteService;

    LocalDate dateAndTime = LocalDate.now();

    public SaveUpdateActivity() {
        noteService = NoteService.getInstance();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_update);

        noteNameEdit = findViewById(R.id.nameTextInputEdit);
        noteDescriptionEdit = findViewById(R.id.descriptionTextInputEdit);

        datePicker = findViewById(R.id.datePicker);
        saveButton = findViewById(R.id.saveButton);
        deleteButton = findViewById(R.id.deleteButton);
        datePicker.setText(dateAndTime.getYear() + "-" + dateAndTime.getMonthValue() + "-" + dateAndTime.getDayOfMonth());
        Note currentNote = (Note) getIntent().getSerializableExtra("currentNote");

        datePicker.setOnClickListener((e) -> setDate());

        deleteButton.setOnClickListener(v -> {
            noteService.delete(currentNote.getId());
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });
        saveButton.setOnClickListener(v -> {
//            createNotificationChannel();
            int id = currentNote == null ? -1 : currentNote.getId();
//            if (id == -1) {
//                createNotification();
//            }
            noteService.addNote(new Note(noteNameEdit.getText().toString(),
                    noteDescriptionEdit.getText().toString(), dateAndTime), id);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });
        if (currentNote != null) {
            dateAndTime = currentNote.getEventTime();
            noteNameEdit.setText(currentNote.getName());
            noteDescriptionEdit.setText(currentNote.getDescription());
            datePicker.setText(currentNote.getEventTime().getYear() + "-" +
                    currentNote.getEventTime().getMonthValue() + "-" +
                    currentNote.getEventTime().getDayOfMonth());
        } else {
            deleteButton.setEnabled(false);
        }
    }

    private void createNotification() {
        Intent intent = new Intent(SaveUpdateActivity.this, ReminderBroadcast.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(SaveUpdateActivity.this, 0, intent, 0);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        long tenSecMillis = 10000;
        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + tenSecMillis, pendingIntent);
    }

    public void setDate() {
        Calendar calendar = Calendar.getInstance();
        new DatePickerDialog(SaveUpdateActivity.this, d,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    @SuppressLint("SetTextI18n")
    DatePickerDialog.OnDateSetListener d = (view, year, monthOfYear, dayOfMonth) -> {

        dateAndTime = LocalDate.of(year, monthOfYear, dayOfMonth);

        datePicker.setText(year + "-" + monthOfYear + "-" + dayOfMonth);
    };

    private void createNotificationChannel() {
        CharSequence name = "notifyUser";
        String description = "Channel for notifications";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel("notifyUser", name, importance);
        channel.setDescription(description);
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }
}
