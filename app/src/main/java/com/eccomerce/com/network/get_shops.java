package com.eccomerce.com.network;


import android.util.Log;

import com.eccomerce.com.Api;
import com.eccomerce.com.constants;
import com.eccomerce.com.main.fragment3;
import com.eccomerce.com.model.shops;

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

public class get_shops {
    public  static String k;
    public  static int con=0;
    static String l;
    public static Thread get_data=new Thread();
    public static void  get_Data(){

        get_data=new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    URL url;

                    url=new URL("http://"+ Api.url+"ygty/get_shops1.php");

                    Log.d("url",""+url);
                    HttpURLConnection conn=(HttpURLConnection)url.openConnection();
                    conn.setDoOutput(true);
                    conn.setDoInput(true);
                    conn.setRequestMethod("POST");
                    conn.connect();
                    OutputStream outputStream = conn.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String post_data = URLEncoder.encode("Region", "UTF-8") + "=" + URLEncoder.encode(constants.location, "UTF-8")+"&"+

                            URLEncoder.encode("category", "UTF-8") + "=" + URLEncoder.encode(constants.category, "UTF-8")
                            +"&"+
                            URLEncoder.encode("size", "UTF-8") + "=" + URLEncoder.encode(""+constants.size2, "UTF-8") +"&"+
                            URLEncoder.encode("shop", "UTF-8") + "=" + URLEncoder.encode(constants.name, "UTF-8")
                            ;
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
                    Log.d("name shop",sb.toString());
                    br.close();
                    in.close();
                    conn.disconnect();

                    if (!sb.toString().equals("") && constants.iter2)GetJson(sb.toString());//idlar gelyar tazelemeli
                    constants.iter2=false;
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

    public static void GetJson(final String s){Log.d("shops",s);
        Thread json=new Thread(new Runnable() {
            @Override
            public void run() {
                try{//bashda data basadan hemme informasiyasyny alyas
                    JSONObject jsonObject=new JSONObject(s);
                    JSONArray jsonArray=jsonObject.getJSONArray("result");
                    shops m;
                    for(int i=0; i<jsonArray.length();i++){
                        JSONObject data=jsonArray.getJSONObject(i);
                        if(!constants.idfd1.contains(data.optString("id"))) {
                            m=new shops(data.optString("id"),
                                    data.optString("uid"),
                                    data.optString("category"),
                                    data.optString("name"),
                                    data.optString("nomer"),
                                    data.optString("content"),
                                    data.optString("skidka"),
                                    data.optString("image"));
                            m.setLocate(data.optString("location"));
                            constants.shopses.add(m);
                            constants.idfd1.add(data.optString("id"));
                        }}
                    if(s.length()<50)   constants.iter2=false; else constants.iter2=true;
                    fragment3.s1.sendEmptyMessage(1);
                    Thread.currentThread().interrupt();
                } catch (JSONException e) {
                    e.printStackTrace();
                }}
        });
        json.start();
    }

}