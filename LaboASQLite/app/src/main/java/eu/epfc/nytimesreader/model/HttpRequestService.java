package eu.epfc.nytimesreader.model;

import android.app.IntentService;

import android.content.Intent;
import android.content.Context;


import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class HttpRequestService extends IntentService {
    public static final String ACTION_HTTP_REQUEST_COMPLETE = "HTTP_REQUESTION_COMPLETE";
    public static final String ACTION_HTTP_REQUEST_FAILED = "HTTP_REQUEST_FAILED";

    private OkHttpClient httpClient = new OkHttpClient();

    private static final String EXTRA_URL_STRING = "eu.epfc.nytimesreader.extra.URL";

    public HttpRequestService() {
        super("HttpRequestService");
    }


    public static void startActionRequestHttp(Context context, String url) {
        Intent intent = new Intent(context, HttpRequestService.class);
        intent.putExtra(EXTRA_URL_STRING, url);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {

            final String url = intent.getStringExtra(EXTRA_URL_STRING);
            Intent completeIntent = null;
            try {
                // launch a HTTP request
                String responseString = startRequest(url);
                completeIntent = new Intent(ACTION_HTTP_REQUEST_COMPLETE);
                completeIntent.putExtra("responseString",responseString);
            }
            catch (IOException e) {
                e.printStackTrace();
                completeIntent = new Intent(ACTION_HTTP_REQUEST_FAILED);
            }
            // broadcast message with the request response
            sendBroadcast(completeIntent);
        }
    }

    private String startRequest(String url) throws IOException {

        Request request = new Request.Builder().url(url).build();
        Response response = httpClient.newCall(request).execute();
        return   response.body().string();
    }
}


