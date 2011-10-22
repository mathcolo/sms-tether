//
//  SMSTetherMain.java
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

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SMSTetherMain extends Activity {

	//A handler to get messages from SocketRunner
	
	private Handler handler;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		//This handler currently used for one type of message; the socket in SocketRunner no longer being open
		handler = new Handler() {
			@Override
			public void handleMessage(android.os.Message msg) {
				Log.d("SMSTether-Handler", "Handler exec");
	        	final Button connectBtn = (Button)findViewById(R.id.connectBtn);
	        	connectBtn.setText("Connect");
	        }
		};

		final Button connectBtn = (Button)findViewById(R.id.connectBtn);
		connectBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				if(!SettingsManager.getConnected()){
					EditText hostField = (EditText)findViewById(R.id.hostField);

					String hostname = hostField.getText().toString();
					SettingsManager.setHostname(hostname);

					Thread thread = new Thread(new SocketRunner(handler,SMSTetherMain.this));
					thread.start();
					startService(new Intent(SMSTetherMain.this.getBaseContext(),NetworkService.class));

					SettingsManager.setConnected(true);
					connectBtn.setText("Disconnect");
				}
				else if(SettingsManager.getConnected())
				{
					//We're connected... let's disconnect
					SettingsManager.setWantsDisconnect(true);
					stopService(new Intent(SMSTetherMain.this.getBaseContext(),NetworkService.class));
					connectBtn.setText("Connect");

				}
			}
		});
	}
}