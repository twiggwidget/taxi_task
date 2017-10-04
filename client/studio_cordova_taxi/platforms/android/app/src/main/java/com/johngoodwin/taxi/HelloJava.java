package com.johngoodwin.taxi;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;

//import android.os.Bundle;

//==========================================================================
public class HelloJava 
{
	public Activity activity;  
//--------------------------------------------------------------------------	
	public HelloJava( Activity _activity)
	{

   		this.activity = _activity;
	}
	
    public String say() 
    {
		Log.v("debug", "Log.v debug from java:");
        return "Hello. I'm Java !";
    }
//--------------------------------------------------------------------------
/*
public class AppStatus {

    private static AppStatus instance = new AppStatus();
    static Context context;
    ConnectivityManager connectivityManager;
    NetworkInfo wifiInfo, mobileInfo;
    boolean connected = false;

    public static AppStatus getInstance(Context ctx) {
        context = ctx.getApplicationContext();
        return instance;
    }

    public boolean isOnline() {
        try {
            connectivityManager = (ConnectivityManager) context
                        .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        connected = networkInfo != null && networkInfo.isAvailable() &&
                networkInfo.isConnected();
        return connected;


        } catch (Exception e) {
            System.out.println("CheckConnectivity Exception: " + e.getMessage());
            Log.v("connectivity", e.toString());
        }
        return connected;
    }
*/
/*
// usage    
    if (AppStatus.getInstance(this).isOnline()) {
	
	    Toast t = Toast.makeText(this,"You are online!!!!",8000).show();
	
	} else {
	
	    Toast t = Toast.makeText(this,"You are not online!!!!",8000).show();
	    Log.v("Home", "############################You are not online!!!!");    
}*/
//--------------------------------------------------------------------------
//another way
private boolean isInternetnline()
{
    ConnectivityManager connectivityManager 
          = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
}//isInternetnline
//--------------------------------------------------------------------------
//
    public boolean isGPSOnline() 
    {
 final LocationManager manager = (LocationManager) activity.getSystemService( Context.LOCATION_SERVICE );

    if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) 
    {
//        buildAlertMessageNoGps();
        return false;
    }
    else return true;
}//isGPSOnline
//--------------------------------------------------------------------------
//
public boolean isDataOnline()
{
    boolean mobileDataEnabled = false; // Assume disabled
    ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
    try {
        Class cmClass = Class.forName(cm.getClass().getName());
        Method method = cmClass.getDeclaredMethod("getMobileDataEnabled");
        method.setAccessible(true); // Make the method callable
        // get the setting for "mobile data"
        mobileDataEnabled = (Boolean) method.invoke(cm);
    } catch (Exception e) {
        // Some problem accessible private API
        // TODO do whatever error handling you want here
    }
    return mobileDataEnabled;
}//isDataOnline
//--------------------------------------------------------------------------
private void buildAlertMessageNoGps() 
{
    final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
    builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
           .setCancelable(false)
           .setPositiveButton("Yes", new DialogInterface.OnClickListener()
	{
               public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) 
               {
                   activity.startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
               }
           })
           .setNegativeButton("No", new DialogInterface.OnClickListener() 
           {
               public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) 
               {
                    dialog.cancel();
               }
           });
    final AlertDialog alert = builder.create();
    alert.show();
}//buildAlertMessageNoGps
//--------------------------------------------------------------------------
public void readLog(int textView)
{
    try {
         Process process = Runtime.getRuntime().exec("logcat -d");
         BufferedReader bufferedReader = new BufferedReader(
              new InputStreamReader(process.getInputStream()));

         StringBuilder log=new StringBuilder();
         String line = ""; 
         while ((line = bufferedReader.readLine()) != null) 
         {
              log.append(line);
         }   
         TextView tv = (TextView)this.activity.findViewById(textView);//R.id.textView1
         tv.setText(log.toString());
    } //try
    catch (IOException e) 
    {
         // Handle Exception
    }//catch
}//readLog
//==========================================================================
}//class HelloJava
//==========================================================================

