package com.eccomerce.com.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eccomerce.com.R;
import com.eccomerce.com.constants;
import com.eccomerce.com.network.getitemsnew;


public class fragment2 extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    Context context;
    SwipeRefreshLayout swipeRefreshLayout;
    public fragment2() {
    }
    public  static Handler s1,s2;
    @SuppressLint("ValidFragment")

    RecycleAdapter2 recycleAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment, container, false);
        context = view.getContext();
        constants.size1=constants.taze.size();
        swipeRefreshLayout=(SwipeRefreshLayout)view.findViewById(R.id.swipe);
        swipeRefreshLayout.setOnRefreshListener(this);
        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler);
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        manager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        recyclerView.setLayoutManager(manager);

        recycleAdapter = new RecycleAdapter2(constants.taze,context);
        recyclerView.setAdapter(recycleAdapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                ((StaggeredGridLayoutManager)recyclerView.getLayoutManager()).invalidateSpanAssignments();
            }
        });

        // recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        constants.status="1";
        s1=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                swipeRefreshLayout.setRefreshing(false);
                recycleAdapter.setData(constants.taze);
            }
        };

        s2=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                swipeRefreshLayout.setRefreshing(true);
            }
        };
        getitemsnew.get_Data();
        return view;

    }

    @Override
    public void onRefresh() {
        constants.taze.clear();
        constants.size1=0;
        constants.iter1=true;
        recycleAdapter.setData(constants.taze);
        constants.idfd.clear();
        getitemsnew.get_Data();
    }
}
