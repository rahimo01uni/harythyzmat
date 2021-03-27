package com.eccomerce.com.shopin;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.eccomerce.com.R;
import com.eccomerce.com.constants;
import com.eccomerce.com.main.MainActivity;
import com.eccomerce.com.main.RecycleAdapter;
import com.eccomerce.com.network.getvip;

public class allshopitems extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    RecycleAdapter recycleAdapter;
    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;
    Toolbar toolbar;
    ImageView backimg;
    public  static Handler s1,s2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all);
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        toolbar = (Toolbar) findViewById(R.id.toolbar_car_sahypa);
        constants.allitem.clear();
        constants.size=constants.allitem.size();
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        constants.idfd.clear();
        constants.category="";
        Intent ids=getIntent();
        constants.shopid=ids.getStringExtra("shopid");

        backimg = findViewById(R.id.imageMain);
        backimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        constants.size=0;
        swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.swipe);
        swipeRefreshLayout.setOnRefreshListener(this);
        recycleAdapter = new RecycleAdapter(constants.allitem,this);
        recyclerView.setAdapter(recycleAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        s1=new Handler(){
            @Override
            public void handleMessage(Message msg) {


                swipeRefreshLayout.setRefreshing(false);
                recycleAdapter.setData(constants.allitem);
            }
        };
        s2=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                swipeRefreshLayout.setRefreshing(true);
            }
        };

        getvip.get_Data();
    }

    @Override
    public void onRefresh() {

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        MainActivity.s1.sendEmptyMessage(1);
    }
}
