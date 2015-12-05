package com.app.jobblechallenge.utils;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;

import com.app.jobblechallenge.R;

/**
 * 
 * @author George
 *
 */
public class MyProgressDialog {
	private Dialog progressDialog;
	final GifMovieView gif1;
	
	public MyProgressDialog(Activity activity) {
		progressDialog = new Dialog(activity, R.style.DialogTheme);

		progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		progressDialog.setCancelable(false);
		progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
		progressDialog.setContentView(R.layout.progress);
		gif1 = (GifMovieView) progressDialog.findViewById(R.id.gif1);
		gif1.setMovieResource(R.drawable.loading);

	}

	public void setProgress(boolean cancellable ) {
		progressDialog.setCancelable(cancellable);
		progressDialog.show();
	}

	public void dismissProgress() {
		if (progressDialog.isShowing()){
			progressDialog.dismiss();
		}
	}
	
}
