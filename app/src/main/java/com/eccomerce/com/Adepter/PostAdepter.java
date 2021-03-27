package com.eccomerce.com.Adepter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.eccomerce.com.R;
import com.eccomerce.com.main.CommentActivity;
import com.eccomerce.com.model.Post;
import com.eccomerce.com.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
//
// import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.List;

public class PostAdepter extends  RecyclerView.Adapter<PostAdepter.ViewHolder> {

    public Context mContext;
    public List<Post> mPost;

    private FirebaseUser firebaseUser;


    public PostAdepter(Context mContext, List<Post> mPost) {
        this.mContext = mContext;
        this.mPost = mPost;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.post_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        Post post = mPost.get(position);
        Glide.with(mContext).load(post.getPostimage()).into(holder.post_image);
        if(post.getDescription().equals(""))
        {
            holder.description.setVisibility(View.GONE);
        }else
        {
            holder.description.setVisibility(View.VISIBLE);
            holder.description.setTextColor(Color.BLACK);
            holder.description.setText(post.getDescription());
        }

        publisherInfo(holder.image_profile,holder.username,holder.publisher,post.getPublisher());
        isLikes(post.getPostid(),holder.like);
        nLikes(holder.likes,post.getPostid());
        getComments(post.getPostid(),holder.comments);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Follow").child(firebaseUser.getUid()).child("following");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(post.getPublisher()).exists()){
                    holder.following_profile.setText("following");
                    holder.following_profile.setTag(2);
                }
                else
                {
                    holder.following_profile.setText("follow");
                    holder.following_profile.setTag(1);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        holder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.like.getTag().equals("like")){
                    FirebaseDatabase.getInstance().getReference().child("Likes").child(post.getPostid())
                            .child(firebaseUser.getUid()).setValue(true);
                }else
                {
                    FirebaseDatabase.getInstance().getReference().child("Likes").child(post.getPostid())
                            .child(firebaseUser.getUid()).removeValue();
                }
            }
        });

        holder.comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, CommentActivity.class);
                intent.putExtra("postid",post.getPostid());
                intent.putExtra("publisherid",post.getPublisher());
                mContext.startActivity(intent);
            }
        });
        holder.comments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, CommentActivity.class);
                intent.putExtra("postid",post.getPostid());
                intent.putExtra("publisherid",post.getPublisher());
                mContext.startActivity(intent);
            }
        });

        holder.following_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (holder.following_profile.getTag().equals(1))
                {
                    FirebaseDatabase.getInstance().getReference().child("Follow").child(firebaseUser.getUid())
                            .child("following").child(post.getPublisher()).setValue(true);
                    holder.following_profile.setTag(2);
                    holder.following_profile.setText("following");
                    // following_profile.setText();
                }
                else
                {
                    FirebaseDatabase.getInstance().getReference().child("Follow").child(firebaseUser.getUid())
                            .child("following").child(post.getPublisher()).removeValue();
                    holder.following_profile.setTag(1);
                    holder.following_profile.setText("follow");
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return mPost.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public ImageView image_profile;
    public ImageView   post_image,like,comment,save;
    public TextView username,likes, publisher,description,comments;
    Button following_profile;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
          image_profile = itemView.findViewById(R.id.image_profile);
          post_image = itemView.findViewById(R.id.image_post);
          like = itemView.findViewById(R.id.like);
          comment = itemView.findViewById(R.id.comment);
          save = itemView.findViewById(R.id.save);
          username = itemView.findViewById(R.id.username);
          likes = itemView.findViewById(R.id.likes);
          publisher = itemView.findViewById(R.id.publisher);
          description = itemView.findViewById(R.id.description);
          comments = itemView.findViewById(R.id.comments);
            following_profile = itemView.findViewById(R.id.following_profile);


        }
    }
    private  void getComments (String postid, TextView comments){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Comments").child(postid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                comments.setText("View All "+dataSnapshot.getChildrenCount() + " Comments");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private  void isLikes(String  postid, ImageView imageView){
        FirebaseUser user =FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference =  FirebaseDatabase.getInstance().getReference()
                .child("Likes")
                .child(postid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(user.getUid()).exists())
                {
                    imageView.setImageResource(R.drawable.ic_liked);
                    imageView.setTag("liked");
                }else
                {
                    imageView.setImageResource(R.drawable.ic_like);
                    imageView.setTag("like");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void nLikes(TextView likes, String postid){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Likes")
                .child(postid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                likes.setText(dataSnapshot.getChildrenCount()+" likes");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private  void publisherInfo(final ImageView image_profil, final TextView username , final TextView publisher, final String userid){

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Seller_user").child(userid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Log.e("check user ", userid);
                User user = dataSnapshot.getValue(User.class);

                Log.e("user",user+"");
                    if (user!=null){
                        Log.e("imag url",user.getImageurl());
                          Glide.with(mContext).load(user.getImageurl()).into(image_profil);
                          username.setText(user.getFull_name()+" "+user.getSurname());
                          publisher.setText(user.getFull_name());
                    }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }



}
