package com.app.jobblechallenge.additemspackage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.app.jobblechallenge.utils.ConfigurableOps;
import com.app.jobblechallenge.utils.MyProgressDialog;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.SaveCallback;

import java.lang.ref.WeakReference;

/**
 * Created by sics on 12/2/2015.
 */
public class AddItemsBusinessLogic implements ConfigurableOps {
    private WeakReference<AddItem> mActivity;
    private AddItem maddActivity;
    private MyProgressDialog load;

    @Override
    public void onConfiguration(Activity activity) {
        mActivity = new WeakReference<AddItem>((AddItem) activity);
        maddActivity = (AddItem) activity;
        load=new MyProgressDialog(maddActivity);
    }



    public void saveItem(String name,String description,String category,ParseFile file,  final addItemCallback callback)
    {
        load.setProgress(false);
        ParseObject item = new ParseObject("Items");
        item.put("ItemName", name);
        item.put("ItemDescription", description);
        item.put("ItemImage", file);
        item.put("CategoryId", category);
        item.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                load.dismissProgress();
                if (e == null) {
                   callback.itemAdded("Item added");
                    Intent refreshIntent = new Intent("changes");
                    Bundle args = new Bundle();
                    refreshIntent.putExtras(args);
                    maddActivity.sendBroadcast(refreshIntent);
                } else {
                   callback.error("Request failed");
                }
            }
        });
    }
}
