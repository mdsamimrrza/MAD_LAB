package com.example.lab_4;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class SuccessActivity extends AppCompatActivity {

    private TextView successMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_successful);

        successMessage = findViewById(R.id.success_message);

        String username = getIntent().getExtras().getString("username", "User");
        successMessage.setText("Login Successful!\nWelcome, " + username);
    }
}
