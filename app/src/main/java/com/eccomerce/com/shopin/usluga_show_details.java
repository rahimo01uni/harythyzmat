package com.eccomerce.com.shopin;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.eccomerce.com.login_package.LoginActivity;
import com.eccomerce.com.main.MessageActivity;
import com.google.android.material.appbar.AppBarLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
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
import com.eccomerce.com.network.delete_liked_usluga;
import com.eccomerce.com.network.sendliked_usluga;
import com.eccomerce.com.network.sendrate_usluga;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.stepstone.apprating.AppRatingDialog;
import com.stepstone.apprating.listener.RatingDialogListener;
import com.viewpagerindicator.CirclePageIndicator;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;


public class usluga_show_details extends AppCompatActivity implements ViewPagerEx.OnPageChangeListener, RatingDialogListener{
    TextView content,price,item_name,next,rate_txt;
    ViewPager mPager;
    ImageButton call,favorite,share;
    Menu menu;
    MenuItem item;
    ImageView backimg;
    String loved;
    Db db;
    RecycleAdapterShowDetails recycleAdapter;
    String id;
    String skidka; Intent i;
    public static ArrayList<String > images;
    Toolbar toolbar;
    SliderLayout mDemoSlider;
    viewpager_adapter_usluga v;
    LinearLayout rt;
    AppBarLayout appBarLayout;
    TextView ady,bahasy,maglumat,item_kody,cody;
    ImageView star1,star2,star3,star4,star5;
    public  static Handler s1=new Handler();
    FirebaseUser firebaseUser;
    ImageButton msengrbtn;
    Button following_profile;
    String shop_user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usluga_show_details_activity);

        favorite = findViewById(R.id.fav);
        Dil d=new Dil(this);
        shop_user  = "HfDLYADAaVZ1Joy2fMYRAjfES3r1";
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        //setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        rt=(LinearLayout)findViewById(R.id.l_rate);
        share = findViewById(R.id.sh);
        item_name=(TextView)findViewById(R.id.item_name);
        content = (TextView) findViewById(R.id.Content);
        mPager = (ViewPager) findViewById(R.id.viewpager_detail);
        call = (ImageButton) findViewById(R.id.call_btn);
        ady = findViewById(R.id.ady);
        maglumat = findViewById(R.id.maglumat);
        rate_txt = (TextView) findViewById(R.id.n_people);

        star1 = findViewById(R.id.cafe_star_image1_show);
        star2 = findViewById(R.id.cafe_star_image2_show);
        star3 = findViewById(R.id.cafe_star_image3_show);
        star4 = findViewById(R.id.cafe_star_image4_show);
        star5 = findViewById(R.id.cafe_star_image5_show);
        ady.setText(d.ady);

        maglumat.setText(d.maglumat);
        following_profile =findViewById(R.id.following_profile);
        msengrbtn =findViewById(R.id.msengrbtn);

        int rate = Integer.parseInt(constants.select.getRate());
        rate_txt.setText(""+rate);
        setRateImg(rate);

        db=new Db(this);
        backimg = findViewById(R.id.imageMain);
        backimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        rt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(db.isin("rate_usluga",constants.select.getId()))
                    Toast.makeText(usluga_show_details.this,Dil.bahalandyrdyn,Toast.LENGTH_LONG).show();
                else  send_rate();

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
                       shareBody = "http://"+ Api.url+"ygty/images/" +   constants.select.getImg2();
                       break;
                   case 1:
                        shareBody = "http://"+ Api.url+"ygty/images/" +  constants.select.getImg3();
                       break;
                   case 2:
                       shareBody = "http://"+ Api.url+"ygty/images/" +  constants.select.getImg4();
                       break;
                   case 3:
                  shareBody = "http://"+ Api.url+"ygty/images/" +       constants.select.getImg5();
                  break;
                   case 4:
                    shareBody = "http://"+ Api.url+"ygty/images/" +     constants.select.getMg6();
                       break;
                       default:
               }

                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject Here");
                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
            }
        });
//        Typeface tp= Typeface.createFromAsset(getAssets(),"font/Panton.otf");
        //      Typeface tp1= Typeface.createFromAsset(getAssets(),"font/Panton-SemiBold.otf");;
        //    content.setTypeface(tp);
        //  item_name.setTypeface(tp1);
        //price.setTypeface(tp1);
        //    mDemoSlider=(SliderLayout)findViewById(R.id.slider_show);
        i=getIntent();
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent call = new Intent(Intent.ACTION_DIAL);
                call.setData(Uri.parse("tel:" + constants.select.getNumber()));
                startActivity(call);
            }
        });
        images=new ArrayList<>();
        if(!constants.select.getImg2().equals("")) images.add(constants.select.getImg2());
        if(!constants.select.getImg3().equals(""))images.add(constants.select.getImg3());
        if(!constants.select.getImg4().equals(""))images.add(constants.select.getImg4());
        if(!constants.select.getImg5().equals(""))images.add(constants.select.getImg5());
        if(!constants.select.getMg6().equals(""))images.add(constants.select.getMg6());
        id=constants.select.getId();
        v=new viewpager_adapter_usluga(usluga_show_details.this, images);
        // Log.d("imagesize",""+images.size()+"image2/"+images.get(1));
        mPager.setAdapter(v);
        CirclePageIndicator indicator = (CirclePageIndicator) findViewById(R.id.small_indicator_realtor_show_details);
        indicator.setViewPager(mPager);
        item_name.setText(constants.select.getTitle());
        content.setText(constants.select.getContent());

        //  daimaija();

        if(db.isin("liked_usluga",constants.select.getId())){
            favorite.setImageResource(R.drawable.ic_favorite_black_24dp);

        }
        else{
            favorite.setImageResource(R.drawable.ic_favorite_border_black_24dp);

        }
        favorite.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(!db.isin("liked_usluga",constants.select.getId())){
                    db.inser_liked_usluga(constants.select.getId());
                    favorite.setImageResource(R.drawable.ic_favorite_black_24dp);
                    sendliked_usluga.get_Data(constants.select.getId(),   FirebaseInstanceId.getInstance().getToken());}
                else {
                    db.delete_liked_usluga(constants.select.getId());
                    favorite.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                    delete_liked_usluga.get_Data(constants.select.getId(),   FirebaseInstanceId.getInstance().getToken());
                }

            }
        });

        s1=new Handler(){
            @Override
            public void handleMessage(Message msg) {

            }};
        s1.sendEmptyMessage(1);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "font/roboto_medium.ttf");
        content.setTypeface(typeface);
        item_name.setTypeface(typeface);
//        next.setTypeface(typeface);
        rate_txt.setTypeface(typeface);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int res_id = item.getItemId();
        if (res_id == android.R.id.home) {
            // mDemoSlider.removeAllViews();
            onBackPressed();

        }

        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //   mDemoSlider.removeAllViews();
        images.clear();
        v.notif(images);

            MainActivity.s1.sendEmptyMessage(1);


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        this.menu = menu;

        return true;
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

                            Intent i=new Intent(usluga_show_details.this,reklam_photo.class);
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
        appRatingDialog.setDefaultRating(5);
        appRatingDialog.setTitle(Dil.usluga_bahalndyryn);
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
        db.inser_rate_usluga(constants.select.getId());
        sendrate_usluga.get_Data(constants.select.getId(),""+postion);
    }

    @Override
    public void onNegativeButtonClicked() {

    }

    @Override
    public void onNeutralButtonClicked() {

    }


    @Override
    protected void onStart() {
        super.onStart();

        if (firebaseUser!=null){
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


        msengrbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (firebaseUser!=null){
                    Intent ii = new Intent(usluga_show_details.this, MessageActivity.class);
                    ii.putExtra("userid",shop_user);
                    startActivity(ii);
                }
                else
                {
                    Intent ii = new Intent(usluga_show_details.this, LoginActivity.class);
                    ii.putExtra("tag",1);
                    startActivity(ii);
                }
            }
        });
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
                    Intent ii = new Intent(usluga_show_details.this,LoginActivity.class);
                    ii.putExtra("tag",1);
                    startActivity(ii);
                }
            }
        });

    }


}
