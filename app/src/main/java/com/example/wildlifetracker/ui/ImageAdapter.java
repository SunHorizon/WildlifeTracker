package com.example.wildlifetracker.ui;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wildlifetracker.Database.ImageEntity;
import com.example.wildlifetracker.R;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

    private final List<ImageEntity> imageList;
    private final OnImageDeleteListener deleteListener;

    public interface OnImageDeleteListener {
        void onDelete(ImageEntity image);
    }

    public ImageAdapter(List<ImageEntity> imageList, OnImageDeleteListener deleteListener){
        this.imageList = imageList;
        this.deleteListener = deleteListener;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.image_item, parent, false);

        return new ImageViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position){
        ImageEntity image = imageList.get(position);
        holder.labelTextView.setText(image.label);
        holder.imageView.setImageURI(Uri.parse(image.imageUri));
        holder.deleteButton.setOnClickListener(v -> deleteListener.onDelete(image));
    }

    @Override
    public int getItemCount(){
        return imageList.size();
    }

    static class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView labelTextView;

        ImageView deleteButton;

        public ImageViewHolder(View itemView){
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view);
            labelTextView = itemView.findViewById(R.id.label_text_view);
            deleteButton = itemView.findViewById(R.id.delete_button);
        }
    }
}
