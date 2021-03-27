package com.eccomerce.com.main;

/**
 * Created by Sulik on 1/3/2019.
 */

import android.content.Context;
import android.content.Intent;

import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.eccomerce.com.Api;
import com.eccomerce.com.R;
import com.eccomerce.com.constants;
import com.eccomerce.com.model.modelvip;
import com.eccomerce.com.shopin.show_details;

import java.util.ArrayList;


public class RecycleAdapterShowDetails extends RecyclerView.Adapter<RecycleAdapterShowDetails.Reklama_viewholder> {
    ArrayList<modelvip> list1;
    Context cntx;
    String table;
    Boolean b1 = false, b2 = false, b3 = false;
    boolean t = false;


    public class Reklama_viewholder extends RecyclerView.ViewHolder {
        TextView title, price,price1, ratenumber, manat, count_number, time,discount;
        ImageView image;    RelativeLayout rl;

        public Reklama_viewholder(View itemView) {
            super(itemView);
            rl=(RelativeLayout)itemView.findViewById(R.id.disc);
            discount=(TextView)itemView.findViewById(R.id.discount);
            image = (ImageView) itemView.findViewById(R.id.imageMain);
            title = (TextView) itemView.findViewById(R.id.title);
            price = (TextView) itemView.findViewById(R.id.price);
            ratenumber = (TextView)itemView.findViewById(R.id.n_people);
//            Typeface tp= Typeface.createFromAsset(cntx.getAssets(),"font/font10.ttf");
           /// title.setTypeface(tp);
        //   price1.setTypeface(tp);
          //  price.setTypeface(tp);
        }
    }

    public RecycleAdapterShowDetails(ArrayList<modelvip> items, Context ctx) {
        this.list1 = items;
        this.cntx = ctx;

    }

    @Override
    public Reklama_viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.vipcard_show_detail_recycle, parent, false);
        final Reklama_viewholder view = new Reklama_viewholder(v);
        return view;

    }

    @Override
    public void onBindViewHolder(final Reklama_viewholder holder, final int position) {
        holder.title.setText(list1.get(position).getName());
        holder.price.setText(list1.get(position).getPrice()+" TMT ");
        holder.ratenumber.setText(list1.get(position).getRate());
//        if(Integer.parseInt(list1.get(position).getPriceold())<=0)holder.price1.setVisibility(View.INVISIBLE);
        if(position==list1.size()-1 && constants.iter){

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
                .centerCrop()
                .into(holder.image);

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("namegityar","asdsad");
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
