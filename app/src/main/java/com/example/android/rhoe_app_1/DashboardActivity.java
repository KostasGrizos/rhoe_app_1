package com.example.android.rhoe_app_1;

import android.content.Intent;
import android.graphics.Color;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.android.app_v12.R;
import com.example.android.rhoe_app_1.Zebra.BluetoothDiscovery;
import com.example.android.rhoe_app_1.Zebra.SettingsHelper;
import com.example.android.rhoe_app_1.Zebra.ConnectionMaintain;
import com.example.android.rhoe_app_1.Zebra.DemoSleeper;
import com.zebra.sdk.comm.BluetoothConnection;
import com.zebra.sdk.comm.Connection;
import com.zebra.sdk.comm.ConnectionException;
import com.zebra.sdk.printer.PrinterLanguage;
import com.zebra.sdk.printer.ZebraPrinter;
import com.zebra.sdk.printer.ZebraPrinterFactory;
import com.zebra.sdk.printer.ZebraPrinterLanguageUnknownException;

public class DashboardActivity extends AppCompatActivity {

    ConnectionMaintain BTM;

    private ImageButton ViewUsersButton;
    private ImageButton NewFineButton;
    private ImageButton SettingsButton;
    private TextView NameText, SurnameText, MunicipalityText, MunicipalIDText;
    private TextView ConnectivityStatusDashTextView;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        ConnectivityStatusDashTextView = (TextView)findViewById(R.id.tvConnectivityStatusDash);

        ViewUsersButton = (ImageButton)findViewById(R.id.btnViewUsers);
        NewFineButton = (ImageButton)findViewById(R.id.btnNewFine);
        SettingsButton = (ImageButton)findViewById(R.id.btnSettings);

        /*
         * Showing the Name of the user
         */
        NameText = (TextView)findViewById(R.id.txtNameView);
        SurnameText = (TextView)findViewById(R.id.txtSurnameView);
        MunicipalityText = (TextView)findViewById(R.id.txtMunicipalityView);
        MunicipalIDText = (TextView)findViewById(R.id.txtMunicipalIDView);

        Bundle user = this.getIntent().getExtras();
        final String[] UserPortableData = user.getStringArray("UserPortableData");

        NameText.setText(UserPortableData[4]);
        SurnameText.setText(UserPortableData[5]);
        MunicipalityText.setText(UserPortableData[7]);
        MunicipalIDText.setText("Κωδικός ΟΤΑ: " + UserPortableData[8]);


        if (UserPortableData[9].equals("0")) setStatus("Status: Τώρα μπήκες", Color.CYAN);
        else{
            setStatus("Attempt", Color.CYAN);
            BTM.MaintainConnection(UserPortableData[9], ConnectivityStatusDashTextView);
        }


        ViewUsersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle user = new Bundle();
                user.putStringArray("UserPortableData", UserPortableData);

                Intent intent = new Intent(DashboardActivity.this, UserListActivity.class);
                intent.putExtras(user);

                startActivity(intent);
            }
        });


        NewFineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle user = new Bundle();
                user.putStringArray("UserPortableData", UserPortableData);

                Bundle con = new Bundle();
                con.putBoolean("Condition", true);
                Bundle con1 = new Bundle();
                con1.putBoolean("Condition1", true);

                Intent intent = new Intent(DashboardActivity.this, FineActivity.class);
                intent.putExtras(user);
                intent.putExtras(con);
                intent.putExtras(con1);

                startActivity(intent);
            }
        });

        SettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle user = new Bundle();
                user.putStringArray("UserPortableData", UserPortableData);

                //TO BE CHANGED AFTER EXTRA SETTINGS!!!
                Intent intent = new Intent(DashboardActivity.this, BluetoothConnectActivity.class);
                intent.putExtras(user);

                startActivity(intent);
            }
        });
    }

    private void setStatus(final String statusMessage, final int color) {
        runOnUiThread(new Runnable() {
            public void run() {
                ConnectivityStatusDashTextView.setBackgroundColor(color);
                ConnectivityStatusDashTextView.setText(statusMessage);
            }
        });
        DemoSleeper.sleep(1000);
    }


}
