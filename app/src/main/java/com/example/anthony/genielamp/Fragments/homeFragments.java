package com.example.anthony.genielamp.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.anthony.genielamp.Adapters.PostHolder;
import com.example.anthony.genielamp.Post;
import com.example.anthony.genielamp.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class homeFragments extends Fragment {

    private FirebaseAuth hAuth;
    private DatabaseReference hDatabase;
    private FirebaseRecyclerAdapter hAdapter;

    private static final String TAG = "HomeState";

    public homeFragments() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            return;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        Log.d(TAG, "is created, removing previous fragments views");

        hDatabase = FirebaseDatabase.getInstance().getReference().child("Post");
        hAuth = FirebaseAuth.getInstance();
        //hDatabase.getInstance().setPersistenceEnabled(true);

        View view = inflater.inflate(R.layout.fragments_home, container, false);

        RecyclerView recycler = (RecyclerView) view.findViewById(R.id.post_list);
        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));

        hAdapter = new FirebaseRecyclerAdapter<Post, PostHolder>(Post.class, R.layout.post_row, PostHolder.class, hDatabase) {
            @Override
            public void populateViewHolder(PostHolder postViewHolder, Post post, int position) {
                Log.d(TAG, "Recyclerview Input Data");
                postViewHolder.setTitle(post.getTitle());
                postViewHolder.setDescription(post.getDescription());
                postViewHolder.setEmail("User ID:" + post.getUserid());
            }
        };
        recycler.setAdapter(hAdapter);

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hAdapter.cleanup();
        Log.d(TAG, "Recyclerview adapter destroyed");
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
        Log.d(TAG, "OptionMenu Created");
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected");

        if(item.getItemId() == R.id.action_add) {
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.addToBackStack(null);
            getFragmentManager().beginTransaction().replace(R.id.home, new PostFragments(), "myFragment").commit();
            Log.d(TAG, "Changed Fragments");
        }

        return super.onOptionsItemSelected(item);
    }
}
