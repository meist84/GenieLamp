package com.example.anthony.genielamp.Fragments;

import android.os.Bundle;
import android.app.ProgressDialog;
import android.support.v4.app.Fragment;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.anthony.genielamp.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class PostFragments extends Fragment {

    private ImageButton pImageButton;
    private EditText pPostTitle, pPostDescription;
    private Button pSubmitBtn;
    private Uri pImagePost = null;
    //private StorageReference pStorage;
    private DatabaseReference pDatabase;
    private FirebaseAuth pAuth;
    private ProgressDialog progressDialog;

    private static final int GALLERY_REQUEST = 1;
    private static final String TAG = "ProfileState";

    public PostFragments() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        if (savedInstanceState != null) {
            return;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        container.removeAllViews();
        Log.d(TAG, "is created, removing previous fragments views");

        pAuth = FirebaseAuth.getInstance();
        //pStorage = FirebaseStorage.getInstance().getReference();
        pDatabase = FirebaseDatabase.getInstance().getReference().child("Post");


        progressDialog = new ProgressDialog(getContext());

        View view = inflater.inflate(R.layout.fragments_post, container, false);

        pImageButton = (ImageButton) view.findViewById(R.id.postImage);
        pPostTitle = (EditText) view.findViewById(R.id.postTitle);
        pPostDescription = (EditText) view.findViewById(R.id.postDescription);
        pSubmitBtn = (Button) view.findViewById(R.id.btn_submit_post);

        pImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageCaptured();
            }
        });

        pSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPost();
            }
        });

        return view;
    }

    private void startPost() {
        progressDialog.setMessage("Posting to forums...");
        progressDialog.show();

        String title = pPostTitle.getText().toString().trim();
        String description = pPostDescription.getText().toString().trim();

        if(!TextUtils.isEmpty(title) && !TextUtils.isEmpty(description) && pImagePost != null) {

            DatabaseReference newPost = pDatabase.push();

            newPost.child("Title").setValue(title);
            newPost.child("Description").setValue(description);
            newPost.child("Image").setValue(pImagePost.getLastPathSegment());
            newPost.child("Userid").setValue(pAuth.getCurrentUser().getUid());

            progressDialog.dismiss();

            /*StorageReference path = pStorage.child("Post_Images").child(pImagePost.getLastPathSegment());
            path.putFile(pImagePost).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri link = taskSnapshot.getDownloadUrl();

                    progressDialog.dismiss();

                }
            });*/

        }
        else {
            Toast.makeText(getContext(), "Posting Fail: Empty Fields are presents ", Toast.LENGTH_LONG).show();
            progressDialog.dismiss();
        }
    }

    private void imageCaptured() {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, GALLERY_REQUEST);
        Log.d(TAG, "Picture Finding from Gallery");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "Resuming from StartActivity");

        if(requestCode == GALLERY_REQUEST && resultCode == Activity.RESULT_OK) {
            pImagePost = data.getData();
            Log.d(TAG, "Getting data from Picture");

            pImageButton.setImageURI(pImagePost);
            Log.d(TAG, "Picture Added to Profile Image");
        }

    }

}
