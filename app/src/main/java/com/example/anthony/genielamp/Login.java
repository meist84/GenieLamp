package com.example.anthony.genielamp;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anthony.genielamp.Adapters.SoftKeyboardAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends SoftKeyboardAdapter {

    private EditText lEmail, lPassword;
    private Button lLoginBtn;
    private TextView lSignUpLink;
    private ProgressDialog progressDialog;

    private static final String TAG = "LoginState";

    private FirebaseAuth.AuthStateListener lAuthListener;
    private FirebaseAuth lAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressDialog = new ProgressDialog(this);
        lEmail = (EditText) findViewById(R.id.input_email);
        lPassword = (EditText) findViewById(R.id.input_password);
        lLoginBtn = (Button) findViewById(R.id.btn_login);
        lSignUpLink = (TextView) findViewById(R.id.link_signup);

        lAuth = FirebaseAuth.getInstance();

        lAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser User = firebaseAuth.getCurrentUser();
                if(User != null) {
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + User.getUid());
                }
                else {
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };

        lLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignIn();
            }
        });

        lSignUpLink.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d(TAG, "onClick:intent_to_register");
                startActivity(new Intent(Login.this, Register.class));
            }
        });

        //lAuth.getInstance().signOut();
    }

    private void SignIn() {
        String email = lEmail.getText().toString();
        String password = lPassword.getText().toString();

        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(Login.this, "Email or Password is Empty", Toast.LENGTH_LONG).show();
        }
        else {
            progressDialog.setMessage("Signing in...");
            progressDialog.show();
            lAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    progressDialog.dismiss();
                    startActivity(new Intent(Login.this, NavigationBar.class));
                    Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());
                    if(!task.isSuccessful()) {
                        progressDialog.dismiss();
                        Log.w(TAG, "signInWithEmail:failed", task.getException());
                        Toast.makeText(Login.this, "Email or Password is wrong", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        lAuth.addAuthStateListener(lAuthListener);
        Log.d(TAG, "AuthStateListener: Started");

    }

    @Override
    public void onStop() {
        super.onStop();
        if (lAuthListener != null) {
            lAuth.removeAuthStateListener(lAuthListener);
            Log.d(TAG, "AuthStateListener: Removed");
        }
    }

    @Override
    public void onPause()
    {
        super.onPause();
        Log.d(TAG, "Login State: Pause");
    }

}


