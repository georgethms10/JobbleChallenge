package com.app.jobblechallenge.loginpackage;

import android.app.Activity;
import android.widget.EditText;

import com.app.jobblechallenge.utils.ConfigurableOps;
import com.app.jobblechallenge.utils.MyProgressDialog;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;

import java.lang.ref.WeakReference;

/**
 * Created by sics on 12/1/2015.
 */
public class ForgotActivityBusinessLogic implements ConfigurableOps{
    private WeakReference<ForgotActivity> mActivity;
    private ForgotActivity mloginActivity;
    private MyProgressDialog load;

    @Override
    public void onConfiguration(Activity activity) {
        mActivity = new WeakReference<ForgotActivity>((ForgotActivity) activity);
        mloginActivity = (ForgotActivity) activity;
        load=new MyProgressDialog(mloginActivity);
    }


    public void sendPasswordSupport(final EditText email)
    {
        load.setProgress(false);
        ParseUser.requestPasswordResetInBackground(email.getText().toString(), new RequestPasswordResetCallback() {
            public void done(ParseException e) {
                load.dismissProgress();
                if (e == null) {
                   mActivity.get().showMessage("Check email",email);
                } else {
                    mActivity.get().showMessage(e.getMessage(),email);
                }
            }
        });
    }
}
