package com.app.jobblechallenge.registerpackage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.app.jobblechallenge.R;
import com.app.jobblechallenge.homepackage.Home;
import com.app.jobblechallenge.utils.GenericActivity;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Registration extends GenericActivity<RegistrationBusinessLogic> implements Validator.ValidationListener, registerResult {

    @NotEmpty
    @Bind(R.id.input_name)
    EditText inputName;
    @Email
    @Bind(R.id.input_email)
    EditText inputEmail;
    @Password(scheme = Password.Scheme.ALPHA_NUMERIC, message = "should be alphanumeric and 6 letters long")
    @Bind(R.id.input_password)
    EditText inputPassword;

    @ConfirmPassword
    @Bind(R.id.renter_password)
    EditText renterPassword;
    @Bind(R.id.btn_regis)
    AppCompatButton btnRegis;
    private Validator validator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, RegistrationBusinessLogic.class);
        setContentView(R.layout.activity_registration);
        ButterKnife.bind(this);
        validator = new Validator(this);
        validator.setValidationListener(this);
    }

    /**
     * @purpose after UI validation register user
     */
    @Override
    public void onValidationSucceeded() {

        User user = new User(inputName.getText().toString(), inputEmail.getText().toString(), inputPassword.getText().toString());
        getOps().registerUser(user, this);

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

    @OnClick(R.id.btn_regis)
    public void Register() {
        validator.validate();
    }

    @OnClick(R.id.already)
    public void Already() {
        finish();
        overridePendingTransition(R.anim.three_, R.anim.four_);
    }

    @Override
    public void result(boolean success, String message) {

        if (success) {
            Intent i = new Intent(Registration.this, Home.class);
            startActivity(i);
            finish();
            overridePendingTransition(R.anim.one_, R.anim.two_);
        } else {
            showMessage(message, btnRegis);
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.three_, R.anim.four_);
    }
}
