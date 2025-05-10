package com.example.lab_1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    int[] images;
    Timer timer = new Timer();
    boolean isTimerRunning = false;
    View wallView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button wpButton = findViewById(R.id.button1);
        wallView = findViewById(R.id.wallView);

        images = new int[]{
                R.drawable.a, R.drawable.c, R.drawable.d,
                R.drawable.e, R.drawable.b, R.drawable.f
        };

        wpButton.setOnClickListener(v -> {
            if (!isTimerRunning) {
                isTimerRunning = true;
                startImageCycle();
            }
        });
    }

    private void startImageCycle() {
        TimerTask task = new TimerTask() {
            @Override public void run() {
                int rNum = new Random().nextInt(images.length);
                runOnUiThread(() -> wallView.setBackground(
                        ContextCompat.getDrawable(getApplicationContext(), images[rNum])
                ));
                timer.schedule(new TimerTask() {
                    @Override public void run() {
                        startImageCycle();
                    }
                }, 1000);
            }
        };
        timer.schedule(task, 0);
    }
}