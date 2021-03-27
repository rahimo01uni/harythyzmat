package com.eccomerce.com.shopin;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import androidx.core.view.MenuItemCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.eccomerce.com.R;
import com.eccomerce.com.categories.categories;
import com.eccomerce.com.constants;
import com.eccomerce.com.main.MainActivity;
import com.eccomerce.com.main.Recycleforshopitems;
import com.eccomerce.com.network.getitemsshop;

/**
 * Created by Admin on 5/23/2019.
 */

public class allshops extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, SearchView.OnQueryTextListener {

    Context context;
    ImageView backimg;
    public  static Handler s1,s2;
    Toolbar toolbar;
    Recycleforshopitems recycleAdapter;
    SwipeRefreshLayout swipeRefreshLayout;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all);
        toolbar = (Toolbar) findViewById(R.id.toolbar_car_sahypa);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        final RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recycler);
        recycleAdapter = new Recycleforshopitems(constants.shopitem,this);
        recyclerView.setAdapter(recycleAdapter);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        Intent i=getIntent();
        constants.shopid=i.getStringExtra("shopid");

        Log.e("shopid",constants.shopid);
        swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.swipe);
        swipeRefreshLayout.setOnRefreshListener(this);
        constants.shopitem.clear();
        constants.size=constants.shopitem.size();
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
                recycleAdapter.setData(constants.shopitem);
            }};

    s1.sendEmptyMessage(1);
        getitemsshop.get_Data();
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

        searchView.setOnQueryTextListener(this);
        return true;
    }




    @Override
    public void onBackPressed() {
        super.onBackPressed();
        MainActivity.s1.sendEmptyMessage(1);
        constants.size=0;
        constants.iter=true;
        constants.shopitem.clear();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Intent intent = new Intent(allshops.this, categories.class);
        startActivity(intent);
        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRefresh() {
        constants.shopitem.clear();
        recycleAdapter.setData(constants.shopitem);
        constants.size=0;
        constants.iter=true;

        getitemsshop.get_Data();
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        constants.shopitem.clear();
        recycleAdapter.setData(constants.shopitem);
        constants.size=0;
        constants.iter=true;
        constants.name=s;
        getitemsshop.get_Data();
        return false;
    }
}
