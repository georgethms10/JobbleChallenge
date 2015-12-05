package com.app.jobblechallenge.homepackage;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

import com.app.jobblechallenge.R;

/**
 * Created by sics on 12/2/2015.
 */
public class AddCategoryDialog   {

    Context context;
    addedCategory callback;
    Dialog dialog;
    EditText categoryName;
    AppCompatButton create;

    AddCategoryDialog(Context context,addedCategory callback)
    {
        this.context=context;
        this.callback=callback;
        callDialog();
    }


    private void callDialog() {
        dialog = new Dialog(context, R.style.MyDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.add_category_dialog);

        categoryName=(EditText)dialog.findViewById(R.id.category_name_dialog);
        create=(AppCompatButton)dialog.findViewById(R.id.creaate);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!categoryName.getText().toString().equals(""))
                {
                    callback.categoryAdded(categoryName.getText().toString());
                    dialog.dismiss();
                }
                else {
                    categoryName.setError("Enter name");
                }

            }
        });
        dialog.show();
    }
}
