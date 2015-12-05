package com.app.jobblechallenge;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.transition.ChangeBounds;
import android.transition.Slide;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.app.jobblechallenge.homepackage.Home;
import com.app.jobblechallenge.loginpackage.LoginActivity;
import com.parse.ParseUser;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity {

    @Bind(R.id.bgs)
    ImageView bgs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        if (Build.VERSION.SDK_INT < 16) {
//            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        } else {
//            View decorView = getWindow().getDecorView();
//            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
//            decorView.setSystemUiVisibility(uiOptions);
//
//        }

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        timer();

    }

    /**
     * Splash screen timer
     */
    private void timer() {

        new CountDownTimer(3000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {

                if (ParseUser.getCurrentUser() != null) {
                    if (ParseUser.getCurrentUser().getUsername()!= null) {
                        Intent  intent = new Intent(SplashActivity.this, Home.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.one_, R.anim.two_);
                        finish();
                    }
                }
                else
                {
                    Intent i = new Intent(getApplicationContext(), LoginActivity.class);

                    int currentapiVersion = Build.VERSION.SDK_INT;
                    if (currentapiVersion >= Build.VERSION_CODES.LOLLIPOP) {
                        ActivityOptionsCompat options = ActivityOptionsCompat.
                                makeSceneTransitionAnimation((Activity) SplashActivity.this, (View) bgs, "image");
                        startActivity(i, options.toBundle());
                        finishAfterTransition();
                    } else {
                        startActivity(i);
                        overridePendingTransition(R.anim.one_, R.anim.two_);
                        finish();
                    }

                }


            }
        }.start();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setUpAnimation(Window window) {

        Slide slideTransition = new Slide(Gravity.RIGHT);
        slideTransition.setDuration(getResources().getInteger(R.integer.anim_duration_medium));

        ChangeBounds changeBoundsTransition = new ChangeBounds();
        changeBoundsTransition.setDuration(getResources().getInteger(R.integer.anim_duration_medium));

        window.setEnterTransition(slideTransition);
        window.setAllowEnterTransitionOverlap(false);
        window.setAllowReturnTransitionOverlap(false);
        window.setSharedElementEnterTransition(changeBoundsTransition);
    }
}
