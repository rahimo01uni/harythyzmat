package com.eccomerce.com.main;

import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AkshayeJH on 11/06/17.
 */


public class SectionsPagerAdapter1 extends FragmentPagerAdapter {

    private final List<Fragment> fragments = new ArrayList<>();
    private final List<String> fragmentTitles = new ArrayList<>();
    FragmentManager fm;
    public SectionsPagerAdapter1(FragmentManager fm) {
        super(fm);
        this.fm=fm;
    }

    @Override
    public Fragment getItem(int position) {

        return fragments.get(position);

    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    public CharSequence getPageTitle(int position) {

        return fragmentTitles.get(position);

    }

    public void AddFragment(Fragment fragment, String title) {
        fragments.add(fragment);
        Log.d("454",""+fragments.size());
        fragmentTitles.add(title);
    }

}