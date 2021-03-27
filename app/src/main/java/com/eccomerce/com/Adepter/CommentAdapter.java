package com.eccomerce.com.Adepter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.eccomerce.com.R;
import com.eccomerce.com.model.Buyer_user;
import com.eccomerce.com.model.Comment;
import com.eccomerce.com.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class CommentAdapter extends  RecyclerView.Adapter<CommentAdapter.ViewHolder>{



    private Context mContext;
    private List<Comment> mComment;
    String mPublisherid;

    private FirebaseUser firebaseUser;

    public CommentAdapter(Context mContext, List<Comment> mComment, String mPublisherid) {
        this.mContext = mContext;
        this.mComment = mComment;
        this.mPublisherid = mPublisherid;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(mContext).inflate(R.layout.comment_item,parent,false);
        return new CommentAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
    firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    Comment comment = mComment.get(position);
    viewHolder.comment.setText(comment.getComment());

    if (!comment.getPublisher().equals(mPublisherid))
    {
        getUserInfo(viewHolder.image_profile,viewHolder.username,comment.getPublisher());
    }else{
        getUserInfoSeller(viewHolder.image_profile,viewHolder.username,mPublisherid);
    }

    viewHolder.comment.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    });
    }

    @Override
    public int getItemCount() {
        return mComment.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder
    {

        public ImageView image_profile;
        public TextView username,comment;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image_profile = itemView.findViewById(R.id.image_profile);
            username = itemView.findViewById(R.id.username);
            comment = itemView.findViewById(R.id.comment);
        }
    }

    private void getUserInfo(final ImageView imageView,TextView username, String publisherid){
        DatabaseReference reference;
                 reference = FirebaseDatabase.getInstance().getReference().child("Buyer_user").child(publisherid);

        Log.e("Reference1",reference.getRef()+"");
        Log.e("Reference1",reference.getRoot()+"");
        Log.e("Reference",reference+"");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
               // Glide.with(mContext).load(user.imageurl).into(imageView);
                username.setText(user.getFull_name()+ " "+ user.getSurname());
              //  username.setText(user.getFull_name());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getUserInfoSeller(final ImageView imageView,TextView username, String publisherid){
        DatabaseReference reference;
        reference = FirebaseDatabase.getInstance().getReference().child("Seller_user").child(publisherid);

        Log.e("Reference1",reference.getRef()+"");
        Log.e("Reference1",reference.getRoot()+"");
        Log.e("Reference",reference+"");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                // Glide.with(mContext).load(user.imageurl).into(imageView);
                // username.setText(user.getFull_name()+ " "+ user.getSurname());
                username.setText(user.getFull_name());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
