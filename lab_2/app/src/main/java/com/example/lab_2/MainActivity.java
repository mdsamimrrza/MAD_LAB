    package com.example.lab_2;

    import androidx.appcompat.app.AppCompatActivity;
    import android.os.Bundle;
    import android.widget.Button;
    import android.widget.TextView;

    public class MainActivity extends AppCompatActivity {
        TextView counterValue;
        Button startButton, stopButton;
        int counter = 0;
         boolean isCounting = false;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            counterValue = findViewById(R.id.counter);
            startButton = findViewById(R.id.startbtn);
            stopButton = findViewById(R.id.stopbtn);

            startButton.setOnClickListener(v -> {
                if (!isCounting) {
                    isCounting = true;
                    new Thread(() -> {
                        counter = 1;
                        while (isCounting) {
                            runOnUiThread(() -> counterValue.setText(String.valueOf(counter++)));
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                                return;
                            }
                        }
                    }).start();
                }
            });

            stopButton.setOnClickListener(v -> isCounting = false);
        }
    }
