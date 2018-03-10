package com.example.android.rhoe_app_1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
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
import com.example.android.rhoe_app_1.SQLite_Obsolete.UserDatabaseHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    UserDatabaseHelper UserDB;

    private EditText Username;
    private EditText Password;
    private TextView LoginMessage;
    private Button NewUserButton;
    private Button LoginFirebaseButton;
    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        UserDB = new UserDatabaseHelper(this);

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        if(firebaseAuth.getCurrentUser() !=null){
            Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
            startActivity(intent);
        }

        Username = (EditText)findViewById(R.id.etUserName);
        Password = (EditText)findViewById(R.id.etPassword);
        LoginMessage = (TextView)findViewById(R.id.tvLoginMessage);
        NewUserButton = (Button)findViewById(R.id.btnRegister);
        LoginFirebaseButton = (Button)findViewById(R.id.btnLoginFirebase);

        LoginMessage.setText("");

        NewUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adminvalidate(Username.getText().toString(), Password.getText().toString());
            }
        });

        LoginFirebaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogin();
            }
        });
    }

    //Firebase Login Method
    private void userLogin(){
        String email = Username.getText().toString().trim();
        String password = Password.getText().toString().trim();

        if (TextUtils.isEmpty(email)){
            //email is empty
            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)){
            //email is empty
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Logging in...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()){
                            finish();
                            Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
                            startActivity(intent);
                        }
                    }
                });

    }

    private void adminvalidate(String userName, String userPassword){
        if(
                (userName.equals("admin")) && (userPassword.equals("kostas"))){
            Intent intent = new Intent(LoginActivity.this, RegisterStep1Activity.class);
            startActivity(intent);
        }else{
            LoginMessage.setText("Admin Access Only");


        }
    }

}
