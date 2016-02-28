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
import android.view.View.OnClickListener;
import android.widget.SearchView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements OnClickListener {

    public static final String ADDITIVE_EXTRA = "additive_extra";
    public static final String UPC_EXTRA = "upc_extra";
    public static final String UPC_SCANNED_EXTRA = "upc_scanned_extra";
    public static final int SCANNED_REQUEST_CODE = 5;


    static boolean IS_CODE_RECEIVED;
    static String ADDITIVE_CODE;

    static String SEARCH_ADDITIVE_URL = "https://vx-e-additives.p.mashape.com/additives/search?q=";
    static String ADDITIVES_QUERY_TEXT;
    static String SEARCH_ADDITIVE_URL_END_SORT ="&sort=name";

    static String INFO_ON_ADDITIVE_URL = "https://vx-e-additives.p.mashape.com/additives/";
    static String INFO_ON_ADDITIVE_URL_END_LANG = "?locale=en";

    static String UPC_URL_1 = "https://api.nutritionix.com/v1_1/item?upc=";
    static String UPC_URL_2 = "&appId=dc0ec67b&appKey=9fd40837b36dc3ffb8402b3860e4f2f5";
    static String UPC_CODE="052000124859";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, UpcScannerActivity.class);
                intent.putExtra("SCAN_MODE", "PRODUCT_MODE");//for Qr code, its "QR_CODE_MODE" instead of "PRODUCT_MODE"
                intent.putExtra("SAVE_HISTORY", false);//this stops saving ur barcode in barcode scanner app's history
                startActivityForResult(intent, 0);
            }
        });

        SearchView additivesSearch = (SearchView)findViewById(R.id.main_additives_searchview);
        additivesSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //TODO: add loading indicator
                searchAdditive(query);
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


        TextView soylentCalcium = (TextView)findViewById(R.id.soylent_calcium);
        soylentCalcium.setOnClickListener(this);
        TextView soylentSucralose = (TextView)findViewById(R.id.soylent_sucralose);
        soylentSucralose.setOnClickListener(this);
        TextView soylentRiboflavin = (TextView)findViewById(R.id.soylent_riboflavin);
        soylentRiboflavin.setOnClickListener(this);

        TextView redbullSucrose = (TextView)findViewById(R.id.redbull_sucralose);
        redbullSucrose.setOnClickListener(this);
        TextView redbullCitric = (TextView)findViewById(R.id.redbull_citric);
        redbullCitric.setOnClickListener(this);
        TextView redbullMagnesium = (TextView)findViewById(R.id.redbull_magnesium);
        redbullMagnesium.setOnClickListener(this);

        TextView chipsTbhq = (TextView)findViewById(R.id.chips_tbhq);
        chipsTbhq.setOnClickListener(this);
        TextView chipsAspartame = (TextView)findViewById(R.id.chips_aspartame);
        chipsAspartame.setOnClickListener(this);
        TextView chipsTartrazine = (TextView)findViewById(R.id.chips_tartrazine);
        chipsTartrazine.setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.wtf("Attempt to GET info", "pls work");
        new UpcAsyncTask().execute();
        Log.wtf("Done sending request", "???");
//        if (requestCode == SCANNED_REQUEST_CODE) {
//            if (data.getStringExtra(UPC_SCANNED_EXTRA) != null){
//                UPC_CODE = data.getStringExtra(UPC_SCANNED_EXTRA);
//                new UpcAsyncTask().execute();
//            }
//
//        }
    }

    private void searchAdditive(String query) {
        IS_CODE_RECEIVED = false;
        ADDITIVES_QUERY_TEXT = query.replaceAll(" ", "%20");
        new AdditivesAsyncTask().execute();
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

    @Override
    public void onClick(View v) {
        searchAdditive(((TextView)v).getText().toString());
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


                URL url = new URL(SEARCH_ADDITIVE_URL +ADDITIVES_QUERY_TEXT+ SEARCH_ADDITIVE_URL_END_SORT);

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
                    JSONArray results = new JSONArray(forecastJsonStr);
                    JSONObject result = results.getJSONObject(0);
                        //TODO: execute asynctask again with received code
                        IS_CODE_RECEIVED = true;
                        ADDITIVE_CODE = result.getString("code");
                    try {
                        // Construct the URL for the OpenWeatherMap query
                        // Possible parameters are avaiable at OWM's forecast API page, at
                        // http://openweathermap.org/API#forecast


                        url = new URL(INFO_ON_ADDITIVE_URL+ADDITIVE_CODE+INFO_ON_ADDITIVE_URL_END_LANG);

                        // Create the request to OpenWeatherMap, and open the connection
                        urlConnection = (HttpURLConnection) url.openConnection();
                        urlConnection.setRequestMethod("GET");
                        urlConnection.setRequestProperty("X-Mashape-Key", "ZP5VMVLaIymshR5c43JaqJIFegmap1AXlMfjsnlm1sC1HKPQZi");
                        urlConnection.setRequestProperty("Accept", "application/json");
                        urlConnection.connect();

                        // Read the input stream into a String
                        inputStream = urlConnection.getInputStream();
                        buffer = new StringBuffer();
                        if (inputStream == null) {
                            // Nothing to do.
                            forecastJsonStr = null;
                        }
                        reader = new BufferedReader(new InputStreamReader(inputStream));

                        String line2;
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
                            Additive additive = new Additive(forecastJsonStr);
                            Log.wtf("Additive Name", additive.getAdditiveName());
                            IS_CODE_RECEIVED = false;
                            Intent i = new Intent(MainActivity.this, ScrollingActivity.class);
                            i.putExtra(ADDITIVE_EXTRA, additive);
                            startActivity(i);



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

                } catch (JSONException e) {
                    e.printStackTrace();
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



    class UpcAsyncTask extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... params) {

            HttpURLConnection urlConnection = null;
            String forecastJsonStr;
            BufferedReader reader = null;
            try {
                // Construct the URL for the OpenWeatherMap query
                // Possible parameters are avaiable at OWM's forecast API page, at
                // http://openweathermap.org/API#forecast


                URL url = new URL(UPC_URL_1+UPC_CODE+UPC_URL_2);

                // Create the request to OpenWeatherMap, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
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
                Log.wtf("UPC Results:", forecastJsonStr);
                UpcScannedObject upcScannedObject = new UpcScannedObject(forecastJsonStr);
                Log.wtf("Parsed data UPC", upcScannedObject.getName()+", Sugar: "+upcScannedObject.getSugar());
                Intent i = new Intent(MainActivity.this, ScrollingActivity.class);
                i.putExtra(UPC_EXTRA,upcScannedObject);
                startActivity(i);

                ////////////// EHRH EH RE HREH RHE  ERHRE HHRE H ERUE HIUDF GFDHJKGDF JKGHDFJIGHDF U
                //***WHERE TO CONTINUE WITH UPC***

            } catch (IOException e) {
                Log.e("PlaceholderFragment", "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in attemping
                // to parse it.
                forecastJsonStr = null;
            } catch (JSONException e) {
                e.printStackTrace();
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