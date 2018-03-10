package com.example.android.rhoe_app_1.SQLite_Obsolete;

        import android.content.ContentValues;
        import android.content.Context;
        import android.database.Cursor;
        import android.database.DatabaseErrorHandler;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteOpenHelper;
        import android.util.Log;

        import java.lang.reflect.Array;


public class FineDatabaseHelper extends SQLiteOpenHelper{

    private static final String TAG = "UserDatabaseHelper";

    private static final String TABLE_NAME = "FineDataTable";
    private static final String COL01 = "_id";
    private static final String COL02 = "Municipality";
    private static final String COL03 = "MunicipalityID";
    private static final String COL04 = "A1";
    private static final String COL05 = "A2";
    private static final String COL06 = "A3";
    private static final String COL07 = "A4";
    private static final String COL08 = "A5";
    private static final String COL09 = "A6";
    private static final String COL10 = "B1";
    private static final String COL11 = "B2";
    private static final String COL12 = "B3";
    private static final String COL13 = "B4";
    private static final String COL14 = "B5";
    private static final String COL15 = "B6";
    private static final String COL16 = "Plate";
    private static final String COL17 = "Type";
    private static final String COL18 = "Brand";
    private static final String COL19 = "Color";
    private static final String COL20 = "C1";
    private static final String COL21 = "C2";
    private static final String COL22 = "C3";
    private static final String COL23 = "C4";
    private static final String COL24 = "C5";
    private static final String COL25 = "C6";
    private static final String COL26 = "C7";
    private static final String COL27 = "C8";
    private static final String COL28 = "D1";
    private static final String COL29 = "D2";
    private static final String COL30 = "D3";
    private static final String COL31 = "D4";
    private static final String COL32 = "D5";
    private static final String COL33 = "Fine_Amount";
    private static final String COL34 = "Date";
    private static final String COL35 = "Officer_Name";
    private static final String COL36 = "Day";
    private static final String COL37 = "Time";
    private static final String COL38 = "Address";
    private static final String COL39 = "Fine_Type";



    public FineDatabaseHelper(Context context) {
        super(context, TABLE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" + COL01 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL02 +" TEXT," +
                COL03 +" TEXT," +
                COL04 +" TEXT," +
                COL05 +" TEXT," +
                COL06 +" TEXT," +
                COL07 +" TEXT," +
                COL08 +" TEXT," +
                COL09 +" TEXT," +
                COL10 +" TEXT," +
                COL11 +" TEXT," +
                COL12 +" TEXT," +
                COL13 +" TEXT," +
                COL14 +" TEXT," +
                COL15 +" TEXT," +
                COL16 +" TEXT," +
                COL17 +" TEXT," +
                COL18 +" TEXT," +
                COL19 +" TEXT," +
                COL20 +" TEXT," +
                COL21 +" TEXT," +
                COL22 +" TEXT," +
                COL23 +" TEXT," +
                COL24 +" TEXT," +
                COL25 +" TEXT," +
                COL26 +" TEXT," +
                COL27 +" TEXT," +
                COL28 +" TEXT," +
                COL29 +" TEXT," +
                COL30 +" TEXT," +
                COL31 +" TEXT," +
                COL32 +" TEXT," +
                COL33 +" TEXT," +
                COL34 +" TEXT," +
                COL35 +" TEXT," +
                COL36 +" TEXT," +
                COL37 +" TEXT," +
                COL38 +" TEXT," +
                COL39 +" TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        if (i != i1) {
            db.execSQL("DROP IF TABLE EXISTS " + TABLE_NAME);
            onCreate(db);}
    }

    public boolean addDataFine(String[] User,
                               String[] FineBasic,
                               String[] A,
                               String[] B,
                               String[] C,
                               String[] D){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL02, User[7]);
        contentValues.put(COL03, User[8]);
        contentValues.put(COL04, A[0]);
        contentValues.put(COL05, A[1]);
        contentValues.put(COL06, A[2]);
        contentValues.put(COL07, A[3]);
        contentValues.put(COL08, A[4]);
        contentValues.put(COL09, A[5]);
        contentValues.put(COL10, B[0]);
        contentValues.put(COL11, B[1]);
        contentValues.put(COL12, B[2]);
        contentValues.put(COL13, B[3]);
        contentValues.put(COL14, B[4]);
        contentValues.put(COL15, B[5]);
        contentValues.put(COL16, FineBasic[0]);
        contentValues.put(COL17, FineBasic[1]);
        contentValues.put(COL18, FineBasic[2]);
        contentValues.put(COL19, FineBasic[3]);
        contentValues.put(COL20, C[0]);
        contentValues.put(COL21, C[1]);
        contentValues.put(COL22, C[2]);
        contentValues.put(COL23, C[3]);
        contentValues.put(COL24, C[4]);
        contentValues.put(COL25, C[5]);
        contentValues.put(COL26, C[6]);
        contentValues.put(COL27, C[7]);
        contentValues.put(COL28, D[0]);
        contentValues.put(COL29, D[1]);
        contentValues.put(COL30, D[2]);
        contentValues.put(COL31, D[3]);
        contentValues.put(COL32, D[4]);
        contentValues.put(COL33, FineBasic[4]);
        contentValues.put(COL34, FineBasic[5]);
        contentValues.put(COL35, User[6] + " " + User[5]);
        contentValues.put(COL36, FineBasic[6]);
        contentValues.put(COL37, FineBasic[7]);
        contentValues.put(COL38, FineBasic[8]);
        contentValues.put(COL39, FineBasic[9]);


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
     * @return
     */

    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getDataForID(int ID){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME +
                " WHERE " + COL01 + "= '" + ID + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    /**
     * Returns only the ID that matches the name passed in
     * @param name
     * @return
     */

    public Cursor getItemID(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COL01 + " FROM " + TABLE_NAME +
                " WHERE " + COL34 + "= '" + name + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    /**
     * Updates the name field
     */

    public void updateName(String newCOL2, String newCOL3, String newCOL4,
                           String newCOL5, String newCOL6, String newCOL7,
                           String newCOL8, String newCOL9, int id,
                           String oldCOL2){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL01,id);
        contentValues.put(COL02,newCOL2);
        contentValues.put(COL03,newCOL3);
        contentValues.put(COL04,newCOL4);
        contentValues.put(COL05,newCOL5);
        contentValues.put(COL06,newCOL6);
        contentValues.put(COL07,newCOL7);
        contentValues.put(COL08,newCOL8);
        contentValues.put(COL09,newCOL9);
        db.update(TABLE_NAME, contentValues, "_id = ?",new String[] { Integer.toString(id) });
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
     * @param id
     * @param Username
     */
    public void deleteName(int id, String Username){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME + " WHERE "
                + COL01 + " = '" + id + "'" +
                " AND " + COL02 + " = '" + Username + "'";
        Log.d(TAG, "deleteName: query: " + query);
        Log.d(TAG, "deleteName: Deleting " + Username + " from database.");
        db.execSQL(query);
    }

}

