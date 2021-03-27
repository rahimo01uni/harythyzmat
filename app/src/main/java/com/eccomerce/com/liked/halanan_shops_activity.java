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

public class halanan_shops_activity extends AppCompatActivity {
    ShopRecyclerAdapter_halanan recycleAdapter;
    public  static Handler s1,s2;
    SwipeRefreshLayout swipeRefreshLayout;
    ImageView backimg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_halanan_taze_harytlar_activity);
        swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.swipe);
        backimg = findViewById(R.id.imageMain);
        backimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        final RecyclerView recyclerView = findViewById(R.id.recycler);
        recycleAdapter = new ShopRecyclerAdapter_halanan(constants.shopses,this);
        recyclerView.setAdapter(recycleAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));


        s1=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                swipeRefreshLayout.setRefreshing(false);
                recycleAdapter.setData(constants.shopses);
            }
        };

        s2=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                swipeRefreshLayout.setRefreshing(true);
            }
        };
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        MainActivity.s1.sendEmptyMessage(1);
    }
}
