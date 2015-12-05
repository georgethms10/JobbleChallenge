package com.app.jobblechallenge;

import android.app.Application;

import com.parse.Parse;

/**
 * Created by sics on 11/30/2015.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(this, "1CRZ2tZ1N47ImpMtP1CgowPnF0zWXCerxiwrDYrD", "tjfALpDw2OcDZt4QI24Pf76lo0VusbIf6WZQHEdq");
    }

}
