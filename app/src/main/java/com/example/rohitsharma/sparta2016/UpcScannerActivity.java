package com.example.rohitsharma.sparta2016;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;

public class UpcScannerActivity extends Activity implements ZBarScannerView.ResultHandler {
    private ZBarScannerView mScannerView;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        mScannerView = new ZBarScannerView(this);    // Programmatically initialize the scanner view
        setContentView(mScannerView);                // Set the scanner view as the content view
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();          // Start camera on resume
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }


    @Override
    public void handleResult(me.dm7.barcodescanner.zbar.Result result) {
// Do something with the result here
        Log.v("YOOOO", result.getContents()); // Prints scan results
        Log.v("YOOOO", result.getBarcodeFormat().getName()); // Prints the scan format (qrcode, pdf417 etc.)

        MainActivity.UPC_CODE = result.getContents();
        Log.wtf("set result", "swaggg");
        Intent hellow = new Intent();
        hellow.putExtra(MainActivity.UPC_SCANNED_EXTRA, result.getContents());
        Log.wtf("set data", "swaggg");
        setResult(RESULT_OK, hellow);
        Log.wtf("set result type", "swaggg");
        finish();
        Log.wtf("attempt to finish", "swaggg");
    }
}
