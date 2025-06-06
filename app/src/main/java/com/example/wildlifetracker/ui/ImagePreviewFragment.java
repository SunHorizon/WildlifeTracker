package com.example.wildlifetracker.ui;

import android.app.AlertDialog;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.wildlifetracker.R;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.label.ImageLabeler;
import com.google.mlkit.vision.label.ImageLabeling;
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions;

import java.io.IOException;
import java.util.List;

public class ImagePreviewFragment  extends Fragment {
    private ImageView imageView;
    private Button btnSaveImage ;
    private Uri imageUri;
    private TextView speciesLabelText;
    private String speciesLabel = "";

    public ImagePreviewFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_image_preview, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        imageView = view.findViewById(R.id.image_preview_view);
        btnSaveImage  = view.findViewById(R.id.btnSaveImage);
        speciesLabelText = view.findViewById(R.id.speciesLabelText);


        if(getArguments() != null){
            imageUri = getArguments().getParcelable("image_uri");
            if(imageUri != null){
                identifyImage(imageUri);
                Glide.with(requireContext())
                        .load(imageUri)
                        .into(imageView);
            }
        }

        btnSaveImage.setOnClickListener(v -> {
            if(!speciesLabel.isEmpty()){
                saveImageData(imageUri, speciesLabel);
            }
        });
    }

    public void identifyImage(Uri imageUri){
        try{
            InputImage image = InputImage.fromFilePath(getContext(), imageUri);
            ImageLabeler labeler = ImageLabeling.getClient(ImageLabelerOptions.DEFAULT_OPTIONS);

            labeler.process(image)
                    .addOnSuccessListener(imageLabels -> {
                        if(!imageLabels.isEmpty()){
                            speciesLabel = imageLabels.get(0).getText();
                            speciesLabelText.setText(String.format("Species: %s", speciesLabel));
                        }else{
                            speciesLabelText.setText("Could not identify species");
                        }
                    })
                    .addOnFailureListener(e -> {
                        e.printStackTrace();
                        speciesLabelText.setText("Labeling failed");
                        Toast.makeText(requireContext(), "Failed to label image", Toast.LENGTH_SHORT).show();
                    });
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void saveImageData(Uri uri, String label){
        Toast.makeText(requireContext(), "Saved: " + label, Toast.LENGTH_SHORT).show();
        // TODO: Add actual saving logic
    }
}
