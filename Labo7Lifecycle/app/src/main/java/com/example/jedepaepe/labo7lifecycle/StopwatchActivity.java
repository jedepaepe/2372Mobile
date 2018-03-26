package com.example.jedepaepe.labo7lifecycle;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

public class StopwatchActivity extends AppCompatActivity {

    private int seconds = 0;
    private boolean running = false;
    private boolean paused = false;
    private TextView timeTextView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);
        timeTextView = findViewById(R.id.stopwatch_tv_time);

        if(savedInstanceState != null) {
            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
        }

        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                runTimer();
                handler.postDelayed(this, 1000);
            }
        };
        handler.post(runnable);
    }

    @Override
    protected void onStart() {
        super.onStart();
        paused = false;
    }

    @Override
    protected  void onResume() {
        super.onResume();
        paused = false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        paused = true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        paused = true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("seconds", seconds);
        outState.putBoolean("running", running);
    }

    public void onClickStart(View view) {
        running = true;
    }

    public void onClickStop(View view) {
        running = false;
    }

    public void onClickReset(View view) {
        running = true;
        seconds = 0;
    }

    private void runTimer() {
        int hours = seconds / 3600;
        int minutes = (seconds % 3600) / 60;
        int secs = (seconds % 60);

        String time = String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, secs);
        timeTextView.setText(time);

        if(running && !paused) {
            seconds++;
        }
    }


}
