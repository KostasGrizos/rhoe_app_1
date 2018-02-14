package com.example.android.rhoe_app_1;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.icu.util.TimeZone;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.android.app_v12.R;

import java.util.Locale;


public class FineActivity extends AppCompatActivity /*implements LocationListener*/{

    FineDatabaseHelper FineDB;

    private ImageButton OCRButton;
    private ImageButton TimeStampButton;
    private Button FineAdvancedButton;
    private Button FineCommentsButton;
    private Button FineConfirmButton;

    private EditText PlateEditText;
    private Spinner TypeEditText;
    private EditText BrandEditText;
    private EditText ColorEditText;
    private EditText DateEditText;
    private String FineAmountEditText;
    private EditText DayEditText;
    private EditText TimeEditText;
    private EditText AddressEditText;
    private Spinner FineTypeEditText;

    Calendar calendar;
    SimpleDateFormat simpleDateFormat;
    String Date;

    SimpleDateFormat simpleTimeFormat;
    String Time;

    SimpleDateFormat simpleDayFormat;
    String Day;


    EditText LocationEditText;

    LocationManager locationManager;
    String provider;
    final int MY_PERMISSION_REQUEST_CODE = 7171;
    double lat, lng;

    private String[] FineBasic = new String[10];

    private String[] A = new String[6];
    private String[] B = new String[6];
    private String[] C = new String[8];
    private String[] D = new String[5];



    @Override
    protected void onResume() {
        super.onResume();
        /* Former Location Processing - Obsolete
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {


            return;
        }
        locationManager.requestLocationUpdates(provider, 400, 1, this);*/
    }
    /* Former Location Processing - Obsolete
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    getLocation();
                break;

        }
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fine);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FineDB = new FineDatabaseHelper(this);

        //Imported Bundles
        final Bundle user = this.getIntent().getExtras();
        final String[] UserPortableData = user.getStringArray("UserPortableData");
        Bundle con = this.getIntent().getExtras();
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

        /*
         * OCR and Timestamp Buttons
         */

        OCRButton = (ImageButton) findViewById(R.id.btnOCR);
        TimeStampButton = (ImageButton) findViewById(R.id.btnTimestamp);


        PlateEditText = (EditText)findViewById(R.id.etLiscencePlate);
        TypeEditText = (Spinner)findViewById(R.id.spCarType);
        BrandEditText = (EditText)findViewById(R.id.etBrand);
        ColorEditText = (EditText)findViewById(R.id.etColor);
        DateEditText = (EditText)findViewById(R.id.etDate);

        DayEditText = (EditText)findViewById(R.id.etDay);
        TimeEditText = (EditText)findViewById(R.id.etTime);
        AddressEditText = (EditText)findViewById(R.id.etAddress);
        FineTypeEditText = (Spinner) findViewById(R.id.spViolation);





        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+2"));
        Date = simpleDateFormat.format(calendar.getTime());

        simpleTimeFormat = new SimpleDateFormat("HH:mm");
        simpleTimeFormat.setTimeZone(TimeZone.getTimeZone("GMT+2"));
        Time = simpleTimeFormat.format(calendar.getTime());

        Locale locale = new Locale("el-GR");
        simpleDayFormat = new SimpleDateFormat("EEEE", locale);
        simpleDayFormat.setTimeZone(TimeZone.getTimeZone("GMT+2"));
        Day = simpleDayFormat.format(calendar.getTime());

        LocationEditText = (EditText) findViewById(R.id.etAddress);


        OCRButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FineActivity.this, OCRActivity.class);
                startActivity(intent);
            }
        });

        /* Former Location Processing - Obsolete
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
            }, MY_PERMISSION_REQUEST_CODE);

        } else {
            getLocation();
        }
        */


        TimeStampButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DateEditText.setText(Date);
                TimeEditText.setText(Time);
                DayEditText.setText(Day);

                /* Former Location Processing - Obsolete
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                    return;
                }
                Location myLocation = locationManager.getLastKnownLocation(provider);
                lat = myLocation.getLatitude();
                lng = myLocation.getLongitude();
                new GetAddress().execute(String.format("%.4f,%.4f",lat,lng));*/
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

                Intent intent = new Intent(FineActivity.this, FineAdvancedActivity.class);
                intent.putExtras(user);
                startActivity(intent);
            }
        });

        FineConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(FineTypeEditText.getSelectedItem().toString()=="Διάβαση") {
                    FineAmountEditText = "40";
                }
                else if(FineTypeEditText.getSelectedItem().toString()=="Διπλή Στάθμευση") {
                    FineAmountEditText = "80";
                }
                else if(FineTypeEditText.getSelectedItem().toString()=="Θέση ΑΜΕΑ") {
                    FineAmountEditText = "100";
                }
                else if(FineTypeEditText.getSelectedItem().toString()=="Πεζόδρομος") {
                    FineAmountEditText = "120";
                }



                FineBasic = new String[]{PlateEditText.getText().toString(),
                        TypeEditText.getSelectedItem().toString(),
                        BrandEditText.getText().toString(),
                        ColorEditText.getText().toString(),
                        DateEditText.getText().toString(),
                        FineAmountEditText,
                        DayEditText.getText().toString(),
                        TimeEditText.getText().toString(),
                        AddressEditText.getText().toString(),
                        FineTypeEditText.getSelectedItem().toString()
                };


                if ((FineBasic[0].length() != 0) &&
                        (FineBasic[1].length() != 0) &&
                        (FineBasic[2].length() != 0) &&
                        (FineBasic[3].length() != 0) &&
                        (FineBasic[4].length() != 0) &&
                        (FineBasic[6].length() != 0) &&
                        (FineBasic[7].length() != 0) &&
                        (FineBasic[8].length() != 0) &&
                        (FineBasic[9].length() != 0)) {
                    AddData(UserPortableData, FineBasic, A, B, C, D);

                    Intent intent = new Intent(FineActivity.this, DashboardActivity.class);
                    startActivity(intent);

                } else {
                    toastMessage("You must complete all the fields!");
                }
            }
        });


    }
    public void AddData(String[] User,
                        String[] FineBasic,
                        String[] A,
                        String[] B,
                        String[] C,
                        String[] D) {
        boolean insertData = FineDB.addDataFine(User, FineBasic, A, B, C, D);

        if (insertData) {
            toastMessage("Fine Successfully Submitted");
        } else {
            toastMessage("Something went wrong!");
        }
    }

    /**
     * customizable toast
     * @param message
     */

    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
}

    /*
     * Former Location Processing - Obsolete

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

        new GetAddress().execute(String.format("%.4f.%.4f",lat,lng));

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
                String url = String.format("https://maps.googleapis.com/maps/api/geocode/json?latlng=%.4f,%.4f&sensor=false",lat,lng);
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
    */





