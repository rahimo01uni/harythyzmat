package com.eccomerce.com.main;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.eccomerce.com.Db;
import com.eccomerce.com.Dil;
import com.eccomerce.com.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;


public class items extends Fragment{
    ViewPager viewPager;
    SectionsPagerAdapter sectionsPagerAdapter;
    TabLayout tabLayout;
    Toolbar toolbar;
    Db db;
    public  static  Context ctx;
    BottomNavigationView bottomNavigationView;
    public  static Handler s1=new Handler();
    Dil d;
    public  static  int i=0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main, container, false);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        sectionsPagerAdapter = new SectionsPagerAdapter(getActivity().getSupportFragmentManager());
        //toolbar = (Toolbar) view.findViewById(R.id.toolbar_car_sahypa);
//        toolbar.setTitle("");
        i=1;

        tabLayout = (TabLayout) view.findViewById(R.id.tablayout);
        sectionsPagerAdapter.AddFragment(new fragment1(),Dil.best);
        sectionsPagerAdapter.AddFragment(new fragment2(), Dil.New);
        sectionsPagerAdapter.AddFragment(new fragment3(), Dil.magazinlar);
        viewPager.setAdapter(sectionsPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }

}