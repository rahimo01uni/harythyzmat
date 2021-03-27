package com.eccomerce.com.login_package;


import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.eccomerce.com.Db;
import com.eccomerce.com.Dil;
import com.eccomerce.com.R;
import com.eccomerce.com.constants;

public class LoginActivity extends AppCompatActivity {
    Dil d;    Db db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
  /*      db = new Db(this);
        d = new Dil(this);
        d.set_text(this);
        constants.ln=db.get_dil();
    */  //  Intent iin= getIntent();
       // Bundle b = iin.getExtras();
        setFragment(new SignInSignUp());

/*
        if(db.get_dil().length()==0){
            d.dil(this);
        }
*/
    }
    protected void setFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commit();
    }
}
