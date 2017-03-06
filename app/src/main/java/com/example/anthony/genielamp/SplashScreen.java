package com.example.anthony.genielamp;

import android.os.Bundle;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        startActivity(new Intent(SplashScreen.this, Login.class));
        finish();
    }
}
