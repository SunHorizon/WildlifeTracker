package com.example.wildlifetracker.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.wildlifetracker.R;

public class CameraUploadChoiceFragment extends Fragment {

    private ActivityResultLauncher<PickVisualMediaRequest> pickMediaLauncher;

    public CameraUploadChoiceFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_camera_upload_choice, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        pickMediaLauncher = registerForActivityResult(
                new ActivityResultContracts.PickVisualMedia(),
                uri -> {
                    if(uri != null){
                        Toast.makeText(getContext(), "Image Selected!", Toast.LENGTH_SHORT).show();

                        // Pass URI to ImagePreviewFragment
                        Bundle previewBundle = new Bundle();
                        previewBundle.putParcelable("image_uri", uri);

                        ImagePreviewFragment previewFragment = new ImagePreviewFragment();
                        previewFragment.setArguments(previewBundle);

                        requireActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.nav_host_fragment, previewFragment)
                                .addToBackStack(null)
                                .commit();

                    }else{
                        Toast.makeText(getContext(), "No image selected", Toast.LENGTH_SHORT).show();
                    }
                });

        Button takePhoto = view.findViewById(R.id.btn_take_photo);
        Button uploadPhoto = view.findViewById(R.id.btn_upload_photo);

        takePhoto.setOnClickListener(v -> getParentFragmentManager()
                .beginTransaction()
                .replace(R.id.nav_host_fragment, new CameraFragment())
                .addToBackStack(null)
                .commit());

        // upload photo from gallery
        uploadPhoto.setOnClickListener(v -> pickMediaLauncher.launch(new PickVisualMediaRequest.Builder()
                .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                .build()));

    }

}
