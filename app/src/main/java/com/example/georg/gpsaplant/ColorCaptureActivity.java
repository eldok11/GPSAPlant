package com.example.georg.gpsaplant;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.io.File;

public class ColorCaptureActivity extends PlantPlaecesActivity {

    public static final int IMAGE_GALLERY_REQUEST = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_capture);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


    }


    @Override
    public int getCurrentMenuId() {
        return R.id.capturecolor;
    }

    public void onImageGalleryClicked(View view) {
        //invoke image gallery with implicit intent, parameter, I want to pick something
        Intent photoPickerIntent=new Intent(Intent.ACTION_PICK);
        //where to find that
        File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        String pictureDirectoryPath = pictureDirectory.getPath();
        //finally get URI
        Uri data= Uri.parse(pictureDirectoryPath);
        //set the data and type, get all imagetypes
        photoPickerIntent.setDataAndType(data,"image/*");
        startActivityForResult(photoPickerIntent, IMAGE_GALLERY_REQUEST);

    }
}
