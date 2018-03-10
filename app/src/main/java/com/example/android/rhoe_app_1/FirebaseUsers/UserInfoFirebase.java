package com.example.android.rhoe_app_1.FirebaseUsers;

/**
 * Created by User on 6/03/2018.
 */

public class UserInfoFirebase {

    public String Type, Fname, Lname, SignatureNum, Municipality, MID;

    public UserInfoFirebase(){

    }

    public UserInfoFirebase(String type, String fname, String lname, String signatureNum, String municipality, String MID) {
        this.Type = type;
        this.Fname = fname;
        this.Lname = lname;
        this.SignatureNum = signatureNum;
        this.Municipality = municipality;
        this.MID = MID;
    }
}
