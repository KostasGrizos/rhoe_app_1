package com.example.android.rhoe_app_1.FirebaseFine;

/**
 * Created by User on 7/03/2018.
 */

public class FineBasicInfoFirebase {

    public String CarPlate, CarType, CarBrand, CarColor, Date, FineAmount, Day, Time, Address, FineType, UserID;

    public FineBasicInfoFirebase(){

    }

    public FineBasicInfoFirebase(String carPlate, String carType, String carBrand, String carColor, String date, String fineAmount, String day, String time, String address, String fineType, String userID) {
        CarPlate = carPlate;
        CarType = carType;
        CarBrand = carBrand;
        CarColor = carColor;
        Date = date;
        FineAmount = fineAmount;
        Day = day;
        Time = time;
        Address = address;
        FineType = fineType;
        UserID = userID;
    }
}
