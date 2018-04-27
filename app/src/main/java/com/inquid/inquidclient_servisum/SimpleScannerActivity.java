package com.inquid.inquidclient_servisum;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.Result;

import java.util.ArrayList;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class SimpleScannerActivity extends Activity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView mScannerView;
    private static String TAG;
    public static final int PERMISSION_REQUEST_CAMERA = 1;
    private Dialog dialog;
    private Spinner qty_sp;
    TextView qty_lb;
    private boolean desbordamiento = false;
    public static final String PREFS_NAME = "BarCodeRead";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        TAG = SimpleScannerActivity.class.getSimpleName();
        super.onCreate(savedInstanceState);
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

        mScannerView = new ZXingScannerView(this);    // Programmatically initialize the scanner view
        setContentView(mScannerView);                // Set the scanner view as the content view

        // Request permission. This does it asynchronously so we have to wait for onRequestPermissionResult before trying to open the camera.
        if (!haveCameraPermission())
            requestPermissions(new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CAMERA);


    }

    private boolean haveCameraPermission() {
        return Build.VERSION.SDK_INT < 23 || checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        // This is because the dialog was cancelled when we recreated the activity.
        if (permissions.length == 0 || grantResults.length == 0)
            return;

        switch (requestCode) {
            case PERMISSION_REQUEST_CAMERA: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startCamera();
                } else {
                    finish();
                }
            }
            break;
        }
    }

    public void startCamera() {
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();          // Start camera on resume
    }

    public void stopCamera() {
        mScannerView.stopCamera();
    }

    @Override
    public void onResume() {
        super.onResume();
        startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        stopCamera();
    }

    @Override
    public void handleResult(Result rawResult) {
        // custom dialog
        dialog = new Dialog(this);

        dialog.setContentView(R.layout.barcode_result);
        dialog.setTitle("Lectura de Etiqueta");
        qty_lb = dialog.findViewById(R.id.lb_qty);
        qty_sp = dialog.findViewById(R.id.sp_qty);
        ArrayList<String> numbers = new ArrayList<>();
        for (int i = 1; i < 100; i++) {
            numbers.add(Integer.toString(i));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, numbers);
        qty_sp.setAdapter(adapter);

        qty_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                setQty();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        TextView result = dialog.findViewById(R.id.lb_result);

        result.setText(rawResult.getText());
        setQty();
        Log.d("result_barcode", rawResult.getText());
        Button dialogButton = dialog.findViewById(R.id.dialogButtonOK);
        Button done_btn = dialog.findViewById(R.id.btn_done);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
                dialog.dismiss();
                startActivity(new Intent(SimpleScannerActivity.this, SimpleScannerActivity.class));
                SimpleScannerActivity.this.finish();
            }
        });
        done_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
                dialog.dismiss();
                Intent intent=new Intent(SimpleScannerActivity.this, MainMenuActivity.class);
                intent.putExtra("send_report", intent);
                startActivity(intent);
                SimpleScannerActivity.this.finish();
            }
        });

        dialog.show();
    }

    private void saveData() {

    }

    private void setQty() {
        qty_lb.setText(String.format(" X %s", String.valueOf(qty_sp.getSelectedItem().toString())));
    }
}