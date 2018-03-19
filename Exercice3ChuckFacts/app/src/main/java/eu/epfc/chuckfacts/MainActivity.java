package eu.epfc.chuckfacts;

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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    TextView urlTextView;

    OkHttpClient httpClient = new OkHttpClient();

    /**
    This is an Inner Class.
     */
    public class ChuckQueryTask extends AsyncTask<String,Void,String> {

        /**
         * Method called on the background thread
         * @param urls array of String urls. The "..." notation is a VarArgs (variable number of parameters).
         *             Only the first one will be used here.
         */
        @Override
        protected String doInBackground(String... urls) {

            // get the url
            String url = urls[0];

            String responseText = null;
            try {
                // send a request
                responseText = getResponseFromHttpUrl(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return responseText;
        }

        /**
         * Method called on the main thread when doInBackground has finished.
         * @param s String returned by doInBackground.
         */
        @Override
        protected void onPostExecute(String s) {
            if (s!=null)
            {
                // extract the text we want to display from the JSON String
                String chuckFactString = null;
                try {

                    JSONObject jsonObject = new JSONObject(s);
                    chuckFactString = jsonObject.getString("value");

                } catch (JSONException e) {

                    // woops, something is wrong with the JSON
                    e.printStackTrace();

                }

                // if the JSON was parsed correctly
                if (chuckFactString != null) {

                    // display the string in the TextView
                    TextView factTextView = findViewById(R.id.text_chuck_facts);
                    factTextView.setText(chuckFactString);
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        urlTextView = findViewById(R.id.text_url_display);
    }

    /**
     * Method called automatically when the activity start.
     * @param menu : The menu that will be displayed
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // get a menuInflater from the activity
        MenuInflater menuInflater = getMenuInflater();

        // inflate the menu "main.xml" into the menu received in argument
        menuInflater.inflate(R.menu.main,menu);

        // display the menu
        return true;
    }

    /**
     * Method called when a menuItem is clicked.
     * @param item : The menu item that has been clicked
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // get the id of the clicked item
        int itemID = item.getItemId();

        // if the menu item is the action_search item ...
        if (itemID == R.id.action_search)
        {
            Context context = getApplicationContext();

            // display toast
            Toast toast = Toast.makeText(context, "Search Clicked", Toast.LENGTH_LONG);
            toast.show();

            // launch a query
            makeQuery();

        }
        return true;
    }

    private void makeQuery()
    {
        // get current category from the Spinner menu
        Spinner categorySpinner = findViewById(R.id.menu_categories);
        String currentCategory = String.valueOf(categorySpinner.getSelectedItem());

        // create an url
        String url = "https://api.chucknorris.io/jokes/random?category=" + currentCategory;

        urlTextView.setText(url);

        // execute the AsyncTask
        new ChuckQueryTask().execute(url);
    }

    /**
     * Execute a http query. MUST BE CALLED FROM A BACKGROUND THREAD!!!
     * @param url : url of the query
     * @return the response of the query
     */
    private String getResponseFromHttpUrl(String url) throws IOException {

        // build a OKHttp request from the url
        Request request = new Request.Builder().url(url).build();

        // execute the request
        Response response = httpClient.newCall(request).execute();

        // return the response
        return response.body().string();
    }

}
