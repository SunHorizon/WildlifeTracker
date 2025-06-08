package com.example.wildlifetracker.Database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "images")
public class ImageEntity {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String imageUri;
    public String label;

    public ImageEntity(String imageUri, String label){
        this.imageUri = imageUri;
        this.label = label;
    }

}
