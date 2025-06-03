package com.example.wildlifetracker.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.wildlifetracker.R;

public class CameraUploadChoiceFragment extends Fragment {

    public CameraUploadChoiceFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_camera_upload_choice, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        Button takePhoto = view.findViewById(R.id.btn_take_photo);
        Button uploadPhoto = view.findViewById(R.id.btn_upload_photo);

        takePhoto.setOnClickListener(v -> {
            getParentFragmentManager()
                    .beginTransaction()
                    .replace(R.id.nav_host_fragment, new CameraFragment())
                    .addToBackStack(null)
                    .commit();
        });

        uploadPhoto.setOnClickListener(v -> {
            // TODO: Trigger image picker intent
        });

    }

}
