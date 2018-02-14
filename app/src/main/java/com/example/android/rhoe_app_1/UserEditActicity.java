package com.example.android.rhoe_app_1;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.app_v12.R;


public class UserEditActicity extends AppCompatActivity {

    private static final String TAG = "EditDataActivity";

    private UserDatabaseHelper UserDB;
    private EditText UsernameE;
    private EditText PasswordE;
    private TextView TypeE;
    private EditText FnameE;
    private EditText LnameE;
    private EditText SignatureE;
    private TextView MunicipalityE;
    private TextView MIDE;
    private Button SaveChangesButtonE;
    private Button DeleteUserButtonE;
    private Button CancelButtonE;

    private String selectedCOL2;
    private String selectedCOL3;
    private String selectedCOL4;
    private String selectedCOL5;
    private String selectedCOL6;
    private String selectedCOL7;
    private String selectedCOL8;
    private String selectedCOL9;

    private int selectedID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_edit);

        UserDB = new UserDatabaseHelper(this);

        UsernameE = (EditText) findViewById(R.id.etUsernameED);
        PasswordE = (EditText) findViewById(R.id.etPasswordED);
        TypeE = (TextView) findViewById(R.id.tvAcTypeE);
        FnameE = (EditText) findViewById(R.id.etFirstNameED);
        LnameE = (EditText) findViewById(R.id.etLastNameED);

        SignatureE = (EditText) findViewById(R.id.etSignatureED);

        MunicipalityE = (TextView) findViewById(R.id.tvMunED);
        MIDE = (TextView) findViewById(R.id.tvMunIDED);

        SaveChangesButtonE = (Button) findViewById(R.id.btnSaveChangesED);
        DeleteUserButtonE = (Button) findViewById(R.id.btnDeleteUserED);
        CancelButtonE = (Button) findViewById(R.id.btnReturnED);

        //get the intent extra from the ListDataActivity
        Bundle editb = this.getIntent().getExtras();
        final String[] editableUser=editb.getStringArray("editb");

        //now get the itemID we passed as an extra
        selectedID = Integer.valueOf(editableUser[0]);

        //now get the name we passed as an extra
        selectedCOL2 = editableUser[1];
        selectedCOL3 = editableUser[2];
        selectedCOL4 = editableUser[3];
        selectedCOL5 = editableUser[4];
        selectedCOL6 = editableUser[5];
        selectedCOL7 = editableUser[6];
        selectedCOL8 = editableUser[7];
        selectedCOL9 = editableUser[8];




        //set the text to show the current selected name
        UsernameE.setText(selectedCOL2);
        PasswordE.setText(selectedCOL3);
        TypeE.setText(selectedCOL4);
        FnameE.setText(selectedCOL5);
        LnameE.setText(selectedCOL6);
        SignatureE.setText(selectedCOL7);
        MunicipalityE.setText(selectedCOL8);
        MIDE.setText(selectedCOL9);


        SaveChangesButtonE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String item2 = UsernameE.getText().toString();
                String item3 = PasswordE.getText().toString();
                String item4 = TypeE.getText().toString();
                String item5 = FnameE.getText().toString();
                String item6 = LnameE.getText().toString();
                String item7 = SignatureE.getText().toString();
                String item8 = MunicipalityE.getText().toString();
                String item9 = MIDE.getText().toString();

                if((!item2.equals("")) ||
                        (!item3.equals("")) ||
                        (!item4.equals("")) ||
                        (!item5.equals("")) ||
                        (!item6.equals("")) ||
                        (!item7.equals("")) ||
                        (!item8.equals("")) ||
                        (!item9.equals(""))) {
                    UserDB.updateName(item2, item3, item4, item5, item6, item7, item8, item9, selectedID, selectedCOL2);

                    Bundle b = UserEditActicity.this.getIntent().getExtras();
                    Intent intent = new Intent(UserEditActicity.this, DashboardActivity.class);
                    intent.putExtras(b);

                    startActivity(intent);
                }else{
                    toastMessage("You must complete all fields");
                }
            }
        });

        DeleteUserButtonE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserDB.deleteName(selectedID,selectedCOL2);
                toastMessage("removed from database");
                Bundle b = UserEditActicity.this.getIntent().getExtras();
                Intent intent = new Intent(UserEditActicity.this, DashboardActivity.class);
                intent.putExtras(b);

                startActivity(intent);

            }
        });

        CancelButtonE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle b = UserEditActicity.this.getIntent().getExtras();
                Intent intent = new Intent(UserEditActicity.this, DashboardActivity.class);
                startActivity(intent);
                intent.putExtras(b);
            }
        });

    }

    /**
     * customizable toast
     * @param message
     */
    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
}

