package com.eccomerce.com.liked;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.eccomerce.com.Dil;
import com.eccomerce.com.R;
import com.eccomerce.com.main.SectionsPagerAdapter;


public class liked extends Fragment {
    ViewPager viewPager;
    SectionsPagerAdapter sectionsPagerAdapter;
    TabLayout tabLayout;
    RelativeLayout towar,Newtowar,uslug;
    Toolbar toolbar;
    public  static  Context ctx;
    BottomNavigationView bottomNavigationView;
    public  static Handler s1=new Handler();
    Dil d;
    TextView h_uslug,h_towar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.liked, container, false);

       /*viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        sectionsPagerAdapter = new SectionsPagerAdapter(getActivity().getSupportFragmentManager());
        tabLayout = (TabLayout) view.findViewById(R.id.tablayout);
        sectionsPagerAdapter.AddFragment(new fragment11(),"best" );
        sectionsPagerAdapter.AddFragment(new fragment22(), "new");
        viewPager.setAdapter(sectionsPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
*/
       towar = view.findViewById(R.id.towar);
       uslug = view.findViewById(R.id.usluga);

       h_uslug = view.findViewById(R.id.title_usluga);
       h_towar = view.findViewById(R.id.title_towar);

       h_towar.setText(Dil.hala_haryt);
       h_uslug.setText(Dil.hala_usluga);

       towar.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               startActivity(new Intent(getActivity(),halanan_towarlar_activity.class));
           }
       });

        uslug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),halanan_usluga_activity.class));
            }
        });

        s1=new Handler(){
            @Override
            public void handleMessage(Message msg) {
             //   updateNavigationBarState(R.id.favorite);
            }};
        s1.sendEmptyMessage(1);
   return view;
    }
}