package com.app.jobblechallenge.homepackage;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.app.jobblechallenge.R;
import com.app.jobblechallenge.loginpackage.LoginActivity;
import com.app.jobblechallenge.tables.CategoryTable;
import com.app.jobblechallenge.utils.ConnectionDetector;
import com.app.jobblechallenge.utils.GenericActivity;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Home extends GenericActivity<HomeBusinesslogic> implements categoryResults, addedCategory {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.categorylist)
    RecyclerView categorylist;
    @Bind(R.id.fab)
    FloatingActionButton fab;
    CategoryAdapter adapter;
    List<CategoryTable> list= new ArrayList<CategoryTable>();
    ConnectionDetector con;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, HomeBusinesslogic.class);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        categorylist.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(Home.this);
        categorylist.setLayoutManager(llm);

        System.out.println("user id is " + ParseUser.getCurrentUser().getEmail());
        con = new ConnectionDetector(Home.this);
        callCategoryList();

    }


    /**
     * @purpose calling parse server for category list
     */
    private void callCategoryList() {

        if (con.isConnectingToInternet()) {
            getOps().getCategoryFromParse(this);
        } else {
            showMessage("No connection", fab);
        }
    }

    /**
     * @param result
     * @param category
     * @purpose setting the category list to UI
     */
    @Override
    public void categoryList(boolean result, List<CategoryTable> category) {

        if (list==null)
        {
            list=new ArrayList<>();
        }
        list.addAll(category);
        adapter = new CategoryAdapter(Home.this, list);
        categorylist.setAdapter(adapter);
    }

    /**
     * @param result
     * @param message
     * @purpose error om parse call
     */
    @Override
    public void error(boolean result, String message) {
        showMessage(message, fab);
    }


    @OnClick(R.id.fab)
    public void fabClick() {
        new AddCategoryDialog(Home.this, this);
    }

    @Override
    public void categoryAdded(String name) {

        if (con.isConnectingToInternet()) {
            getOps().addCategorytoParse(name);
        }
        else {
            showMessage("No connection",fab);
        }
    }


    public void addtoList(CategoryTable obj)
    {
        list.add(obj);
        if (adapter!=null)
        {
            adapter.notifyDataSetChanged();
        }
        else
        {
            adapter=new CategoryAdapter(Home.this,list);
            categorylist.setAdapter(adapter);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.set, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.action_settings:
                //your code here
                ParseUser.logOut();
                Intent i = new Intent(Home.this, LoginActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.three_, R.anim.four_);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.three_, R.anim.four_);
    }
}
