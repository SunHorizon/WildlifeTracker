package com.example.wildlifetracker;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wildlifetracker.ui.CameraUploadChoiceFragment;
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
}