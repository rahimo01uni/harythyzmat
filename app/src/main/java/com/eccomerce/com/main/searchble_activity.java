package com.eccomerce.com.main;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import androidx.annotation.Nullable;
import androidx.core.view.MenuItemCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.eccomerce.com.R;
import com.eccomerce.com.constants;
import com.eccomerce.com.network.getitems_search;


/**
 * Created by Admin on 5/25/2019.
 */

public class searchble_activity extends AppCompatActivity implements SearchView.OnQueryTextListener, SwipeRefreshLayout.OnRefreshListener {
    Toolbar toolbar;
    RecycleAdapterforsearch recycleAdapterforVip;
    SwipeRefreshLayout swipeRefreshLayout;
    ImageView backimg;
    public  static Handler s1,s2;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        toolbar = (Toolbar) findViewById(R.id.toolbar_car_sahypa);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
constants.iter=true;
constants.idfd.clear();
        constants.name="";
        swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.swipe);
       swipeRefreshLayout.setOnRefreshListener(searchble_activity.this);
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recycleAdapterforVip = new RecycleAdapterforsearch(constants.search_item,this);
        recyclerView.setAdapter(recycleAdapterforVip);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));


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
                recycleAdapterforVip.setData(constants.search_item);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menusearch, menu);
        SearchManager searchManager = (SearchManager)getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.search));
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName())
        );
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(this);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        constants.search_item.clear();
        recycleAdapterforVip.setData(constants.search_item);
        constants.size=0;
        constants.idfd.clear();
        constants.iter=true;
        constants.name=s;
        getitems_search.get_Data();
        return false;
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        constants.iter=true;
        constants.size=0;
    }

    @Override
    public void onRefresh() {
        constants.name="";
        constants.search_item.clear();
        recycleAdapterforVip.setData(constants.search_item);
        constants.size=0;
        constants.idfd.clear();
        constants.iter=true;
        getitems_search.get_Data();
    }
}
