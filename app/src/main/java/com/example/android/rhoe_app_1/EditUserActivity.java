package com.example.android.rhoe_app_1;

        import android.content.Intent;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.example.android.app_v12.R;
        import com.example.android.rhoe_app_1.FirebaseUsers.RetrieveUserInfoFirebase;
        import com.example.android.rhoe_app_1.FirebaseUsers.UserInfoFirebase;
        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.auth.FirebaseUser;
        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.ValueEventListener;

public class EditUserActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";

    private TextView TypeEdit;
    private EditText FnameEdit;
    private EditText LnameEdit;
    private EditText SignatureNumEdit;
    private TextView MunicipalityEdit;
    private TextView MIDEdit;
    private Button SaveChangesEdit;
    private Button CancelEdit;
    private Button DeleteUserEdit;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseUser user =firebaseAuth.getCurrentUser();
        userID = user.getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                showData(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        TypeEdit = (TextView) findViewById(R.id.tvTypeEdit);
        FnameEdit = (EditText) findViewById(R.id.etFirstNameEdit);
        LnameEdit = (EditText) findViewById(R.id.etLastNameEdit);
        SignatureNumEdit = (EditText) findViewById(R.id.etSignatureNumEdit);
        MunicipalityEdit = (TextView) findViewById(R.id.tvMunicipalityEdit);
        MIDEdit = (TextView) findViewById(R.id.tvMIDEdit);

        SaveChangesEdit = (Button) findViewById(R.id.btnSaveChangesEdit);
        CancelEdit = (Button) findViewById(R.id.btnCancelEdit);
        DeleteUserEdit = (Button) findViewById(R.id.btnDeletUserEdit);

        SaveChangesEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUserEditInformation();
            }
        });

        CancelEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditUserActivity.this, DashboardActivity.class);
                startActivity(intent);
            }
        });

        DeleteUserEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditUserActivity.this, DashboardActivity.class);
                startActivity(intent);
            }
        });
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

            TypeEdit.setText(RUInfo.getType());
            FnameEdit.setText(RUInfo.getFname());
            LnameEdit.setText(RUInfo.getLname());
            SignatureNumEdit.setText(RUInfo.getSignatureNum());
            MunicipalityEdit.setText(RUInfo.getMunicipality());
            MIDEdit.setText(RUInfo.getMID());
        }
    }


    private void saveUserEditInformation(){
        String newEntry1 = TypeEdit.getText().toString();
        String newEntry2 = FnameEdit.getText().toString();
        String newEntry3 = LnameEdit.getText().toString();
        String newEntry4 = SignatureNumEdit.getText().toString();
        String newEntry5 = MunicipalityEdit.getText().toString();
        String newEntry6 = MIDEdit.getText().toString();


        if ((newEntry1.length() != 0) &&
                (newEntry2.length() != 0) &&
                (newEntry3.length() != 0) &&
                (newEntry4.length() != 0) &&
                (newEntry5.length() != 0) &&
                (newEntry6.length() != 0)) {

            UserInfoFirebase userInformationFirebase = new UserInfoFirebase(newEntry1, newEntry2, newEntry3, newEntry4, newEntry5, newEntry6);

            FirebaseUser user =firebaseAuth.getCurrentUser();

            databaseReference.child(user.getUid()).setValue(userInformationFirebase);

            Toast.makeText(this, "Changes Saved", Toast.LENGTH_LONG).show();
            finish();

            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);

        } else {
            Toast.makeText(this, "You must complete all fields", Toast.LENGTH_LONG).show();
        }



    }
}
