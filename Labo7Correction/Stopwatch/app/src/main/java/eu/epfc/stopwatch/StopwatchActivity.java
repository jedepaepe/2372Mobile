package eu.epfc.stopwatch;


import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

public class StopwatchActivity extends AppCompatActivity {

    private int seconds;
    private boolean running;
    private boolean wasRunning;

    private TextView timeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);

        timeTextView = findViewById(R.id.text_time);

        // if something has been saved in the Bundle
        if(savedInstanceState != null)
        {
            // extract the variables stored in the Bundle and assign them
            // to our private vars
            this.seconds = savedInstanceState.getInt("seconds");
            this.running = savedInstanceState.getBoolean("running");
            this.wasRunning = savedInstanceState.getBoolean("wasRunning");
        }

        runTimer();
    }

    public void onClickStart(View view) {
        running = true;
    }

    public void onClickStop(View view) {
        running = false;
    }

    public void onClickReset(View view) {
        running = false;
        seconds = 0;
    }

    private void runTimer()
    {
        final Handler handler = new Handler();

        // define piece of code embed in a Runnable object
        Runnable stopwatchUpdateRunnable = new Runnable() {
            @Override
            public void run() {
                int hours = seconds/3600;
                int minutes = (seconds%3600)/60;
                int secs = seconds%60;

                // we want 1 digit for hours, and 2 digits for minutes and secs
                // -> we use the String format to get that result
                String time = String.format(Locale.getDefault(),"%d:%02d:%02d",hours,minutes,secs);

                // timeTextView is our TextView, assigned in onCreate
                timeTextView.setText(time);

                // if the stopwatch is running -> increment the seconds
                if (running) {
                    seconds++;
                }

                // call this runnable again in 1 sec (recursion)
                handler.postDelayed(this,1000);
            }
        };

        // call the runnable
        handler.post(stopwatchUpdateRunnable);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // save ours private vars in the Bundle
        outState.putInt("seconds",seconds);
        outState.putBoolean("running",running);
        outState.putBoolean("wasRunning",wasRunning);
    }

    // not needed anymore -> commented
//    @Override
//    protected void onStop() {
//        super.onStop();
//
//        wasRunning = running;
//        running = false;
//    }


    // not needed anymore -> commented
//    @Override
//    protected void onStart() {
//        super.onStart();
//
//        if (wasRunning){
//            running = true;
//        }
//    }

    @Override
    protected void onPause() {
        super.onPause();

        wasRunning = running;
        running = false;
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (wasRunning) {
            running = true;
        }
    }


    public void onClickShare(View view) {

        Intent intent = new Intent(Intent.ACTION_SEND);
        String textToShare = "My time is " + String.valueOf(seconds) + "seconds";
        intent.putExtra("time",textToShare);
        intent.setType("text/plain");

        // display a share activity
        startActivity(intent);
    }
}
