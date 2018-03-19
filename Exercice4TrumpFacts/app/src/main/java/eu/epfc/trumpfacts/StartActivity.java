package eu.epfc.trumpfacts;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class StartActivity extends AppCompatActivity {

    private EditText nameEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        nameEditText = findViewById(R.id.edit_text_name);

    }

    public void onGoButtonClicked(View view) {

        // create an explicit Intent with class DisplayQuoteActivity
        Intent intent = new Intent(this, DisplayQuoteActivity.class);
        String message = nameEditText.getText().toString();
        // add data
        intent.putExtra(Intent.EXTRA_TEXT, message);
        // start the activity DisplayQuoteActivity
        startActivity(intent);
    }
}
