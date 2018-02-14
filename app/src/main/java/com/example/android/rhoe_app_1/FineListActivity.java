package com.example.android.rhoe_app_1;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.android.app_v12.R;

import java.util.ArrayList;



public class FineListActivity extends AppCompatActivity {

    private static final String TAG = "FineListActivity";

    FineDatabaseHelper FineDB;

    private ListView FineListLV;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fine_list); //CHECK//
        FineListLV = (ListView) findViewById(R.id.lvFineList);
        FineDB = new FineDatabaseHelper(this);

        populateListView();
    }

    private void populateListView() {
        Log.d(TAG, "populateListView: Displaying data in the ListView.");

        //get the data and append to a list
        Cursor data = FineDB.getData();
        ArrayList<String> listData = new ArrayList<>();
        while(data.moveToNext()){
            //get the value from the database in column 1
            //then add it to the ArrayList
            listData.add(data.getString(33));
        }
        //create the list adapter and set the adapter
        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        FineListLV.setAdapter(adapter);

        //set an onItemClickListener to the ListView
        /*FineListLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String UN = adapterView.getItemAtPosition(i).toString();
                Log.d(TAG, "onItemClick: You Clicked on " + UN);

                Cursor data = FineDB.getItemID(UN); //get the id associated with that name
                int itemID = -1;
                while(data.moveToNext()){
                    itemID = data.getInt(0);
                }
                if(itemID > -1){
                    Log.d(TAG, "onItemClick: The ID is: " + itemID);


                    Cursor editableUserCursor = FineDB.getDataForID(itemID);
                    String[] editableFineArray = new String[9];
                    while(editableUserCursor.moveToNext()) {
                        editableFineArray[0] = (editableUserCursor.getString(15));
                        editableFineArray[1] = (editableUserCursor.getString(16));
                        editableFineArray[2] = (editableUserCursor.getString(17));
                        editableFineArray[3] = (editableUserCursor.getString(18));
                        editableFineArray[4] = (editableUserCursor.getString(32));
                        editableFineArray[5] = (editableUserCursor.getString(33));
                        editableFineArray[6] = (editableUserCursor.getString(35));
                        editableFineArray[7] = (editableUserCursor.getString(36));
                        editableFineArray[8] = (editableUserCursor.getString(38));
                    }

                    Bundle b = FineListActivity.this.getIntent().getExtras();

                    Bundle editb = FineListActivity.this.getIntent().getExtras();
                    editb.putStringArray("editb", editableFineArray);

                    Intent editScreenIntent = new Intent(FineListActivity.this, FineEditActicity.class);


                    editScreenIntent.putExtras(b);
                    editScreenIntent.putExtras(editb);


                    startActivity(editScreenIntent);
                }
                else{
                    toastMessage("No ID associated with that name");
                }
            }
        });*/
    }

    /**
     * customizable toast
     * @param message
     */
    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
}