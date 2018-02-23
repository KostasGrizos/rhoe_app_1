package com.example.android.rhoe_app_1.Zebra;

/**
 * Created by User on 22/02/2018.
 */

public class DemoSleeper {

    private DemoSleeper() {

    }

    public static void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}