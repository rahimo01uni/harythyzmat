package com.eccomerce.com.network;


import android.util.Log;

import com.eccomerce.com.Api;
import com.eccomerce.com.constants;
import com.eccomerce.com.main.fragment1;
import com.eccomerce.com.model.modelvip;

import org.json.JSONArray;
import org.json.JSONException;
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


/**
 * Created by User on 27.06.2018.
 */

public class sendliked_shop {
   public  static String k;
    public  static int con=0;
    static String l;
    public static   Thread get_data=new Thread();
    public static void  get_Data( final  String id,final String token){
          Log.d("gelenok","ok");
        get_data=new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    URL url=new URL("http://"+ Api.url+"ygty/insert_liked_shop.php");
                    Log.d("url",""+url);
                    HttpURLConnection conn=(HttpURLConnection)url.openConnection();
                    conn.setDoOutput(true);
                    conn.setDoInput(true);
                    conn.setRequestMethod("POST");
                    conn.connect();
                    OutputStream outputStream = conn.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String post_data = URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8")+"&"+
                            URLEncoder.encode("token", "UTF-8") + "=" +
                            URLEncoder.encode(token, "UTF-8");
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
                    Log.d("post",sb.toString());
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
                }    }
        }) ;
       if(con==0) get_data.start();
    }

    public static void GetJson(final String s){
        Thread json=new Thread(new Runnable() {
            @Override
            public void run() {
                try{//bashda data basadan hemme informasiyasyny alyas
                    JSONObject jsonObject=new JSONObject(s);
                    JSONArray jsonArray=jsonObject.getJSONArray("result");
                    modelvip m;
                    for(int i=0; i<jsonArray.length();i++) {
                        JSONObject data = jsonArray.getJSONObject(i);
                        if(!constants.idfd.contains(data.optString("id"))) {
                            m=new modelvip(data.optString("id"),
                                    data.optString("uid"),
                                    data.optString("name"),
                                    data.optString("price"),
                                    data.optString("priceold"),
                                    data.optString("number"),
                                    data.optString("shopid"),
                                    data.optString("shop_uid"),
                                    data.optString("image"),
                                    data.optString("image1"),
                                    data.optString("image2"),
                                    data.optString("image3"),
                                    data.optString("skidka"),
                                    data.optString("content"));
                            constants.vip.add(m);
                            constants.idfd.add(data.optString("id"));
                    }}
                    fragment1.s1.sendEmptyMessage(1);

                    Thread.currentThread().interrupt();
                } catch (JSONException e) {
                    e.printStackTrace();
                }}
        });
        json.start();
    }
}
