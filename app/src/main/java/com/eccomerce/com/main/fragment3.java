package com.eccomerce.com.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


import com.eccomerce.com.Dil;
import com.eccomerce.com.R;
import com.eccomerce.com.constants;
import com.eccomerce.com.network.get_followlist;
import com.eccomerce.com.network.get_shops;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class fragment3 extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    EditText edit_search;
    Context context;
    SwipeRefreshLayout swipeRefreshLayout;
    private FirebaseUser firebaseUser;
    public fragment3() {
    }
    public  static Handler s1,s2;
    @SuppressLint("ValidFragment")

    ShopRecyclerAdapter recycleAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment2, container, false);
        context = view.getContext();
        constants.size2=constants.shopses.size();
        swipeRefreshLayout=(SwipeRefreshLayout)view.findViewById(R.id.swipe);
        swipeRefreshLayout.setOnRefreshListener(this);
        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler);
        recycleAdapter = new ShopRecyclerAdapter(constants.shopses,context);
        recyclerView.setAdapter(recycleAdapter);
        constants.status="0";
        edit_search=view.findViewById(R.id.edit_search);
        edit_search.setHint(Dil.gözleg);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        s1=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                swipeRefreshLayout.setRefreshing(false);
                recycleAdapter.setData(constants.shopses);
            }
        };
        edit_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                constants.name = charSequence.toString();
                constants.shopses.clear();
                s1.sendEmptyMessage(10);
                constants.size2=0;
                constants.iter2=true;
                constants.idfd1.clear();
                get_shops.get_Data();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        s2=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                swipeRefreshLayout.setRefreshing(true);
            }
        };
        get_shops.get_Data();
        onRefresh();
        return view;

    }

    @Override
    public void onRefresh() {
        get_followlist.get_Data(firebaseUser.getUid());
        constants.shopses.clear();
        constants.size2=0;
        constants.idfd1.clear();
        constants.iter2=true;
        recycleAdapter.setData(constants.shopses);
        get_shops.get_Data();
    }
}
