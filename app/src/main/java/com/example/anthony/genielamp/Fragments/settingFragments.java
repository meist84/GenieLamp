package com.example.anthony.genielamp.Fragments;

import android.os.Bundle;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.anthony.genielamp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class settingFragments extends Fragment {

    private FirebaseAuth sAuth;
    //private FirebaseDatabase sDatabase;
    private ImageView sSignOutImg, sChangePassImg;
    private onImageClickListener sSignOutImageClickListener, sChgPassImgClickListener;

    private static final String TAG = "SettingState";

    public settingFragments() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            return;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        sAuth = FirebaseAuth.getInstance();
        //sDatabase.getInstance().setPersistenceEnabled(true);

        View view = inflater.inflate(R.layout.fragments_setting, container, false);

        sSignOutImg = (ImageView) view.findViewById(R.id.image_signout);
        sChangePassImg = (ImageView) view.findViewById(R.id.image_changepassword);

        sSignOutImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sSignOutImageClickListener.signOut(TAG);
            }
        });

        sChangePassImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //sChgPassImgClickListener.changePass(TAG);
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            sChgPassImgClickListener = (onImageClickListener) context;
        }
        catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement onImageClickListener");
        }

        try {
            sSignOutImageClickListener = (onImageClickListener) context;
        }
        catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement onImageClickListener");
        }
    }

    public interface onImageClickListener{
        public void signOut(String TAG);
        public void changePass(String TAG);
    }

}
