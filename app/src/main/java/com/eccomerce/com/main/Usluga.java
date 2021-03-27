package com.eccomerce.com.main;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import com.google.android.material.appbar.AppBarLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.slider.library.SliderLayout;
import com.eccomerce.com.Dil;
import com.eccomerce.com.R;
import com.eccomerce.com.constants;
import com.eccomerce.com.network.get_usluga;
import com.eccomerce.com.shopin.viewpager_adapter;

import java.util.ArrayList;

public class Usluga extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    ImageView img_uslug;
    TextView title_uslug, n_rate;
    ViewPager mPager;
    Menu menu;
    MenuItem item;
    EditText edit_search;
    UslugRecyclerAdapter recycleAdapter;
    String id;
    Intent i;
    public static ArrayList<String > images;
    Toolbar toolbar;
    SliderLayout mDemoSlider;
    viewpager_adapter v;
    AppBarLayout appBarLayout;
    SwipeRefreshLayout swipeRefreshLayout;
    Context context;
    public  static Handler s1=new Handler(),s2=new Handler();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_usluga, container, false);

        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler);
        recycleAdapter = new UslugRecyclerAdapter(constants.ulgslist,getActivity());
        recyclerView.setAdapter(recycleAdapter);
        edit_search=view.findViewById(R.id.edit_search);
        edit_search.setHint(Dil.gözleg);
        edit_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                constants.name = charSequence.toString();
                constants.ulgslist.clear();
                s1.sendEmptyMessage(10);
                constants.size3=0;
                constants.iter=true;
                constants.idfd2.clear();
                get_usluga.get_Data();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
swipeRefreshLayout=(SwipeRefreshLayout)view.findViewById(R.id.swipe);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        swipeRefreshLayout.setOnRefreshListener(this);
        s1=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                swipeRefreshLayout.setRefreshing(false);
                recycleAdapter.setData(constants.ulgslist);
            }
        };

        s2=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                swipeRefreshLayout.setRefreshing(true);
            }
        };

        get_usluga.get_Data();

   return view;

    }


    @Override
    public void onRefresh() {
        constants.ulgslist.clear();
        constants.size3=0;
        constants.idfd2.clear();
        constants.iter=true;
        get_usluga.get_Data();
    }
}
