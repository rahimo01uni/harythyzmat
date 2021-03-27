package com.eccomerce.com.shopin;

import android.content.Context;
import android.content.Intent;
import androidx.viewpager.widget.PagerAdapter;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.eccomerce.com.Api;
import com.eccomerce.com.R;
import com.eccomerce.com.constants;
import com.eccomerce.com.main.CustomWatermark;

import java.util.ArrayList;


public class viewpager_adapter extends PagerAdapter {
    private LayoutInflater inflater;
    ArrayList<String> images;
    private Context context;
   String table;
    public viewpager_adapter(Context context, ArrayList<String> images) {
        this.context = context;
        this.images = images;
        inflater = LayoutInflater.from(context);

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        try {
            return images.size();
        }catch (IllegalStateException s){
            return  0;
        }

    }

    @Override
    public Object instantiateItem(ViewGroup view, final int position) {
       View myImageLayout = inflater.inflate(R.layout.card_for_image_adapter_for_show_details, view, false);
        ImageView myImage = (ImageView) myImageLayout
                .findViewById(R.id.image_for_viewpager_for_show_details);
       Glide.with(context)
                .load("http://"+ Api.url+"ygty/images/"+images.get(position))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(myImage);
        Log.d("url",Api.url+"images/"+images.get(position));
        myImage.setTag(position);
        constants.posimage=position;
        myImage.setClickable(true);
        myImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "Here is the share content body";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                context.startActivity(Intent.createChooser(sharingIntent, "Share via"));*/
                context.startActivity(new Intent(context.getApplicationContext(),reklam_photo.class));

            }
        });
        view.addView(myImageLayout, 0);
        return myImageLayout;
    }
    public  void notif(ArrayList<String> images ){
        this.images=images;
        notifyDataSetChanged();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }



}
