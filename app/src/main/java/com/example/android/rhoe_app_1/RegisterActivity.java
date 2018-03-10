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
import com.example.android.rhoe_app_1.FirebaseUsers.UserInfoFirebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";

    //UserDatabaseHelper UserDB;
    //private EditText UsernameR;
    //private EditText PasswordR;
    private Spinner TypeReg;
    private EditText FnameReg;
    private EditText LnameReg;
    private EditText SignatureNumReg;
    private Spinner MunicipalityReg;
    private EditText MIDReg;
    private Button FinishReg;
    private Button GoBackReg;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_step_2);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        TypeReg = (Spinner) findViewById(R.id.spAccountTypeReg);
        FnameReg = (EditText) findViewById(R.id.etFirstNameReg);
        LnameReg = (EditText) findViewById(R.id.etLastNameReg);
        SignatureNumReg = (EditText) findViewById(R.id.etSignatureNumReg);
        MunicipalityReg = (Spinner) findViewById(R.id.spMunicipalityReg);
        MIDReg = (EditText) findViewById(R.id.etMunicipalIDReg);

        FinishReg = (Button) findViewById(R.id.btnCreateUserReg);
        GoBackReg = (Button) findViewById(R.id.btnReturnReg);

        FinishReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUserInformation();
            }
        });

        GoBackReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

    }

    private void saveUserInformation(){
        String newEntry1 = TypeReg.getSelectedItem().toString();
        String newEntry2 = FnameReg.getText().toString();
        String newEntry3 = LnameReg.getText().toString();
        String newEntry4 = SignatureNumReg.getText().toString();
        String newEntry5 = MunicipalityReg.getSelectedItem().toString();
        String newEntry6 = MIDReg.getText().toString();

        if ((newEntry1.length() != 0) &&
                (newEntry2.length() != 0) &&
                (newEntry3.length() != 0) &&
                (newEntry4.length() != 0) &&
                (newEntry5.length() != 0) &&
                (newEntry6.length() != 0)) {

        UserInfoFirebase userInformationFirebase = new UserInfoFirebase(newEntry1, newEntry2, newEntry3, newEntry4, newEntry5, newEntry6);

        FirebaseUser user =firebaseAuth.getCurrentUser();

        databaseReference.child(user.getUid()).setValue(userInformationFirebase);

        Toast.makeText(this, "Information Saved!", Toast.LENGTH_LONG).show();

        firebaseAuth.signOut();
        finish();

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);

        } else {
            Toast.makeText(this, "You must complete all fields", Toast.LENGTH_LONG).show();
        }
    }
}
