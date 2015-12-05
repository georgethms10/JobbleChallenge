package com.app.jobblechallenge.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConnectionDetector {

    Context context;

    public ConnectionDetector(Context context) {
        this.context = context;
    }

    public boolean isConnectingToInternet() {
        try {
            ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) { //System.out.println("???????");
                NetworkInfo[] info = connectivity.getAllNetworkInfo();
                //  System.out.println(info+"infooo");
                if (info != null) {
                    //     	  System.out.println("info no null");
                    for (int i = 0; i < info.length; i++)
                        if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                            //           	  System.out.println("TRUEEEEEEEEEEE");
                            return true;
                        }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            //System.out.println("EXCEPTION  "+e);
        }
        return false;
    }
}