package com.example.android.rhoe_app_1;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.android.app_v12.R;

import java.util.ArrayList;



public class UserListActivity extends AppCompatActivity {

    private static final String TAG = "UserListActivity";

    UserDatabaseHelper UserDB;

    private ListView UserListLV;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list); //CHECK//
        UserListLV = (ListView) findViewById(R.id.lvUserList);
        UserDB = new UserDatabaseHelper(this);

        populateListView();
    }

    private void populateListView() {
        Log.d(TAG, "populateListView: Displaying data in the ListView.");

        //get the data and append to a list
        Cursor data = UserDB.getData();
        ArrayList<String> listData = new ArrayList<>();
        while(data.moveToNext()){
            //get the value from the database in column 1
            //then add it to the ArrayList
            listData.add(data.getString(1));
        }
        //create the list adapter and set the adapter
        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        UserListLV.setAdapter(adapter);

        //set an onItemClickListener to the ListView
        UserListLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String UN = adapterView.getItemAtPosition(i).toString();
                Log.d(TAG, "onItemClick: You Clicked on " + UN);

                Cursor data = UserDB.getItemID(UN); //get the id associated with that name
                int itemID = -1;
                while(data.moveToNext()){
                    itemID = data.getInt(0);
                }
                if(itemID > -1){
                    Log.d(TAG, "onItemClick: The ID is: " + itemID);


                    Cursor editableUserCursor = UserDB.getDataForID(itemID);
                    String[] editableUserArray = new String[9];
                    while(editableUserCursor.moveToNext()) {
                        editableUserArray[0] = (editableUserCursor.getString(0));
                        editableUserArray[1] = (editableUserCursor.getString(1));
                        editableUserArray[2] = (editableUserCursor.getString(2));
                        editableUserArray[3] = (editableUserCursor.getString(3));
                        editableUserArray[4] = (editableUserCursor.getString(4));
                        editableUserArray[5] = (editableUserCursor.getString(5));
                        editableUserArray[6] = (editableUserCursor.getString(6));
                        editableUserArray[7] = (editableUserCursor.getString(7));
                        editableUserArray[8] = (editableUserCursor.getString(8));
                    }

                    Bundle b = UserListActivity.this.getIntent().getExtras();

                    Bundle editb = UserListActivity.this.getIntent().getExtras();
                    editb.putStringArray("editb", editableUserArray);

                    Intent editScreenIntent = new Intent(UserListActivity.this, UserEditActicity.class);


                    editScreenIntent.putExtras(b);
                    editScreenIntent.putExtras(editb);


                    startActivity(editScreenIntent);
                }
                else{
                    toastMessage("No ID associated with that name");
                }
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