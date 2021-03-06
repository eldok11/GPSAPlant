package com.example.georg.gpsaplant;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by Georg on 19.12.2017.
 * create one superclass for menu
 */

abstract class PlantPlaecesActivity extends AppCompatActivity {
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_gpsaplant, menu);
        int currentMenuId = getCurrentMenuId();
        //wenn wir eine ID habe, entferne von unserem menu
        if(getCurrentMenuId()!=0){
            menu.removeItem(currentMenuId);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.capturecolor) {
            Intent searchbyColorIntent=new Intent(this,ColorCaptureActivity.class);
            startActivity(searchbyColorIntent);
        }else if(id==R.id.gpsaplant){
            Intent gpsAPlantIntent=new Intent(this,GPSAplant.class);
            startActivity(gpsAPlantIntent);
        }

        return super.onOptionsItemSelected(item);
    }

    public abstract int getCurrentMenuId();

}
