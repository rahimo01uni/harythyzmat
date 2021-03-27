package com.eccomerce.com.main;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.eccomerce.com.Adepter.MenucartAdepter;
import com.eccomerce.com.Db;
import com.eccomerce.com.R;
import com.eccomerce.com.login_package.LoginActivity;
import com.eccomerce.com.model.ItemModel;
import com.github.paolorotolo.expandableheightlistview.ExpandableHeightListView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Cart_view_Fragment extends Fragment implements   MenucartAdepter.customButtonListener {


    public Cart_view_Fragment() {
        // Required empty public constructor
    }
    Db db;
    Cursor cursor;
    List<ItemModel> records;
    MenucartAdepter menuAdapter;
    ExpandableHeightListView listView;
    Button btnproceed_confirm;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_cart_view_, container, false);
        db=new Db(getActivity());
        records = new ArrayList<ItemModel>();
        listView = (ExpandableHeightListView) view.findViewById(R.id.listView2);
        btnproceed_confirm = (Button) view.findViewById(R.id.btnproceed_confirm);
        get_cart();

        btnproceed_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (records.size()<=0){
                    Toast.makeText(getActivity(), "Cart Empty please select atleast one product", Toast.LENGTH_SHORT).show();
                }else
                {
                    if (FirebaseAuth.getInstance().getCurrentUser()!=null){
                        getFragmentManager().beginTransaction().replace(R.id.fram_cart,new Orderconfirm_Fragment()).commit();

                    }else
                    {
                        Intent ii = new Intent(getActivity(), LoginActivity.class);
                        ii.putExtra("tag",1);
                        startActivity(ii);

                    }

                }

            }
        });
        return  view;
    }



    void get_cart(){
        cursor=db.getCartData();
        if (cursor.moveToFirst()) {
            do {
                ItemModel lm = new ItemModel();
                lm.setItemId(String.valueOf(cursor.getInt(1)));
                lm.setName(cursor.getString(3));
                lm.setPrice(String.valueOf(cursor.getInt(2)));
                lm.setIteam_qty(cursor.getString(4));
                Log.e("iteam weihht",cursor.getString(3));
                records.add(lm);

            } while (cursor.moveToNext());
        }

        menuAdapter =new MenucartAdepter(getContext(), records);
        menuAdapter.setCustomButtonListner(Cart_view_Fragment.this);
        listView.setAdapter(null);
        listView.setAdapter(menuAdapter);
        listView.setExpanded(true);
    }

    @Override
    public void onButtonClickListner(int position, String value, String btn, HashMap<String, String> details) {
        if (btn.equals("Add")){

            Log.e("details ",""+ details);

        }
        else if(btn.equals("plus"))
        {
            Log.e("plus ",""+ details);
        }
        else if(btn.equals("minus"))
        {
            /*JSONObject jsonObject=new JSONObject(details);
            try {

                if(jsonObject.getString("item_remove").equals("0")){
                  get_cart();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }*/
            Log.e("minus ",""+ details);
        }
    }
}
