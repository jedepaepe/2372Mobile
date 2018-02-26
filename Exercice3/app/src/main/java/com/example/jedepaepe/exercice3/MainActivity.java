package com.example.jedepaepe.exercice3;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    Spinner menuCategories;
    TextView textUrl;
    TextView textChuckFact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        menuCategories = findViewById(R.id.menu_categories);
        textUrl = findViewById(R.id.text_url);
        textChuckFact = findViewById(R.id.text_chuck_fact);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if(itemId == R.id.action_search) {
            Context context = getApplicationContext();
            CharSequence text = "Search clicked";   // TODO put it in string resources
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            String selected = menuCategories.getSelectedItem().toString();
            String url = "https://api.chucknorris.io/jokes/random?category=" + selected; // TODO put it in resource
            ChuckQueryTask chuckQueryTask = new ChuckQueryTask();
            chuckQueryTask.execute(url);
        }
        return true;    // TODO
    }

    private class ChuckQueryTask extends AsyncTask<String, Void, String> {

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
                textChuckFact.append(fact + "\n\n");
            } catch (Exception ex) {
                textChuckFact.append("Sorry, the connection is lost");
            }
        }
    }

}
