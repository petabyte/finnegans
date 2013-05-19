package org.finnegans;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
/**
 * 
 * @author georgesanchez
 *
 */
public class SplashViewActivity extends Activity {
	private boolean mIsBound = false;
	private BackgroundMusicService mServ;
	private ServiceConnection Scon =new ServiceConnection(){

		public void onServiceConnected(ComponentName name, IBinder binder) {
	  	  mServ =((BackgroundMusicService.ServiceBinder)binder).getService();
		}

		public void onServiceDisconnected(ComponentName name) {
			mServ = null;
		}
	  };
	
	  /**
	 * 	
	 */
	 void doBindService(){
	 		bindService(new Intent(this, BackgroundMusicService.class), Scon,Context.BIND_AUTO_CREATE);
			mIsBound = true;
	 }
     
	 /**
      * 
      */
	 void doUnbindService()
		{
			if(mIsBound)
			{
				unbindService(Scon);
	      		mIsBound = false;
			}
		}


  @Override
  public void onCreate(Bundle icicle){
	  super.onCreate(icicle);
	  setContentView(R.layout.finnegans_splash);
	  Handler splashHandler = new Handler();
	  splashHandler.postDelayed(new SplashHandler(),5000);	 
	  doBindService();
      Intent music = new Intent();
      music.setClass(this,BackgroundMusicService.class);
      startService(music);
  }
  
  @Override
  protected void onDestroy() {
	// TODO Auto-generated method stub
	super.onDestroy();
	  mServ.stopMusic();
	  Intent music = new Intent();
	  music.setClass(this,BackgroundMusicService.class);
	  stopService(music);
	  doUnbindService();
  }
  /**
   * 
   * @author georgesanchez
   *
   */
  class SplashHandler implements Runnable{
	  public void run(){	 
		  startActivity(new Intent(getApplication(), WebViewActivity.class));
		  finish();
	  }
  }
}
