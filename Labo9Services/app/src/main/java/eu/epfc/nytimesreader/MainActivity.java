package eu.epfc.nytimesreader;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static String TAG = "MainActivity";
    private HttpRequestService httpRequestService = new HttpRequestService();
    private ArticlesAdapter articlesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // create a Broadcast receiver
        HttpReceiver httpReceiver = new HttpReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("httpRequestComplete");
        // register the broadcast receiver to intent with action "httpRequestComplete"
        registerReceiver(httpReceiver,intentFilter);

        String urlString = "https://api.nytimes.com/svc/topstories/v2/technology.json?api-key=88111103e9a649df9be4e7f7145e0f2d";
        HttpRequestService.startActionRequestHttp(getApplicationContext(), urlString);

        RecyclerView recyclerView = findViewById(R.id.list_articles);

        // set the adapter of the RecyclerView
        articlesAdapter = new ArticlesAdapter();
        recyclerView.setAdapter(articlesAdapter);

        // set the layoutManager of the recyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    private List<Article> parseTopStoriesResponse(String jsonString)
    {
        ArrayList<Article> articles = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonArticles = jsonObject.getJSONArray("results");

            for (int i = 0; i<jsonArticles.length(); ++i)
            {
                JSONObject jsonArticle = jsonArticles.getJSONObject(i);

                String title = jsonArticle.getString("title");
                String articleAbstract = jsonArticle.getString("abstract");

                Article newArticle = new Article(title,articleAbstract);

                articles.add(newArticle);
            }

        } catch (JSONException e) {
            Log.e(TAG,"can't parse json string correctly");
            e.printStackTrace();
        }

        return articles;

    }

    private class HttpReceiver extends BroadcastReceiver
    {
        @Override
        public void onReceive(Context context, Intent intent) {

            // extract JSON response from the intent
            String response = intent.getStringExtra("responseString");

            // parse Articles from JSON
            List<Article> articles = parseTopStoriesResponse(response);

            // update the recycler view with those new articles
            articlesAdapter.setArticles(articles);
        }
    }
}
