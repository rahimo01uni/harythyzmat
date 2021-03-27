package com.eccomerce.com.login_package;


import android.app.ActivityManager;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.eccomerce.com.R;
import com.eccomerce.com.main.bottom;
import com.eccomerce.com.model.Buyer_user;
import com.eccomerce.com.userinfo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * A simple {@link Fragment} subclass.
 */
public class OtpVerifyFragment extends Fragment {
    Button btnGenerateOTP, btnSignIn;
    EditText code;
    String phoneNumber, otp;
    FirebaseAuth auth;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallback;
    private String verificationCode;
    String tag = "";
    FirebaseDatabase firebaseDatabase;
    ArrayList<String> list;
    ProgressBar progressBar;
    public OtpVerifyFragment() {
        // Required empty public constructor
    }
    private void init(View view) {
        btnGenerateOTP=(Button)view.findViewById(R.id.btn_register);
        code=(EditText)view.findViewById(R.id.code);
        btnSignIn=(Button)view.findViewById(R.id.btn_register);
        progressBar=(ProgressBar)view.findViewById(R.id.progressBar);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_otp_verify, container, false);
        Bundle bundle = getArguments();
        if (bundle != null) {
            list=bundle.getStringArrayList("data");
        }
        init(view);
        // mActivity = bottom;
        StartFirebaseLogin();
        if(auth != null) {
//            phoneNumber = etPhoneNumber.getText().toString();
            // phoneNumber = "+918058626456";
            PhoneAuthProvider.getInstance().verifyPhoneNumber(list.get(2), 60, TimeUnit.SECONDS, getActivity(), mCallback);
        }


        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otp=code.getText().toString();
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCode, otp);
                signInWithPhoneAuthCredential(credential);
                startActivity(new Intent(getActivity(), bottom.class));
                            getActivity().finish();
            }
        });
        return view;
    }

    private void    signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        auth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            String userid= FirebaseAuth.getInstance().getUid();
                            String name,address,phone,email,imageurl;
                             name=list.get(0);list.add(name);
                             address=list.get(1);
                             phone=list.get(2);
                             email=list.get(3);
                              imageurl=list.get(4);
                            Buyer_user user_data = new Buyer_user(
                                    name,
                                    phone,
                                    email,
                                    imageurl,
                                    userid,
                                    address);

                            //  FirebaseDatabase.getInstance().getReference()
                            firebaseDatabase.getReference().child("Buyer_user").child(userid)
                                    .setValue(user_data).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    // Toast.makeText(getActivity(),"Successfil",Toast.LENGTH_LONG);
                                    Intent intent = new Intent(getActivity(), bottom.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                }
                            });


                            updateUI(userid);
                        } else {
                            Toast.makeText(getActivity(),"Incorrect OTP",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void StartFirebaseLogin() {
        // FirebaseApp.initializeApp(getActivity());
        FirebaseApp.initializeApp(getActivity());
        auth = FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        final  ArrayList<String> list=RegisterFragment.list;

        mCallback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                String otp = phoneAuthCredential.getSmsCode();
                Toast.makeText(getActivity(),otp,Toast.LENGTH_LONG).show();
                if (otp != null) {
                    code.setText(otp);
                    //verifying the code
                    verifyVerificationCode(otp);
                }



                Toast.makeText(getActivity(),"verification completed",Toast.LENGTH_SHORT).show();
            }
           @Override
           public void onVerificationFailed(FirebaseException e) {
               Toast.makeText(getActivity(),"verification fialed"+e.getMessage(),Toast.LENGTH_SHORT).show();
           }
           @Override
           public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
               super.onCodeSent(s, forceResendingToken);
               verificationCode = s;
               Toast.makeText(getActivity(),"Code sent",Toast.LENGTH_SHORT).show();
           }
       };
    }
    private void verifyVerificationCode(String code) {
        //creating the credential
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential( verificationCode, code);
        signInWithPhoneAuthCredential(credential);
    }
    void updateUI(String user){
        progressBar.setVisibility(View.VISIBLE);
        getActivity().finish();
    }
}


