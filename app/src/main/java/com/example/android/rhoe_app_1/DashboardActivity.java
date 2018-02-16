package com.example.android.rhoe_app_1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.android.app_v12.R;

public class DashboardActivity extends AppCompatActivity {

    private ImageButton ViewUsersButton;
    private ImageButton NewFineButton;
    private TextView NameText, SurnameText, MunicipalityText, MunicipalIDText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        ViewUsersButton = (ImageButton)findViewById(R.id.btnViewUsers);
        NewFineButton = (ImageButton)findViewById(R.id.btnNewFine);

        /*
         * Showing the Name of the user
         */
        NameText = (TextView)findViewById(R.id.txtNameView);
        SurnameText = (TextView)findViewById(R.id.txtSurnameView);
        MunicipalityText = (TextView)findViewById(R.id.txtMunicipalityView);
        MunicipalIDText = (TextView)findViewById(R.id.txtMunicipalIDView);

        Bundle user = this.getIntent().getExtras();
        final String[] UserPortableData=user.getStringArray("UserPortableData");

        NameText.setText(UserPortableData[4]);
        SurnameText.setText(UserPortableData[5]);
        MunicipalityText.setText(UserPortableData[7]);
        MunicipalIDText.setText("Κωδικός ΟΤΑ: " + UserPortableData[8]);

        ViewUsersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle user = new Bundle();
                user.putStringArray("UserPortableData", UserPortableData);

                Intent intent = new Intent(DashboardActivity.this, UserListActivity.class);
                intent.putExtras(user);

                startActivity(intent);
            }
        });



        NewFineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle user = new Bundle();
                user.putStringArray("UserPortableData", UserPortableData);

                Bundle con = new Bundle();
                con.putBoolean("Condition", true);
                Bundle con1 = new Bundle();
                con1.putBoolean("Condition1", true);

                Intent intent = new Intent(DashboardActivity.this, FineActivity.class);
                intent.putExtras(user);
                intent.putExtras(con);
                intent.putExtras(con1);

                startActivity(intent);
            }
        });


    }
}
