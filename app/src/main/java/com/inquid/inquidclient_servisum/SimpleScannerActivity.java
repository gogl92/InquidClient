package com.inquid.inquidclient_servisum;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Camera;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class SimpleScannerActivity extends Activity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView mScannerView;
    private static String TAG;
    public static final int PERMISSION_REQUEST_CAMERA = 1;
    private Button btn_0;
    private Button btn_1;
    private Button btn_2;
    private Button btn_3;
    private Button btn_4;
    private Button btn_5;
    private Button btn_6;
    private Button btn_7;
    private Button btn_8;
    private Button btn_9;
    private Dialog dialog;

    private int multiplier = 1;
    private int unidades = 1;
    private int decenas = 0;
    TextView qty_lb;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        TAG = SimpleScannerActivity.class.getSimpleName();
        super.onCreate(savedInstanceState);

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


        btn_0 = dialog.findViewById(R.id.btn_0);
        btn_1 = dialog.findViewById(R.id.btn_1);
        btn_2 = dialog.findViewById(R.id.btn_2);
        btn_3 = dialog.findViewById(R.id.btn_3);
        btn_4 = dialog.findViewById(R.id.btn_4);
        btn_5 = dialog.findViewById(R.id.btn_5);
        btn_6 = dialog.findViewById(R.id.btn_6);
        btn_7 = dialog.findViewById(R.id.btn_7);
        btn_8 = dialog.findViewById(R.id.btn_8);
        btn_9 = dialog.findViewById(R.id.btn_9);

        qty_lb = dialog.findViewById(R.id.lb_qty);
        btn_0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (unidades != 1) {
                    decenas = 0;
                }
                setQty();
            }
        });
        btn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (unidades <= 1 && decenas == 0) {
                    unidades = 1;
                }
                if (unidades != 1 && decenas == 0) {
                    decenas = 1;
                }
                setQty();
            }
        });
        btn_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (unidades <= 1 && decenas == 0) {
                    unidades = 2;
                }
                setQty();
            }
        });
        btn_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (unidades <= 1 && decenas == 0) {
                    unidades = 3;
                }
                setQty();
            }
        });
        btn_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (unidades <= 1 && decenas == 0) {
                    unidades = 4;
                }
                setQty();
            }
        });
        btn_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (unidades <= 1 && decenas == 0) {
                    unidades = 5;
                }
                setQty();
            }
        });
        btn_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (unidades <= 1 && decenas == 0) {
                    unidades = 6;
                }
                setQty();
            }
        });
        btn_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (unidades <= 1 && decenas == 0) {
                    unidades = 7;
                }
                setQty();
            }
        });
        btn_8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (unidades <= 1 && decenas == 0) {
                    unidades = 8;
                }
                setQty();
            }
        });
        btn_9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (unidades <= 1 && decenas == 0) {
                    unidades = 9;
                }
                setQty();
            }
        });

        TextView result = dialog.findViewById(R.id.lb_result);

        result.setText(rawResult.getText());
        setQty();
        Log.d("result_barcode", rawResult.getText());
        Button dialogButton = dialog.findViewById(R.id.dialogButtonOK);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
                dialog.dismiss();
                startActivity(new Intent(SimpleScannerActivity.this, SimpleScannerActivity.class));
                SimpleScannerActivity.this.finish();
            }
        });

        dialog.show();
    }

    private void saveData() {

    }

    private void setQty() {
        multiplier = (decenas * 10) + unidades;
        qty_lb.setText(String.valueOf(multiplier));
    }
}