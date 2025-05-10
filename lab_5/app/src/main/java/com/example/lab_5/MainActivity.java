package com.example.lab_5;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.telephony.SmsManager;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
     ExecutorService executorService;
     Handler mainhandler;


@SuppressLint("MissingInflatedId")
@Override
protected void onCreate(Bundle savedInstanceState) {

    super.onCreate(savedInstanceState);
    EdgeToEdge.enable(this);
    setContentView(R.layout.activity_main);

    executorService= Executors.newSingleThreadExecutor();
    mainhandler=new Handler(Looper.getMainLooper());

    if(ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)!=
            PackageManager.PERMISSION_GRANTED)
    {
        ActivityCompat.requestPermissions(this,new
                String[]{Manifest.permission.SEND_SMS},1);
    }
    findViewById(R.id.buttonSendSMS).setOnClickListener(v->{
        String ph="7050765950";
        String mes="hello surya ";
        startrun(ph,mes);
    });
}
public void startrun(String ph,String mess){
    executorService.execute(()-> {
            try {
                SmsManager smsmanager = SmsManager.getDefault();
                smsmanager.sendTextMessage(ph, null, mess, null, null);
                mainhandler.post(() -> Toast.makeText(MainActivity.this, "success",
                        Toast.LENGTH_SHORT).show()
                );
            }
            catch(Exception e) {
                e.printStackTrace();
                mainhandler.post(() ->Toast.makeText(MainActivity.this,
                        "successssasdfdsafasfadfafadsfa"
                        ,Toast.LENGTH_SHORT).show()
                );
            }
        });
    }
}