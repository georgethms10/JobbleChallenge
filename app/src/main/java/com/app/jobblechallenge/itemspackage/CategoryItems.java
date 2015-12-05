package com.app.jobblechallenge.itemspackage;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.app.jobblechallenge.R;
import com.app.jobblechallenge.additemspackage.AddItem;
import com.app.jobblechallenge.utils.AppPrefes;
import com.app.jobblechallenge.utils.ConnectionDetector;
import com.app.jobblechallenge.utils.GenericActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CategoryItems extends GenericActivity<CategoryItemsBusinessLogic> implements itemResults {

    AppPrefes appPrefes;
    ConnectionDetector con;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.itemslist)
    RecyclerView itemslist;
    @Bind(R.id.fab1)
    FloatingActionButton fab;
    itemResults callback;

    ItemAdapter adapter;

    List<ItemsPojo> myitemslist=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, CategoryItemsBusinessLogic.class);
        setContentView(R.layout.activity_category_items);
        ButterKnife.bind(this);
        callback=this;
        registerReceiver(receiver, new IntentFilter("changes"));
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        itemslist.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(CategoryItems.this);
        itemslist.setLayoutManager(llm);
        con=new ConnectionDetector(CategoryItems.this);
        appPrefes=new AppPrefes(CategoryItems.this);
        System.out.println("category id is " + appPrefes.getData("category"));
        findallItems(appPrefes.getData("category"));

    }

    private void findallItems(String category) {
        if (con.isConnectingToInternet())
        getOps().getAllItems(category,this);
    }

    @Override
    public void setList(List<ItemsPojo> items) {

        if (items!=null)
        {
            if (items.size()>0)
            {
                myitemslist.clear();
                myitemslist.addAll(items);
                adapter=new ItemAdapter(CategoryItems.this,myitemslist);
                itemslist.setAdapter(adapter);
            }
            else
            {
                showMessage("No items",fab);
            }
        }
    }

    @Override
    public void error(String message) {
        showMessage(message, fab);
    }


    @OnClick(R.id.fab1)
    public void AddnewItem()
    {
        Intent i = new Intent(CategoryItems.this, AddItem.class);
        startActivity(i);
        overridePendingTransition(R.anim.one_, R.anim.two_);
    }


    BroadcastReceiver receiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {

            Bundle b = intent.getExtras();
            if (b != null) {

                getOps().getAllItems(appPrefes.getData("category"),callback);

            }

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.three_,R.anim.four_);
    }
}
