package com.example.android.rhoe_app_1;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.android.app_v12.R;


public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    UserDatabaseHelper UserDB;

    private EditText Username;
    private EditText Password;
    private Button LoginButton;
    private TextView LoginMessage;
    private Button NewUserButton;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        UserDB = new UserDatabaseHelper(this);

        Username = (EditText)findViewById(R.id.etUserName);
        Password = (EditText)findViewById(R.id.etPassword);
        LoginButton = (Button)findViewById(R.id.btnLogin);
        LoginMessage = (TextView)findViewById(R.id.tvLoginMessage);
        NewUserButton = (Button)findViewById(R.id.btnRegister);

        LoginMessage.setText("");

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate(Username.getText().toString(), Password.getText().toString());
            }
        });

        NewUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adminvalidate(Username.getText().toString(), Password.getText().toString());
            }
        });


    }

    private void validate(String userName, String userPassword){
        if((userName.equals("admin")) && (userPassword.equals("kostas"))){

            Bundle b = new Bundle();
            b.putStringArray("UserPortableData", new String[]{"ID:000", "AdminUser", "No Password", "Admin", "Konstandinos", "Grizos", "No Signature", "Rhoe Admin", "RA001"});

            //More Data from UserDB

            Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
            intent.putExtras(b);

            startActivity(intent);
        }
        else if (UserLocatorDB(userName) == -1){
            LoginMessage.setText("User Not Found");
        }
        else if (UserLocatorDB(userName) > -1){
            Cursor UserCursor = UserDB.getDataForID(UserLocatorDB(userName));
            String[] UserPortableData = new String [9];
            while(UserCursor.moveToNext()) {
                UserPortableData[0] = (UserCursor.getString(0));
                UserPortableData[1] = (UserCursor.getString(1));
                UserPortableData[2] = (UserCursor.getString(2));
                UserPortableData[3] = (UserCursor.getString(3));
                UserPortableData[4] = (UserCursor.getString(4));
                UserPortableData[5] = (UserCursor.getString(5));
                UserPortableData[6] = (UserCursor.getString(6));
                UserPortableData[7] = (UserCursor.getString(7));
                UserPortableData[8] = (UserCursor.getString(8));
            }
            if (userPassword.equals(UserPortableData[2])){
                Bundle b = new Bundle();
                b.putStringArray("UserPortableData", UserPortableData);

                //More Data from UserDB

                Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                intent.putExtras(b);

                startActivity(intent);
            }
        }
        else {
            LoginMessage.setText("Wrong Username/Password");

        }
    }

    private void adminvalidate(String userName, String userPassword){
        if(
                (userName.equals("admin")) && (userPassword.equals("kostas"))){
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        }else{
            LoginMessage.setText("Admin Access Only");


        }
    }

    private int UserLocatorDB(String UN){
        Cursor data = UserDB.getItemID(UN);
        int itemID = -1;
        while(data.moveToNext()) {
            itemID = data.getInt(0);
        }
        return itemID;
    }


}
