package com.example.wildlifetracker.ui;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.wildlifetracker.R;

public class ImagePreviewFragment  extends Fragment {
    private ImageView imageView;
    private Button analyzeButton;
    private Uri imageUri;

    public ImagePreviewFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_image_preview, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        imageView = view.findViewById(R.id.image_preview_view);
        analyzeButton = view.findViewById(R.id.btn_analyze);

        if(getArguments() != null){
            imageUri = getArguments().getParcelable("image_uri");
            if(imageUri != null){
//                imageView.setImageURI(imageUri);
                Glide.with(requireContext())
                        .load(imageUri)
                        .into(imageView);
            }
        }

        analyzeButton.setOnClickListener(v -> {
            // TODO: Call ML Kit processing logic here using imageUri
        });
    }
}
