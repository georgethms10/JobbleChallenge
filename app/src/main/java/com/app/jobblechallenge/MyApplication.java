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
        Parse.initialize(this, "my_id", "my_key");
    }

}
