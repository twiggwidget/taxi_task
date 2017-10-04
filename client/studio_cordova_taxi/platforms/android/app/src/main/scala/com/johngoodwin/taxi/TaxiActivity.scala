package com.johngoodwin.taxi

import android.app.Activity
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.view.ContextMenu
import com.google.common.collect.ImmutableSet
import org.apache.commons.math3.analysis.function.Abs
import android.view.Menu
import android.view.MenuItem
import android.view.View
import org.apache.cordova.CordovaActivity
//import android.view.TextView
import android.widget.Toast
import android.os.Bundle
import org.apache.cordova._
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.location.LocationManager
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.support.v7.app.AlertDialog
import android.util.Log
import android.widget.TextView
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.lang.reflect.Method
import akka.actor._
//------------------------------------------------------------------------------------------

//============================================================================================
class TaxiActivity extends CordovaActivity
{
  private[taxi] val activity: CordovaActivity = this

  override def onCreate(savedInstanceState: Bundle) 
  {
    super.onCreate(savedInstanceState)
    // Set by <content src="index.html" /> in config.xml
    if ((isGPSOnline || isDataOnline) && isInternetnline) loadUrl(launchUrl)
    else {
      //info dialog ?
      //loadUrl(launchUrl);//lets see if we get two entries on the back-stack...No
      loadUrl("file:///android_asset/www/error.html")
    }


  }//onCreate
  //------------------------------------------------------------------------------------------
  //
  private def isInternetnline: Boolean = {
    val connectivityManager: ConnectivityManager = activity.getSystemService(Context.CONNECTIVITY_SERVICE).asInstanceOf[ConnectivityManager]
    val activeNetworkInfo: NetworkInfo = connectivityManager.getActiveNetworkInfo
    return activeNetworkInfo != null && activeNetworkInfo.isConnected
  }

  //isInternetnline
  //--------------------------------------------------------------------------
  //
  def isGPSOnline: Boolean = {
    val manager: LocationManager = activity.getSystemService(Context.LOCATION_SERVICE).asInstanceOf[LocationManager]
    if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
      //        	buildAlertMessageNoGps();
      return false
    }
    else return true
  }

  //isGPSOnline
  //--------------------------------------------------------------------------
  //
  def isDataOnline: Boolean = {
    var mobileDataEnabled: Boolean = false // Assume disabled
    val cm: ConnectivityManager = activity.getSystemService(Context.CONNECTIVITY_SERVICE).asInstanceOf[ConnectivityManager]
    try {
      val cmClass: Class[_] = Class.forName(cm.getClass.getName)
      val method: Method = cmClass.getDeclaredMethod("getMobileDataEnabled")
      method.setAccessible(true) // Make the method callable
      // get the setting for "mobile data"
      mobileDataEnabled = method.invoke(cm).asInstanceOf[Boolean]
    }
    catch {
      case e: Exception => {
        // Some problem accessible private API
        // TODO do whatever error handling you want here
      }
    }
    return mobileDataEnabled
  }//isDataOnline
  //--------------------------------------------------------------------------
override def onCreateOptionsMenu(amenu   : Menu) : Boolean =
{
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, amenu)//empty parenthesis
    return true //redundant
  }//onCreateOptionsMenu
  //------------------------------------------------------------------------------------------
override def onCreateContextMenu(menu : ContextMenu  , v:  View, menuInfo :ContextMenu.ContextMenuInfo )
  {
    super.onCreateContextMenu(menu, v, menuInfo)
    //        menu.add(0, NoteListActivity.DELETE_ID, 0, R.string.menu_delete);
  }//onCreateContextMenu
  //------------------------------------------------------------------------------------------

  //------------------------------------------------------------------------------------------

}//class TaxiActivity
//============================================================================================
