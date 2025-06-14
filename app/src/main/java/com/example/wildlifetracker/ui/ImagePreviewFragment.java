package com.example.wildlifetracker.ui;


import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.wildlifetracker.Database.ImageEntity;
import com.example.wildlifetracker.Database.imageRepository;
import com.example.wildlifetracker.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.mlkit.common.model.LocalModel;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.label.ImageLabeler;
import com.google.mlkit.vision.label.ImageLabeling;
import com.google.mlkit.vision.label.custom.CustomImageLabelerOptions;
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions;

import java.io.IOException;
import java.util.concurrent.Executors;

public class ImagePreviewFragment  extends Fragment {
    private ImageView imageView;
    private Button btnSaveImage ;
    private Uri imageUri;
    private TextView speciesLabelText;
    private String speciesLabel = "";

    private ImageButton backButton;

    private imageRepository respository;
    private EditText notesField;

    private FusedLocationProviderClient fusedLocationProviderClient;

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
        backButton = view.findViewById(R.id.back_button);
        respository = new imageRepository(requireContext());
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity());
        notesField = view.findViewById(R.id.editNotes);

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
                String notes = notesField.getText().toString();
                saveImageData(imageUri, speciesLabel, notes);
            }
        });

        backButton.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().popBackStack();
        });
    }

    public void identifyImage(Uri imageUri){
        try{
            InputImage image = InputImage.fromFilePath(requireContext(), imageUri);
            ImageLabeler labeler = ImageLabeling.getClient(ImageLabelerOptions.DEFAULT_OPTIONS);

//            LocalModel localModel = new LocalModel.Builder()
//                    .setAssetFilePath("uas.tflite")
//                    .build();
//
//            CustomImageLabelerOptions options =
//                    new CustomImageLabelerOptions.Builder(localModel)
//                            .setConfidenceThreshold(0.5f)
//                            .setMaxResultCount(3)
//                            .build();
//
//            ImageLabeler labeler = ImageLabeling.getClient(options);

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

    private void saveImageData(Uri uri, String label, String notes){
        if(uri != null){
            if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(requireActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        101);
            }else {
                fusedLocationProviderClient.getLastLocation()
                        .addOnSuccessListener(location -> {
                            double lat = (location != null) ? location.getLatitude() : 0.0;
                            double lon = (location != null) ? location.getLongitude() : 0.0;
                            long timestamp = System.currentTimeMillis();
                            ImageEntity entity = new ImageEntity(uri.toString(), label, timestamp, lon, lat, notes);
                            respository.insertImage(entity);
                            Toast.makeText(getContext(), "Sighting saved", Toast.LENGTH_SHORT).show();

                            // Navigate back
                            requireActivity().getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.nav_host_fragment, new CameraUploadChoiceFragment())
                                    .commit();
                        });
            }

        }else{
            Toast.makeText(requireContext(), "Failed to save image: " , Toast.LENGTH_SHORT).show();
        }
    }
}
