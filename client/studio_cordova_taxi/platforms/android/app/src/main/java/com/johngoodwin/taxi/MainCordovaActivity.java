package com.johngoodwin.taxi;

import android.os.Bundle;
import org.apache.cordova.*;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.TextView;
import android.webkit.WebView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;

import android.os.Handler;
import java.lang.Runnable;
//==========================================================================
public class MainCordovaActivity extends CordovaActivity
{
//--------------------------------------------------------------------------
	final CordovaActivity activity = this;
	boolean backOveride = false;

Handler   homeHandler = new Handler();
Runnable homeRunnable = new Runnable() 
{
    public void run()
    {
		Intent showOptions = new Intent(Intent.ACTION_MAIN);
		showOptions.addCategory(Intent.CATEGORY_HOME);
		startActivity(showOptions);          
    }
};
	
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        // Set by <content src="index.html" /> in config.xml
        if((isGPSOnline() || isDataOnline()) && isInternetnline())
        {
			backOveride = true;
        	loadUrl(launchUrl);
		}
		else
		{
			//info dialog ?
			//loadUrl(launchUrl);//lets see if we get two entries on the back-stack...No
			loadUrl("file:///android_asset/www/error.html");
		}
    }//onCreate
//--------------------------------------------------------------------------
@Override
public void onBackPressed()
    {
		if (backOveride)
		{
			//loadUrl("file:///android_asset/www/bye.html");
			homeHandler.postDelayed(homeRunnable, 1000);
		} else super.onBackPressed();
	}
//--------------------------------------------------------------------------
//
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
//        	buildAlertMessageNoGps();
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
    } 
    catch (Exception e) 
    {
        // Some problem accessible private API
        // TODO do whatever error handling you want here
    }
    return mobileDataEnabled;
	}//isDataOnline
//==========================================================================    
}//MainCordovaActivity
//==========================================================================
//--------------------------------------------------------------------------
/*@Override
public void onDestroy()
	{
		Log.v("onDestroy","Fixing \"Error: WebView.destroy() called while still attached!\" bug");
		loadUrl("file:///android_asset/www/bye.html");
		Sleep(3000);
		super.onDestroy();//dont call super
		if (this.appView != null) 
		{
			//this.appView.handleDestroy();//fails
			WebView wv = (WebView) findViewById(100);//YES they (Cordova) hard-coded it !

		if (wv != null) 
		{
			wv.removeAllViews();
			wv.destroy();
			Log.v("onDestroy","Fixing \"Error: WebView.destroy() called while still attached!\" bug");
		}
			//this.appView.getView().removeAllViews();
			//this.appView.getView().destroy();
		}
	}*/
//--------------------------------------------------------------------------
/* @Override
public void onDetachedFromWindow() 
{
    super.onDetachedFromWindow();
    }
}*/

/*
import android.os.Bundle;
import android.widget.LinearLayout;
import org.apache.cordova.*;
import org.apache.cordova.DroidGap;

public class MainActivity extends DroidGap {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.init();
        super.loadUrl(Config.getStartUrl());
        super.loadUrl("file:///android_asset/www/index.html");

    }
}
*/