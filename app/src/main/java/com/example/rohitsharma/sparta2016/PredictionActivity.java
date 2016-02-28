package com.example.rohitsharma.sparta2016;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class PredictionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prediction);

        Spinner spinner = (Spinner) findViewById(R.id.sex);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.sex_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);


        TextView tCal = (TextView)findViewById(R.id.calories);
        tCal.setText(""+App.CaloriesTotal());

        TextView tCarb = (TextView)findViewById(R.id.carbs);
        tCarb.setText(""+App.CarbsTotal());

        TextView tFat = (TextView)findViewById(R.id.fat);
        tFat.setText(""+App.FatTotal());

        TextView tSugar = (TextView)findViewById(R.id.sugar);
        tSugar.setText(""+App.SugarTotal());

        TextView Wait;
    }
}
