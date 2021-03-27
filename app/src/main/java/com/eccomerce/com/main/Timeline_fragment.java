package com.eccomerce.com.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.eccomerce.com.Adepter.PostAdepter;
import com.eccomerce.com.R;
import com.eccomerce.com.constants;
import com.eccomerce.com.login_package.LoginActivity;
import com.eccomerce.com.model.Post;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Timeline_fragment extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener  {

    public  static Handler s1=new Handler();
    SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private PostAdepter postAdepter;
    private List<Post> postLists;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_timeline);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Timeline");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.swipe_feed);
        swipeRefreshLayout.setOnRefreshListener(this);
        recyclerView = findViewById(R.id.recycler_view);

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager =  new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        postLists = new ArrayList<>();
        postAdepter = new PostAdepter(this, postLists);
        recyclerView.setAdapter(postAdepter);

        if (FirebaseAuth.getInstance().getCurrentUser()==null) {
  //           readPost_withoutlogin();

            Intent ii = new Intent(this,LoginActivity.class);
            ii.putExtra("tag",1);
            startActivity(ii);
        }


    }


    @Override
    public void onRefresh() {
        if (FirebaseAuth.getInstance().getCurrentUser()!=null) {
            readPost();
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        if (FirebaseAuth.getInstance().getCurrentUser()!=null) {
            readPost();
        }

    }

    private  void readPost_withoutlogin(){

//        Log.e("userid", FirebaseAuth.getInstance().getCurrentUser().getUid());
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("posts");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                postLists.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Post post = snapshot.getValue(Post.class);
                    postLists.add(post);
                  /*  if (post.getPublisher().equals(FirebaseAuth.getInstance().getCurrentUser().getUid()))
                    {
                        Log.e("check post", post+"");
                        Log.e("check post", post.getPostid()+"");
                        postLists.add(post);
                    }
*/
                }
                postAdepter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    private  void readPost(){

        Log.e("userid", FirebaseAuth.getInstance().getCurrentUser().getUid());
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("posts");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                postLists.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Post post = snapshot.getValue(Post.class);
                    postLists.add(post);
                  /*  if (post.getPublisher().equals(FirebaseAuth.getInstance().getCurrentUser().getUid()))
                    {
                        Log.e("check post", post+"");
                        Log.e("check post", post.getPostid()+"");
                        postLists.add(post);
                    }
*/
                }
                postAdepter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
