package com.eccomerce.com.main;

import android.content.Intent;

import androidx.annotation.NonNull;

import com.eccomerce.com.network.get_followlist;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.eccomerce.com.Db;
import com.eccomerce.com.Dil;
import com.eccomerce.com.R;
import com.eccomerce.com.categories.categories;
import com.eccomerce.com.constants;
import com.eccomerce.com.liked.liked;
import com.eccomerce.com.userinfo;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

public class bottom extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    Toolbar toolbar;
    FirebaseUser currentUser;
    Fragment fragment0=null,fragment00=null,fragment000=null,fragment=null, fragment_timeline =null;
    final FragmentManager fm = getSupportFragmentManager();
    Dil d;
    Db db;
    Fragment active = fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom);
        init();
        //toolbar.setLogo(R.drawable.brnd_logo);
         setSupportActionBar(toolbar);
/*        FirebaseMessaging.getInstance().subscribeToTopic("test");
        FirebaseInstanceId.getInstance().getToken();
*/

        //handle bottom navigation
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.home1:
                    fm.beginTransaction().hide(active).show(fragment).commit();
                     active = fragment;
                        return true;
                    case R.id.uslugi:
                        fm.beginTransaction().hide(active).show(fragment0).commit();
                        active = fragment0;
                        return true;
                    case R.id.timeline:
                        Log.d("post","3");
                        Intent ii= new Intent(bottom.this,Timeline_fragment.class);
                        startActivity(ii);
                      /*  fm.beginTransaction().hide(active).show(fragment_timeline).commit();
                        active = fragment_timeline;
                      */  return true;

                    case R.id.favorite:
                        Log.d("post","4");
                        fm.beginTransaction().hide(active).show(fragment00).commit();
                        active = fragment00;
                        return true;

                    case R.id.person:
                        Log.d("post","5");
                        fm.beginTransaction().hide(active).show(fragment000).commit();

                        active = fragment000;
                        return  true;
                    default:
                        break;
                }

                return false;
            }
        });
        updateNavigationBarname();
requests();




    }


    protected void setFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commit();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.se, menu);
        return true;
    }
     void requests(){
        FirebaseUser firebaseUser=FirebaseAuth.getInstance().getCurrentUser();;
         get_followlist.get_Data(firebaseUser.getUid());
     }
     void init(){
         toolbar = (Toolbar) findViewById(R.id.toolbar_car_sahypa);
         toolbar.setTitle("");
         FirebaseApp.initializeApp(this);
         bottomNavigationView=(  BottomNavigationView )findViewById(R.id.navigationView);
         db = new Db(this);
         d = new Dil(this);
         d.set_text(this);

         Log.d("diliLENGTH","EJEN"+db.get_dil());

         constants.ln=db.get_dil();
         fragment=new MainActivity();
         fragment0=new Usluga();
         //  fragment_timeline=new Timeline_fragment();
         fragment00=new liked();
         fragment000=new userinfo();
         active = fragment;
         //init fragments
         fm.beginTransaction().add(R.id.frame, fragment000, "5").hide(fragment000).commit();
         fm.beginTransaction().add(R.id.frame, fragment00, "4").hide(fragment00).commit();
         //fm.beginTransaction().add(R.id.frame, fragment_timeline, "3").hide(fragment_timeline).commit();
         fm.beginTransaction().add(R.id.frame, fragment0, "2").hide(fragment0).commit();
         fm.beginTransaction().add(R.id.frame, fragment, "1").commit();
         //default language
         if(db.get_dil().length()==0){
             d.dil(this);
         }
     }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if(id==R.id.cat){
            Intent intent = new Intent(bottom.this, categories.class);
            startActivity(intent);
        }
        if(id==R.id.crt){
                Intent intent = new Intent(bottom.this,CartActivity.class);
                startActivity(intent);
        }
        //noinspection SimplifiableIfStatement
        return super.onOptionsItemSelected(item);
    }
    boolean load(Fragment frag){
        if (frag!=null){
            //getFragmentManager().popBackStack();
            getSupportFragmentManager().notifyAll();
            return true;
        }
        return  false;
    }
    private void updateNavigationBarname( ){
        Menu menu = bottomNavigationView.getMenu();
        for (int i = 0, size = menu.size(); i < size; i++) {

            MenuItem item = menu.getItem(i);
            Log.d("post1",""+item.toString());

            switch (i){
                case  0:
                    item.setTitle(Dil.home);
                    break;
                case  1:
                    item.setTitle(Dil.uslugi);
                    break;
                case  2:
                    item.setTitle("Timeline");
                   // item.setTitle(Dil.timeline);
                    break;
                case  3:
                    item.setTitle(Dil.favorite);
                    break;
                case  4:
                    item.setTitle(Dil.user);
                    break;
            }
        }
    }
}
