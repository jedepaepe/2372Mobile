package eu.epfc.nytimesreader;

import android.app.IntentService;

import android.content.Intent;
import android.content.Context;


import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class HttpRequestService extends IntentService {

    private OkHttpClient httpClient = new OkHttpClient();

    private static final String EXTRA_URLSTRING = "eu.epfc.nytimesreader.extra.URL";

    public HttpRequestService() {
        super("HttpRequestService");
    }


    public static void startActionRequestHttp(Context context, String url) {
        Intent intent = new Intent(context, HttpRequestService.class);
        intent.putExtra(EXTRA_URLSTRING, url);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {

            final String url = intent.getStringExtra(EXTRA_URLSTRING);
            try {
                // launch a HTTP request
                String responseString = startRequest(url);

                // broadcase message with the request response
                Intent completeIntent = new Intent("httpRequestComplete");
                completeIntent.putExtra("responseString",responseString);
                sendBroadcast(completeIntent);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String startRequest(String url) throws IOException {

        Request request = new Request.Builder().url(url).build();
        Response response = httpClient.newCall(request).execute();
        return   response.body().string();
    }
}


