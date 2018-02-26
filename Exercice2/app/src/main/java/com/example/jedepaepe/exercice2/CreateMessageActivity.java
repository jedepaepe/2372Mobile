package com.example.jedepaepe.exercice2;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class CreateMessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_meassage_activity);
    }

    protected void onSendButtonClicked(View view) {
        Context context = this;
        Intent intent = new Intent(context, ReceiveMessageActivity.class);
        startActivity(intent);
    }
}
