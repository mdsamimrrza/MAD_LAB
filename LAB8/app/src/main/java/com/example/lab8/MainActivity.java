package com.example.lab8;

import android.os.Build;
import android.os.Bundle;
import android.widget.*;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.database.sqlite.*;
import android.database.Cursor;

import java.time.LocalDate;
import java.time.LocalTime;

public class MainActivity extends AppCompatActivity {
    EditText medName, medDate;
    Spinner timeSpinner;
    Button saveBtn, checkBtn;
    TextView result;
    SQLiteDatabase db;

    String[] times = {"Morning", "Afternoon", "Evening", "Night"};

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        medName = findViewById(R.id.medName);
        medDate = findViewById(R.id.medDate);
        timeSpinner = findViewById(R.id.timeOfTheDay);
        saveBtn = findViewById(R.id.medSave);
        checkBtn = findViewById(R.id.medCheckAlarm);
        result = findViewById(R.id.medOutput);

        timeSpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, times));

        db = openOrCreateDatabase("MedicinesDB", MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS medicine_schedule (medicine_name TEXT, date TEXT, time_of_day TEXT)");

        saveBtn.setOnClickListener(v -> {
            String name = medName.getText().toString().trim();
            String date = medDate.getText().toString().trim();
            String time = timeSpinner.getSelectedItem().toString();
            if (name.isEmpty() || date.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }
            db.execSQL("INSERT INTO medicine_schedule VALUES (?, ?, ?)", new Object[]{name, date, time});
            Toast.makeText(this, "Saved!", Toast.LENGTH_SHORT).show();
            medName.setText(""); medDate.setText("");
        });

        checkBtn.setOnClickListener(v -> {
            String today = LocalDate.now().toString();
            String timeNow = getCurrentTimeOfDay();
            Cursor c = db.rawQuery("SELECT medicine_name FROM medicine_schedule WHERE date=? AND time_of_day=?", new String[]{today, timeNow});
            if (c.getCount() == 0) {
                result.setText("No medicine for " + timeNow + " on " + today);
            } else {
                StringBuilder out = new StringBuilder("Take at " + timeNow + " (" + today + "):\n");
                while (c.moveToNext()) out.append("• ").append(c.getString(0)).append("\n");
                result.setText(out.toString());
            }
            c.close();
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    String getCurrentTimeOfDay() {
        int hour = LocalTime.now().getHour();
        if (hour >= 5 && hour <= 11) return "Morning";
        else if (hour <= 16) return "Afternoon";
        else if (hour <= 20) return "Evening";
        else return "Night";
    }
}
