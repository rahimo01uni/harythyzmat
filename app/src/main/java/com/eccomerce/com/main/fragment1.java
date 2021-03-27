package com.eccomerce.com.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager.widget.ViewPager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eccomerce.com.R;
import com.eccomerce.com.constants;
import com.eccomerce.com.network.getvip;


public class fragment1 extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    ViewPager viewPager;
    SectionsPagerAdapter sectionsPagerAdapter;
    TabLayout tabLayout;
    Context context;
    SwipeRefreshLayout swipeRefreshLayout;
    public fragment1() {
    }
    public  static Handler s1,s2;
    @SuppressLint("ValidFragment")

    RecycleAdapterforVip recycleAdapterforVip;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment, container, false);
        context = view.getContext();
        constants.size=constants.vip.size();

        swipeRefreshLayout=(SwipeRefreshLayout)view.findViewById(R.id.swipe);
        swipeRefreshLayout.setOnRefreshListener(this);
        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler);

   //     GridLayoutManager manager = new GridLayoutManager(getActivity(),2);
     //   recyclerView.setLayoutManager(manager);
        //StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2,LinearLayoutManager.VERTICAL);
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        //manager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        manager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
             //   manager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        recyclerView.setLayoutManager(manager);
               // recyclerView.setItemAnimator(null);

       // recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
       // recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
        recycleAdapterforVip = new RecycleAdapterforVip(constants.vip,context);
        recyclerView.setAdapter(recycleAdapterforVip);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                ((StaggeredGridLayoutManager)recyclerView.getLayoutManager()).invalidateSpanAssignments();
            }
        });

        constants.status="2";
        s1=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                swipeRefreshLayout.setRefreshing(false);
                recycleAdapterforVip.setData(constants.vip);
            }
        };

        s2=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                swipeRefreshLayout.setRefreshing(true);
            }
        };
        getvip.get_Data();
        return view;

    }

    @Override
    public void onRefresh() {
        constants.vip.clear();
        constants.size=0;
        constants.iter=true;
        recycleAdapterforVip.setData(constants.vip);
        constants.idfd.clear();
        getvip.get_Data();
    }
}
