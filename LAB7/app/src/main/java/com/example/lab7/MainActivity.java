package com.example.lab7;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class MainActivity extends AppCompatActivity {
    EditText phone;

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_main);

        phone = findViewById(R.id.phoneNumber);
        if (checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 1);

        int[] ids = {R.id.b0,R.id.b1,R.id.b2,R.id.b3,R.id.b4,R.id.b5,R.id.b6,R.id.b7,R.id.b8,R.id.b9,R.id.b10,R.id.b11};
        String[] chars = {"0","1","2","3","4","5","6","7","8","9","*","#"};

        for (int i = 0; i < ids.length; i++) {
            final String ch = chars[i];
            findViewById(ids[i]).setOnClickListener(v -> phone.append(ch));
        }

        findViewById(R.id.btnCall).setOnClickListener(v -> {
            String num = phone.getText().toString();
            if (!num.isEmpty())
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + num)));
            else Toast.makeText(this, "No number", Toast.LENGTH_SHORT).show();
        });

        findViewById(R.id.btnClear).setOnClickListener(v -> {
            String num = phone.getText().toString();
            if (!num.isEmpty()) phone.setText(num.substring(0, num.length() - 1));
        });

        findViewById(R.id.btnSave).setOnClickListener(v -> {
            String num = phone.getText().toString();
            if (!num.isEmpty()) {
                Intent i = new Intent(Intent.ACTION_INSERT);
                i.setType(ContactsContract.RawContacts.CONTENT_TYPE);
                i.putExtra(ContactsContract.Intents.Insert.PHONE, num);
                startActivity(i);
            }
        });
    }
}
