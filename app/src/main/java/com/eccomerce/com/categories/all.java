package com.eccomerce.com.categories;

import android.app.SearchManager;
import android.content.Context;
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
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;

import com.eccomerce.com.R;
import com.eccomerce.com.constants;
import com.eccomerce.com.network.getitems_by_category;

public class all extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, SearchView.OnQueryTextListener {
    RecycleAdapterfromcategories recycleAdapter;
    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;
    Toolbar toolbar;
    ImageView backimg;
    public static Handler s1 = new Handler(), s2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all);
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        toolbar = (Toolbar) findViewById(R.id.toolbar_car_sahypa);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
       // recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        constants.size = 0;
        constants.iter=true;
        constants.name="";
        constants.allitem.clear();
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe);
        swipeRefreshLayout.setOnRefreshListener(this);
        recycleAdapter = new RecycleAdapterfromcategories(constants.allitem, this);
        recyclerView.setAdapter(recycleAdapter);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        constants.idfd.clear();
        constants.size = 0;
        s1 = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                swipeRefreshLayout.setRefreshing(false);
                recycleAdapter.setData(constants.allitem);
            }
        };

        backimg = findViewById(R.id.imageMain);
        backimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        getitems_by_category.get_Data();
    }

    @Override
    public void onRefresh() {
        constants.allitem.clear();
        constants.size=0;
        constants.name="";
        constants.iter=true;
constants.idfd.clear();
        s1.sendEmptyMessage(1);
        getitems_by_category.get_Data();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menusearch, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.search));
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName())
        );   searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        constants.allitem.clear();
        constants.size=0;
        constants.iter=true;
        constants.idfd.clear();

        constants.name=s;
        s1.sendEmptyMessage(1);
        getitems_by_category.get_Data();
        return false;
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        constants.iter=true;
        constants.size=0;
    }
}
