package com.example.rohitsharma.sparta2016;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

public class ScrollingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Additive additive;
        if(getIntent().getSerializableExtra(MainActivity.ADDITIVE_EXTRA) != null){
            additive = (Additive)getIntent().getSerializableExtra(MainActivity.ADDITIVE_EXTRA);
            setTitle(additive.getAdditiveName());

            //getting all the textviews and setting them with additive data
            TextView category = (TextView)findViewById(R.id.scrolling_category_name);
            category.setText(additive.getCategoryName());

            TextView function = (TextView)findViewById(R.id.scrolling_function);
            function.setText(Html.fromHtml("<b>Function: </b> "+additive.getFunction()));

            TextView foods = (TextView)findViewById(R.id.scrolling_foods);
            foods.setText(Html.fromHtml("<b>Foods it's used in: </b> "+additive.getFoodsUsedIn()));

            TextView notice = (TextView)findViewById(R.id.scrolling_notices);
            String text = additive.getHealthNotices() == null ? "None" : additive.getHealthNotices();
            notice.setText(Html.fromHtml("<b>Health notice: </b> "+text));

            TextView info = (TextView)findViewById(R.id.scrolling_info);
            info.setText(Html.fromHtml("<b>Additional information: </b> "+additive.getAdditionalInfo()));

            if(!additive.isIsVegetarian()) {
                TextView vegetarian = (TextView)findViewById(R.id.scrolling_is_vegetarian);
                vegetarian.setVisibility(View.GONE);
            }
        }

    }
}
