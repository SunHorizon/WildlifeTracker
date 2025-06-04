package com.example.wildlifetracker;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wildlifetracker.ui.CameraUploadChoiceFragment;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton fabMainAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fabMainAction = findViewById(R.id.fab_main_action);

        //Load default screen
        if(savedInstanceState == null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.nav_host_fragment, new CameraUploadChoiceFragment())
                    .commit();
        }

        // Fab click opens camera/upload choice fragment
        fabMainAction.setOnClickListener(v -> {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.nav_host_fragment, new CameraUploadChoiceFragment())
                    .addToBackStack(null)
                    .commit();
        });
    }

    public void hideBottomBar(){
        BottomAppBar bottomAppBar = findViewById(R.id.bottom_app_bar);
        FloatingActionButton fab = findViewById(R.id.fab_main_action);
        bottomAppBar.setVisibility(View.GONE);
        fab.setVisibility(View.GONE);
    }

    public void showBottomBar(){
        BottomAppBar bottomAppBar = findViewById(R.id.bottom_app_bar);
        FloatingActionButton fab = findViewById(R.id.fab_main_action);
        bottomAppBar.setVisibility(View.VISIBLE);
        fab.setVisibility(View.VISIBLE);
    }

}