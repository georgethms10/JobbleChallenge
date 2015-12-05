package com.app.jobblechallenge.loginpackage;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.app.jobblechallenge.R;
import com.app.jobblechallenge.homepackage.Home;
import com.app.jobblechallenge.registerpackage.Registration;
import com.app.jobblechallenge.utils.GenericActivity;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends GenericActivity<LoginBusinessLogic> implements loginResult, Validator.ValidationListener {

    @Bind(R.id.logo)
    ImageView logo;

    @Email(message = "Enter a valid email")
    @Bind(R.id.input_email)
    EditText inputEmail;

    @NotEmpty
    @Bind(R.id.input_password)
    EditText inputPassword;
    @Bind(R.id.btn_login)
    AppCompatButton btnLogin;
    private Validator validator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, LoginBusinessLogic.class);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        validator = new Validator(this);
        validator.setValidationListener(this);


        //setting up animation for lolipop devices
        setupAnimations(getWindow());
    }

    /**
     * @param window
     * @purpose animation above 5.0
     */
    private void setupAnimations(Window window) {
        int currentapiVersion = Build.VERSION.SDK_INT;
        if (currentapiVersion >= Build.VERSION_CODES.LOLLIPOP) {
            getOps().setUpAnimation(window);
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        supportFinishAfterTransition();
    }

    /**
     * @param b
     * @purpose result of login
     */
    @Override
    public void login(boolean b) {
        if (b) {

            Intent i = new Intent(LoginActivity.this, Home.class);
            startActivity(i);
            finish();
            overridePendingTransition(R.anim.one_,R.anim.two_);
        } else {
            showMessage("Login failed", logo);
        }
    }


    /**
     * @purpose UI validation succeeded
     */
    @Override
    public void onValidationSucceeded() {

        getOps().loginLogic(inputEmail.getText().toString(), inputPassword.getText().toString(), this);

    }

    /**
     * @param errors
     * @purpose UI validation failed
     */
    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);

            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
                YoYo.with(Techniques.Shake)
                        .duration(700)
                        .playOn(view);
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }
    }

    //sign in button click
    @OnClick(R.id.btn_login)
    public void SignIn() {
        validator.validate();
    }


    @OnClick(R.id.signupl)
    public void signUp() {
        Intent i = new Intent(LoginActivity.this, Registration.class);
        startActivity(i);
        overridePendingTransition(R.anim.one_, R.anim.two_);
    }

    @OnClick(R.id.forgot)
    public void forgot() {
        Intent i = new Intent(LoginActivity.this, ForgotActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.one_, R.anim.two_);
    }
}
