package com.example.android.rhoe_app_1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.android.app_v12.R;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";

    UserDatabaseHelper UserDB;
    private EditText UsernameR;
    private EditText PasswordR;
    private Spinner TypeR;
    private EditText FnameR;
    private EditText LnameR;
    private EditText SignatureR;
    private Spinner MunicipalityR;
    private EditText MIDR;
    private Button AddNewUserButtonR;
    private Button CancelButtonR;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        UsernameR = (EditText) findViewById(R.id.etUsername);
        PasswordR = (EditText) findViewById(R.id.etPassword);
        TypeR = (Spinner) findViewById(R.id.spAccountType);
        FnameR = (EditText) findViewById(R.id.etFirstName);
        LnameR = (EditText) findViewById(R.id.etLastName);
        SignatureR = (EditText) findViewById(R.id.etSignature);
        MunicipalityR = (Spinner) findViewById(R.id.spMunicipality);
        MIDR = (EditText) findViewById(R.id.etMunicipalID);

        AddNewUserButtonR = (Button) findViewById(R.id.btnCreateUser);
        CancelButtonR = (Button) findViewById(R.id.btnReturn);

        UserDB = new UserDatabaseHelper(this);

        AddNewUserButtonR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newEntry2 = UsernameR.getText().toString();
                String newEntry3 = PasswordR.getText().toString();
                String newEntry4 = TypeR.getSelectedItem().toString();
                String newEntry5 = FnameR.getText().toString();
                String newEntry6 = LnameR.getText().toString();
                String newEntry7 = SignatureR.getText().toString();
                String newEntry8 = MunicipalityR.getSelectedItem().toString();
                String newEntry9 = MIDR.getText().toString();


                if ((newEntry2.length() != 0) &&
                        (newEntry3.length() != 0) &&
                        (newEntry4.length() != 0) &&
                        (newEntry5.length() != 0) &&
                        (newEntry6.length() != 0) &&
                        (newEntry7.length() != 0) &&
                        (newEntry8.length() != 0) &&
                        (newEntry9.length() != 0)) {
                    AddData(newEntry2, newEntry3, newEntry4, newEntry5, newEntry6, newEntry7, newEntry8, newEntry9);

                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);

                } else {
                    toastMessage("You must complete all the fields!");
                }

            }
        });

        CancelButtonR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

    }


    public void AddData(String newEntry2,String newEntry3,String newEntry4,String newEntry5,String newEntry6,String newEntry7,String newEntry8,String newEntry9) {
        boolean insertData = UserDB.addData(newEntry2, newEntry3, newEntry4, newEntry5, newEntry6, newEntry7, newEntry8, newEntry9);

        if (insertData) {
            toastMessage("User Successfully Registered");
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
