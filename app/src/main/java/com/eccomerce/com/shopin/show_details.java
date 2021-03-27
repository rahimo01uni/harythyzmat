package com.eccomerce.com.shopin;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.eccomerce.com.login_package.LoginActivity;
import com.eccomerce.com.main.MessageActivity;
import com.eccomerce.com.main.SessionManager;
import com.google.android.material.appbar.AppBarLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.eccomerce.com.Api;
import com.eccomerce.com.Db;
import com.eccomerce.com.Dil;
import com.eccomerce.com.R;
import com.eccomerce.com.constants;
import com.eccomerce.com.main.MainActivity;
import com.eccomerce.com.main.RecycleAdapterShowDetails;
import com.eccomerce.com.network.delete_liked;
import com.eccomerce.com.network.getitemsshow;
import com.eccomerce.com.network.sendliked;
import com.eccomerce.com.network.sendrate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.makeramen.roundedimageview.RoundedImageView;
import com.stepstone.apprating.AppRatingDialog;
import com.stepstone.apprating.listener.RatingDialogListener;
import com.viewpagerindicator.CirclePageIndicator;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;


public class show_details extends AppCompatActivity implements ViewPagerEx.OnPageChangeListener, RatingDialogListener  {
    TextView content,price,item_name,next,market_name,rate_txt,yeri_show_set;
    ViewPager mPager;
    ImageButton call,favorite,share,sendemail,msengrbtn,addcartbtn;
    Button following_profile;
    RoundedImageView roundedImageView;
    ImageView backimg;
    Menu menu;
    MenuItem item;
    Db db;
    String loved;
    RecycleAdapterShowDetails recycleAdapter;
    String id;
    String skidka; Intent i;
    public static ArrayList<String > images;
    Toolbar toolbar;
    SliderLayout mDemoSlider;
    viewpager_adapter v;
    LinearLayout rt;
    AppBarLayout appBarLayout;
    TextView ady,bahasy,maglumat,item_kody,cody;
    public  static Handler s1=new Handler();
    ImageView star1,star2,star3,star4,star5;
    String shop_user;
    FirebaseUser firebaseUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_details1);
        db = new Db(this);
        Dil d=new Dil(this);
        shop_user=constants.selected.getUid();
        // shop_user  = constants.selected.getShop_uid();
         firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        //setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        constants.showitem.clear();
        favorite = findViewById(R.id.fav);
        share = findViewById(R.id.sh);
        roundedImageView = findViewById(R.id.market_round_img);
        market_name=findViewById(R.id.market_name);
        constants.size=0;
        constants.iter=true;
        rt=(LinearLayout)findViewById(R.id.l_rate);
        item_name=(TextView)findViewById(R.id.item_name);
        content = (TextView) findViewById(R.id.Content);
        mPager = (ViewPager) findViewById(R.id.viewpager_detail);
        price = (TextView) findViewById(R.id.item_price);
        call = (ImageButton) findViewById(R.id.call_btn);
        addcartbtn = (ImageButton) findViewById(R.id.addcartbtn);
        ady = findViewById(R.id.ady);
        bahasy = findViewById(R.id.bahasy);
        maglumat = findViewById(R.id.maglumat);
        item_kody = findViewById(R.id.item_kody);
        rate_txt = findViewById(R.id.n_people);
        cody = findViewById(R.id.cody);
        backimg = findViewById(R.id.imageMain);

        yeri_show_set = findViewById(R.id.yeri_show_set);

        yeri_show_set.setText(constants.selected.getYeri());

        ady.setText(d.ady);
        bahasy.setText(d.bahasy);
        cody.setText(d.cody);
        maglumat.setText(d.maglumat);

        star1 = findViewById(R.id.cafe_star_image1_show);
        star2 = findViewById(R.id.cafe_star_image2_show);
        star3 = findViewById(R.id.cafe_star_image3_show);
        star4 = findViewById(R.id.cafe_star_image4_show);
        star5 = findViewById(R.id.cafe_star_image5_show);
         following_profile =findViewById(R.id.following_profile);


backimg.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        onBackPressed();
    }
});

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recycleAdapter = new RecycleAdapterShowDetails(constants.showitem,this);
        recyclerView.setAdapter(recycleAdapter);

        market_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(show_details.this, allshops.class);
                i.putExtra("shopid",constants.selected.getShopid());
                startActivity(i);
            }
        });

        rt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(db.isin("rate",constants.selected.getId()))
                    Toast.makeText(show_details.this,Dil.bahalandyrdyn,Toast.LENGTH_LONG).show();
                else{

                    send_rate();
                }

            }
        });
        Typeface typeface = Typeface.createFromAsset(getAssets(), "font/roboto_medium.ttf");

        int rate = Integer.parseInt(constants.selected.getRate());
        rate_txt.setText(""+constants.selected.getRate());
        item_kody.setText(""+constants.selected.getId());
        setRateImg(rate);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
//        Typeface tp= Typeface.createFromAsset(getAssets(),"font/Panton.otf");
        //      Typeface tp1= Typeface.createFromAsset(getAssets(),"font/Panton-SemiBold.otf");
        //    content.setTypeface(tp);
        //  item_name.setTypeface(tp1);
        //price.setTypeface(tp1);
        //    mDemoSlider=(SliderLayout)findViewById(R.id.slider_show);
        i=getIntent();
        constants.shopid=constants.selected.getShopid();

        //if(constants.shopitem.size()==0) getitemsshopshow.get_Data();
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent call = new Intent(Intent.ACTION_DIAL);
                call.setData(Uri.parse("tel:" + constants.selected.getNumber()));
                startActivity(call);
            }
        });
        sendemail=(ImageButton) findViewById(R.id.email_btn);
        msengrbtn=(ImageButton) findViewById(R.id.msengrbtn);
      /*
        sendemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto",constants.selected.getEamil(), null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Body");
                startActivity(Intent.createChooser(emailIntent, "Send email..."));
            }
        });
        */

        images=new ArrayList<>();
        if(!constants.selected.getImage1().equals("")) images.add(constants.selected.getImage1());
        if(!constants.selected.getImage2().equals(""))images.add(constants.selected.getImage2());
        if(!constants.selected.getImage3().equals(""))images.add(constants.selected.getImage3());
        id=constants.selected.getId();
        v=new viewpager_adapter(show_details.this, images);
        // Log.d("imagesize",""+images.size()+"image2/"+images.get(1));
        mPager.setAdapter(v);
        CirclePageIndicator indicator = (CirclePageIndicator) findViewById(R.id.small_indicator_realtor_show_details);
        indicator.setViewPager(mPager);
        item_name.setText(constants.selected.getName());
        content.setText(constants.selected.getContent());
        price.setText(constants.selected.getPrice()+" TMT ");
        skidka=constants.selected.getSkidka();
         // daimaija();

if(db.isin("liked",constants.selected.getId()))
    favorite.setImageResource(R.drawable.ic_favorite_black_24dp);
 else      favorite.setImageResource(R.drawable.ic_favorite_border_black_24dp);
 favorite.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(!db.isin("liked",constants.selected.getId())){
                    db.inser_liked(constants.selected.getId());
                    favorite.setImageResource(R.drawable.ic_favorite_black_24dp);
                    sendliked.get_Data(constants.selected.getId(),   FirebaseInstanceId.getInstance().getToken());}
                else {
                    db.delete_liked(constants.selected.getId());
                    favorite.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                    delete_liked.get_Data(constants.selected.getId(),   FirebaseInstanceId.getInstance().getToken());
                }

            }
        });
        share.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "";
                switch (constants.posimage){
                    case 0:
                        shareBody = "http://"+ Api.url+"ygty/images/" +   constants.selected.getImage1();
                        break;
                    case 1:
                        shareBody = "http://"+ Api.url+"ygty/images/" +  constants.selected.getImage2();
                        break;
                    case 2:
                        shareBody = "http://"+ Api.url+"ygty/images/" +  constants.selected.getImage3();
                        break;
                    default:
                }

                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject Here");
                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
            }
        });
        addcartbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(constants.selected.getPrice().equals(""))
                {
                  String msg=  db.insert_item_cart(Integer.parseInt(constants.selected.getId()),0,constants.selected.getName());
                    Toast.makeText(show_details.this, msg, Toast.LENGTH_SHORT).show();
                }
                else {
                    String msg= db.insert_item_cart(Integer.parseInt(constants.selected.getId()), Integer.parseInt(constants.selected.getPrice()), constants.selected.getName());
                    Toast.makeText(show_details.this, msg, Toast.LENGTH_SHORT).show();
                }
            }
        });

        s1=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                Glide.with(show_details.this)
                        //.load("http://"+ Api.url+"images/" + dataList.get(position).getImage())
                        .load("http://"+ Api.url+"ygty/images/" + constants.shopimg).thumbnail(0.01f).diskCacheStrategy(DiskCacheStrategy.ALL)
                        .centerCrop()
                        .into(roundedImageView);
                market_name.setText(constants.shopname);

                //  updateNavigationBarState(R.id.home1);
                recycleAdapter.setData(constants.showitem);
            }};
        s1.sendEmptyMessage(1);

        getitemsshow.get_Data();

        content.setTypeface(typeface);
        price.setTypeface(typeface);
        item_name.setTypeface(typeface);
//        next.setTypeface(typeface);
        market_name.setTypeface(typeface);
        rate_txt.setTypeface(typeface);
        yeri_show_set.setTypeface(typeface);

    }

    @Override
    protected void onStart() {
        super.onStart();

        if (firebaseUser!=null){
            Log.e("userId",firebaseUser.getUid());
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Follow").child(firebaseUser.getUid()).child("following");
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.child(shop_user).exists()){
                        following_profile.setText("following");
                        following_profile.setTag(2);
                    }
                    else
                    {
                        following_profile.setText("follow");
                        following_profile.setTag(1);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        following_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (firebaseUser!=null){
                    if (following_profile.getTag().equals(1))
                    {
                        FirebaseDatabase.getInstance().getReference().child("Follow").child(firebaseUser.getUid())
                                .child("following").child(shop_user).setValue(true);
                        following_profile.setTag(2);
                        following_profile.setText("following");
                        // following_profile.setText();
                    }
                    else
                    {
                        FirebaseDatabase.getInstance().getReference().child("Follow").child(firebaseUser.getUid())
                                .child("following").child(shop_user).removeValue();
                        following_profile.setTag(1);
                        following_profile.setText("follow");
                    }
                }
                else
                {
                    Intent ii = new Intent(show_details.this,LoginActivity.class);
                    ii.putExtra("tag",1);
                    startActivity(ii);
                }
            }
        });

        msengrbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (firebaseUser!=null){
                    SessionManager sessionManager =new SessionManager(show_details.this);
                    if (sessionManager.getChatCrediential(constants.selected.getId())!=null)
                    {
                        Intent ii = new Intent(show_details.this,MessageActivity.class);
                        ii.putExtra("userid",shop_user);
                       // ii.putExtra("ProductName",constants.selected.getName());
                       // ii.putExtra("ProductPrice",constants.selected.getPrice());
                        startActivity(ii);
                    }
                    else
                    {
                        sessionManager.createChatSession(constants.selected.getId());
                        Intent ii = new Intent(show_details.this,MessageActivity.class);
                        ii.putExtra("userid",shop_user);
                        ii.putExtra("ProductName",constants.selected.getName());
                        ii.putExtra("ProductPrice",constants.selected.getPrice());
                        startActivity(ii);
                    }

                }
                else
                {
                    Intent ii = new Intent(show_details.this,LoginActivity.class);
                    ii.putExtra("tag",1);
                    startActivity(ii);
                }
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
     //   mDemoSlider.removeAllViews();
        images.clear();
        v.notif(images);
        MainActivity.s1.sendEmptyMessage(1);
    }

    void daimaija(){
        for(final String name : images){
            DefaultSliderView textSliderView = new DefaultSliderView(this);
            // initialize a SliderLayout
            textSliderView
                    .image("http://"+ Api.url+"images/"+name)
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                        @Override
                        public void onSliderClick(BaseSliderView slider) {
                            Intent i=new Intent(show_details.this,reklam_photo.class);
                            startActivity(i);
                        }
                    });

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra",name);

            mDemoSlider.addSlider(textSliderView);
        }
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(10000);
        mDemoSlider.addOnPageChangeListener(this);




    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
    public void setRateImg(int rate){
        switch (rate){
            case 1:
                star1.setImageResource(R.drawable.star_icon);
                star2.setImageResource(R.drawable.star_white);
                star3.setImageResource(R.drawable.star_white);
                star4.setImageResource(R.drawable.star_white);
                star5.setImageResource(R.drawable.star_white);
                break;
            case 2:
                star1.setImageResource(R.drawable.star_icon);
                star2.setImageResource(R.drawable.star_icon);
                star3.setImageResource(R.drawable.star_white);
                star4.setImageResource(R.drawable.star_white);
                star5.setImageResource(R.drawable.star_white);
                break;
            case 3:
                star1.setImageResource(R.drawable.star_icon);
                star2.setImageResource(R.drawable.star_icon);
                star3.setImageResource(R.drawable.star_icon);
                star4.setImageResource(R.drawable.star_white);
                star5.setImageResource(R.drawable.star_white);
                break;
            case 4:
                star1.setImageResource(R.drawable.star_icon);
                star2.setImageResource(R.drawable.star_icon);
                star3.setImageResource(R.drawable.star_icon);
                star4.setImageResource(R.drawable.star_icon);
                star5.setImageResource(R.drawable.star_white);
                break;
            case 5:
                star1.setImageResource(R.drawable.star_icon);
                star2.setImageResource(R.drawable.star_icon);
                star3.setImageResource(R.drawable.star_icon);
                star4.setImageResource(R.drawable.star_icon);
                star5.setImageResource(R.drawable.star_icon);
                break;
        }
    }
    public void send_rate() {
        AppRatingDialog.Builder appRatingDialog = new AppRatingDialog.Builder();
        appRatingDialog.setPositiveButtonText(Dil.tassykla);
        appRatingDialog.setNegativeButtonText(Dil.yza);
        appRatingDialog.setNoteDescriptions(Arrays.asList(Dil.erbet, Dil.kanagatlanarly, Dil.gowy, Dil.oran_gowy, Dil.ajayyp));
        appRatingDialog.setDefaultRating(2);
        appRatingDialog.setTitle(Dil.tagamy_bahalndyryn);
        appRatingDialog.setStarColor(R.color.colorPrimary);
        appRatingDialog.setNoteDescriptionTextColor(R.color.button_blue);
        appRatingDialog.setTitleTextColor(R.color.colorPrimary);
        appRatingDialog.setCommentInputEnabled(false);
        appRatingDialog.setDescriptionTextColor(R.color.colorPrimary);
        appRatingDialog.setWindowAnimation(R.style.MyDialogFadeAnimation);
        appRatingDialog.setCancelable(false);
        appRatingDialog.setCanceledOnTouchOutside(false);
        appRatingDialog.create((AppCompatActivity) this).show();
    }

    @Override
    public void onPositiveButtonClicked(int postion, @NotNull String s) {
        Log.v("rate","dkfhsk");
        db.inser_rate(constants.selected.getId());
        sendrate.get_Data(constants.selected.getId(),""+postion);
    }

    @Override
    public void onNegativeButtonClicked() {

    }

    @Override
    public void onNeutralButtonClicked() {

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.se, menu);
        return true;
    }
}
