package com.example.wildlifetracker.Database;

import android.content.Context;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class imageRepository {

    private final ImageDao imageDao;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public imageRepository(Context context){
        AppDatabase db = AppDatabase.getInstance(context);
        imageDao = db.imageDao();
    }

    public void insertImage(ImageEntity image){
        executor.execute(() -> imageDao.insertImage(image));
    }

    public List<ImageEntity> getAllImages(){
        return imageDao.getAllImages();
    }
}
