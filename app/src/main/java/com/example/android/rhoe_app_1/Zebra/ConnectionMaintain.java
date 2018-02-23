package com.example.android.rhoe_app_1.Zebra;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.android.app_v12.R;
import com.example.android.rhoe_app_1.Zebra.BluetoothDiscovery;
import com.example.android.rhoe_app_1.Zebra.SettingsHelper;
import com.example.android.rhoe_app_1.Zebra.DemoSleeper;
import com.zebra.sdk.comm.BluetoothConnection;
import com.zebra.sdk.comm.Connection;
import com.zebra.sdk.comm.ConnectionException;
import com.zebra.sdk.printer.PrinterLanguage;
import com.zebra.sdk.printer.ZebraPrinter;
import com.zebra.sdk.printer.ZebraPrinterFactory;
import com.zebra.sdk.printer.ZebraPrinterLanguageUnknownException;

/**
 * Created by User on 23/02/2018.
 */



public class ConnectionMaintain extends AppCompatActivity {

    private ZebraPrinter printer;
    private Connection printerConnection;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_connect);
        String MACAddressVar;


    }

    public void MaintainConnection(final String MACAddressVar,final TextView StatusView){
        new Thread(new Runnable() {
            public void run() {
                Looper.prepare();
                runOnUiThread(new Runnable() {
                    public void run() {
                        StatusView.setBackgroundColor(Color.YELLOW);
                        StatusView.setText("Connecting");
                    }
                });
                printerConnection = null;
                printerConnection = new BluetoothConnection(MACAddressVar);

                try {
                    printerConnection.open();
                    runOnUiThread(new Runnable() {
                        public void run() {
                            StatusView.setBackgroundColor(Color.GREEN);
                            StatusView.setText("Connected");
                        }
                    });
                    DemoSleeper.sleep(1000);
                } catch (ConnectionException e) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            StatusView.setBackgroundColor(Color.RED);
                            StatusView.setText("Comm Error! Disconnecting");
                        }
                    });
                    DemoSleeper.sleep(1000);
                    try {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                StatusView.setBackgroundColor(Color.RED);
                                StatusView.setText("Disconnecting");
                            }
                        });
                        if (printerConnection != null) {
                            printerConnection.close();
                        }
                        runOnUiThread(new Runnable() {
                            public void run() {
                                StatusView.setBackgroundColor(Color.RED);
                                StatusView.setText("Not Connected");
                            }
                        });
                    } catch (ConnectionException e1) {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                StatusView.setBackgroundColor(Color.RED);
                                StatusView.setText("COMM Error! Disconnected");
                            }
                        });
                    }
                }

                ZebraPrinter printer = null;

                Looper.loop();
                Looper.myLooper().quit();
            }
        }).start();
    }}
