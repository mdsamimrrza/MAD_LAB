package com.example.lab_4;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText usernameEditText, passwordEditText;
    Button actionButton;
    TextView statusMessage;
    String savedUsername, savedPassword;
    int loginAttempts = 0;
    boolean isSignUp = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usernameEditText = findViewById(R.id.username_edittext);
        passwordEditText = findViewById(R.id.password_edittext);
        actionButton = findViewById(R.id.action_button);
        statusMessage = findViewById(R.id.status_message);

        setSignUpUI();

        actionButton.setOnClickListener(v -> {
            String user = usernameEditText.getText().toString().trim();
            String pass = passwordEditText.getText().toString().trim();

            if (user.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Fill both fields", Toast.LENGTH_SHORT).show();
                return;
            }

            if (isSignUp) {
                if (!isStrongPassword(pass)) {
                    Toast.makeText(this, "Weak password (min 8 chars, upper, lower, digit, special)", Toast.LENGTH_LONG).show();
                    return;
                }
                savedUsername = user;
                savedPassword = pass;
                isSignUp = false;
                setLoginUI();
                Toast.makeText(this, "Sign Up Successful", Toast.LENGTH_SHORT).show();
            } else {
                if (user.equals(savedUsername) && pass.equals(savedPassword)) {
                    Intent intent = new Intent(this, SuccessActivity.class);
                    intent.putExtra("username", user);
                    startActivity(intent);
                    finish();
                } else {
                    loginAttempts++;
                    Toast.makeText(this, loginAttempts >= 2 ? "Login Blocked" : "Login Failed", Toast.LENGTH_SHORT).show();
                    if (loginAttempts >= 2) actionButton.setEnabled(false);
                }
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void setSignUpUI() {
        statusMessage.setText("Sign Up");
        actionButton.setText("Sign Up");
        usernameEditText.setText("");
        passwordEditText.setText("");
    }

    @SuppressLint("SetTextI18n")
    private void setLoginUI() {
        statusMessage.setText("Login");
        actionButton.setText("Login");
        usernameEditText.setText("");
        passwordEditText.setText("");
    }

    private boolean isStrongPassword( String pass) {
        if (pass.length() < 8) return false;
        boolean upper = false, lower = false, digit = false, special = false;
        for (char c : pass.toCharArray()) {
            if (Character.isUpperCase(c)) upper = true;
            else if (Character.isLowerCase(c)) lower = true;
            else if (Character.isDigit(c)) digit = true;
            else special = true;
        }
        return upper && lower && digit && special;
    }
}
