package com.eccomerce.com.shopin;

import android.content.Intent;
import android.os.Bundle;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import com.eccomerce.com.R;
import com.viewpagerindicator.CirclePageIndicator;



public class reklam_photo extends AppCompatActivity {
Toolbar p;
    ViewPager mPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reklama_photo_show);
        p=(Toolbar)findViewById(R.id.toolbar_photo_show_reklama);
        setSupportActionBar(p);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        mPager = (ViewPager) findViewById(R.id.viewpager_for_photo_show_reklama);
        mPager.setAdapter(new viewpageradapter(reklam_photo.this, show_details.images));
        CirclePageIndicator indicator = (CirclePageIndicator) findViewById(R.id.indicator_photo_show_reklama);
        indicator.setViewPager(mPager);
        Intent i=getIntent();

    }
    public boolean onOptionsItemSelected(MenuItem item) {
        int res_id = item.getItemId();
        if (res_id == android.R.id.home) {
            finish();
        }
        return true;
    }
}
