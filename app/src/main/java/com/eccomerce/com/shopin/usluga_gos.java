package com.eccomerce.com.shopin;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.eccomerce.com.Api;
import com.eccomerce.com.Dil;
import com.eccomerce.com.R;
import com.eccomerce.com.main.MainActivity;
import com.eccomerce.com.main.adapter_for_recycle_for_caard_surat_gos;
import com.eccomerce.com.model.model_surat_gos;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

import in.myinnos.awesomeimagepicker.activities.AlbumSelectActivity;
import in.myinnos.awesomeimagepicker.helpers.ConstantsCustomGallery;
import in.myinnos.awesomeimagepicker.models.Image;

public class usluga_gos extends AppCompatActivity {
    static public String data;
    static public EditText editText_sene,email_edit,ulanjy_edit,password_edit,bildiris_edit;
    TextView ulanjy,email,password,bildisady,surat_txt,spinner_yeri,hyzmatyn_ady,surat_gos,dusun,nomur2;
    ImageView imagepicker;
    Spinner spin;
    Handler setImageView,set_image,s1,s2;
    public  static String image="",image1,image2,image3,image4,image5;
    ArrayList<Bitmap> img = new ArrayList<>();
    ArrayList<model_surat_gos> listView_surat;
    adapter_for_recycle_for_caard_surat_gos a;
    EditText name,number,content;
    String   name1,number1,content1,username1,password1,email1;
    Button add;
    ImageView backimg;
    LinearLayout lenta_surat_gos_layout_bil_gos;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_lenta1_usluga_gos);
        //LinearLayout layout_hatar = (LinearLayout) view.findViewById(R.id.layout_lenta_hatary);
        listView_surat=new ArrayList<>();
        recyclerView=(RecyclerView)findViewById(R.id.recycle_lenta_add_surat);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.HORIZONTAL));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        a=new adapter_for_recycle_for_caard_surat_gos(listView_surat,getApplicationContext());
        recyclerView.setAdapter(a);
        imagepicker=(ImageView)findViewById(R.id.imagepickerlenta) ;
        surat_gos=findViewById(R.id.textView_surat_gos);
        surat_gos.setText(Dil.surat_gos);
        name=(EditText)findViewById(R.id.Ady);
        content=(EditText)findViewById(R.id.content);
        content.setHint(Dil.dus_hint_usluga);
        dusun=findViewById(R.id.lenta_dusundirisi_title_bil_gos);
        dusun.setText(Dil.maglumat2);
        number=(EditText)findViewById(R.id.nomer);
        number.setHint(Dil.nomer_hint);
        nomur2=findViewById(R.id.nomer2_txt);
        nomur2.setText(Dil.nomer);
        hyzmatyn_ady=findViewById(R.id.usluga_ady);
        hyzmatyn_ady.setText(Dil.hyzmatyn_ady);
name.setHint(Dil.hyzmatyn_hint);
        lenta_surat_gos_layout_bil_gos=findViewById(R.id.lenta_surat_gos_layout_bil_gos);
        final ProgressDialog progressDialog=new ProgressDialog(this);


        backimg = findViewById(R.id.imageMain);
        backimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        add=(Button)findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                number1=number.getText().toString();
                content1=content.getText().toString();
                name1=name.getText().toString();
                if(name1.equals("")&&content1.equals("")&&name1.equals("")){
                    Toast.makeText(usluga_gos.this, Dil.hemmesini_doldur, Toast.LENGTH_LONG).show();
                }
                else {
                    setImageView.sendEmptyMessage(1);
                    name.setText("");
                    number.setText("");
                    content.setText("");
                    recyclerView.removeAllViews();
                    Toast.makeText(usluga_gos.this, Dil.hyzmat_gosulyar, Toast.LENGTH_LONG).show();
                }
            }
        });
        add.setText(Dil.usluga_gos);
        listView_surat=new  ArrayList<model_surat_gos>();
        handSetImageview();

        lenta_surat_gos_layout_bil_gos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(usluga_gos.this, AlbumSelectActivity.class);
                intent.putExtra(ConstantsCustomGallery.INTENT_EXTRA_LIMIT, 5); // set limit for image selection
                startActivityForResult(intent, ConstantsCustomGallery.REQUEST_CODE);

            }
        });
        set_image=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                a.setData(listView_surat);
            }
        };
        s1=new Handler(){
            @Override
            public void handleMessage(Message msg) {

                progressDialog.show();
            }
        };
        s2=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                Toast.makeText(usluga_gos.this,Dil.hyzmat_goshudy,Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                onBackPressed();
            }
        };

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("geldiResult","geldi");
        if (requestCode == ConstantsCustomGallery.REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            ArrayList<Image> images = data.getParcelableArrayListExtra(ConstantsCustomGallery.INTENT_EXTRA_IMAGES);
            model_surat_gos m;
            for (int i = 0; i < images.size(); i++) {
                Uri uri = Uri.fromFile(new File(images.get(i).path));
                Log.d("uriset",uri.toString());
                m=new model_surat_gos();
                m.setUri(uri);
                if(listView_surat.size()<5)listView_surat.add(m);
            }
            set_image.sendEmptyMessage(1);
        }
    }
    void handSetImageview() {
        setImageView = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 1) {
                    final Thread comp = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            int t = 0;
                            for (model_surat_gos m:listView_surat) {
                                Bitmap bitmap = null;
                                try {
                                    bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(),m.getUri());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                Log.d("getCountBitmap",""+bitmap.getByteCount());
                                img.add(bitmap);
                            }
                            compress(img, "detail");
                            Thread.currentThread().interrupt();
                        }
                    });
                    comp.start();
                }}
        };}

    Thread compres;
    public void compress(final ArrayList<Bitmap> bmp, final String type) {
        compres = new Thread(new Runnable() {
            @Override
            public void run() {
                int p = 0;
                Log.d("type", type + "  size:" + bmp.size());
                for (int t = 0; t < bmp.size(); t++) {
                    Log.d("haysy", type);
                    Bitmap bm = bmp.get(t);
                    int razmer = 0;
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    int k = 90;
                    bm.compress(Bitmap.CompressFormat.JPEG, k, baos);
                    byte[] imageBytes = baos.toByteArray();
                    if (t==0) {

                        bm = Scaler(imageBytes, 320, 240);
                        razmer =90;
                        Log.d("girdi", "main");
                        bm.compress(Bitmap.CompressFormat.JPEG, razmer, baos);
                        while (baos.toByteArray().length / 1024 > razmer) {
                            k -= 10;
                            baos.reset();
                            bm.compress(Bitmap.CompressFormat.JPEG, k, baos);
                            Log.d("beforekb", "" + baos.toByteArray().length);
                        }
                        byte[] img = baos.toByteArray();
                        image = Base64.encodeToString(img, Base64.DEFAULT);

                    }
                    bm = bmp.get(t);
                    bm = Scaler(imageBytes, 320, 240);
                    razmer =90;

                    baos.reset();
                    bm.compress(Bitmap.CompressFormat.JPEG,razmer, baos);
                    while (baos.toByteArray().length / 1024 > razmer) {
                        k -= 10;
                        baos.reset();
                        bm.compress(Bitmap.CompressFormat.JPEG, k, baos);
                        Log.d("beforekb", "" + baos.toByteArray().length);
                    }

                    imageBytes = baos.toByteArray();
                    if (t == 0) image1 = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                    if (t== 1) image2 = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                    if (t == 2) image3 = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                    if (t== 3) image4 = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                    if (t == 4) image5 = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                    //  p++;
                }
                Register();

                Thread.currentThread().interrupt();


            }
        });
        if(compres.isAlive())compres.interrupt(); compres.start();
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {

        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            while ((halfHeight / inSampleSize) >= reqHeight && (halfWidth / inSampleSize) >= reqWidth) {

                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    public static Bitmap Scaler(byte[] arr, int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Log.d("array", " " + arr.length);
        BitmapFactory.decodeByteArray(arr, 0, arr.length, options);

        Log.d("size", "" + options.inScaled);
        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        Log.d("insample size", "" + options.inSampleSize);
        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeByteArray(arr, 0, arr.length, options);


    }
    public void Register() {
        s1.sendEmptyMessage(1);
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    HashMap<String, String> object = new HashMap<>();
                    object.put("name", name1);
                    object.put("number", number1);
                    object.put("content",content1);
                    object.put("image", image);
                    object.put("image1", image1);
                    object.put("image2", image2);
                    object.put("image3", image3);
                    object.put("image4", image4);
                    object.put("image5", image5);

listView_surat.clear();

                    image="";
                    image1="";
                            image2="";
                    image3="";
                    image4="";
                    image5="";
                    final String json = new Gson().toJson(object);
                    Log.d("infromation", json);
                    listView_surat.clear();
                    img.clear();
                    set_image.sendEmptyMessage(1);
//                    a.setData(listView_surat);
                    //   URL url=new URL("http://ynamly.biz/reklama3/adds/insert_add.php");

                    URL url=new URL("http://"+ Api.url+"ygty/insert_usluga1.php");
                    Log.d("url",""+url);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setDoOutput(true);
                    con.setRequestMethod("POST");
                    OutputStream outputStream = con.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String post_data = URLEncoder.encode("json", "UTF-8") + "=" + URLEncoder.encode(json, "UTF-8");
                    bufferedWriter.write(post_data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();
                    InputStream in;
                    in = con.getInputStream();
                    BufferedReader br = new BufferedReader(new InputStreamReader(in));
                    String s = "";
                    while ((s = br.readLine()) != null) {
                        Log.d("namegelya", s);
                        s2.sendEmptyMessage(1);
                    }

                    br.close();
                    in.close();


                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        MainActivity.s1.sendEmptyMessage(1);
    }
}

