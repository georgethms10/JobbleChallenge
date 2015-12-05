package com.app.jobblechallenge.utils;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Toast;

public class GenericFragments<Operations extends FragmentOps> extends Fragment {

    Operations operation_instance;


    public void onCreate(Bundle savedInstanceState, Class<Operations> opsType) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        try {
            initialize(opsType);
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void initialize(Class<Operations> opsType)
            throws InstantiationException, IllegalAccessException {
        // Create the OpsType object.
        try {
            operation_instance = opsType.newInstance();
        } catch (java.lang.InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        operation_instance.onConfigurationf(this);
    }


    /**
     * Return the initialized OpsType instance for use by the application.
     */
    public Operations getOps() {
        return operation_instance;
    }


    public void showMessageFragments(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }


    public void LogMessage(String message) {

        Log.d("VERGEFM", message);
    }


}
