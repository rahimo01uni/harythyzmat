package com.eccomerce.com.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.eccomerce.com.R;

public class Followinglist extends AppCompatActivity {
RecyclerView recycler_followlist ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_followinglist);

        recycler_followlist = (RecyclerView)findViewById(R.id.recycler_followlist);


    }
}
