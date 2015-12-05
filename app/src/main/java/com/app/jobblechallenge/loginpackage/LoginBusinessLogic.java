package com.app.jobblechallenge.loginpackage;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.transition.ChangeBounds;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.view.Window;

import com.app.jobblechallenge.R;
import com.app.jobblechallenge.utils.ConfigurableOps;
import com.app.jobblechallenge.utils.MyProgressDialog;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import java.lang.ref.WeakReference;

/**
 * Created by George on 12/1/2015.
 */
public class LoginBusinessLogic implements ConfigurableOps {
    private WeakReference<LoginActivity> mActivity;
    private LoginActivity mloginActivity;
    private MyProgressDialog load;

    @Override
    public void onConfiguration(Activity activity) {
        mActivity = new WeakReference<LoginActivity>((LoginActivity) activity);
        mloginActivity = (LoginActivity) activity;
        load=new MyProgressDialog(mloginActivity);
    }

    /**
     * @purpose show animations if 5.0 and above
     * @param window
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void setUpAnimation(Window window) {

        Slide slideTransition = new Slide(Gravity.RIGHT);
        slideTransition.setDuration(mloginActivity.getResources().getInteger(R.integer.anim_duration_medium));

        ChangeBounds changeBoundsTransition = new ChangeBounds();
        changeBoundsTransition.setDuration(mloginActivity.getResources().getInteger(R.integer.anim_duration_medium));

        window.setEnterTransition(slideTransition);
        window.setAllowEnterTransitionOverlap(false);
        window.setAllowReturnTransitionOverlap(false);
        window.setSharedElementEnterTransition(changeBoundsTransition);
    }


    /**
     * @purpose login background[parse]
     * @param username
     * @param password
     */
    public void loginLogic(String username,String password, final loginResult callback)
    {
        load.setProgress(false);
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {

                load.dismissProgress();
                if (user != null) {
                    callback.login(true);
                } else {
                    callback.login(false);
                    Log.d("error", e.getMessage());
                }
            }
        });
    }

}
