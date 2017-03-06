package com.example.anthony.genielamp;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends Login {

    private EditText rUserName, rEmail, rPassword, rConfirmPassword;
    private Button rRegisterBtn;
    private TextView rLoginLink;
    private ProgressDialog progressDialog;

    private static final String TAG = "RegisterState";


    private FirebaseAuth rAuth;
    private DatabaseReference rDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        rAuth = FirebaseAuth.getInstance();
        rDatabase = FirebaseDatabase.getInstance().getReference().child("Users");

        progressDialog = new ProgressDialog(this);
        rUserName = (EditText) findViewById(R.id.input_name);
        rEmail = (EditText) findViewById(R.id.input_email);
        rPassword = (EditText) findViewById(R.id.input_password);
        rConfirmPassword = (EditText) findViewById(R.id.confirm_password);
        rRegisterBtn = (Button) findViewById(R.id.btn_signup);
        rLoginLink = (TextView) findViewById(R.id.link_login);

        rRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegisterUser();
            }
        });

        rLoginLink.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d(TAG, "onClick:intent_to_login");
                startActivity(new Intent(Register.this, Login.class));
            }
        });
    }

    private void RegisterUser() {
        final String username = rUserName.getText().toString().trim();
        final String email = rEmail.getText().toString().trim();
        final String password = rPassword.getText().toString().trim();
        final String confirm_password = rConfirmPassword.getText().toString().trim();


        if(!TextUtils.isEmpty(username) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
            if(TextUtils.equals(password,confirm_password)) {
                progressDialog.setMessage("Signing Up...");
                progressDialog.show();
                rAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Register:Complete:" + task.isSuccessful());
                            String user_id = rAuth.getCurrentUser().getUid();

                            DatabaseReference user_db = rDatabase.child(user_id);
                            user_db.child("Username").setValue(username);
                            user_db.child("Image").setValue("default");

                            progressDialog.dismiss();
                            startActivity(new Intent(Register.this, Login.class));
                            rAuth.getInstance().signOut();
                            Log.d(TAG, "Register:Auto sign out, proceed to Login Page:");
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(Register.this, "Registration failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            Log.w(TAG, "Register:failed", task.getException());
                        }
                    }
                });
            }
            else {
                Toast.makeText(Register.this, "Password not matched :(", Toast.LENGTH_LONG).show();
            }
        }
        else {
            Toast.makeText(Register.this, "Required Field are empty :(", Toast.LENGTH_LONG).show();
        }

    }
}
