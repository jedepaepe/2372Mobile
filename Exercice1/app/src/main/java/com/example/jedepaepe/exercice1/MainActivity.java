package com.example.jedepaepe.exercice1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void findRestaurant(View view) {
        TextView restaurantView = (TextView) findViewById(R.id.text_restaurants);
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        String type = String.valueOf( spinner.getSelectedItem());
        List<String> selection = (new RestaurantManager()).getRestaurants(type);
        StringBuilder sb = new StringBuilder();
        for(String s : selection) {
            sb.append(s).append('\n');
        }
        sb.deleteCharAt(sb.length()-1);
        restaurantView.setText(sb.toString());

        //((TextView) findViewById(R.id.text_restaurants)).setText(String.valueOf((Spinner) findViewById(R.id.spinner)));
    }

}
