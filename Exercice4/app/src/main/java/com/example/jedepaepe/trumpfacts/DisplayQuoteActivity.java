package com.example.jedepaepe.trumpfacts;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DisplayQuoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_quote);
    }

    private class TrumpQueryTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String responseString = null;
            if(strings.length != 0) {
                String url = strings[0];
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(url)
                        .build();  // TODO (1) political from selection && text from resources
                Response response;
                try {
                    response = client.newCall(request).execute();
                    responseString = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();    // TODO
                }
            }
            return responseString;
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                JSONObject jsonObject = new JSONObject(result);
                String fact = jsonObject.getString("value");
//                textChuckFact.append(fact + "\n\n");
            } catch (Exception ex) {
//                textChuckFact.append("Sorry, the connection is lost");
            }
//            simpleProgressBar.setVisibility(View.INVISIBLE);
        }
    }

}
