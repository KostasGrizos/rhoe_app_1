package com.example.android.rhoe_app_1;

import android.content.Intent;
import android.graphics.Color;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class BluetoothConnectActivity extends AppCompatActivity {

    private Button BluetoothFindButton, EstablishConnectionButton, PrintTestButton, DisconnectionButton, SaveConnectionButton;
    private EditText MACAddressEditText, PrinterNameEditText;
    private TextView ConnectivityStatusTextView;

    private ZebraPrinter printer;
    private Connection printerConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_connect);

        Bundle user = this.getIntent().getExtras();
        final String[] UserPortableData=user.getStringArray("UserPortableData");

        ConnectivityStatusTextView = (TextView) findViewById(R.id.tvConnectivityStatus);

        BluetoothFindButton = (Button)findViewById(R.id.btnBluetoothFind);
        EstablishConnectionButton = (Button)findViewById(R.id.btnEstablishConnection);
        PrintTestButton = (Button)findViewById(R.id.btnPrintTest);
        DisconnectionButton = (Button)findViewById(R.id.btnDisconnection);
        SaveConnectionButton = (Button)findViewById(R.id.btnSaveConnection);

        MACAddressEditText = (EditText) findViewById(R.id.etMACAddress);
        PrinterNameEditText = (EditText) findViewById(R.id.etPrinterName);

        BluetoothFindButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle user = new Bundle();
                user.putStringArray("UserPortableData", UserPortableData);

                Intent intent = new Intent(BluetoothConnectActivity.this, BluetoothDiscovery.class);
                intent.putExtras(user);

                startActivity(intent);
            }
        });

        EstablishConnectionButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                new Thread(new Runnable() {
                    public void run() {
                        Looper.prepare();
                        doConnection();
                        Looper.loop();
                        Looper.myLooper().quit();
                    }
                }).start();
            }
        });

        DisconnectionButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                new Thread(new Runnable() {
                    public void run() {
                        Looper.prepare();
                        doDisconnection();
                        Looper.loop();
                        Looper.myLooper().quit();
                    }
                }).start();
            }
        });


        PrintTestButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                new Thread(new Runnable() {
                    public void run() {
                        Looper.prepare();
                        doConnectionTest();
                        Looper.loop();
                        Looper.myLooper().quit();
                    }
                }).start();
            }
        });

        SaveConnectionButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if (printer != null) {
                    UserPortableData[9]=MACAddressEditText.getText().toString();
                    UserPortableData[10]=PrinterNameEditText.getText().toString();
                    Bundle bluetooth = new Bundle();
                    bluetooth.putStringArray("BluetoothData", new String[]{"tryout", MACAddressEditText.getText().toString(), PrinterNameEditText.getText().toString()});
                    Bundle user = new Bundle();
                    user.putStringArray("UserPortableData", UserPortableData);
                    Intent intent = new Intent(BluetoothConnectActivity.this, DashboardActivity.class);
                    intent.putExtras(user);
                    intent.putExtras(bluetooth);

                    startActivity(intent);
                }
            }
        });

        if (printer != null) {
            setStatus("Connected", Color.GREEN);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (printerConnection != null && printerConnection.isConnected()) {
            //disconnect();
        }
    }

    private void enableTestButton(final boolean enabled) {
        runOnUiThread(new Runnable() {
            public void run() {
                PrintTestButton.setEnabled(enabled);
            }
        });
    }

    private boolean isBluetoothSelected() {
        return true;
    }

    public ZebraPrinter connect() {
        setStatus("Connecting...", Color.YELLOW);
        printerConnection = null;
        printerConnection = new BluetoothConnection(getMacAddressFieldText());
        SettingsHelper.saveBluetoothAddress(this, getMacAddressFieldText());

        try {
            printerConnection.open();
            setStatus("Connected", Color.GREEN);
        } catch (ConnectionException e) {
            setStatus("Comm Error! Disconnecting", Color.RED);
            DemoSleeper.sleep(1000);
            disconnect();
        }

        ZebraPrinter printer = null;

        if (printerConnection.isConnected()) {
            try {
                printer = ZebraPrinterFactory.getInstance(printerConnection);
                setStatus("Determining Printer Language", Color.YELLOW);
                PrinterLanguage pl = printer.getPrinterControlLanguage();
                setStatus("Printer Language " + pl, Color.BLUE);
            } catch (ConnectionException e) {
                setStatus("Unknown Printer Language", Color.RED);
                printer = null;
                DemoSleeper.sleep(1000);
                disconnect();
            } catch (ZebraPrinterLanguageUnknownException e) {
                setStatus("Unknown Printer Language", Color.RED);
                printer = null;
                DemoSleeper.sleep(1000);
                disconnect();
            }
        }

        return printer;
    }

    public void disconnect() {
        try {
            setStatus("Disconnecting", Color.RED);
            if (printerConnection != null) {
                printerConnection.close();
            }
            setStatus("Not Connected", Color.RED);
        } catch (ConnectionException e) {
            setStatus("COMM Error! Disconnected", Color.RED);
        } finally {
            enableTestButton(true);
        }
    }

    private void setStatus(final String statusMessage, final int color) {
        runOnUiThread(new Runnable() {
            public void run() {
                ConnectivityStatusTextView.setBackgroundColor(color);
                ConnectivityStatusTextView.setText(statusMessage);
            }
        });
        DemoSleeper.sleep(1000);
    }

    private String getMacAddressFieldText() {
        return MACAddressEditText.getText().toString();
    }

    private void doConnectionTest() {
        if (printer != null) {
            sendTestLabel();
        }
    }

    private void doConnection() {
        printer = connect();
    }

    private void doDisconnection() {
        if (printer != null) {
            disconnect();
        }
    }

    private void sendTestLabel() {
        try {
            byte[] configLabel = getConfigLabel();
            printerConnection.write(configLabel);
            setStatus("Sending Data", Color.BLUE);
            DemoSleeper.sleep(1500);
            if (printerConnection instanceof BluetoothConnection) {
                String friendlyName = ((BluetoothConnection) printerConnection).getFriendlyName();
                setStatus(friendlyName, Color.MAGENTA);
                DemoSleeper.sleep(500);
            }
        } catch (ConnectionException e) {
            setStatus(e.getMessage(), Color.RED);
        } finally {
        }
    }

    /*
    * Returns the command for a test label depending on the printer control language
    * The test label is a box with the word "TEST" inside of it
    *
    * _________________________
    * |                       |
    * |                       |
    * |        TEST           |
    * |                       |
    * |                       |
    * |_______________________|
    *
    *
    */
    private byte[] getConfigLabel() {
        PrinterLanguage printerLanguage = printer.getPrinterControlLanguage();

        byte[] configLabel = null;
        if (printerLanguage == PrinterLanguage.ZPL) {
            configLabel = "^XA^FO17,16^GB379,371,8^FS^FT65,255^A0N,135,134^FDTEST^FS^XZ".getBytes();
        } else if (printerLanguage == PrinterLanguage.CPCL) {
            String cpclConfigLabel = "! 0 200 200 406 1\r\n" + "ON-FEED IGNORE\r\n" + "BOX 20 20 380 380 8\r\n" + "T 0 6 137 177 TEST\r\n" + "PRINT\r\n";
            configLabel = cpclConfigLabel.getBytes();
        }
        return configLabel;
    }

}
