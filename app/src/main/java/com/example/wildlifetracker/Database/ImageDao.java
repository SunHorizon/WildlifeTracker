package com.example.wildlifetracker.Database;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ImageDao {

    @Insert
    void insertImage(ImageEntity image);

    @Delete
    void deleteImage(ImageEntity image);

    @Query("SELECT * FROM images ORDER BY id DESC")
    List<ImageEntity> getAllImages();
}
