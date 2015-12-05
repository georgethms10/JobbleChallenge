package com.app.jobblechallenge.itemspackage;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.jobblechallenge.R;
import com.app.jobblechallenge.detailspackage.DetailsActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by sics on 12/2/2015.
 */
public class ItemAdapter  extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    Context context;
    List<ItemsPojo> list;

    public ItemAdapter(Context context,List<ItemsPojo> list)
    {
        this.context=context;
        this.list=list;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_single, parent, false);
        ItemViewHolder pvh = new ItemViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, final int position) {

        holder.name.setText(list.get(position).getName());
        Picasso.with(holder.image.getContext())
                .load(list.get(position).getImage())
                .into(holder.image);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //go to detail activity
                Intent i = new Intent(context, DetailsActivity.class);
                i.putExtra("image",list.get(position).getImage());
                i.putExtra("name",list.get(position).getName());
                i.putExtra("desc",list.get(position).getDescription());
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        public final ImageView image;
        public final TextView name;
        public final View mView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            mView=itemView;
            image = (ImageView)itemView.findViewById(R.id.items_image);
            name = (TextView)itemView.findViewById(R.id.item_name);
        }
    }


}
