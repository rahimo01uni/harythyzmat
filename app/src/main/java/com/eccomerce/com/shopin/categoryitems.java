package com.eccomerce.com.shopin;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.core.view.MenuItemCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.eccomerce.com.Dil;
import com.eccomerce.com.R;
import com.eccomerce.com.constants;
import com.eccomerce.com.liked.liked;
import com.eccomerce.com.main.MainActivity;
import com.eccomerce.com.main.RecycleAdapter;
import com.eccomerce.com.main.Usluga;
import com.eccomerce.com.network.getitemsct;
import com.eccomerce.com.network.getitemsll;
import com.eccomerce.com.userinfo;

public class categoryitems extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener,BottomNavigationView.OnNavigationItemSelectedListener, SearchView.OnQueryTextListener {
    RecycleAdapter recycleAdapter;
    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;
    Toolbar toolbar;
    BottomNavigationView bottomNavigationView;
    public  static Handler s1,s2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all);
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        toolbar = (Toolbar) findViewById(R.id.toolbar_car_sahypa);
        setSupportActionBar(toolbar);
        bottomNavigationView=(BottomNavigationView)findViewById(R.id.navigationView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        constants.idfd.clear();

        Intent i=getIntent();
        constants.shopid=i.getStringExtra("id");

        constants.size=0;
        swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.swipe);
        swipeRefreshLayout.setOnRefreshListener(this);
        recycleAdapter = new RecycleAdapter(constants.allitem,this);
        recyclerView.setAdapter(recycleAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        s1=new Handler(){
            @Override
            public void handleMessage(Message msg) {


                swipeRefreshLayout.setRefreshing(false);
                recycleAdapter.setData(constants.allitem);
            }
        };


        getitemsll.get_Data();

        s1=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                updateNavigationBarState(R.id.home1);
            }};
        s1.sendEmptyMessage(1);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.home1:
                        Log.d("post","1");
                        //   menuItem.setTitle(R.string.app_name);
                        //     bottomNavigationView.setSelectedItemId(R.id.home1);
                        startActivity(new Intent(categoryitems.this,MainActivity.class));
                        //updateNavigationBarState(menuItem.getItemId());
                        finish();
                        break;
                    case R.id.uslugi:
                        Log.d("post","2");
                        //
                        //updateNavigationBarState(menuItem.getItemId());

                        startActivity(new Intent(categoryitems.this,Usluga.class));
                        finish();
                        //  bottomNavigationView.setSelectedItemId(R.id.uslugi);
                        break;
                    case R.id.favorite:
                        Log.d("post","3");
                        //updateNavigationBarState(menuItem.getItemId());
                        //   bottomNavigationView.setSelectedItemId(R.id.home1);
                        startActivity(new Intent(categoryitems.this,liked.class));
                        finish();
                        break;
                    case R.id.person:
                        Log.d("post","4");
                        //updateNavigationBarState(menuItem.getItemId());
                        startActivity(new Intent(categoryitems.this,userinfo.class));
                        finish();
                        break;
                    default:
                        break;
                }
                // s1.sendEmptyMessage(1);
                return false;
            }
        });
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        MainActivity.s1.sendEmptyMessage(1);
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
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //Intent intent = new Intent(MainActivity.this, categories.class);
        //startActivity(intent);
        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    return false;
                }
            private void updateNavigationBarState(  int actionId){
                Menu menu = bottomNavigationView.getMenu();
                Log.d("post",""+actionId);
                for (int i = 0, size = menu.size(); i < size; i++) {

                    MenuItem item = menu.getItem(i);
                    Log.d("post1",""+item.getItemId()+" =" +actionId);
                    if (item.getItemId()==actionId){item.setChecked(true); }

                }

            }
            private void updateNavigationBarname( ){
                Menu menu = bottomNavigationView.getMenu();

                for (int i = 0, size = menu.size(); i < size; i++) {

                    MenuItem item = menu.getItem(i);
                    Log.d("post1",""+item.toString());

                    switch (i){
                        case  0:
                            item.setTitle(Dil.home);
                    break;
                case  1:
                    item.setTitle(Dil.uslugi);
                    break;
                case  2:
                    item.setTitle(Dil.favorite);
                    break;
                case  3:
                    item.setTitle(Dil.user);
                    break;
            }



                }}

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        constants.name=newText;
        getitemsct.get_Data();

        return false;
    }
}
