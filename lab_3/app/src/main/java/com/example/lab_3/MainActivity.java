package com.example.lab_3;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private EditText editText;
    private Button btnSpeak;
    private TextToSpeech textToSpeech;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        btnSpeak = findViewById(R.id.btnSpeak);

        // Initialize Text ToSpeech
        textToSpeech = new TextToSpeech(getApplicationContext(), status -> {
            if (status == TextToSpeech.SUCCESS) {
                textToSpeech.setLanguage(Locale.US);
            } else {
                Toast.makeText(MainActivity.this,
                        "Text-to-Speech Initialization Failed",
                        Toast.LENGTH_SHORT).show();
            }
        });

        btnSpeak.setOnClickListener(v -> {
            String text = editText.getText().toString();
            if (!text.isEmpty()) {
                textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
            } else {
                Toast.makeText(MainActivity.this,
                        "Please enter text",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
