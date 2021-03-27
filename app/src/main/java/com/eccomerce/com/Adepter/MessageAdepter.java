package com.eccomerce.com.Adepter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.eccomerce.com.R;
import com.eccomerce.com.model.Chat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageAdepter extends  RecyclerView.Adapter<MessageAdepter.ViewHolder> {
public  static  final  int MSG_TYPE_LEFT=0;
public  static  final  int MSG_TYPE_RIGHT=1;

    public Context mContext;
    public List<Chat> mChat;
    public  String image_url;
    private FirebaseUser fuser;


    public MessageAdepter(Context mContext, List<Chat> mChat, String image_url) {
        this.mContext = mContext;
        this.mChat = mChat;
        this.image_url = image_url;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType==MSG_TYPE_RIGHT){
            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_right,parent,false);
            return new ViewHolder(view);
        }
        else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_left,parent,false);
            return new ViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Chat chat = mChat.get(position);
        holder.show_message.setText(chat.getMessage());
        if (image_url.equals("default")){
            holder.profile_image.setImageResource(R.drawable.birn);
        }
        else
        {
            Glide.with(mContext).load(image_url).into(holder.profile_image);

        }
    }

    @Override
    public int getItemCount() {
        return mChat.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public CircleImageView profile_image;
        public TextView show_message;
        //  public Button btn_follow;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profile_image = itemView.findViewById(R.id.profile_image);
            show_message = itemView.findViewById(R.id.show_message);

        }
    }

    @Override
    public int getItemViewType(int position) {
        fuser =FirebaseAuth.getInstance().getCurrentUser();
        if (mChat.get(position).getSender().equals(fuser.getUid()))
        {
            return MSG_TYPE_RIGHT;
        }
        else {
            return  MSG_TYPE_LEFT;
        }
    }
}


