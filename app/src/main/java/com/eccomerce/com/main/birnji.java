package com.eccomerce.com.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.Toast;

import com.eccomerce.com.R;
import com.eccomerce.com.login_package.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Admin on 7/4/2019.
 */

public class birnji extends AppCompatActivity{
    ImageView imgg;
    FirebaseUser currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_birnji);
        imgg=findViewById(R.id.img);
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        try{
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (currentUser != null) {
                        startActivity(new Intent(birnji.this, bottom.class));
                        finish();
                    } else {
                        startActivity(new Intent(birnji.this, LoginActivity.class));
                        finish();
                    }

                    }
        }, 1000);

        }catch(Exception e){
            Toast.makeText(this,"djfksl",Toast.LENGTH_LONG).show();
        }

    }
}
