package com.example.georg.gpsaplant;

import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.georg.DAO.IPlantDAO;
import com.example.georg.DAO.PlantDAO;
import com.example.georg.DAO.PlantDAOStub;
import com.example.georg.DTO.PlantDTO;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import org.json.JSONException;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GPSAplant extends PlantPlaecesActivity implements GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener,LocationListener {

    public static final int CAMERA_REQUEST = 10;
    private AutoCompleteTextView actPlantName;
    private ImageView imgSpecimenPhoto;
    private FusedLocationProviderApi locationProvider= LocationServices.FusedLocationApi;
    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;
    private final static int MILLISECONDS_PER_SECOND=1000;
    public final static int MINUTE=60* MILLISECONDS_PER_SECOND; //frequency of gsp request update
    private double longitude;
    private double latitude;
    private TextView lblLongtitudeValue;
    private TextView lblLangtitudeValue;
    private boolean paused=false;
    private Button btnPause;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gpsaplant);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actPlantName = findViewById(R.id.actPlantName);
        imgSpecimenPhoto = findViewById(R.id.imageSpecimenPhoto);
        lblLongtitudeValue = findViewById(R.id.lblLongtitudeValue);
        lblLangtitudeValue = findViewById(R.id.lblLatitudeValue);

        //getPlantNames for our autocompletetextview, the result that is showing up in autocompletetext is the toString in plandto!!
        PlantSearchTask pet=new PlantSearchTask();
        pet.execute("redbud");

        //for gpslocation, need to implementgoogleapiclient.connectioncallbacks and googleapiclient.onconnetionfailedlistener , tell us if connection suspended or fail
        googleApiClient=new GoogleApiClient.Builder(this).addApi(LocationServices.API).addConnectionCallbacks(this).addOnConnectionFailedListener(this).build();

    //initialize the location request with the acuracy and frequence in whic we want gps updates
        locationRequest=new LocationRequest();
        //I will request update every minute
        locationRequest.setInterval(MINUTE);
        //I will see any other application update it all 15 second
        locationRequest.setFastestInterval(15*MILLISECONDS_PER_SECOND);
        //wie genau soll location seit, wegen energiebedarf
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);


        btnPause = findViewById(R.id.buttonPause);

    }

    public void btnPauseGPSClicked(View view) {

        if(paused==false) {
            pauseGPS();
            paused = true;
            Toast.makeText(this,"paused",Toast.LENGTH_LONG).show();
            btnPause.setText(R.string.lblGPResume);
        }else{
            resumeGPS();
            paused=false;
            Toast.makeText(this,"resumed",Toast.LENGTH_LONG).show();
            btnPause.setText(R.string.lblGPSpause);

        }
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
    //für plantplacesactivity, um zu erkennen, wo wir uns gerade befinden und was somit aus der menubar entfernt werden sollte
    @Override
    public int getCurrentMenuId() {
        return R.id.gpsaplant;
    }


    /*
    3 methods implemented for gpslocation
     */
    //called, when we have required a gps signal from somewhere
    @Override
    public void onConnected(Bundle bundle) {
        requestLocationUpdates();


    }
// google api , locationrequest und locationlisteneer, die wir gemacht haben werden jetzt gebraucht
    private void requestLocationUpdates() {
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient,locationRequest,this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }


    @Override
    protected void onStart() {
        super.onStart();
        googleApiClient.connect();
    }
   //we are going inviseble, safe energy
    @Override
    protected void onStop() {
        super.onStop();
        googleApiClient.disconnect();
    }

    @Override
    protected void onResume() {
        super.onResume();
        resumeGPS();
    }

    private void resumeGPS() {
        if(googleApiClient.isConnected()){
            requestLocationUpdates();
            //if not connected, it will connect onconnected method we did above
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        pauseGPS();
    }

    private PendingResult<Status> pauseGPS() {
        return LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient,this);
    }

    //implement locationlistener, will be called, when we get new gsp location, careful to implement the right interface!
    @Override
    public void onLocationChanged(Location location) {
        //Toast.makeText(this,"location changed: "+location.getLatitude()+" "+location.getLongitude(),Toast.LENGTH_LONG).show();
        longitude = location.getLongitude();
        latitude = location.getLatitude();
        lblLangtitudeValue.setText(Double.toString(longitude));
        lblLongtitudeValue.setText(Double.toString(latitude));


    }

    //interclass for threads with asynctask, so it loads data without interrupting other actions
    //parameter string, we search in plant name, progress we want to show process comlete, so also integer and list of plants for the result
    class PlantSearchTask extends AsyncTask<String, Integer, List<PlantDTO>> {
        //when thread is finished, get the return of doinBackground-thread and has access to items in userinterface bacause its running ui-thread
        @Override
        protected void onPostExecute(List<PlantDTO> plantDTOS) {
            super.onPostExecute(plantDTOS);
            //set adapter, how it should show results, each plant in one row ->simplelistitem 1
            ArrayAdapter<PlantDTO>plantAdapter=new ArrayAdapter<PlantDTO>(GPSAplant.this.getApplicationContext(),android.R.layout.simple_list_item_1,plantDTOS);
            //adapter takes collection of data
            actPlantName.setAdapter(plantAdapter);
        }

        //knows that its going to be string parameter because we said above in generic identifier
        @Override
        protected List<PlantDTO> doInBackground(String... strings) {

            //we use plandaostub, because plantdao is not ready yet
            IPlantDAO plantDAO=new PlantDAO();

            //strings[] dots aufter string means he can put mor than on string for parameter

            try {
                return plantDAO.fetchPlants(strings[0]);
            } catch (JSONException e) {
                e.printStackTrace();
            }
                return null;
        }
    }





}
