package eu.epfc.trumpfacts;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DisplayQuoteActivity extends AppCompatActivity {

    private OkHttpClient httpClient = new OkHttpClient();

    private static final String baseURL = "https://api.whatdoestrumpthink.com/api/v1/";
    private static final String trumpPersonalizedQuoteURL = "quotes/personalized?q=";

    private TextView quoteTextView;
    private String name;
    private Button getNewQuoteButton;
    private ProgressBar progressBar;

    private class TrumpQueryTask extends AsyncTask<String,Void,String>
    {

        @Override
        protected void onPreExecute()
        {
            getNewQuoteButton.setEnabled(false);

            // hide the TextView
            quoteTextView.setVisibility(View.INVISIBLE);

            // display the progress bar
            progressBar.setVisibility(View.VISIBLE);

            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... urls) {
            String url = urls[0];

            String responseText = null;
            try {
                responseText = getResponseFromHttpUrl(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return responseText;
        }

        @Override
        protected void onPostExecute(String s) {

            // change the visibility of the textView and progres bar
            quoteTextView.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.INVISIBLE);

            // animate the TextView
            animateText();

            // enable the Button
            getNewQuoteButton.setEnabled(true);

            if (s!=null)
            {
                String quoteString = null;
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    quoteString = jsonObject.getString("message");
                } catch (JSONException e) {
                    DisplayQuoteActivity.this.displayQuote(getResources().getString(R.string.error_message));
                    e.printStackTrace();
                }

                DisplayQuoteActivity.this.displayQuote(quoteString);
            }
            else
            {
                DisplayQuoteActivity.this.displayQuote(getResources().getString(R.string.error_message));
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_quote);

        // get View references
        quoteTextView = findViewById(R.id.text_quote);
        getNewQuoteButton = findViewById(R.id.button_getNewQuote);
        progressBar = findViewById(R.id.progress_bar);

        // hide the progress bar
        progressBar.setVisibility(View.INVISIBLE);

        // get the intent that started this activity
        Intent intent = getIntent();

        // safety check : has the intent data with name Intent.EXTRA_TEXT ?
        if (intent.hasExtra(Intent.EXTRA_TEXT)){

            // extract the name
            name = intent.getStringExtra(Intent.EXTRA_TEXT);

            String url = getURLFromName(name);

            // send a query
            sendQuery(url);
        }
    }

    private String getResponseFromHttpUrl(String url) throws IOException {

        Request request = new Request.Builder().url(url).build();
        Response response = httpClient.newCall(request).execute();
        return response.body().string();
    }

    private void displayQuote(String quote)
    {
        quoteTextView.setText(quote);
    }

    // Callback from "Get new quote button"
    public void getNewQuoteButtonClicked(View view)
    {
        String url = getURLFromName(name);
        sendQuery(url);
    }

    private void sendQuery(String url)
    {
        TrumpQueryTask queryTask = new TrumpQueryTask();
        queryTask.execute(url);
    }


    private String getURLFromName(String name)
    {
        return baseURL + trumpPersonalizedQuoteURL + name;
    }

    private void animateText()
    {

        // create objectAnimator
        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(quoteTextView,"alpha",0.0f,1.0f);

        // set the duration of the animation
        alphaAnimator.setDuration(1000);

        // create scale animators
        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(quoteTextView,"scaleX",0.9f,1.0f);
        scaleXAnimator.setDuration(1000);
        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(quoteTextView,"scaleY",0.9f,1.0f);
        scaleYAnimator.setDuration(1000);

        // create animator Set
        AnimatorSet animatorSet = new AnimatorSet();
        // chain animations
        animatorSet.play(scaleXAnimator).with(scaleYAnimator).with(alphaAnimator);
        // start the animations
        animatorSet.start();

    }

}
