package com.example.wildlifetracker.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.wildlifetracker.Database.ImageEntity;
import com.example.wildlifetracker.Database.imageRepository;
import com.example.wildlifetracker.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap map;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_fragment_container);
        if(mapFragment != null){
            mapFragment.getMapAsync(this);
        }

        return view;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap){
        new Thread(() -> {

            imageRepository entity = new imageRepository(requireContext());
            List<ImageEntity> images = entity.getAllImages();

            requireActivity().runOnUiThread(()  -> {
                for(ImageEntity image : images){
                    if(image.latitude != 0 && image.longitude != 0){
                        LatLng position = new LatLng(image.latitude, image.longitude);
                        String title = image.label != null ? image.label : "Unknown Species";
                        MarkerOptions markerOptions = new MarkerOptions().position(position).title(title).snippet(image.notes);
                        googleMap.addMarker(markerOptions);
                    }
                }
            });
        }).start();
    }
}
