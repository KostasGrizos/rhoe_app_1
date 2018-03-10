package com.example.android.rhoe_app_1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TableLayout;

import com.example.android.app_v12.R;
import com.example.android.rhoe_app_1.SQLite_Obsolete.FineDatabaseHelper;

public class FineAdvancedActivity extends AppCompatActivity {

    private TableLayout tableA;
    private TableLayout tableB;
    private TableLayout tableC;
    private TableLayout tableD;
    private Switch switchA;
    private Switch switchB;
    private Switch switchC;
    private Switch switchD;

    private static final String TAG = "RegisterActivity";

    private EditText A1, A2, A3, A4, A5, A6;
    private EditText B1, B2, B3, B4, B5, B6;
    private Spinner C1;
    private EditText C2, C3, C4, C5, C6, C7, C8;
    private EditText D1, D2, D3, D4, D5;

    private String[] Atemp = new String[6];
    private String[] Btemp = new String[6];
    private String[] Ctemp = new String[8];
    private String[] Dtemp = new String[5];

    private Button SaveButton;
    private Button CancelButton;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fine_advanced);

        tableA = (TableLayout) this.findViewById(R.id.tlA);
        switchA = (Switch) this.findViewById(R.id.swA);
        tableB = (TableLayout) this.findViewById(R.id.tlB);
        switchB = (Switch) this.findViewById(R.id.swB);
        tableC = (TableLayout) this.findViewById(R.id.tlC);
        switchC = (Switch) this.findViewById(R.id.swC);
        tableD = (TableLayout) this.findViewById(R.id.tlD);
        switchD = (Switch) this.findViewById(R.id.swD);

        SaveButton = (Button) this.findViewById(R.id.btnSaveFineAdvenced);
        CancelButton = (Button) this.findViewById(R.id.btnCancelFineAdvenced);

        switchA.setChecked(false);
        tableA.setVisibility(TableLayout.GONE);
        switchA.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkSection(switchA.isChecked(),tableA);
            }
        });

        switchB.setChecked(false);
        tableB.setVisibility(TableLayout.GONE);
        switchB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkSection(switchB.isChecked(),tableB);
            }
        });

        switchC.setChecked(false);
        tableC.setVisibility(TableLayout.GONE);
        switchC.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkSection(switchC.isChecked(),tableC);
            }
        });

        switchD.setChecked(false);
        tableD.setVisibility(TableLayout.GONE);
        switchD.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkSection(switchD.isChecked(),tableD);
            }
        });

        A1 = (EditText) findViewById(R.id.etA1);
        A2 = (EditText) findViewById(R.id.etA2);
        A3 = (EditText) findViewById(R.id.etA3);
        A4 = (EditText) findViewById(R.id.etA4);
        A5 = (EditText) findViewById(R.id.etA5);
        A6 = (EditText) findViewById(R.id.etA6);
        Atemp = new String[]{A1.getText().toString(),
                A2.getText().toString(),
                A3.getText().toString(),
                A4.getText().toString(),
                A5.getText().toString(),
                A6.getText().toString()};

        B1 = (EditText) findViewById(R.id.etB1);
        B2 = (EditText) findViewById(R.id.etB2);
        B3 = (EditText) findViewById(R.id.etB3);
        B4 = (EditText) findViewById(R.id.etB4);
        B5 = (EditText) findViewById(R.id.etB5);
        B6 = (EditText) findViewById(R.id.etB6);
        Btemp = new String[]{B1.getText().toString(),
                B2.getText().toString(),
                B3.getText().toString(),
                B4.getText().toString(),
                B5.getText().toString(),
                B6.getText().toString()};

        C1 = (Spinner) findViewById(R.id.spC1);
        C2 = (EditText) findViewById(R.id.etC2);
        C3 = (EditText) findViewById(R.id.etC3);
        C4 = (EditText) findViewById(R.id.etC4);
        C5 = (EditText) findViewById(R.id.etC5);
        C6 = (EditText) findViewById(R.id.etC6);
        C7 = (EditText) findViewById(R.id.etC7);
        C8 = (EditText) findViewById(R.id.etC8);
        Ctemp = new String[]{C1.getSelectedItem().toString(),
                C2.getText().toString(),
                C3.getText().toString(),
                C4.getText().toString(),
                C5.getText().toString(),
                C6.getText().toString(),
                C7.getText().toString(),
                C8.getText().toString()};

        D1 = (EditText) findViewById(R.id.etD1);
        D2 = (EditText) findViewById(R.id.etD2);
        D3 = (EditText) findViewById(R.id.etD3);
        D4 = (EditText) findViewById(R.id.etD4);
        D5 = (EditText) findViewById(R.id.etD5);
        Dtemp = new String[]{D1.getText().toString(),
                D2.getText().toString(),
                D3.getText().toString(),
                D4.getText().toString(),
                D5.getText().toString()};

        SaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Bundle Construction for tables A, B, C, D
                Bundle Atable = new Bundle();
                Atable.putStringArray("Atemp", Atemp);
                Bundle Btable = new Bundle();
                Btable.putStringArray("Btemp", Btemp);
                Bundle Ctable = new Bundle();
                Ctable.putStringArray("Ctemp", Ctemp);
                Bundle Dtable = new Bundle();
                Dtable.putStringArray("Dtemp", Dtemp);

                //Bundle Construction for Condition
                Bundle con = new Bundle();
                con.putBoolean("Condition", false);

                //Intention to go to Fine Activity with new data
                Intent intent = new Intent(FineAdvancedActivity.this, FineActivity.class);
                intent.putExtras(con);
                intent.putExtras(Atable);
                intent.putExtras(Btable);
                intent.putExtras(Ctable);
                intent.putExtras(Dtable);

                startActivity(intent);
            }
        });

        CancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Bundle Construction for Condition
                Bundle con1 = new Bundle();
                con1.putBoolean("Condition1", false);
                //Intention to go to Fine Activity with new data
                Intent intent = new Intent(FineAdvancedActivity.this, FineActivity.class);

                intent.putExtras(con1);

            }
        });
    }

    private void checkSection(boolean condition, TableLayout table){
        if(condition){
            table.setVisibility(TableLayout.VISIBLE);
        }
        else {
            table.setVisibility(TableLayout.GONE);
        }
    }
}
