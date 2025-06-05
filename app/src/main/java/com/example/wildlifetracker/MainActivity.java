package com.example.wildlifetracker;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.wildlifetracker.ui.CameraUploadChoiceFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_app_bar);

        //Load default screen
        if(savedInstanceState == null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.nav_host_fragment, new CameraUploadChoiceFragment())
                    .commit();
        }
        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            int itemId = item.getItemId();

            if (itemId == R.id.nav_camera) {
                selectedFragment = new CameraUploadChoiceFragment();
            } else if (itemId == R.id.nav_gallery) {
                selectedFragment = null; // Replace with actual fragment
            } else if (itemId == R.id.nav_settings) {
                selectedFragment = null; // Replace with actual fragment
            }

            if (selectedFragment != null) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment, selectedFragment)
                        .commit();
            }
            return true;
        });
    }

    public void hideBottomBar(){
        BottomNavigationView bottomAppBar = findViewById(R.id.bottom_app_bar);
        bottomAppBar.setVisibility(View.GONE);
    }

    public void showBottomBar(){
        BottomNavigationView bottomAppBar = findViewById(R.id.bottom_app_bar);
        bottomAppBar.setVisibility(View.VISIBLE);
    }
}