package com.example.rohitsharma.sparta2016;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    static boolean IS_CODE_RECEIVED;
    static String ADDITIVE_CODE;

    static String SEARCH_ADDITIVE_URL = "https://vx-e-additives.p.mashape.com/additives/search?q=";
    static String ADDITIVES_QUERY_TEXT;
    static String SEARCH_ADDITIVE_URL_END_SORT ="&sort=name";

    static String INFO_ON_ADDITIVE_URL = "https://vx-e-additives.p.mashape.com/additives/";
    static String INFO_ON_ADDITIVE_URL_END_LANG = "?locale=en";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent i = new Intent(MainActivity.this, ScrollingActivity.class);
                startActivity(i);
            }
        });

        SearchView additivesSearch = (SearchView)findViewById(R.id.main_additives_searchview);
        additivesSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                IS_CODE_RECEIVED = false;
                ADDITIVES_QUERY_TEXT = query.replaceAll(" ", "%20");
                new AdditivesAsyncTask().execute();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        additivesSearch.onActionViewExpanded();
        //Stop keyboard from automatically popping up
        additivesSearch.clearFocus();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    class AdditivesAsyncTask extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... params) {

            HttpURLConnection urlConnection = null;
            String forecastJsonStr;
            BufferedReader reader = null;
            try {
                // Construct the URL for the OpenWeatherMap query
                // Possible parameters are avaiable at OWM's forecast API page, at
                // http://openweathermap.org/API#forecast


                URL url = (IS_CODE_RECEIVED) ? new URL(INFO_ON_ADDITIVE_URL+ADDITIVE_CODE+INFO_ON_ADDITIVE_URL_END_LANG) :
                        new URL(SEARCH_ADDITIVE_URL +ADDITIVES_QUERY_TEXT+ SEARCH_ADDITIVE_URL_END_SORT);

                // Create the request to OpenWeatherMap, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setRequestProperty("X-Mashape-Key", "ZP5VMVLaIymshR5c43JaqJIFegmap1AXlMfjsnlm1sC1HKPQZi");
                urlConnection.setRequestProperty("Accept", "application/json");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    forecastJsonStr = null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    forecastJsonStr = null;
                }
                forecastJsonStr = buffer.toString();
                Log.wtf("Results:", forecastJsonStr);
                try {
                    JSONObject result = new JSONObject(forecastJsonStr);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Snackbar.make(null, "An error has occured", Snackbar.LENGTH_LONG);
                }


                //TODO: parse data

            } catch (IOException e) {
                Log.e("PlaceholderFragment", "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in attemping
                // to parse it.
                forecastJsonStr = null;
            } finally{
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("PlaceholderFragment", "Error closing stream", e);
                    }
                }
            }
            return null;
        }
    }
}