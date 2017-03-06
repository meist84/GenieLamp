package com.example.anthony.genielamp.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.anthony.genielamp.R;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class messageFragments extends Fragment {

    private FirebaseAuth mAuth;
    //private FirebaseDatabase mDatabase;

    public messageFragments() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            return;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mAuth = FirebaseAuth.getInstance();
        //mDatabase.getInstance().setPersistenceEnabled(true);

        View view = inflater.inflate(R.layout.fragments_message, container, false);

        return view;    }

}
