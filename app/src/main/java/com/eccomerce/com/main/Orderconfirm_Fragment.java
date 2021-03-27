package com.eccomerce.com.main;


import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.eccomerce.com.Api;
import com.eccomerce.com.Db;
import com.eccomerce.com.R;
import com.eccomerce.com.constants;
import com.eccomerce.com.model.ItemModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Orderconfirm_Fragment extends Fragment {


    public Orderconfirm_Fragment() {
        // Required empty public constructor
    }

EditText editTextName,editTextPhone,txtAddress;
    Button btnorder_confirm;
    String email,uid;
    Cursor cursor;
    Db db;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_orderconfirm_, container, false);
        editTextName = (EditText)v.findViewById(R.id.editTextName);
        editTextPhone = (EditText)v.findViewById(R.id.editTextPhone);
        txtAddress = (EditText)v.findViewById(R.id.txtAddress);
        btnorder_confirm = (Button) v.findViewById(R.id.btnorder_confirm);
        db=new Db(getActivity());
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        uid= user.getUid();
        DatabaseReference refefrn=   FirebaseDatabase.getInstance().getReference().child("Buyer_user").child(user.getUid());
        refefrn.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String name = (String) dataSnapshot.child("full_name").getValue();
                String phone = (String) dataSnapshot.child("phone").getValue();
                String surname = (String) dataSnapshot.child("surname").getValue();
                email = (String) dataSnapshot.child("email").getValue();
              //  String imageurl = (String) dataSnapshot.child("imageurl").getValue();

                editTextName.setText(name+ " "+surname );
                editTextPhone.setText(phone);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        btnorder_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (editTextName.getText().toString().equals("")){
                    Toast.makeText(getActivity(), "User Name Field can't be blank", Toast.LENGTH_SHORT).show();
                }else
                if (editTextPhone.getText().toString().equals("")){
                    Toast.makeText(getActivity(), "Phone Field can't be blank", Toast.LENGTH_SHORT).show();
                }else
                if (txtAddress.getText().toString().equals(""))
                {
                    Toast.makeText(getActivity(), "Address Field can't be blank Please Enter valid address", Toast.LENGTH_SHORT).show();
                }else
                {
                    placeOredr();
                }

            }
        });

        return  v;
    }


    void placeOredr(){

        String del_username =  editTextName.getText().toString();
        String del_phone =  editTextPhone.getText().toString();
        String del_address =  txtAddress.getText().toString();
        String order_email = email;
        String order_uid = uid;

        List<ItemModel> records  = new ArrayList<ItemModel>();
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
        try {
        JSONObject objItem = new JSONObject();
        JSONArray ItemData = new JSONArray();

        for (ItemModel record : records) {
            String id = record.getItemId();
            String name = record.getName();
            String price = record.getPrice();
            String item_qty = record.getItem_qty();

                JSONObject itmData = new JSONObject();
                itmData.put("id", id);
                itmData.put("name", name);
                itmData.put("price", price);
                itmData.put("item_qty", item_qty);
                ItemData.put(itmData);


        }
            objItem.put("All_product", ItemData);
            objItem.put("del_username", del_username);
            objItem.put("del_phone", del_phone);
            objItem.put("del_address", del_address);
            objItem.put("order_email", order_email);
            objItem.put("order_uid", order_uid);
            String result = objItem.toString();
            System.out.println(result);
            Log.e("check json",result);
            get_Data(result);

        }catch (Exception ex){

        }
    }


    public  static String k;
    public  static int con=0;
    static String l;
    public static   Thread get_data=new Thread();
    public void  get_Data(String post_data){
        Log.d("gelenok","ok");
        get_data=new Thread(new Runnable() {
            @Override
            public void run() {
                try{

                    URL url=new URL("http://"+ Api.url+"ygty/order_send.php");
                    Log.d("url",""+url);
                    HttpURLConnection conn=(HttpURLConnection)url.openConnection();
                    conn.setDoOutput(true);
                    conn.setDoInput(true);
                    conn.setRequestMethod("POST");
                    conn.connect();
                    OutputStream outputStream = conn.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    bufferedWriter.write(post_data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();
                    InputStream in = conn.getInputStream();
                    BufferedReader br = new BufferedReader(new InputStreamReader(in));
                    StringBuilder sb = new StringBuilder();
                    String row = "";
                    Log.d("post",post_data);
                    while ((row = br.readLine()) != null) {
                        sb.append(row + "\n");}
                    br.close();
                    in.close();
                    Log.d("post1",sb.toString());
                    conn.disconnect();
                    if(sb.toString().length()<20)constants.iter=false;

                      if (!sb.toString().equals(""))GetJson(sb.toString());//idlar gelyar tazelemeli

                    Thread.currentThread().interrupt();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }) ;
        if(con==0) get_data.start();
    }


  public  void GetJson(String resultt){
        if(resultt!="0"){
            db.delet_data_pages();
        }else
        {

        }

        getActivity().finish();

    }
}


