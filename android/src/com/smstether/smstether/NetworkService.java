//
//  NetworkService.java
//  SMS Tether
//
//  http://code.google.com/p/sms-tether
//
//This file is part of SMS Tether.
//
//SMS Tether is free software: you can redistribute it and/or modify
//it under the terms of the GNU General Public License as published by
//the Free Software Foundation, either version 3 of the License, or
//(at your option) any later version.
//
//SMS Tether is distributed in the hope that it will be useful,
//but WITHOUT ANY WARRANTY; without even the implied warranty of
//MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//GNU General Public License for more details.

//You should have received a copy of the GNU General Public License
//along with SMS Tether.  If not, see <http://www.gnu.org/licenses/>.

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