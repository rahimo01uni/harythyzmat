package com.eccomerce.com.shopin;

import android.content.Context;

import androidx.viewpager.widget.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.eccomerce.com.Api;
import com.eccomerce.com.R;

import java.util.ArrayList;


public class viewpageradapter1 extends PagerAdapter {
    private LayoutInflater inflater;
    ArrayList<String> images;
    private Context context;
   String table;
    public viewpageradapter1(Context context, ArrayList<String> images) {
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
        myImage.setClickable(true);
        myImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


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
