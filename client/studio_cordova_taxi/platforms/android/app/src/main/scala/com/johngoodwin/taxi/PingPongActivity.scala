package com.johngoodwin.taxi

import android.app.Activity
import android.os.Bundle
import android.util.Log                               //jrg does not work (I think know why !) (akka has one I think)!
import android.widget.TextView
import android.view.ContextMenu
import com.google.common.collect.ImmutableSet
import org.apache.commons.math3.analysis.function.Abs
import android.view.Menu
import android.view.MenuItem
import android.view.View
//import android.view.TextView
import android.widget.Toast

import akka.actor._
//------------------------------------------------------------------------------------------
case object PingMessage
case object PongMessage
case object StartMessage
case object StopMessage

//singleton
object PingPong
{
var system         :ActorSystem = null
    var ping       : ActorRef   = null
    var pong       : ActorRef   = null
    var helloJava  :HelloJava   = null
    var needsInit  :Boolean     = true
    var pingRunning:Boolean     = false
    var pongRunning:Boolean     = false
}//object PingPong
//============================================================================================
class PingPongActivity extends Activity 
{
   
  override def onCreate(savedInstanceState: Bundle) 
  {
    super.onCreate(savedInstanceState)
    
    setContentView(R.layout.activity_hello)
    val scalaTextView = findViewById(R.id.scala_text_view).asInstanceOf[TextView]
    PingPong.helloJava = new HelloJava(this)
    scalaTextView.setText(PingPong.helloJava.say())

//    val values = for {
//      str <- List("1", "2", "3", "string", "5")
//      int <- str.parseInt.toOption
//    } yield new Abs().value(int)
//    Log.v("debug", "ImmutableSet.of(values):" + ImmutableSet.of(values))

  // * Create new actor system * //
    PingPong.system = ActorSystem("PingPongSystem")

	  doInit()

  }//onCreate
  //------------------------------------------------------------------------------------------
def doInit()
{
	if(PingPong.needsInit)
	{
		// * Create a pong actor * //
		PingPong.pong = PingPong.system.actorOf(Props[Pong], name = "pong")

		// * Create a ping actor * //
		PingPong.ping = PingPong.system.actorOf(Props(new Ping(PingPong.pong)), name = "ping")
		PingPong.needsInit = false
    }
}//doInit
  //------------------------------------------------------------------------------------------
override def onCreateOptionsMenu(amenu   : Menu) : Boolean =
{
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, amenu);
    return true;
  }//onCreateOptionsMenu
  //------------------------------------------------------------------------------------------
override def onCreateContextMenu(menu : ContextMenu  , v:  View, menuInfo :ContextMenu.ContextMenuInfo )
  {
    super.onCreateContextMenu(menu, v, menuInfo);
    //        menu.add(0, NoteListActivity.DELETE_ID, 0, R.string.menu_delete);
  }//onCreateContextMenu
  //------------------------------------------------------------------------------------------
def onClickStart( view : View )
  {
    // * Start using '!' operator of akka (//call the ! method on the ActorRef) * //
    if(!(PingPong.pingRunning == true && PingPong.pongRunning == true))
        PingPong.ping ! StartMessage
  }//onClickStart
  //------------------------------------------------------------------------------------------
def onClickStop( view : View )
  {
      if(PingPong.pingRunning == true && PingPong.pongRunning == true)
        PingPong.ping ! StopMessage
  }//onClickStop
  //------------------------------------------------------------------------------------------
def onClickLog( view : View )
  {
        PingPong.helloJava.readLog(R.id.etLogText)
  }//onClickLog
  //------------------------------------------------------------------------------------------

}//class PingPong
//============================================================================================
/**
 * amit.dixit
 * Ping actor
 */
class Ping(pong: ActorRef) extends Actor {

  // its a var because it can change //
  var count = 0

  // Method without any parameter //
  def incrementAndPrint 
  { 
      count += 1
      println("ping" + count)
      Log.v("debug", "ping" )
  }

  // If a message is received //
  def receive = 
  {
    case StartMessage =>
      incrementAndPrint
      pong ! PingMessage
      PingPong.pingRunning = true

    case PongMessage =>

      incrementAndPrint

      if (count > 99) 
      {
        sender ! StopMessage
        println("ping stopped")
        Log.v("debug", "ping stopped" )
        context.stop(self)
      } else {

        sender ! PingMessage
   }
   case StopMessage =>
        pong ! StopMessage
        println("ping stopped")
        Log.v("debug", "ping stopped" )
        context.stop(self)
        PingPong.pingRunning = false
        PingPong.needsInit = true
  }
}//class Ping
//============================================================================================
/**
 * Pong actor
 */
class Pong extends Actor {

  // If a message is received //
  def receive = {

    case PingMessage =>
      println("pong")
      Log.v("debug", "pong" )
      sender ! PongMessage
      PingPong.pongRunning = true

    case StopMessage =>
      println("pong stopped")
      Log.v("debug", "pong stopped" )
      context.stop(self)
      PingPong.pongRunning = false
      PingPong.needsInit = true
  }
}//class Pong
//============================================================================================
