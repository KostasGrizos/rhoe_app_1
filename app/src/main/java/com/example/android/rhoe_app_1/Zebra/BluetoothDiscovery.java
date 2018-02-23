package com.example.android.rhoe_app_1.Zebra;

/**
 * Created by User on 22/02/2018.
 */

import android.os.Bundle;
import android.os.Looper;

import com.zebra.sdk.comm.ConnectionException;
import com.zebra.sdk.printer.discovery.BluetoothDiscoverer;
import com.example.android.rhoe_app_1.BluetoothFindActivity;

public class BluetoothDiscovery extends BluetoothFindActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new Thread(new Runnable() {
            public void run() {
                Looper.prepare();
                try {
                    BluetoothDiscoverer.findPrinters(BluetoothDiscovery.this, BluetoothDiscovery.this);
                } catch (ConnectionException e) {
                    new UIHelper(BluetoothDiscovery.this).showErrorDialogOnGuiThread(e.getMessage());
                } finally {
                    Looper.myLooper().quit();
                }
            }
        }).start();
    }

}