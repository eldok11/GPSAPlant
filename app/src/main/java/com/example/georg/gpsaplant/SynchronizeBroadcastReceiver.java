package com.example.georg.gpsaplant;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class SynchronizeBroadcastReceiver extends BroadcastReceiver {
    boolean power=false;
    boolean wifi=false;

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        //check if we are connected to power
        if(intent.getAction().equalsIgnoreCase(Intent.ACTION_POWER_CONNECTED)){
            power=true;
        }else if(intent.getAction().equals(Intent.ACTION_POWER_DISCONNECTED)){
            power=false;
        }

        //check if we are connected to wifi
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo=connectivityManager.getActiveNetworkInfo();
        if(activeNetworkInfo!=null&&activeNetworkInfo.getType()==ConnectivityManager.TYPE_WIFI){
            wifi=true;
        }else{
            wifi=false;
        }

        //check now all posibilities
        if(power&&wifi){
            upload(context);
        }
        //dowloadig json isnt power intensiv and not large, so not worried about power
        if(wifi){
            download(context);
        }
    }

    private void download(Context context) {
        Toast.makeText(context,"downloading",Toast.LENGTH_LONG).show();
    }

    private void upload(Context context) {
        Toast.makeText(context,"upload",Toast.LENGTH_LONG).show();
    }
}
