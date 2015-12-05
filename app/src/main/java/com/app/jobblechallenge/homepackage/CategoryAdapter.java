package com.app.jobblechallenge.homepackage;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.jobblechallenge.R;
import com.app.jobblechallenge.itemspackage.CategoryItems;
import com.app.jobblechallenge.tables.CategoryTable;
import com.app.jobblechallenge.utils.AppPrefes;

import java.util.List;

/**
 * Created by George on 12/2/2015.
 */
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryAdapterViewHolder>{


    List<CategoryTable> categories;
    Context context;
    AppPrefes appPrefes;


    public CategoryAdapter(Context context, List<CategoryTable> categories)
    {
        this.context=context;
        this.categories=categories;
        appPrefes=new AppPrefes(context);
    }

    @Override
    public CategoryAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.category_single, viewGroup, false);
        CategoryAdapterViewHolder pvh = new CategoryAdapterViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(CategoryAdapterViewHolder holder, final int position) {
        holder.categoryName.setText(categories.get(position).getCategoryName());

        System.out.println("name on adapter " + categories.get(position).getCategoryName());

        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //goto details page
                appPrefes.saveData("category",categories.get(position).getCategoryId());
                System.out.println("category id is " + categories.get(position).getCategoryId());
                Intent i = new Intent(context, CategoryItems.class);
                context.startActivity(i);


            }
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public static class CategoryAdapterViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView categoryName;


        CategoryAdapterViewHolder(View itemView) {
            super(itemView);

            cv = (CardView)itemView.findViewById(R.id.cv_cat);
            categoryName = (TextView)itemView.findViewById(R.id.category_name);
        }
    }

}
