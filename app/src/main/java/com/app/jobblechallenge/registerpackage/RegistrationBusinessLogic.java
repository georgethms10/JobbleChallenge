package com.app.jobblechallenge.registerpackage;

import android.app.Activity;

import com.app.jobblechallenge.utils.ConfigurableOps;
import com.app.jobblechallenge.utils.MyProgressDialog;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.lang.ref.WeakReference;

/**
 * Created by sics on 12/2/2015.
 */
public class RegistrationBusinessLogic implements ConfigurableOps {
    private WeakReference<Registration> mActivity;
    private Registration mregisActivity;
    private MyProgressDialog load;

    @Override
    public void onConfiguration(Activity activity) {
        mActivity = new WeakReference<Registration>((Registration) activity);
        mregisActivity = (Registration) activity;
        load=new MyProgressDialog(mregisActivity);
    }


    /**
     * @purpose regster user parse
     * @param userpojo user details
     * @param callback result callback
     */
    public void registerUser(User userpojo, final registerResult callback)
    {

        load.setProgress(false);
        ParseUser user = new ParseUser();
        user.setUsername(userpojo.getEmail());
        user.setPassword(userpojo.getPassword());
        user.setEmail(userpojo.getEmail());
        user.signUpInBackground(new SignUpCallback() {
            public void done(ParseException e) {
                load.dismissProgress();
                if (e == null) {
                   callback.result(true,"success");
                } else {
                   callback.result(false,e.getMessage());
                }
            }
        });
    }


}
