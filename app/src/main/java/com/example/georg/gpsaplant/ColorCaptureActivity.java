package com.example.georg.gpsaplant;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.renderscript.ScriptGroup;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class ColorCaptureActivity extends PlantPlaecesActivity {

    public static final int IMAGE_GALLERY_REQUEST = 20;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_capture);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        imageView = findViewById(R.id.imageView);


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
        //set the data and type, wo und welche daten bei starten des intents sollen angezeigt werden.
        //setdata wird meistens dazu verwernde um auf etwas zu zeigen
        photoPickerIntent.setDataAndType(data,"image/*");

        startActivityForResult(photoPickerIntent, IMAGE_GALLERY_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==RESULT_OK){
            if(requestCode==IMAGE_GALLERY_REQUEST){
                //adress of picked image
                Uri imageUri=data.getData();
                //read image data from sd card.
                InputStream inputStream;
                try {
                    inputStream=getContentResolver().openInputStream(imageUri);
                    //get bitman from the stream
                    Bitmap image = BitmapFactory.decodeStream(inputStream);
                    imageView.setImageBitmap(image);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(this,"Fehler beim streamen",Toast.LENGTH_LONG).show();
                }
                //Bitmap cameraImage = (Bitmap)data.getExtras().get("data");
                //imageView.setImageBitmap(cameraImage);

            }
        }
    }
}
