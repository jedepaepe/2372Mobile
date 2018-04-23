package com.example.jedepaepe.labo9service;

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

public class MainActivity extends AppCompatActivity {

    private RecyclerView articleRecyclerView;
    private ArticleAdapter articleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        HttpReceiver httpReceiver = new HttpReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("httpRequestComplete");
        registerReceiver(httpReceiver, intentFilter);
        HttpRequestService.startActionRequestHttp(getApplicationContext(), "https://api.nytimes.com/svc/topstories/v2/technology.json");

        articleRecyclerView = findViewById(R.id.recyclerview_articles);

        // set the adapter of the RecyclerView
        articleAdapter = new ArticleAdapter();
        articleRecyclerView.setAdapter(articleAdapter);

        // set the layoutManager of the recyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        articleRecyclerView.setLayoutManager(layoutManager);
    }

    private class HttpReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String response = intent.getStringExtra("responseString");
            Log.d(MainActivity.class.getName(), response.substring(0, 20));
            ArrayList<Article> articles = parseTopStoriesResponse(response);
            Log.d(HttpReceiver.class.getName(), "size articles: " + articles.size());
            articleAdapter.setArticles(articles);
        }

        private ArrayList<Article> parseTopStoriesResponse(String jsonString) {
            ArrayList<Article> articles = new ArrayList<>();
            try {
                JSONObject jsonObject = new JSONObject(jsonString);
                JSONArray jsonResults = jsonObject.getJSONArray("results");
                for(int i = 0; i < jsonResults.length(); i++) {
                    articles.add(
                            new Article(jsonResults.getJSONObject(i).getString("title"),
                                    jsonResults.getJSONObject(i).getString("abstract"))
                    );
                }
            } catch (JSONException ex) {
                Log.e(HttpReceiver.class.getName(), ex.getMessage());
            }
            return articles;
        }
    }


}
