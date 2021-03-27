package com.eccomerce.com;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.eccomerce.com.categories.categories;
import com.eccomerce.com.main.MainActivity;
import com.eccomerce.com.main.UslugRecyclerAdapter2;

public class UslugiciActivity extends AppCompatActivity{
    ImageView img_uslug;
    TextView title_uslug;
    UslugRecyclerAdapter2 recycleAdapter;
    ImageView backimg;
    public  static Handler s1=new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uslugici);

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recycleAdapter = new UslugRecyclerAdapter2(constants.vip,this);
        recyclerView.setAdapter(recycleAdapter);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        s1=new Handler(){
            @Override
            public void handleMessage(Message msg) {

            }};



        backimg = findViewById(R.id.imageMain);
        backimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        s1.sendEmptyMessage(1);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.se, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        MainActivity.s1.sendEmptyMessage(1);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Intent intent = new Intent(UslugiciActivity.this, categories.class);
        startActivity(intent);
        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }


}

