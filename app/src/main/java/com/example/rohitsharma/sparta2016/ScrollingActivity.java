package com.example.rohitsharma.sparta2016;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class ScrollingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Additive additive;
        if(getIntent().getSerializableExtra(MainActivity.ADDITIVE_EXTRA) != null){
            additive = (Additive)getIntent().getSerializableExtra(MainActivity.ADDITIVE_EXTRA);
            setTitle(additive.getAdditiveName());

            //getting all the textviews and setting them with additive data
            //TODO: fix issue when attributes are null
            TextView category = (TextView)findViewById(R.id.scrolling_category_name);
            category.setText(additive.getCategoryName());

            TextView function = (TextView)findViewById(R.id.scrolling_function);
            function.setText(Html.fromHtml("<b>Function: </b> "+additive.getFunction()));

            TextView foods = (TextView)findViewById(R.id.scrolling_foods);
            foods.setText(Html.fromHtml("<b>Foods it's used in: </b> "+additive.getFoodsUsedIn()));

            TextView notice = (TextView)findViewById(R.id.scrolling_notices);
            String text = additive.getHealthNotices().equals("null") ||
                    additive.getHealthNotices().equals(null) ||
                    additive.getHealthNotices().equals("") ?
                    "None" : additive.getHealthNotices();
            notice.setText(Html.fromHtml("<b>Health notice: </b> "+text));

            TextView info = (TextView)findViewById(R.id.scrolling_info);
            info.setText(Html.fromHtml("<b>Additional information: </b> "+additive.getAdditionalInfo()));

            if(!additive.isIsVegetarian()) {
                TextView vegetarian = (TextView)findViewById(R.id.scrolling_is_vegetarian);
                vegetarian.setText("Non-Vegetarian");
            }
        } else if (getIntent().getSerializableExtra(MainActivity.UPC_EXTRA) != null) {
            //// TODO: 2016-02-28 SETUP FOR NUTRIENT VALUES
            final UpcScannedObject upcScannedObject = (UpcScannedObject)getIntent().getSerializableExtra(MainActivity.UPC_EXTRA);

            setTitle(upcScannedObject.getName());
            TextView category = (TextView)findViewById(R.id.scrolling_category_name);
            category.setText(upcScannedObject.getCalories()+" Cal");

            TextView function = (TextView)findViewById(R.id.scrolling_function);
            function.setText(Html.fromHtml("<b>Fat: </b> "+upcScannedObject.getFat()));

            TextView foods = (TextView)findViewById(R.id.scrolling_foods);
            foods.setText(Html.fromHtml("<b>Protein (g): </b> "+upcScannedObject.getProtein()));

            TextView notice = (TextView)findViewById(R.id.scrolling_notices);
            notice.setText(Html.fromHtml("<b>Carbohydrates (g): </b> "+upcScannedObject.getCarbs()));

            TextView info = (TextView)findViewById(R.id.scrolling_info);
            info.setText(Html.fromHtml("<b>Sugar (g): </b> "+upcScannedObject.getSugar()));

            TextView vegetarian = (TextView)findViewById(R.id.scrolling_is_vegetarian);
            if(upcScannedObject.isContainsGluten()) {
                vegetarian.setText("Contains Gluten");
            } else {
                vegetarian.setText("Gluten-free");
            }

            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(App.IsPresentInMap(upcScannedObject)){
                        App.IncrementQuantity(upcScannedObject);
                    } else {
                        App.AddObjectToMap(upcScannedObject);
                    }
                    Log.wtf("Success", "h4cker");
                }
            });
        }

    }
}
