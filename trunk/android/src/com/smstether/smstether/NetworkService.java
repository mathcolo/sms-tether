package com.smstether.smstether;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class NetworkService extends Service {
	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}
	
	@Override
	public void onCreate() {
		Toast.makeText(this, "SMS Tethering started...", Toast.LENGTH_LONG).show();
		Log.d("NetworkService", "onCreate");
	}
	
	@Override
	public void onDestroy() {
		Toast.makeText(this, "SMS Tethering stopped...", Toast.LENGTH_LONG).show();
		Log.d("NetworkService", "onDestroy");
	}
}