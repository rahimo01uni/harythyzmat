package com.eccomerce.com.login_package;


import android.content.Intent;
import android.os.Bundle;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.eccomerce.com.Api;
import com.eccomerce.com.R;
import com.eccomerce.com.main.bottom;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {
static  String tag = "loginfragment";
Button btn_register,login;
TextView textView_reg,textView_forgotpassword;
EditText userid,password;
FirebaseAuth mAuth;
    public LoginFragment() {
        // Required empty public constructor
    }
  void init(View v){
      textView_reg= (TextView) v.findViewById(R.id.textView_reg);
      textView_forgotpassword= (TextView) v.findViewById(R.id.textView_forgotpassword);
      login= (Button)v.findViewById(R.id.btn_login);
      userid= (EditText) v.findViewById(R.id.user_id);
      password= (EditText) v.findViewById(R.id.password);
      mAuth= FirebaseAuth.getInstance();
  }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_login, container, false);
       init(v);

        textView_forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new ForgotPassFragment());
            }
        });
        textView_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new RegisterFragment());
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(userid.getText().toString().equals(""))
                {
                    Toast.makeText(getActivity(), "Please Enter Phone number", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Bundle bundle = new Bundle();

                    bundle.putString("data", userid.getText().toString());
                    OtpVerifyFragmentLogin otp =new OtpVerifyFragmentLogin();
                    otp.setArguments(bundle);
                    setFragment(otp);
                  //  login(userid.getText().toString(),password.getText().toString());
                }

              /*  Intent ii=new Intent(getActivity(), bottom.class);
                startActivity(ii);*/
            }
        });
        return  v;
    }
    public void setFragment(Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commit();
    }

    void login(String email,String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("login check", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            Toast.makeText(getActivity(), task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();

                            // If sign in fails, display a message to the user.
                          /*  Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(EmailPasswordActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                      */  }

                        // ...
                    }
                });
    }

    void updateUI(FirebaseUser user ){
        getActivity().finish();
    }
/*
    public void login(String phone,String password) {
        // s1.sendEmptyMessage(1);
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    HashMap<String, String> object = new HashMap<>();
                    object.put("name", phone);
                    object.put("password", password);

                    final String json = new Gson().toJson(object);
                    Log.d("infromation", json);

                    //   a.setData(listView_surat);
                    //   URL url=new URL("http://ynamly.biz/reklama3/adds/insert_add.php");

                    URL url=new URL("http://"+ Api.api_url+"ygty/login_user_account.php");
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
                        Log.d("registration", s);
                        //  s2.sendEmptyMessage(1);
                        getActivity().finish();
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
*/
}
