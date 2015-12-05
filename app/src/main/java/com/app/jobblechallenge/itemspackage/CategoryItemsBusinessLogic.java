package com.app.jobblechallenge.itemspackage;

import android.app.Activity;
import android.util.Log;

import com.app.jobblechallenge.utils.ConfigurableOps;
import com.app.jobblechallenge.utils.MyProgressDialog;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sics on 12/2/2015.
 */
public class CategoryItemsBusinessLogic implements ConfigurableOps {
    private WeakReference<CategoryItems> mActivity;
    private CategoryItems mitemActivity;
    private MyProgressDialog load;

    @Override
    public void onConfiguration(Activity activity) {
        mActivity = new WeakReference<CategoryItems>((CategoryItems) activity);
        mitemActivity = (CategoryItems) activity;
        load=new MyProgressDialog(mitemActivity);
    }


    public void getAllItems(String catid, final itemResults callback)
    {
        load.setProgress(false);
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Items");
        query.whereEqualTo("CategoryId", catid);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> items, ParseException e) {

                load.dismissProgress();
                if (e == null) {
                    List<ItemsPojo> listItems = new ArrayList<ItemsPojo>();
                    for (int i = 0; i < items.size(); i++) {
                        System.out.println("heee"+items.get(i).getString("ItemName"));
                        ItemsPojo itempojo = new ItemsPojo();
                        itempojo.setName(items.get(i).getString("ItemName"));
                        itempojo.setDescription(items.get(i).getString("ItemDescription"));
                        itempojo.setImage(items.get(i).getParseFile("ItemImage").getUrl());
                        itempojo.setCategoryId(items.get(i).getObjectId());
                        listItems.add(itempojo);
                    }
                    if (listItems.size() == 0) {

                    }
                    callback.setList(listItems);
                } else {
                    Log.d("item", "Error: " + e.getMessage());
                    callback.error(e.getMessage());
                }
            }
        });
    }

}
