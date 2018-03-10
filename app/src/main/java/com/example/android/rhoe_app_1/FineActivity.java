package com.example.android.rhoe_app_1;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.icu.util.TimeZone;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.app_v12.R;
import com.example.android.rhoe_app_1.FirebaseFine.FineAInfoFirebase;
import com.example.android.rhoe_app_1.FirebaseFine.FineBInfoFirebase;
import com.example.android.rhoe_app_1.FirebaseFine.FineBasicInfoFirebase;
import com.example.android.rhoe_app_1.FirebaseFine.FineCInfoFirebase;
import com.example.android.rhoe_app_1.FirebaseFine.FineDInfoFirebase;
import com.example.android.rhoe_app_1.FirebaseUsers.RetrieveUserInfoFirebase;
import com.example.android.rhoe_app_1.Zebra.DemoSleeper;
import com.example.android.rhoe_app_1.Zebra.SettingsHelper;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;


public class FineActivity extends AppCompatActivity implements LocationListener {

    DatabaseReference databaseReference;
    DatabaseReference userDatabaseReference;
    private FirebaseAuth firebaseAuth;
    private String userID;
    private String MunicipalityIndex;

    private ImageButton FineInfoButton;
    private ImageButton FineClearButton;
    private ImageButton OCRButton;
    private ImageButton TimeStampButton;
    private Button FineAdvancedButton;
    private Button FineCommentsButton;
    private Button FineConfirmButton;

    private EditText PlateEditText;
    private Spinner TypeEditText;
    private EditText ColorEditText;
    private EditText DateEditText;
    private EditText DayEditText;
    private EditText TimeEditText;
    private EditText AddressEditText;
    private AutoCompleteTextView FineTypeAutoCompl;
    private AutoCompleteTextView BrandAutoCompl;
    private EditText FineAmountEditText;
    private EditText FinePointsEditText;

    private TextView ConnectivityStatusFineTextView;

    private ZebraPrinter printer;
    private Connection printerConnection;

    Calendar calendar;
    SimpleDateFormat simpleDateFormat;
    String Date;

    SimpleDateFormat simpleDateFirebaseFormat;
    String DateFirebase;

    SimpleDateFormat simpleTimeFormat;
    String Time;

    SimpleDateFormat simpleDayFormat;
    String Day;


    EditText LocationEditText;

    LocationManager locationManager;
    String provider;
    final int MY_PERMISSION_REQUEST_CODE = 7171;
    double lat, lng;

    private String[] FineType;
    private String[] FineAmount;
    private String[] FinePoints;
    private String[] CarBrand;

    private String[] FineBasic = new String[10];
    private String[] A = new String[6];
    private String[] B = new String[6];
    private String[] C = new String[8];
    private String[] D = new String[5];
    //private String[] FineBasicPortable = new String[7];



    @Override
    protected void onResume() {
        super.onResume();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        locationManager.requestLocationUpdates(provider, 2400, 1, this);


    }

    // Former Location Processing - Obsolete
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    getLocation();
                break;

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fine);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Firebase Auth and Database
        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() ==null){
            //profile activity
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseUser user =firebaseAuth.getCurrentUser();
        userID = user.getUid();
        userDatabaseReference = FirebaseDatabase.getInstance().getReference("Users");
        userDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                showData(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //Imported Bundles
        final Bundle con = this.getIntent().getExtras();
        final boolean Condition = con.getBoolean("Condition");
        Bundle con1 = this.getIntent().getExtras();
        final boolean Condition1 = con1.getBoolean("Condition1");

        //Condition for Fine_Advanced
        if(Condition1){
            if(Condition){
                A = new String[]{"", "", "", "", "", ""};
                B = new String[]{"", "", "", "", "", ""};
                C = new String[]{"", "", "", "", "", "", "", ""};
                D = new String[]{"", "", "", "", ""};
            }
            else{
                final Bundle Atable = this.getIntent().getExtras();
                final String[] Atemp = Atable.getStringArray("Atemp");
                final Bundle Btable = this.getIntent().getExtras();
                final String[] Btemp = Btable.getStringArray("Btemp");
                final Bundle Ctable = this.getIntent().getExtras();
                final String[] Ctemp = Ctable.getStringArray("Ctemp");
                final Bundle Dtable = this.getIntent().getExtras();
                final String[] Dtemp = Dtable.getStringArray("Dtemp");
                A = Atemp;
                B = Btemp;
                C = Ctemp;
                D = Dtemp;
            }
        }

        ConnectivityStatusFineTextView =(TextView) findViewById(R.id.tvConnectivityStatusFine);

        FineInfoButton = (ImageButton) findViewById(R.id.btnFineInfo);
        FineClearButton = (ImageButton) findViewById(R.id.btnFineClear);
        OCRButton = (ImageButton) findViewById(R.id.btnOCR);
        TimeStampButton = (ImageButton) findViewById(R.id.btnTimestamp);

        PlateEditText = (EditText)findViewById(R.id.etLiscencePlate);
        TypeEditText = (Spinner)findViewById(R.id.spCarType);
        ColorEditText = (EditText)findViewById(R.id.etColor);
        DateEditText = (EditText)findViewById(R.id.etDate);

        DayEditText = (EditText)findViewById(R.id.etDay);
        TimeEditText = (EditText)findViewById(R.id.etTime);
        AddressEditText = (EditText)findViewById(R.id.etAddress);

        FineTypeAutoCompl = (AutoCompleteTextView) findViewById(R.id.acViolation);
        FineType = getResources().getStringArray(R.array.autoComplViolations);
        ArrayAdapter<String> adapterType = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, FineType);
        FineTypeAutoCompl.setAdapter(adapterType);

        FineAmountEditText = (EditText) findViewById(R.id.etFineAmmount);
        FineAmount = getResources().getStringArray(R.array.autoComplViolationPrice);
        ArrayAdapter<String> adapterAmount = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, FineAmount);

        FinePointsEditText = (EditText) findViewById(R.id.etPoints);
        FinePoints = getResources().getStringArray(R.array.autoComplViolationPoints);
        ArrayAdapter<String> adapterPoints = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, FinePoints);

        FineTypeAutoCompl.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (Arrays.asList(FineType).indexOf(FineTypeAutoCompl.getText().toString())>= 0) {
                    FineAmountEditText.setText(FineAmount[Arrays.asList(FineType).indexOf(FineTypeAutoCompl.getText().toString())]);
                    FinePointsEditText.setText(FinePoints[Arrays.asList(FineType).indexOf(FineTypeAutoCompl.getText().toString())]);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        BrandAutoCompl = (AutoCompleteTextView) findViewById(R.id.acBrand);
        CarBrand = getResources().getStringArray(R.array.autoComplBrands);
        ArrayAdapter<String> adapterBrand = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, CarBrand);
        BrandAutoCompl.setAdapter(adapterBrand);


        LocationEditText = (EditText) findViewById(R.id.etAddress);

        FineInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFineDetails(FineActivity.this);
            }
        });

        FineClearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FineTypeAutoCompl.setText("");
                FineAmountEditText.setText("");
                FinePointsEditText.setText("");

            }
        });

        OCRButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FineActivity.this, OCRActivity.class);
                startActivity(intent);
            }
        });

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
            }, MY_PERMISSION_REQUEST_CODE);

        } else {
            getLocation();
        }



        TimeStampButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+2"));
                Date = simpleDateFormat.format(calendar.getTime());

                simpleDateFirebaseFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss:SSSZ");
                simpleDateFirebaseFormat.setTimeZone(TimeZone.getTimeZone("GMT+2"));
                DateFirebase = simpleDateFirebaseFormat.format(calendar.getTime());

                simpleTimeFormat = new SimpleDateFormat("HH:mm");
                simpleTimeFormat.setTimeZone(TimeZone.getTimeZone("GMT+2"));
                Time = simpleTimeFormat.format(calendar.getTime());

                Locale locale = new Locale("el-GR");
                simpleDayFormat = new SimpleDateFormat("EEEE", locale);
                simpleDayFormat.setTimeZone(TimeZone.getTimeZone("GMT+2"));
                Day = simpleDayFormat.format(calendar.getTime());

                DateEditText.setText(Date);
                TimeEditText.setText(Time);
                DayEditText.setText(Day);

                // Former Location Processing - Obsolete
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                    return;
                }
                Location myLocation = locationManager.getLastKnownLocation(provider);
                lat = myLocation.getLatitude();
                lng = myLocation.getLongitude();
                new GetAddress().execute(String.format("%.4f,%.4f",lat,lng));
            }
        });



        /*
         * Bottom Buttons
         */



        FineAdvancedButton =(Button)findViewById(R.id.btnFineAdvanced);
        FineCommentsButton =(Button)findViewById(R.id.btnFineComments);
        FineConfirmButton =(Button)findViewById(R.id.btnFineConfirm);

        FineAdvancedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //FineBasicPortable String[] =
                Intent intent = new Intent(FineActivity.this, FineAdvancedActivity.class);
                startActivity(intent);
            }
        });

        FineConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                FineBasic = new String[]{PlateEditText.getText().toString(),
                        TypeEditText.getSelectedItem().toString(),
                        BrandAutoCompl.getText().toString(),
                        ColorEditText.getText().toString(),
                        DateEditText.getText().toString(),
                        FineAmountEditText.getText().toString(),
                        DayEditText.getText().toString(),
                        TimeEditText.getText().toString(),
                        AddressEditText.getText().toString(),
                        FineTypeAutoCompl.getText().toString()
                };


                if ((FineBasic[0].length() != 0) &&
                        (FineBasic[1].length() != 0) &&
                        (FineBasic[2].length() != 0) &&
                        (FineBasic[3].length() != 0) &&
                        (FineBasic[4].length() != 0) &&
                        (FineBasic[5].length() != 0) &&
                        (FineBasic[6].length() != 0) &&
                        (FineBasic[7].length() != 0) &&
                        (FineBasic[8].length() != 0) &&
                        (FineBasic[9].length() != 0) &&
                        DateFirebase != null) {

                    FirebaseUser userFirebase =firebaseAuth.getCurrentUser();

                    FineBasicInfoFirebase fineBasicInfoFirebase = new FineBasicInfoFirebase(FineBasic[0],
                            FineBasic[1],
                            FineBasic[2],
                            FineBasic[3],
                            FineBasic[4],
                            FineBasic[5],
                            FineBasic[6],
                            FineBasic[7],
                            FineBasic[8],
                            FineBasic[9],
                            userFirebase.getUid());

                    databaseReference.child(DateFirebase).child("Basic Fine").setValue(fineBasicInfoFirebase);

                    if (Condition) {

                    } else {
                        FineAInfoFirebase fineAInfoFirebase = new FineAInfoFirebase(A[0], A[1], A[2], A[3], A[4], A[5]);
                        FineBInfoFirebase fineBInfoFirebase = new FineBInfoFirebase(B[0], B[1], B[2], B[3], B[4], B[5]);
                        FineCInfoFirebase fineCInfoFirebase = new FineCInfoFirebase(C[0], C[1], C[2], C[3], C[4], C[5], C[6], C[7]);
                        FineDInfoFirebase fineDInfoFirebase = new FineDInfoFirebase(D[0], C[1], C[2], C[3], C[4]);

                        databaseReference.child(DateFirebase).child("Fine A").setValue(fineAInfoFirebase);
                        databaseReference.child(DateFirebase).child("Fine B").setValue(fineBInfoFirebase);
                        databaseReference.child(DateFirebase).child("Fine C").setValue(fineCInfoFirebase);
                        databaseReference.child(DateFirebase).child("Fine D").setValue(fineDInfoFirebase);
                    }

                    //Printer
                    /*
                    new Thread(new Runnable() {
                        public void run() {
                            Looper.prepare();
                            try {
                                doConnectionTest();
                            } catch (ConnectionException e) {
                                e.printStackTrace();
                            }
                            Looper.loop();
                            Looper.myLooper().quit();
                        }
                    }).start();*/

                    Intent intent = new Intent(FineActivity.this, DashboardActivity.class);
                    startActivity(intent);

                } else {
                    toastMessage("You must complete all the fields!");
                }
            }
        });
    }

    public void showFineDetails(FineActivity view) {
        AlertDialog.Builder myAlert = new AlertDialog.Builder(this);
        myAlert.setMessage(Html.fromHtml("<b>Περιγραφή Παράβασης:</b>" + FineTypeAutoCompl.getText().toString() + "\n" +
                "<b>Χρηματικό πρόστιμο:</b>" + FineAmountEditText.getText().toString() + "\n" +
                "<b>Βαθμοί Σ.Ε.Σ.Ο.:</b>" + FinePointsEditText.getText().toString() + "\n" +
                "<b>Αφαίρεση ΣΚ</b>" + "" + "\n")).create();
        myAlert.show();
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

            MunicipalityIndex = RUInfo.getMunicipality();
            databaseReference= FirebaseDatabase.getInstance().getReference("Fines").child(MunicipalityIndex);
        }
    }

    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }

    //Former Location Processing - Obsolete

    private void getLocation() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        provider = locationManager.getBestProvider(new Criteria(), false);


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        final Location location = locationManager.getLastKnownLocation(provider);
        if(location == null)
            Log.e("ERROR","Location is null");
    }


    @Override
    public void onLocationChanged(Location location) {
        lat = location.getLatitude();
        lng = location.getLongitude();

        new GetAddress().execute(String.format("%.4f,%.4f",lat,lng));
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    private class GetAddress extends AsyncTask<String,Void,String> {

        ProgressDialog dialog = new ProgressDialog(FineActivity.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setMessage("Please wait...");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            try{
                double lat = Double.parseDouble(strings[0].split(",")[0]);
                double lng = Double.parseDouble(strings[0].split(",")[1]);
                String response;
                HttpDataHandler http = new HttpDataHandler();
                String url = String.format("https://maps.googleapis.com/maps/api/geocode/json?latlng=%.4f,%.4f&sensor=false&language=el",lat,lng);
                //String url = String.format("https://maps.googleapis.com/maps/api/geocode/json?latlng=%.4f,%.4f&sensor=false",lat,lng);
                response = http.GetHTTPData(url);
                return response;
            }
            catch (Exception ex)
            {

            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            try{
                JSONObject jsonObject = new JSONObject(s);

                String address = ((JSONArray)jsonObject.get("results")).getJSONObject(0).get("formatted_address").toString();
                LocationEditText.setText(address);


            } catch (JSONException e) {
                e.printStackTrace();
            }

            if(dialog.isShowing())
                dialog.dismiss();
        }
    }

    //Label Printing

    private void doConnectionTest() throws ConnectionException {
        printer = connect();
        if (printer != null) {
            storeFormat();
            printFormat();

            //sendTestLabel();
        } else {
            disconnect();
        }
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
        }
    }

    private String getMacAddressFieldText() {
        return "CC78AB3EB08A";
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
            //configLabel = ""
            //configLabel = "^XA~TA000~JSN^LT0^MNW^MTD^PON^PMN^LH0,0^JMA^PR5,5~SD10^JUS^LRN^CI0^XZ,^XA,MMT,^PW561,^LL1199,^LS0,^FO0,0^GFA,02048,02048,00016,:Z64:,eJzdVTuO2zAQdROkCDZ7OkGVwVMYrAj6Xix4CkEVwRuIlcCCoCbz4UdOlzbCrncfnt78Z/x4/OfPN/CT5M+74VrXD+z5Z+DTZIg3nLqFhq09z5u+FPDlps97NlpPnI4jiQHBQQUI2g6MYicGGJ8ZqQpPO+MDuK6h14siJyfceQpDcM0CQ2380U0IztYK3i3j0vVe+Njx+Zf95s+oBvAf1vuGnfC2vxyYv47xNP9r41t+Xd7ir3VfFgvbshqJL7ESP7ACxMddXs/WqK73LHfEb8D6c1l7/VJ3kBLiJwx9zZKfcx6Kc+D9p/0N+d+p6y/JP9/sR8Ru6ot7P77ytA8W9Rx6+yD/cfKv8H78cjDiZ/8w7e+G/KfuX/KHqT8r6v30Dzc91Q+oPkPf+2f33r8I3X9rAMWvBm9BU/8+/c/8yBDyfurBUf3U4G34rN+B7/9UU/8KuJ9lGvAF9cv0H3fkr1k/6t8Pzk9RfjarR/Mv9aP4xX6U/ire/yNR6Q/Zn59w47dPPZB/Pe1XLfsz+nfR/MShV6vUf+i99LfrT8z/+5j1I/9fu+56k9v8jvlzPL+52+f6z/7DRfOjTW72c+T6l9KOl8x/2F+iD1hA1oPkT33g/auW3z7Dytj5rmY9rv2yrPS7ayv7gecLtxiug+cf1xq2l6ITRbjI5HD75H6Qc9rx08r+0wv3+4tSCrDiibzfl9L4bJjHG8r5oduLs+/xRw043zHnp9wHTN5zCXzTZ12tqXA+x/27ehHFn8lr0GHLDePg4ON85+0eNhzOPY77d3EJOp8rKFPXeX9J6ub9xszrluP0NwvQMJZI3TEmcP/+gIqx3/UpfeqDBWliw+OEv//16/EPIa1IeQ==:4946,^FO96,0^GFA,02304,02304,00036,:Z64:,eJztkrFqG0EQhne1lzuJHKdVEdhCIRsMqSfg4iBgz2JQldaQIsUavYDeICOdUVwYk9KlH0Gly1Wu8QMkbZhwpSEknRODLutTXiGkiH6Y7ufj42eE2GWXXf51WmJFwTLUVb8QhSDmjO56xSGZ4caarnMsQkboZ9qlJhGZJBcy+SXNn4Txo0847josrzJiewdNYQoxjJyr4VL1DPgDwfag66CEJCC8ATeOnEHkrAaJSrWGfYF+v+sEtcpDsDdQl9FnQMTXj8/lAkr7QoR4W45OEP1r7SByRnNyH0a5PEUNE4E46Tq/VNlntt+g8dGnrKi5fHWmFgx2Gn2mXeddzxh/PJuAw6dvhXaf15cvjXwftD+KPkdbn7RvbMsfoebhRtjQ8vnemViEw1DH2WrqfJJcA3q39bGO3KnryyU+E2sRcL3dpyhKYNusmjL6WE98MY0+AOI2znb7h5OARnArZyIHT8gtXS5T0LINeNKGzqdf2DLY8L3O4z5siRf1hVqYUlIIz6nroEm8QY8zlzxwRuSqeZpFSRnncQIfOvfXPd9ny7NGRZ/7n/S1qqpNrwAlmPcEd/tohTlCNFKR0/4gN5dylqZaCY9T4f/GP+6yy/+Q3/0toCA=:4642,^FO96,32^GFA,03840,03840,00060,:Z64:,eJztlLGq3EYUhs/RGWsUGGZlSDEGQRb8AnMbM4W5dxabpPEDuEih5AnkIuBudVeGdWGuXbr0I7h0KXFNSBHIK0y8jQvjbDoVyypndLVpHIi5lTH7g0Azmn9+fTpnBHDUUUcdddRXJH19K72F6rDJvp41exj46jWuaEUX0EMIWfKeeuj5ej/0IQtpmZHT4dZw+Ss/HyVh2Z51AwzrndwJXKOUgvfdVkryBO1xoN2y96oUVUFGlTeHyxTMlXdWhxA2TVsnoLXm1JXWnPa6z7ROeKJp1oogZFbb08QYa6EhcFfeb8D7FqFrCQoU2CApAT+ifaiMJFKImAKBL6ywRaqMrZC9U+4tzm2pDpzrYOQ1Gj7Qqw+Z45dQ2JxfQMx1yp1q7VzJs/XkvVn7RY1QthJyGHlzAfepuq9ylGSgwycxV+Uq/1aklfGS8OB1bdjUVNuawALjUWM1fEzsx8zywMGmWV/lGndHU9AhJaonXut9x7knbfSOvKWA71P7g7LYSM4laiOvMea2oPZGKwjaKXce/mpi7kD7+X42rKgJGt7p+ebpHFfaweUeQ8zNXPazonZWq+Rfb+mXHSDYQe6qpRyY1wu4J6qFqnC9y6HbwePIy8CLDPmrGB4dvOXEm4BNxvpWGjbP7OYp8/bMewGnMVdbtXlJ/AqORxPvL5V/xLyPIq8cea2AhbGL35mXC9lJEJFXWLEwsuRVBOWUO2zDCeeWsb7JWF+n4c+X8/Ab15e9TcNfP+aWunujozGB+eRtc3/CfeUjiRzry321eFH5B1xfbrVzHHNN6kX3QuR8y9EHrws23Oa+IuYa68v9HLY2PHaSYsGbmnNLRyE9f60jLHuveLlHfFn9hLGfZ5GXiM+Rryp/t0gJpER8UvBqS62st6Yg5IW5OXiHcLYZ6mEFsz3zniX0vA9hHtxslwANTf1HT3AWKDa904TDOzCTF4olfIc7Pr+AW+YtJMre+8ob3ErAv7vxiMk25nkr2Ht58P6v+k//OZC5z/Pm/+EVn5n7qahWb67tbUV1Xe9RRx31JegfU8kNnQ==:7E61^PQ1,0,1,Y^XZ".getBytes();
        } else if (printerLanguage == PrinterLanguage.CPCL) {
            String cpclConfigLabel = "! 0 200 200 406 1\r\n" + "ON-FEED IGNORE\r\n" + "BOX 20 20 380 380 8\r\n" + "T 0 6 137 177 TEST\r\n" + "PRINT\r\n";
            configLabel = cpclConfigLabel.getBytes();
        }
        return configLabel;
    }

    private void setStatus(final String statusMessage, final int color) {
        runOnUiThread(new Runnable() {
            public void run() {
                ConnectivityStatusFineTextView.setBackgroundColor(color);
                ConnectivityStatusFineTextView.setText(statusMessage);
            }
        });
        DemoSleeper.sleep(1000);
    }

    private void storeFormat() throws ConnectionException {
        printer.sendFileContents("sdcard/Download/test1.zpl");
    }

    private void printFormat() throws ConnectionException {
        String name ="TSONIOTIS";
        String format = "Z:test1.zpl";
        Map<Integer, String> vars = new HashMap<Integer, String>();
        vars.put(1, name);
        printer.printStoredFormat(format, vars);
    }

    private void sendFineZebra() {
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
        }
    }

    private byte[] getConfigFineZebra() {
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





