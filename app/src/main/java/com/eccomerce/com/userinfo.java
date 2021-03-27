package com.eccomerce.com;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.eccomerce.com.login_package.LoginActivity;
import com.eccomerce.com.main.Followinglist;
import com.eccomerce.com.main.bizbarada;
import com.eccomerce.com.model.Buyer_user;
import com.eccomerce.com.model.Chat;
import com.eccomerce.com.model.Post;
import com.eccomerce.com.model.User;
import com.eccomerce.com.shopin.shopadd;
import com.eccomerce.com.shopin.usluga_gos;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.gson.JsonObject;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageActivity;

import org.json.JSONArray;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static android.app.Activity.RESULT_OK;


public class userinfo extends Fragment {

    AlertDialog.Builder dialog;
    RelativeLayout dil,duzgun,yeri,magazyn_gosh,hyzmat_gos,bizbarada_rel,habarlash_rel;
    BottomNavigationView bottomNavigationView;
    TextView yeri_tx,yer_ahal,dill,dil_truk,duzgunn,okap,addshop,hyzmat_txt,bizbarada_txt,habarlash_txt;
    ImageView profile_image,profileedit;
    TextView user_name,user_phone,user_address,user_email;
    Button login_btnn, reg_btnn;

    Uri imageUri;
    String myUrl = "";
    StorageTask uploadTask;
    StorageReference storageReference,storageReference_profile;
    FirebaseUser user;
    public  static Handler s1=new Handler();
    Dil d;
    Db db ;
    RelativeLayout user_logout,user_follow;
    LinearLayout user_name_layout, login_layout;
    String image_url;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_userinfo, container, false);

        user_logout=view.findViewById(R.id.user_logout);
        user_follow=view.findViewById(R.id.user_follow);
        dil=view.findViewById(R.id.user_dil);
        dill=view.findViewById(R.id.dill);
        dil_truk=view.findViewById(R.id.dil_turk);

      //
        db = new Db(getActivity());
        dil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d = new Dil(getContext());
                d.dil(getActivity());
            }
        });


        yeri = view.findViewById(R.id.Reluser_yeri);
        reg_btnn = (Button)view.findViewById(R.id.reg_btnn);
        login_btnn = (Button)view.findViewById(R.id.login_btnn);
        yer_ahal = (TextView)view.findViewById(R.id.yeri_txt);
        yeri_tx = view.findViewById(R.id.yeri);
        hyzmat_gos=view.findViewById(R.id.user_usluga_add);
        hyzmat_txt=view.findViewById(R.id.addusluga);
        hyzmat_txt.setText(Dil.usluga_gos);

        duzgunn = view.findViewById(R.id.duzgun);
        okap = view.findViewById(R.id.okap);
        okap.setText(Dil.okap);

        addshop = view.findViewById(R.id.addshop);

        bizbarada_rel = view.findViewById(R.id.bizbarda_relative);
        bizbarada_txt = view.findViewById(R.id.bizbarda);
        bizbarada_txt.setText(Dil.bizbarada);
        user_name_layout = view.findViewById(R.id.user_name_layout);
        login_layout = view.findViewById(R.id.login_layout);

        profileedit = view.findViewById(R.id.profileedit);
        profile_image = view.findViewById(R.id.profile_image);
        user_name = view.findViewById(R.id.user_name);
        user_email = view.findViewById(R.id.user_email);
        user_phone = view.findViewById(R.id.user_phone);
        user_address = view.findViewById(R.id.user_address);

       user= FirebaseAuth.getInstance().getCurrentUser();
        user_name_layout.setVisibility(View.GONE);
        login_layout.setVisibility(View.VISIBLE);
       if (user!=null)
       {
           user_name_layout.setVisibility(View.VISIBLE);
           user_logout.setVisibility(View.VISIBLE);
           login_layout.setVisibility(View.GONE);
           user_name.setText(user.getDisplayName());

       }

        bizbarada_rel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), bizbarada.class));
            }
        });

        duzgun=view.findViewById(R.id.user_duzgun);
        duzgun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), duzgunnama.class));
            }
        });

        habarlash_rel=view.findViewById(R.id.bizbilenhabarlash);
        habarlash_txt=view.findViewById(R.id.habarlash);
        habarlash_txt.setText(Dil.bizbilenhabarlash);
        habarlash_rel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto","harythyzmat@gmail.com", null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Body");
                startActivity(Intent.createChooser(emailIntent, "Send email..."));
            }
        });


        yeri_tx.setText(Dil.yeri);
        duzgunn.setText(Dil.duzgun);
        okap.setText(Dil.okap);
        addshop.setText(Dil.addshop);
        dil_truk.setText(Dil.dil_turk);
        dill.setText(Dil.dill);


        List<String> list = new ArrayList<>();
        if (db.get_dil().equals("tm"))
            list.addAll(Arrays.asList(getResources().getStringArray(R.array.yeri_car)));
        else list.addAll(Arrays.asList(getResources().getStringArray(R.array.yeri_car_ru)));
        Log.v("fds","yeri"+db.get_yeri());
        if(!db.get_yeri().equals(""))yer_ahal.setText(list.get(Integer.parseInt(db.get_yeri())));
         else yer_ahal.setText(Dil.saylanmadyk);

         magazyn_gosh = view.findViewById(R.id.user_market_gosh);
         magazyn_gosh.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 startActivity(new Intent(getActivity(),shopadd.class));
             }
         });
        hyzmat_gos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), usluga_gos.class));
            }
        });

        user_follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), Followinglist.class));
            }
        });

        s1=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                //updateNavigationBarState(R.id.person);
            }};

        s1.sendEmptyMessage(1);
        set_location();

        return view;
    }

    private  String getFileExtension(Uri uri){
        ContentResolver conetntResolver =getActivity().getContentResolver();
        MimeTypeMap mime =MimeTypeMap.getSingleton();
        return  mime.getExtensionFromMimeType(conetntResolver.getType(uri));
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user!=null)
        {
            user_name_layout.setVisibility(View.VISIBLE);
            user_logout.setVisibility(View.VISIBLE);
            login_layout.setVisibility(View.GONE);

            DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Buyer_user").child(user.getUid());

            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                  Buyer_user user =   dataSnapshot.getValue(Buyer_user.class);
                  if (user.full_name.equals(null))
                  {

                  }else
                  {
                      user_name.setText(user.full_name+" "+user.surname);
                  }

                  user_phone.setText(user.phone);
                  user_email.setText(user.email);
                  image_url = user.imageurl;

                    Glide.with(getContext())
                            //.load("http://"+ Api.url+"images/" + dataList.get(position).getImage())
                            .load(user.imageurl).thumbnail(0.01f).diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(profile_image);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

          storageReference = FirebaseStorage.getInstance().getReference("posts");
          storageReference_profile = FirebaseStorage.getInstance().getReference("profile_image");
            profile_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

            profileedit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    CropImage.activity()
                            .setAspectRatio(1,1)
                            .setOutputCompressQuality(50)
                            .start(getContext(),userinfo.this);
                }
            });
        }
        else
        {
            user_name_layout.setVisibility(View.GONE);
            user_logout.setVisibility(View.GONE);
            login_layout.setVisibility(View.VISIBLE);
        }

        login_btnn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ii = new Intent(getActivity(),LoginActivity.class);
                ii.putExtra("tag",1);
                startActivity(ii);
                getActivity().finish();
            }
        });

        reg_btnn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ii = new Intent(getActivity(), LoginActivity.class);
                ii.putExtra("tag", 2);
                startActivity(ii);
                getActivity().finish();
            }
        });

        user_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                user_name_layout.setVisibility(View.GONE);
                user_logout.setVisibility(View.GONE);
                login_layout.setVisibility(View.VISIBLE);
            }
        });

}

    void set_location() {
        yeri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(Dil.yerlesyan_yeri);
                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line);
                List<String> list = new ArrayList<>();
                if (db.get_dil().equals("tm"))
                    list.addAll(Arrays.asList(getResources().getStringArray(R.array.yeri_car)));
                else list.addAll(Arrays.asList(getResources().getStringArray(R.array.yeri_car_ru)));
                arrayAdapter.addAll(list);
                builder.setNegativeButton(Dil.yza, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        yer_ahal.setText(arrayAdapter.getItem(which));
                        // location = arrayAdapter.getItem(which);
                        db.inser_yeri(""+which);
                    }
                });
                builder.show();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == getActivity().RESULT_OK) {
                 Uri resultUri = result.getUri();
                  CropImage.ActivityResult result1 = CropImage.getActivityResult(data);
                imageUri =result1.getUri();
                uploadImage();
                //image_aded.setImageURI(imageUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
      /*  if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && requestCode== RESULT_OK){

        }*/
        else
        {
            Toast.makeText(getActivity(), "Something gone wrong", Toast.LENGTH_SHORT).show();
           // finish();
        }
    }

    private  void uploadImage(){
        final ProgressDialog progressDialog =new ProgressDialog(getContext());
        progressDialog.setMessage("Uploading");
        progressDialog.show();

        if(imageUri != null){
            final StorageReference filereference = storageReference_profile.child("buyr_img"+System.currentTimeMillis());

            uploadTask = filereference.putFile(imageUri);
            uploadTask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {
                    if(!task.isComplete()){
                        throw task.getException();
                    }
                    return filereference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if(task.isSuccessful()){
                        Uri downloaduri = task.getResult();
                        myUrl = downloaduri.toString();

                        DatabaseReference  reference = FirebaseDatabase.getInstance().getReference("Buyer_user");

                        FirebaseDatabase.getInstance().getReference("Buyer_user").child(user.getUid()).child("imageurl").setValue(myUrl);
                        progressDialog.dismiss();

                    }
                    else
                    {
                        Toast.makeText(getActivity(), "Failed !", Toast.LENGTH_SHORT).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        else
        {
            Toast.makeText(getContext(), "No Image Selected", Toast.LENGTH_SHORT).show();
        }
    }

}
