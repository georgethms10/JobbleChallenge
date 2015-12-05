package com.app.jobblechallenge.utils;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class GenericActivity<Operation  extends ConfigurableOps> extends AppCompatActivity implements NetworkChangeReceiver.Internet{

	Operation operation_instance;
	
	protected void onCreate(Bundle savedInstanceState,Class<Operation> opsType) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
//		NetworkChangeReceiver.internet = this;
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
	
	/**
	 * Return the initialized OpsType instance for use by the application.
	 */
	public Operation getOps() {
		return operation_instance;
	}

	
	private void initialize(Class<Operation> opsType)
			throws InstantiationException, IllegalAccessException {
		// Create the OpsType object.
		operation_instance = opsType.newInstance();
		operation_instance.onConfiguration(this);
	}


	public void showMessage(String message,View v)
	{
//
		Snackbar snackbar = Snackbar
				.make(v, message, Snackbar.LENGTH_LONG);

		snackbar.show();

		View view = snackbar.getView();
		TextView tv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
		tv.setTextColor(Color.WHITE);

	}



	public void Finish()
	{
		finish();
	}

	@Override
	public void net() {
		showAlert("No Connection");
	}

	private void showAlert(String s) {

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		alertDialogBuilder.setMessage("Are you sure,You wanted to make decision");

		alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface arg0, int arg1) {

			}
		});

		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
	}



}
