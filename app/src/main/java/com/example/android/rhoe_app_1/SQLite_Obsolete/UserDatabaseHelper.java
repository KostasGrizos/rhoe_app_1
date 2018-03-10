package com.example.android.rhoe_app_1.SQLite_Obsolete;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.lang.reflect.Array;


public class UserDatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "UserDatabaseHelper";

    private static final String TABLE_NAME = "UserDataTable";
    private static final String COL1 = "_id";
    private static final String COL2 = "User_Name";
    private static final String COL3 = "Password";
    private static final String COL4 = "Account_Type";
    private static final String COL5 = "First_Name";
    private static final String COL6 = "Last_Name";
    private static final String COL7 = "Signature";
    private static final String COL8 = "Municipality";
    private static final String COL9 = "Municipality_ID";


    public UserDatabaseHelper(Context context) {
        super(context, TABLE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" + COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL2 + " TEXT," +
                COL3 + " TEXT," +
                COL4 + " TEXT," +
                COL5 + " TEXT," +
                COL6 + " TEXT," +
                COL7 + " TEXT," +
                COL8 + " TEXT," +
                COL9 + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        if (i != i1) {
            db.execSQL("DROP IF TABLE EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }

    public boolean addData(String Username, String Password, String Type, String Fname, String Lname, String Signature, String Municipality, String MID) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, Username);
        contentValues.put(COL3, Password);
        contentValues.put(COL4, Type);
        contentValues.put(COL5, Fname);
        contentValues.put(COL6, Lname);
        contentValues.put(COL7, Signature);
        contentValues.put(COL8, Municipality);
        contentValues.put(COL9, MID);

        long result = db.insert(TABLE_NAME, null, contentValues);

        //if date as inserted incorrectly it will return -1
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Returns all the data from database
     *
     * @return
     */

    public Cursor getData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getDataForID(int ID) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME +
                " WHERE " + COL1 + "= '" + ID + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    /**
     * Returns only the ID that matches the name passed in
     *
     * @param name
     * @return
     */

    public Cursor getItemID(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COL1 + " FROM " + TABLE_NAME +
                " WHERE " + COL2 + "= '" + name + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    /**
     * Updates the name field
     */

    public void updateName(String newCOL2, String newCOL3, String newCOL4,
                           String newCOL5, String newCOL6, String newCOL7,
                           String newCOL8, String newCOL9, int id,
                           String oldCOL2) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1, id);
        contentValues.put(COL2, newCOL2);
        contentValues.put(COL3, newCOL3);
        contentValues.put(COL4, newCOL4);
        contentValues.put(COL5, newCOL5);
        contentValues.put(COL6, newCOL6);
        contentValues.put(COL7, newCOL7);
        contentValues.put(COL8, newCOL8);
        contentValues.put(COL9, newCOL9);
        db.update(TABLE_NAME, contentValues, "_id = ?", new String[]{Integer.toString(id)});
        //SQLiteDatabase db = this.getWritableDatabase();
        //String query = "UPDATE " + TABLE_NAME +
        //        " SET " + COL2 + " = '" + newCOL2 +
        //        "' AND " + " SET " + COL3 + " = '" + newCOL3 +
        //        "' AND " + " SET " + COL4 + " = '" + newCOL4 +
        //        "' AND " + " SET " + COL5 + " = '" + newCOL5 +
        //        "' AND " + " SET " + COL6 + " = '" + newCOL6 +
        //        "' AND " + " SET " + COL7 + " = '" + newCOL7 +
        //        "' AND " + " SET " + COL8 + " = '" + newCOL8 +
        //        "' AND " + " SET " + COL9 + " = '" + newCOL9 +
        //        "' WHERE " + COL1 + " = '" + id + "'" +
        //        " AND " + COL2 + " = '" + oldCOL2 + "'";
        //Log.d(TAG, "updateName: query: " + query);
        //db.execSQL(query);
    }

    /**
     * Delete from database
     *
     * @param id
     * @param Username
     */
    public void deleteName(int id, String Username) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME + " WHERE "
                + COL1 + " = '" + id + "'" +
                " AND " + COL2 + " = '" + Username + "'";
        Log.d(TAG, "deleteName: query: " + query);
        Log.d(TAG, "deleteName: Deleting " + Username + " from database.");
        db.execSQL(query);
    }

}
