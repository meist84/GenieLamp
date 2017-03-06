package com.example.anthony.genielamp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.view.MenuItem;

import com.example.anthony.genielamp.Fragments.homeFragments;
import com.example.anthony.genielamp.Fragments.messageFragments;
import com.example.anthony.genielamp.Fragments.profileFragments;
import com.example.anthony.genielamp.Fragments.settingFragments;
import com.example.anthony.genielamp.NavigationSettings.BottomNavigationOnShitDisable;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;



public class NavigationBar extends Login
        implements
        settingFragments.onImageClickListener {

    private FirebaseAuth mAuth;
    //private FirebaseDatabase mDatabase;
    private static final String CURRENT_FRAGMENT = "myFragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigationbar);

        mAuth = FirebaseAuth.getInstance();
        //mDatabase.getInstance().setPersistenceEnabled(true);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        BottomNavigationOnShitDisable.disableShiftMode(bottomNavigationView);

        Fragment main = null;

        if (savedInstanceState == null) {
            main = new profileFragments();
            getSupportFragmentManager().beginTransaction().replace(R.id.navigation_bar, main, CURRENT_FRAGMENT).commit();
        }
        else {
            main =(Fragment) getSupportFragmentManager().findFragmentByTag(CURRENT_FRAGMENT);
        }

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment fragment = null;
                        switch (item.getItemId()) {
                            case R.id.tab_home:
                                fragment = new homeFragments();
                                getSupportFragmentManager().beginTransaction().replace(R.id.navigation_bar,fragment, CURRENT_FRAGMENT).commit();
                                break;
                            case R.id.tab_profile:
                                fragment = new profileFragments();
                                getSupportFragmentManager().beginTransaction().replace(R.id.navigation_bar,fragment, CURRENT_FRAGMENT).commit();
                                break;
                            case R.id.tab_message:
                                fragment = new messageFragments();
                                getSupportFragmentManager().beginTransaction().replace(R.id.navigation_bar,fragment, CURRENT_FRAGMENT).commit();
                                break;
                            case R.id.tab_setting:
                                fragment = new settingFragments();
                                getSupportFragmentManager().beginTransaction().replace(R.id.navigation_bar,fragment, CURRENT_FRAGMENT).commit();
                                break;
                        }
                        return false;
                    }
                });
    }

    public void signOut(String TAG) {
        mAuth.getInstance().signOut();
        startActivity(new Intent(NavigationBar.this, Login.class));
        Log.d(TAG, "Signing out, proceed to Login Page:");
    }

    public void changePass(String TAG) {

    }
}
