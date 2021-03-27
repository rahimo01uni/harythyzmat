package com.eccomerce.com.main;

/**
 * Created by Sulik on 1/3/2019.
 */


import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.eccomerce.com.Api;
import com.eccomerce.com.R;
import com.eccomerce.com.constants;
import com.eccomerce.com.login_package.LoginActivity;
import com.eccomerce.com.model.model_ulg;
import com.eccomerce.com.network.get_usluga;
import com.eccomerce.com.shopin.show_details;
import com.eccomerce.com.shopin.usluga_show_details;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;



public class UslugRecyclerAdapter extends RecyclerView.Adapter<UslugRecyclerAdapter.Reklama_viewholder> {
    ArrayList<model_ulg> list1;
    Context cntx;
    private FirebaseUser firebaseUser;

    public class Reklama_viewholder extends RecyclerView.ViewHolder {
        TextView title,rate;
        ImageView image;
        Button following_profile;

        public Reklama_viewholder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image_uslug);
            title = (TextView) itemView.findViewById(R.id.title_uslug);
            rate = itemView.findViewById(R.id.rate_uslug);
            following_profile = itemView.findViewById(R.id.following_profile);
            //  Typeface tp= Typeface.createFromAsset(context.getAssets(),"font/font10.ttf");
            //  title.setTypeface(tp);
            //   old_price.setTypeface(tp);
            //   price.setTypeface(tp);
        }
    }

    public UslugRecyclerAdapter(ArrayList<model_ulg> items, Context ctx) {
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
        holder.title.setText(list1.get(position).getTitle());
        int rate = Integer.parseInt(list1.get(position).getRate());
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        holder.rate.setText(""+rate);
        if(position==list1.size()-1 && constants.iter){
            constants.size3=list1.size();
                get_usluga.get_Data();
        }
        Glide.with(cntx)
                //.load("http://"+ Api.url+"images/" + dataList.get(position).getImage())
                .load("http://"+ Api.url+"ygty/images/" + list1.get(position).getImg()).thumbnail(0.01f).diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.image);

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(cntx,usluga_show_details.class);
                constants.select=list1.get(position);
                cntx.startActivity(i);
            }
        });

        if (firebaseUser!=null){
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Follow").child(firebaseUser.getUid()).child("following");
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Log.e("chekuid",list1.get(position).getUid());


                    if (dataSnapshot.child(list1.get(position).getUid()).exists()){
                        holder.following_profile.setText("following");
                        holder.following_profile.setTag(2);
                        Log.e("chekuid",list1.get(position).getUid());
                    }
                    else
                    {
                        holder.following_profile.setText("follow");
                        holder.following_profile.setTag(1);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        holder.following_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (firebaseUser!=null) {
                    if (holder.following_profile.getTag().equals(1)) {
                        FirebaseDatabase.getInstance().getReference().child("Follow").child(firebaseUser.getUid())
                                .child("following").child(list1.get(position).getUid()).setValue(true);
                        holder.following_profile.setTag(2);
                        holder.following_profile.setText("following");
                        // following_profile.setText();
                    } else {
                        FirebaseDatabase.getInstance().getReference().child("Follow").child(firebaseUser.getUid())
                                .child("following").child(list1.get(position).getUid()).removeValue();
                        holder.following_profile.setTag(1);
                        holder.following_profile.setText("follow");
                    }
                }else{
                    Intent ii = new Intent(cntx,LoginActivity.class);
                    ii.putExtra("tag",1);
                    cntx. startActivity(ii);
                }
            }
        });
/*
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Follow").child(firebaseUser.getUid()).child("following");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.e("chekuid",list1.get(position).getUid());


                if (dataSnapshot.child(list1.get(position).getUid()).exists()){
                    holder.following_profile.setText("following");
                    holder.following_profile.setTag(2);
                    Log.e("chekuid",list1.get(position).getUid());
                }
                else
                {
                    holder.following_profile.setText("follow");
                    holder.following_profile.setTag(1);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        holder.following_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (holder.following_profile.getTag().equals(1))
                {
                    FirebaseDatabase.getInstance().getReference().child("Follow").child(firebaseUser.getUid())
                            .child("following").child(list1.get(position).getUid()).setValue(true);
                    holder.following_profile.setTag(2);
                    holder.following_profile.setText("following");
                    // following_profile.setText();
                }
                else
                {
                    FirebaseDatabase.getInstance().getReference().child("Follow").child(firebaseUser.getUid())
                            .child("following").child(list1.get(position).getUid()).removeValue();
                    holder.following_profile.setTag(1);
                    holder.following_profile.setText("follow");
                }
            }
        });
        */
    }

    @Override
    public int getItemCount() {
        return list1.size();
    }

    public void setData(ArrayList<model_ulg> food) {
        list1 = food;
        notifyDataSetChanged();
    }
}
