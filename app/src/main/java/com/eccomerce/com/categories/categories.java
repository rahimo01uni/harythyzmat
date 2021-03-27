package com.eccomerce.com.categories;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.google.android.material.bottomnavigation.BottomNavigationView.OnNavigationItemSelectedListener;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;


import com.eccomerce.com.R;
import com.eccomerce.com.constants;
import com.eccomerce.com.main.MainActivity;
import com.eccomerce.com.main.searchble_activity;
import com.eccomerce.com.network.getcategory;

public class categories extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    RecycleAdaptercategories r;
    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;
    Toolbar toolbar;
    public  static Handler s1=new Handler();
    ImageView backimg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        toolbar = (Toolbar) findViewById(R.id.toolbar_car_sahypa);
        swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.swipe);
        swipeRefreshLayout.setOnRefreshListener(this);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
         constants.catmodels.clear();
        r = new RecycleAdaptercategories(constants.catmodels,this);
        recyclerView.setAdapter(r);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        backimg = findViewById(R.id.imageMain);
        backimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        s1=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                swipeRefreshLayout.setRefreshing(false);
                r.setData(constants.catmodels);
            }};
        s1.sendEmptyMessage(1);

        getcategory.get_Data();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        MainActivity.s1.sendEmptyMessage(1);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search, menu);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Intent intent = new Intent(categories.this, searchble_activity.class);
        startActivity(intent);
        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRefresh() {
        constants.catmodels.clear();
        constants.size=0;
        constants.iter2=true;
        r.setData(constants.catmodels);
        getcategory.get_Data();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
