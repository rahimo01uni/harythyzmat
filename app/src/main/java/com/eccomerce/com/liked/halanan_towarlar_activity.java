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
import com.eccomerce.com.network.get_liked_items;

public class halanan_towarlar_activity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    RecycleAdapter_towarlar_halanan recycleAdapter;
    public  static Handler s1,s2;
    SwipeRefreshLayout swipeRefreshLayout;
    ImageView backimg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_halanan_towarlar_activity);
        swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.swipe);
        constants.idfd.clear();
        constants.likeditems.clear();
 swipeRefreshLayout.setOnRefreshListener(this);
        final RecyclerView recyclerView = findViewById(R.id.recycler);
        recycleAdapter = new RecycleAdapter_towarlar_halanan(constants.likeditems,this);
        recyclerView.setAdapter(recycleAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
    constants.size=constants.likeditems.size();
constants.iter=true;
        s1=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                swipeRefreshLayout.setRefreshing(false);
                recycleAdapter.setData(constants.likeditems);
            }
        };

        s2=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                swipeRefreshLayout.setRefreshing(true);
            }
        };

        backimg = findViewById(R.id.imageMain);
        backimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        get_liked_items.get_Data();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        constants.idfd.clear();
        MainActivity.s1.sendEmptyMessage(1);
    }

    @Override
    public void onRefresh() {
        constants.likeditems.clear();
        constants.size=0;
        constants.idfd.clear();
        constants.iter=true;
        get_liked_items.get_Data();
    }
}
