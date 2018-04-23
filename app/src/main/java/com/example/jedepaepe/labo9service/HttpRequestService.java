package com.example.jedepaepe.labo9service;

import android.app.IntentService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by jedepaepe on 23/04/2018.
 */

public class HttpRequestService extends IntentService {
    OkHttpClient client = new OkHttpClient();

    public HttpRequestService() {
        super("MyService");
    }

    @Override
    protected  void onHandleIntent(Intent intent) {
        final String url = intent.getStringExtra("URL");
        String responseString;
        try {
            Request request = new Request.Builder().url(url + "?api-key=88111103e9a649df9be4e7f7145e0f2d").build();
            Response response = client.newCall(request).execute();
            responseString = response.body().string();
            Intent completeIntent = new Intent("httpRequestComplete");
            completeIntent.putExtra("responseString", responseString);
            Log.d(getClass().getName(), responseString.substring(0, 20));
            sendBroadcast(completeIntent);
        } catch (IOException ex) {
            System.out.println("error " + ex.getMessage());
            responseString = "error";
        }
    }

    public static void startActionRequestHttp(Context context, String url) {
        Intent intent = new Intent(context, HttpRequestService.class);
        intent.putExtra("URL", url);
        context.startService(intent);
    }

}
