package com.example.wildlifetracker.Database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "images")
public class ImageEntity {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String imageUri;
    public String label;
    public long timestamp;
    public Double longitude;
    public Double latitude;
    public String notes;


    public ImageEntity(String imageUri, String label, long timestamp, Double longitude, Double latitude, String notes){
        this.imageUri = imageUri;
        this.label = label;
        this.timestamp = timestamp;
        this.longitude = longitude;
        this.latitude = latitude;
        this.notes = notes;
    }

}
