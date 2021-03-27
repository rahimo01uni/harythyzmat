package com.eccomerce.com.liked;

import android.os.Handler;
import android.os.Message;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.eccomerce.com.R;
import com.eccomerce.com.constants;
import com.eccomerce.com.main.MainActivity;
import com.eccomerce.com.network.get_liked_usluga;

public class halanan_usluga_activity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    UslugRecyclerAdapterhalanan recycleAdapter;
    public  static Handler s1,s2;
    SwipeRefreshLayout swipeRefreshLayout;
    ImageView backimg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_halanan_usluga_activity);
        swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.swipe);
        constants.size=0;
        constants.likedusluga.clear();
        constants.iter=true;
        swipeRefreshLayout.setOnRefreshListener(this);
        final RecyclerView recyclerView = findViewById(R.id.recycler);
        recycleAdapter = new UslugRecyclerAdapterhalanan(constants.likedusluga,this);
        recyclerView.setAdapter(recycleAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
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
                recycleAdapter.setData(constants.likedusluga);
            }
        };

        s2=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                swipeRefreshLayout.setRefreshing(true);
            }
        };
        get_liked_usluga.get_Data();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        MainActivity.s1.sendEmptyMessage(1);
    }

    @Override
    public void onRefresh() {
        constants.likedusluga.clear();
        constants.size=0;
        constants.iter=true;
        get_liked_usluga.get_Data();
    }
}
