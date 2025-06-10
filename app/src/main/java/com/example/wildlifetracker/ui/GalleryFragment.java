package com.example.wildlifetracker.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wildlifetracker.Database.ImageEntity;
import com.example.wildlifetracker.Database.imageRepository;
import com.example.wildlifetracker.R;

import java.util.List;

public class GalleryFragment extends Fragment {

    private RecyclerView recyclerView;
    private ImageAdapter imageAdapter;
    private imageRepository repositoryImage;

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable  ViewGroup container,
                             @Nullable  Bundle savedInstanceState){
        return inflater.inflate(R.layout.gallery_fragment, container, false);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        repositoryImage = new imageRepository(requireContext());

        new Thread(() -> {
            List<ImageEntity> images = repositoryImage.getAllImages();
            requireActivity().runOnUiThread(() -> {
                if(images.isEmpty()){
                    Toast.makeText(getContext(), "No saved images", Toast.LENGTH_SHORT).show();
                }
                imageAdapter = new ImageAdapter(images, image -> {
                    repositoryImage.deleteImage(image);
                    images.remove(image);
                    imageAdapter.notifyDataSetChanged();
                });
                recyclerView.setAdapter(imageAdapter);
            });
        }).start();
    }
}
