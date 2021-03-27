package com.eccomerce.com.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.eccomerce.com.Adepter.MessageAdepter;
import com.eccomerce.com.R;
import com.eccomerce.com.model.Chat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MessageActivity extends AppCompatActivity {
FirebaseUser fuser;
DatabaseReference reference;
Intent intent;
EditText chatmsg;
ImageButton sendmsg;
TextView user_name_chat;
ImageView profile_image_chat;

MessageAdepter messageAdepter;
List<Chat> mChat;
RecyclerView chat_recycleview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        profile_image_chat  = findViewById(R.id.profile_image_chat);
        user_name_chat = findViewById(R.id.user_name_chat);
        sendmsg = findViewById(R.id.sendmsg);
        chatmsg = findViewById(R.id.chatmsg);
        chat_recycleview = findViewById(R.id.chat_recycleview);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Message");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        intent =getIntent();
        Bundle bundle = intent.getExtras();
        String userid= bundle.getString("userid");


        //String ProductName= intent.getStringExtra("ProductName");
        //String ProductPrice= intent.getStringExtra("ProductPrice");

      //  if (intent.getStringExtra("ProductName")!="")

        fuser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Seller_user").child(userid);
        readMessage(fuser.getUid(),userid,"");

        if (bundle.getString("ProductName")!=null ||bundle.getString("ProductPrice")!=null)
        {
            String msg = "Product Name : "+bundle.getString("ProductName").toString()+"\n" +
                    "Product Price : "+bundle.getString("ProductPrice").toString();

            sendmessage(msg, fuser.getUid(),userid);
        }

    /*    reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
              //  Seller_User user = dataSnapshot.getValue(Seller_User.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    */
        chat_recycleview.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        chat_recycleview.setLayoutManager(linearLayoutManager);


        sendmsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = chatmsg.getText().toString();
                if (!msg.equals(""))
                {
                    sendmessage(msg, fuser.getUid(),userid);
                }
                else
                {
                    Toast.makeText(MessageActivity.this, "You can't send empty message", Toast.LENGTH_SHORT).show();

                }
                chatmsg.setText(" ");
            }
        });
        
    }

    private  void sendmessage(String msg, String sender , String receiver){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        HashMap<String,Object> hashMap=new HashMap<>();
        hashMap.put("sender",sender);
        hashMap.put("receiver",receiver);
        hashMap.put("message",msg);
        reference.child("Chats").push().setValue(hashMap);

    }

    private  void readMessage(String myid, String userid, String imageurl){
        mChat = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mChat.clear();
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    Chat chat = snapshot.getValue(Chat.class);
                    if (chat.getReceiver().equals(myid)&& chat.getSender().equals(userid) || chat.getReceiver().equals(userid)&& chat.getSender().equals(myid) )
                    {
                        mChat.add(chat);
                    }
                    messageAdepter = new MessageAdepter(MessageActivity.this,mChat,imageurl);
                    chat_recycleview.setAdapter(messageAdepter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
