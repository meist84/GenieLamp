package com.example.anthony.genielamp.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.anthony.genielamp.R;
import com.example.anthony.genielamp.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class profileFragments extends Fragment {

    private ImageButton pImageButton;
    private EditText pName, pEmail, pDescription;
    private Button pUpdateBtn;
    private FirebaseAuth pAuth;
    private DatabaseReference pDatabaseReference;
    private DatabaseReference p2DatabaseReference;
    //private FirebaseDatabase pDatabase;
    //private StorageReference pStorageRef;
    private Uri pProfileImage = null;

    private static final int GALLERY_REQUEST = 1;
    private static final String TAG = "ProfileState";

    public profileFragments() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //pDatabase.getInstance().setPersistenceEnabled(true);

        pAuth = FirebaseAuth.getInstance();

        //pStorageRef = FirebaseStorage.getInstance().getReference();

        String user_id = pAuth.getCurrentUser().getUid();
        pDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(user_id);

        if (savedInstanceState != null) {
            return;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragments_profile, container, false);

        pImageButton = (ImageButton) view.findViewById(R.id.profileImage);
        pName = (EditText) view.findViewById(R.id.name);
        pEmail = (EditText) view.findViewById(R.id.email);
        pDescription = (EditText) view.findViewById(R.id.description);
        pUpdateBtn = (Button) view.findViewById(R.id.btn_update_profile);

        pEmail.setText("Email: "+ pAuth.getCurrentUser().getEmail().toString());
        pEmail.setKeyListener(null);
        Log.d(TAG, "Email is set and disable editing");

        pImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageCaptured();
            }
        });

        pUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setupProfile();
            }
        });

        pDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "Listener is set up for: " + pDatabaseReference + dataSnapshot.getValue());
                User userDetail = dataSnapshot.getValue(User.class);
                Log.d(TAG, "Listener Received: " + userDetail.toString());
                pName.setText(userDetail.getUsername());
                pDescription.setText(userDetail.getDescription());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    private void imageCaptured() {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, GALLERY_REQUEST);
        Log.d(TAG, "Picture Finding from Gallery");
    }


    private void setupProfile() {
        Log.d(TAG, "Start process to upload data to Firebase");
        final String name = pName.getText().toString().trim();
        final String description = pDescription.getText().toString().trim();

        if(!TextUtils.isEmpty(name) && pProfileImage != null) {
            Log.d(TAG, "Update Name & Description");
            pDatabaseReference.child("Username").setValue(name);
            pDatabaseReference.child("Description").setValue(description);
            pDatabaseReference.child("Image").setValue(pProfileImage.getLastPathSegment());

            /*Log.d(TAG, "Start to generate uri path for image");
            StorageReference path = pStorageRef.child(pProfileImage.getLastPathSegment());

            path.putFile(pProfileImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri downloadUri = taskSnapshot.getDownloadUrl();
                    Log.d(TAG, "Start to generate download path for image");
                    pDatabaseReference.child(user_id).child("Username").setValue(name);
                    pDatabaseReference.child(user_id).child("Image").setValue(downloadUri);
                    Log.d(TAG, "Picture Uploaded");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Log.w(TAG, "Picture Unable to Uploads");
                }
            });*/
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "Resuming from StartActivity");

        if(requestCode == GALLERY_REQUEST && resultCode == Activity.RESULT_OK) {
            Uri imageUri = data.getData();
            Log.d(TAG, "Getting data from Picture");

            CropImage.activity(imageUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1, 1)
                    .start(getContext(), this);
            Log.d(TAG, "Cropped Image");
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            Log.d(TAG, "Retrieved Crop Image");
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode == Activity.RESULT_OK) {
                Log.d(TAG, "Cropped Image is successfully received");
                pProfileImage = result.getUri();

                if(pProfileImage != null) {
                    pImageButton.setImageURI(pProfileImage);
                    Log.d(TAG, "Picture Added to Profile Image");
                }
            }
            else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Log.w(TAG, "Error" + result.getError());
            }
        }
    }
}
