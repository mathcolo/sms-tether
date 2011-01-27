//
//  SocketRunner.java
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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

import android.telephony.SmsManager;
import android.util.Log;

public class SocketRunner implements Runnable {

    public void run() {
         try {
        	 
        	 InetAddress serverAddr = InetAddress.getByName(SettingsManager.getHostname());//TCPServer.SERVERIP
        	 
        	 Log.d("SMSTether-TCPLoop", "Connecting to server at address " + serverAddr);
        	 Socket socket = new Socket(serverAddr, 58149);
        	 
		     try {
		    	 
		    	 PrintWriter out = new PrintWriter( new BufferedWriter( new OutputStreamWriter(socket.getOutputStream())),true);
		    	 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		    	 
		    	 out.println("ACK-L9gyYX-CONNECT\r");
		    	 
		    	 Boolean remainConnected = true;

		    	 //Check for received message in SettingsManager class to send to out
		    	 while(remainConnected==true)
		    	 {
		    		 //If there's a message waiting to be sent over socket
		    		 //Check global class
		    		 //Log.d("TCPLoop", "1");
		    		 if(SettingsManager.getMessageReceived()==true)
		    		 {
		    			 Log.d("SMSTether-TCPLoop", "SocketRunner has a message to send.");
		    			 Message[] toTransfer = SettingsManager.getMessages();
		    			 for(int i = 0; i < toTransfer.length; i++)
		    			 {
		    				 Log.d("SMSTether-TCPLoop", "Attempting to send to a message to socket via TCPLoop.");
		    				 out.println("RECEIVE-L9gyYX-" + toTransfer[i].getRecipient() + "-L9gyYX-" + toTransfer[i].getContent() + "\r");
		    			 }
		    			 //Log.d("TCPLoop", "2");
		    			 SettingsManager.setMessageReceived(false);
		    			 SettingsManager.setMessages(null);
		    			 
		    		 }
		    		 
		    		 Thread.sleep(5000);
		    		 
			    	 //if there's socket input
		    		 //Log.d("TCPLoop", "3");
		    		 if (in.ready()) {
		    			 String s = in.readLine();
		    			 if ((s != null) &&  (s.length() != 0)) {
		    				 Log.d("SMSTether-TCPLoop", "Message received: " + s);
		    				 if(s.equals("ACK-L9gyYX-SERVER"))Log.d("SMSTether-TCPLoop", "Server acknowledgement received.");

		    				 if(s.startsWith("SEND-L9gyYX-"))
		    				 {
		    					 //Send a message
		    					 String[] args = s.split("-L9gyYX-");
		    					 //Number is 1
		    					 Log.d("SMSTether-TCPLoop", "Number received: " + args[1]);
		    					 //Message is 2
		    					 Log.d("SMSTether-TCPLoop", "Message received: " + args[2]);
		    					 SmsManager sms = SmsManager.getDefault();
		    					 sms.sendTextMessage(args[1], null, args[2], null, null);  
		    				 }
		    			 }
		    		
		    		 }

                  if(SettingsManager.getWantsDisconnect())
		    		 {
                	  	Log.d("SMSTether-TCPLoop", "Disconnect signal received.");
		    			 remainConnected = false;
		    			 SettingsManager.setWantsDisconnect(false);
		    		 }
		    	 }
		    	 
             } catch(Exception e) {
                 Log.e("SMSTether-TCPLoop", "Exception thrown: ", e);
		      } finally {
		        socket.close();
		        SettingsManager.setConnected(false);
		        Log.d("SMSTether-TCPLoop", "Socket closed and connected:false.");
		      }
         } catch (Exception e) {
			Log.e("SMSTether-TCPLoop", "Exception thrown: ", e);
         }
    }
}
