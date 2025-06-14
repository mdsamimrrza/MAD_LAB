package com.ghoul.lab9;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    MediaPlayer mediaPlayer;
    SeekBar seekBar;
    Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mediaPlayer = MediaPlayer.create(this, R.raw.sample_audio);
        seekBar = findViewById(R.id.seekBar);
        seekBar.setMax(mediaPlayer.getDuration());

        ((TextView) findViewById(R.id.audioName)).setText(getResources().getResourceEntryName(R.raw.sample_audio));

        Runnable updateSeek = new Runnable() {
            public void run() {
                if (mediaPlayer.isPlaying()) {
                    seekBar.setProgress(mediaPlayer.getCurrentPosition());
                    handler.postDelayed(this, 1000);
                }
            }
        };

        findViewById(R.id.btnPlay).setOnClickListener(v -> {
            if (!mediaPlayer.isPlaying()) {
                mediaPlayer.start();
                handler.post(updateSeek);
            }
        });

        findViewById(R.id.btnPause).setOnClickListener(v -> {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
                handler.removeCallbacks(updateSeek);
            }
        });

        findViewById(R.id.btnForward).setOnClickListener(v ->
                mediaPlayer.seekTo(Math.min(mediaPlayer.getCurrentPosition() + 5000, mediaPlayer.getDuration()))
        );

        findViewById(R.id.btnBackward).setOnClickListener(v ->
                mediaPlayer.seekTo(Math.max(mediaPlayer.getCurrentPosition() - 5000, 0))
        );

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar sb, int p, boolean f) {
                if (f) mediaPlayer.seekTo(p);
            }
            public void onStartTrackingTouch(SeekBar sb) {}
            public void onStopTrackingTouch(SeekBar sb) {}
        });
    }

    @Override
    protected void onDestroy() {
        if (mediaPlayer != null) {
            handler.removeCallbacksAndMessages(null);
            mediaPlayer.release();
        }
        super.onDestroy();
    }
}
