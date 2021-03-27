package com.eccomerce.com.liked;

/**
 * Created by Sulik on 1/3/2019.
 */


import android.content.Context;
import android.content.Intent;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.eccomerce.com.Api;
import com.eccomerce.com.R;
import com.eccomerce.com.constants;
import com.eccomerce.com.model.shops;
import com.eccomerce.com.network.get_shops;
import com.eccomerce.com.shopin.allshops;

import java.util.ArrayList;


public class ShopRecyclerAdapter_halanan extends RecyclerView.Adapter<ShopRecyclerAdapter_halanan.Reklama_viewholder> {
    ArrayList<shops> list1;
    Context cntx;


    public class Reklama_viewholder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView image;

        public Reklama_viewholder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image_uslug);
            title = (TextView) itemView.findViewById(R.id.title_uslug);
            //  Typeface tp= Typeface.createFromAsset(context.getAssets(),"font/font10.ttf");
            //  title.setTypeface(tp);
            //   old_price.setTypeface(tp);
            //   price.setTypeface(tp);
        }
    }

    public ShopRecyclerAdapter_halanan(ArrayList<shops> items, Context ctx) {
        this.list1 = items;
        this.cntx = ctx;

    }

    @Override
    public Reklama_viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.usluga_card, parent, false);
        final Reklama_viewholder view = new Reklama_viewholder(v);
        return view;

    }

    @Override
    public void onBindViewHolder(final Reklama_viewholder holder, final int position) {
        holder.title.setText(list1.get(position).getName());

        if(position==list1.size()-1 && constants.iter){
            constants.size=list1.size();
            get_shops.get_Data();
        }
        Glide.with(cntx)
                //.load("http://"+ Api.url+"images/" + dataList.get(position).getImage())
                .load("http://"+ Api.url+"ygty/images/" + list1.get(position).getImage()).thumbnail(0.01f).diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(holder.image);

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(cntx,allshops.class);
                i.putExtra("id",list1.get(position).getId());
                i.putExtra("name",list1.get(position).getName());
                i.putExtra("image",list1.get(position).getImage());
                cntx.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list1.size();
    }

    public void setData(ArrayList<shops> food) {
        list1 = food;
        notifyDataSetChanged();
    }
}
