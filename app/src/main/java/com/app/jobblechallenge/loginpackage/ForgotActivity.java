package com.app.jobblechallenge.loginpackage;

import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.app.jobblechallenge.R;
import com.app.jobblechallenge.utils.GenericActivity;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ForgotActivity extends GenericActivity<ForgotActivityBusinessLogic> implements  Validator.ValidationListener  {

    @Email
    @Bind(R.id.input_email)
    EditText inputEmail;
    @Bind(R.id.btn_forgot)
    AppCompatButton btnForgot;
    private Validator validator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState,ForgotActivityBusinessLogic.class);
        setContentView(R.layout.forgot_password);
        ButterKnife.bind(this);
        validator = new Validator(this);
        validator.setValidationListener(this);
    }


    @OnClick(R.id.btn_forgot)
    public void forgotPassword()
    {
        validator.validate();
    }

    @Override
    public void onValidationSucceeded() {
        getOps().sendPasswordSupport(inputEmail);
    }

    /**
     * @purpose UI validation failed
     * @param errors
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.three_, R.anim.four_);
    }
}
