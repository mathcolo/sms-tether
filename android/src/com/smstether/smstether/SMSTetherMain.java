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
