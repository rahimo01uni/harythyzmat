package com.eccomerce.com.categories;

/**
 * Created by Sulik on 1/3/2019.
 */

import android.content.Context;
import android.content.Intent;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.eccomerce.com.Api;
import com.eccomerce.com.R;
import com.eccomerce.com.constants;
import com.eccomerce.com.model.modelvip;
import com.eccomerce.com.network.getitems_by_category;
import com.eccomerce.com.shopin.show_details;

import java.util.ArrayList;


public class RecycleAdapterfromcategories extends RecyclerView.Adapter<RecycleAdapterfromcategories.Reklama_viewholder> {
    ArrayList<modelvip> list1;
    Context cntx;
    String table;
    Boolean b1 = false, b2 = false, b3 = false;
    boolean t = false;


    public class Reklama_viewholder extends RecyclerView.ViewHolder {
        TextView title, price,price1, ratenumber, manat, count_number, time,discount;
        ImageView image;    RelativeLayout rl;

        TextView textView_oldprice, text_productname, text_newprice;
        RatingBar ratingBar;

        public Reklama_viewholder(View itemView) {
            super(itemView);
            rl=(RelativeLayout)itemView.findViewById(R.id.disc);
            discount=(TextView)itemView.findViewById(R.id.discount);
            image = (ImageView) itemView.findViewById(R.id.imageMain);
            title = (TextView) itemView.findViewById(R.id.title);
            price = (TextView) itemView.findViewById(R.id.price);
            price1=(TextView)itemView.findViewById(R.id.old_price);

            textView_oldprice=itemView.findViewById(R.id.textView_oldprice);
            text_productname=itemView.findViewById(R.id.text_productname);
            text_newprice=itemView.findViewById(R.id.text_newprice);
            ratingBar=itemView.findViewById(R.id.ratingBar);

            //  Typeface tp= Typeface.createFromAsset(context.getAssets(),"font/font10.ttf");
            //  title.setTypeface(tp);
            //   old_price.setTypeface(tp);
            //   price.setTypeface(tp);
        }
    }

    public RecycleAdapterfromcategories(ArrayList<modelvip> items, Context ctx) {
        this.list1 = items;
        this.cntx = ctx;

    }

    @Override
    public Reklama_viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.vipcard, parent, false);
        final Reklama_viewholder view = new Reklama_viewholder(v);
        return view;

    }

    @Override
    public void onBindViewHolder(final Reklama_viewholder holder, final int position) {

        holder.text_productname.setText(list1.get(position).getName());
        holder.text_newprice.setText(list1.get(position).getPrice()+" TMT ");
        holder.textView_oldprice.setText(list1.get(position).getPriceold() +" TMT ");
        holder.textView_oldprice.setPaintFlags(holder.textView_oldprice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        int rate = Integer.parseInt(list1.get(position).getRate());
        holder.ratingBar.setRating(rate);

       /*
        holder.title.setText(list1.get(position).getName());
        holder.price.setText(list1.get(position).getPrice()+" TMT ");
        holder.price1.setText(list1.get(position).getPriceold() +" TMT ");


        if(Integer.parseInt(list1.get(position).getPriceold())<=0)holder.price1.setVisibility(View.INVISIBLE);
        */
       if(position==list1.size()-1 && constants.iter){
            constants.size=list1.size();
            getitems_by_category.get_Data();
        }
        if (list1.get(position).getSkidka().equals("")){
            holder.discount.setText("");
            holder.rl.setVisibility(View.INVISIBLE);
        }else {
            holder.discount.setText(list1.get(position).getSkidka()+"%");
            holder.rl.setVisibility(View.VISIBLE);
        }

        Glide.with(cntx)
                //.load("http://"+ Api.url+"images/" + dataList.get(position).getImage())
                .load("http://"+ Api.url+"ygty/images/" + list1.get(position).getImage()).thumbnail(0.01f).diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.image);

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(cntx, show_details.class);
                constants.selected=list1.get(position);
                cntx.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list1.size();
    }

    public void setData(ArrayList<modelvip> food) {
        list1 = food;
        notifyDataSetChanged();
    }
}