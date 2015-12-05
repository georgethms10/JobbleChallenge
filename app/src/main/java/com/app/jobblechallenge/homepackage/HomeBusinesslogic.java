package com.app.jobblechallenge.homepackage;

import android.app.Activity;

import com.app.jobblechallenge.tables.CategoryTable;
import com.app.jobblechallenge.utils.ConfigurableOps;
import com.app.jobblechallenge.utils.MyProgressDialog;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Random;

/**
 * Created by sics on 12/2/2015.
 */
public class HomeBusinesslogic implements ConfigurableOps {

    private WeakReference<Home> mActivity;
    private Home mhomeActivity;
    private MyProgressDialog load;

    @Override
    public void onConfiguration(Activity activity) {
        mActivity = new WeakReference<Home>((Home) activity);
        mhomeActivity = (Home) activity;
        load=new MyProgressDialog(mhomeActivity);
    }



    public void getCategoryFromParse(final categoryResults callback)
    {
        load.setProgress(true);

        ParseQuery<CategoryTable> call = ParseQuery.getQuery(CategoryTable.class);
        ParseObject.registerSubclass(CategoryTable.class);
        call.whereEqualTo("email", ParseUser.getCurrentUser().getEmail());
        call.findInBackground(new FindCallback<CategoryTable>() {
            public void done(List<CategoryTable> category, ParseException exception) {
                load.dismissProgress();
                if (category != null) {
                    if (category.size() > 0) {
//                        adapter = new ReviewAdapter(reviews, ShowReview.this);
//                        review_recycle.setAdapter(adapter);
                        callback.categoryList(true,category);
                    } else {
                        callback.error(false, "No Categories");
                    }
                } else {
                    callback.error(false,"No Categories");
                }
            }
        });
    }


    public void addCategorytoParse(final String name)
    {
        Random rand = new Random();
        final String id=name + rand.nextInt((1000 - 1) + 1);
        CategoryTable obj=new CategoryTable();
        obj.setCategoryName(name);
        obj.setCategoryId(id);
        obj.setEmail(ParseUser.getCurrentUser().getEmail());

        obj.saveInBackground(new SaveCallback() {
            public void done(ParseException e) {

                CategoryTable cat=new CategoryTable();
                cat.setCategoryName(name);
                cat.setCategoryId(id);
//                cat.setEmail(ParseUser.getCurrentUser().getEmail());
                mActivity.get().addtoList(cat);
            }
        });
    }
}
