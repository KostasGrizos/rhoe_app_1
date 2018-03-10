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
import com.example.android.rhoe_app_1.FirebaseUsers.RetrieveUserInfoFirebase;
import com.example.android.rhoe_app_1.Zebra.BluetoothDiscovery;
import com.example.android.rhoe_app_1.Zebra.SettingsHelper;
import com.example.android.rhoe_app_1.Zebra.ConnectionMaintain;
import com.example.android.rhoe_app_1.Zebra.DemoSleeper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.zebra.sdk.comm.BluetoothConnection;
import com.zebra.sdk.comm.Connection;
import com.zebra.sdk.comm.ConnectionException;
import com.zebra.sdk.printer.PrinterLanguage;
import com.zebra.sdk.printer.ZebraPrinter;
import com.zebra.sdk.printer.ZebraPrinterFactory;
import com.zebra.sdk.printer.ZebraPrinterLanguageUnknownException;

public class DashboardActivity extends AppCompatActivity {

    private ImageButton LogoutButton;
    private ImageButton ViewUsersButton;
    private ImageButton NewFineButton;
    private ImageButton SettingsButton;
    private TextView NameText, SurnameText, MunicipalityText, MunicipalIDText;
    private TextView ConnectivityStatusDashTextView;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseUser user =firebaseAuth.getCurrentUser();
        userID = user.getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                showData(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        ConnectivityStatusDashTextView = (TextView)findViewById(R.id.tvConnectivityStatusDash);

        LogoutButton = (ImageButton)findViewById(R.id.btnLogout);
        ViewUsersButton = (ImageButton)findViewById(R.id.btnViewUsers);
        NewFineButton = (ImageButton)findViewById(R.id.btnNewFine);
        SettingsButton = (ImageButton)findViewById(R.id.btnSettings);

        NameText = (TextView) findViewById(R.id.txtNameView);
        SurnameText = (TextView) findViewById(R.id.txtSurnameView);
        MunicipalityText = (TextView) findViewById(R.id.txtMunicipalityView);
        MunicipalIDText = (TextView) findViewById(R.id.txtMunicipalIDView);

        //Printer Status
        /*if (UserPortableData[9].equals("0")) setStatus("Status: Τώρα μπήκες", Color.CYAN);
        else{
            setStatus("Attempt", Color.CYAN);
            BTM.MaintainConnection(UserPortableData[9], ConnectivityStatusDashTextView);
        }*/

        LogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                firebaseAuth.signOut();
                finish();
                Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
                startActivity(intent);

            }
        });

        ViewUsersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this, EditUserActivity.class);
                startActivity(intent);
            }
        });


        NewFineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle con = new Bundle();
                con.putBoolean("Condition", true);
                Bundle con1 = new Bundle();
                con1.putBoolean("Condition1", true);
                Bundle con2 = new Bundle();
                con2.putBoolean("Condition1", true);


                Intent intent = new Intent(DashboardActivity.this, FineActivity.class);
                intent.putExtras(con);
                intent.putExtras(con1);
                intent.putExtras(con2);

                startActivity(intent);
            }
        });

        SettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //TO BE CHANGED AFTER EXTRA SETTINGS!!!
                Intent intent = new Intent(DashboardActivity.this, BluetoothConnectActivity.class);
                startActivity(intent);
            }
        });
    }

    private void showData (DataSnapshot dataSnapshot) {
        for (DataSnapshot ds : dataSnapshot.getChildren()){
            RetrieveUserInfoFirebase RUInfo = new RetrieveUserInfoFirebase();
            RUInfo.setFname(dataSnapshot.child(userID).getValue(RetrieveUserInfoFirebase.class).getFname());
            RUInfo.setLname(dataSnapshot.child(userID).getValue(RetrieveUserInfoFirebase.class).getLname());
            RUInfo.setMID(dataSnapshot.child(userID).getValue(RetrieveUserInfoFirebase.class).getMID());
            RUInfo.setMunicipality(dataSnapshot.child(userID).getValue(RetrieveUserInfoFirebase.class).getMunicipality());
            RUInfo.setSignatureNum(dataSnapshot.child(userID).getValue(RetrieveUserInfoFirebase.class).getSignatureNum());
            RUInfo.setType(dataSnapshot.child(userID).getValue(RetrieveUserInfoFirebase.class).getType());

            NameText.setText(RUInfo.getFname());
            SurnameText.setText(RUInfo.getLname());
            MunicipalityText.setText(RUInfo.getMunicipality());
            MunicipalIDText.setText(RUInfo.getMID());
        }
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
