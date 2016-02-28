package com.example.rohitsharma.sparta2016;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class PredictionActivity extends AppCompatActivity {

    private TextView mWeeksT;
    private EditText mEWeight;
    private EditText mEHeight;
    private EditText mEAge;
    private EditText mEWorkout;
    private Spinner mSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prediction);

        mSpinner = (Spinner) findViewById(R.id.sex);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.sex_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        mSpinner.setAdapter(adapter);


        TextView tCal = (TextView)findViewById(R.id.calories);
        tCal.setText(""+App.CaloriesTotal());

        TextView tCarb = (TextView)findViewById(R.id.carbs);
        tCarb.setText(""+App.CarbsTotal());

        TextView tFat = (TextView)findViewById(R.id.fat);
        tFat.setText(""+App.FatTotal());

        TextView tSugar = (TextView)findViewById(R.id.sugar);
        tSugar.setText(""+App.SugarTotal());

        mEAge = (EditText)findViewById(R.id.age);
        mEWeight = (EditText)findViewById(R.id.weight);
        mEHeight = (EditText)findViewById(R.id.height);
        mEWorkout = (EditText)findViewById(R.id.workout);

        mWeeksT = (TextView)findViewById(R.id.wait_count);
        mWeeksT.setText("" + Weeks(mEWeight, mEHeight, mEAge, mEWorkout, mSpinner) + " Weeks to Wait Until Next Hackathon");

        Button update = (Button)findViewById(R.id.update_weeks);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWeeksT.setText("" + Weeks(mEWeight, mEHeight, mEAge, mEWorkout, mSpinner) + " Weeks to Wait Until Next Hackathon");
            }
        });
    }

    private int Weeks(EditText eWeight, EditText eHeight, EditText eAge, EditText eWorkout, Spinner spinner) {
        return App.WeeksToWait(App.CaloriesTotal(),
                App.FatTotal(), Integer.parseInt(eWeight.getText().toString()),
                Integer.parseInt(eHeight.getText().toString()), spinner.getSelectedItemPosition() == 1,
                Integer.parseInt(eAge.getText().toString()),
                Integer.parseInt(eWorkout.getText().toString()));
    }
}
