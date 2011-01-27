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
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class SMSTetherMain extends Activity {

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
  
        final Button connectBtn = (Button)findViewById(R.id.connectBtn);
        connectBtn.setOnClickListener(new View.OnClickListener() {
        	@Override
        	public void onClick(View v) {
        		
        		if(!SettingsManager.getConnected()){
        		
	        		EditText hostField = (EditText)findViewById(R.id.hostField);
	        		
	        		String hostname = hostField.getText().toString();
	        		SettingsManager.setHostname(hostname);
	        		
	                Thread thread = new Thread(new SocketRunner());
	                thread.start();
	                
	                SettingsManager.setConnected(true);
	                connectBtn.setText("Disconnect");

	                
        		}
        		else if(SettingsManager.getConnected())
        		{
        			//We're connected... let's disconnect
        			SettingsManager.setWantsDisconnect(true);
        			connectBtn.setText("Connect");
        			
        		}
                
        	 }
        	
        	 });
        
        }
        

    }
