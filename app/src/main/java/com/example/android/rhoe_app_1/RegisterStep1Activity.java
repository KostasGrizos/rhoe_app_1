package com.example.android.rhoe_app_1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.app_v12.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

import static org.apache.commons.lang3.StringUtils.length;

public class RegisterStep1Activity extends AppCompatActivity {

    private EditText EmailReg, PasswordReg, PasswordConfReg;
    private Button CancelReg, ContinueReg;
    private TextView MessageReg;

    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_step_1);

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        EmailReg = (EditText) findViewById(R.id.etEmailReg);
        PasswordReg = (EditText) findViewById(R.id.etPasswordReg);
        PasswordConfReg = (EditText) findViewById(R.id.etPasswordConfReg);
        MessageReg = (TextView) findViewById(R.id.tvMessageReg);
        CancelReg = (Button) findViewById(R.id.btnCancelReg);
        ContinueReg = (Button) findViewById(R.id.btnContinueReg);

        CancelReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent = new Intent(RegisterStep1Activity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        ContinueReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });
    }

    //Firebase Register-Login Method
    private void registerUser() {
        String email = EmailReg.getText().toString().trim();
        String password = PasswordReg.getText().toString().trim();
        String passwordConf = PasswordConfReg.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            //email is empty
            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            //email is empty
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(passwordConf)) {
            //email is empty
            Toast.makeText(this, "Please re-enter password", Toast.LENGTH_SHORT).show();
            return;
        }
        if (length(password)<8) {
            //email is empty
            Toast.makeText(this, "Please enter a password with at least 8 characters", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!Objects.equals(passwordConf, password)) {
            //email is empty
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Registering User...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(RegisterStep1Activity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(RegisterStep1Activity.this, "Registration failure, please try again", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        progressDialog.setMessage("Attempting Initial Login");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            //Start Dashboard Activity
                            finish();
                            Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                            startActivity(intent);
                        }
                    }
                });
    }
}


