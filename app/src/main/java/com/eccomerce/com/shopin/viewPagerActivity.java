package com.eccomerce.com.shopin;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.eccomerce.com.R;
import com.eccomerce.com.main.MainActivity;

import java.util.ArrayList;

public class viewPagerActivity extends AppCompatActivity {
    viewpager_adapter v;
    ViewPager mPager;
    public static ArrayList<String > images;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);
        mPager = (ViewPager) findViewById(R.id.viewpager_detail);

        /*images=new ArrayList<>();
        images.add(constants.selected.getImage1());
        images.add(constants.selected.getImage2());
        images.add(constants.selected.getImage3());*/


        v=new viewpager_adapter(viewPagerActivity.this, images);


        mPager.setAdapter(v);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //   mDemoSlider.removeAllViews();
        images.clear();
        v.notif(images);
        MainActivity.s1.sendEmptyMessage(1);
    }
}
