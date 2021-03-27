package com.eccomerce.com.login_package;


import android.content.Intent;
import android.os.Bundle;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.eccomerce.com.R;
import com.eccomerce.com.model.Buyer_user;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment {

    TextView textView_login;
    Button btn_register;
    EditText phone, name,address,email;
    FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    static ArrayList<String> list;
    public RegisterFragment() {
        // Required empty public constructor
    }


    ProgressBar progressBar;
    void init (View v)
    {
        textView_login = (TextView)v.findViewById(R.id.textView_login);

        name = (EditText)v.findViewById(R.id.name);
        address=v.findViewById(R.id.adress);
        phone=v.findViewById(R.id.phone);
        progressBar = (ProgressBar)v.findViewById(R.id.progressBar);
        mAuth= FirebaseAuth.getInstance();
        email=(EditText)v.findViewById(R.id.email);
        firebaseDatabase = FirebaseDatabase.getInstance();
        btn_register = (Button) v.findViewById(R.id.btn_register);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_register, container, false);
        init(v);
        textView_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new LoginFragment());
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String  name1 = name.getText().toString();
               String  phone1 = phone.getText().toString();
               String  address1 = address.getText().toString();
               String email1=email.getText().toString();
                if (name1.equals("") || address1.equals("") || phone1.equals("")) {
                    Toast.makeText(getContext(), "Please Fill All Fields", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Register(name1,email1,phone1,"","",address1);
                }
               // setFragment(new OtpVerifyFragment());
            }
        });
        return  v;


    }



    public void Register(String name, String email, String phone, String password, String imageurl,String address){
        progressBar.setVisibility(View.VISIBLE);


        list=new ArrayList<>();
        list.add(name);
        list.add(address);
        list.add(phone);
        list.add(email);
        list.add(imageurl);
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("data", list);
        OtpVerifyFragment otp =new OtpVerifyFragment();
        otp.setArguments(bundle);
        setFragment(otp);


    }


    void updateUI(String user){
        progressBar.setVisibility(View.VISIBLE);
        getActivity().finish();
    }

    protected void setFragment(Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commit();
    }
    @Override
    public void onStart() {
        super.onStart();
    }
}
