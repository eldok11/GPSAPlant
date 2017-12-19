package com.example.georg.gpsaplant;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GPSAplant extends AppCompatActivity {

    public static final int CAMERA_REQUEST = 10;
    private AutoCompleteTextView actPlantName;
    private ImageView imgSpecimenPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gpsaplant);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actPlantName = findViewById(R.id.actPlantName);
        imgSpecimenPhoto = findViewById(R.id.imageSpecimenPhoto);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_gpsaplant, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void btnPauseGPSClicked(View view) {
        Toast.makeText(this,"button clicked",Toast.LENGTH_LONG).show();
    }

    public void btnShowSavedClicked(View v){
       String plantName= actPlantName.getText().toString();
       Toast.makeText(this,plantName,Toast.LENGTH_LONG).show();
    }

    public void btnTakePhotoClicked(View view) {
        Intent cameraIntent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);


        File pictureDirectory=Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        String pictureName=getPictureName();
        File imageFile=new File(pictureDirectory,pictureName);
        Uri pictureUri=Uri.fromFile(imageFile);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,pictureUri);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
        
    }

    private String getPictureName() {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyMMdd_HHmmss");
        String timestamp=sdf.format(new Date());
        return "plantPlacesImage"+timestamp+".jpg";

    }
    //wenn wir staractivityforresult aufrufen, brauchen wir onactivityResult

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            if(requestCode==CAMERA_REQUEST){
                //Bitmap cameraImage = (Bitmap)data.getExtras().get("data");
                //imgSpecimenPhoto.setImageBitmap(cameraImage);


            }
        }
    }
}
