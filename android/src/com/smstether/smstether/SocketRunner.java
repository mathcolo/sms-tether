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
import java.util.Date;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Handler;
import android.telephony.SmsManager;
import android.util.Log;

public class SocketRunner implements Runnable {

	private Handler handler;
	private Activity mainActivity;
	
	public static final String SMS_SENT = "com.smstether.smstether.SMS_SENT_ACTION";
	
	public SocketRunner(Handler handler, Activity mainActivity) {
		super();
		this.handler = handler;
		this.mainActivity = mainActivity;
	}

	
	public void run() {
		try {

			InetAddress serverAddr = InetAddress.getByName(SettingsManager.getHostname());//TCPServer.SERVERIP

			Log.d("SMSTether-TCPLoop", "Connecting to server at address " + serverAddr);
			Socket socket = new Socket(serverAddr, 58149);
			socket.setKeepAlive(true);
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
					
					Boolean stillConnected = true;
					
					
					
					if (in.ready()) {
						String s = in.readLine();
						if ((s != null) &&  (s.length() != 0)) {
							Log.d("SMSTether-TCPLoop", "Message received: " + s);
							if(s.equals("ACK-L9gyYX-SERVER"))Log.d("SMSTether-TCPLoop", "Server acknowledgement received.");
							
							if (s.startsWith("SERVER-L9gyYX-DISCONNECT"))
							{
								//Server disconnected, and sent server disconnect message to phone
								out.println("CLIENT-L9gyYX-DISCONNECT\r");
								stillConnected = false;
								Log.d("SMSTether-TCPLoop", "Socket server disconnecting... stillConnected marked as false");
							}

							if(s.startsWith("SEND-L9gyYX-"))
							{
								//Send a message
								String[] args = s.split("-L9gyYX-");
								//Number is 1
								Log.d("SMSTether-TCPLoop", "Number received: " + args[1]);
								//Message is 2
								Log.d("SMSTether-TCPLoop", "Message received: " + args[2]);
								SmsManager sms = SmsManager.getDefault();
								//Intent pending = new Intent(SMS_SENT);
								//pending.putExtra("args", args);
								//sms.sendTextMessage(args[1], null, args[2], PendingIntent.getBroadcast(mainActivity, 0, pending, PendingIntent.FLAG_ONE_SHOT), null);
								sms.sendTextMessage(args[1], null, args[2], null, null);
								
								/*
								// Register broadcast receivers for SMS sent and delivered intents
						        mainActivity.registerReceiver(new BroadcastReceiver() {
						            @Override
						            public void onReceive(Context context, Intent intent) {
						                String message = null;
						                boolean error = true;
						                
						                String[] args = intent.getStringArrayExtra("args");
						                
						                switch (getResultCode()) {
							                case Activity.RESULT_OK:
							                    message = "Message sent!";
							                    error = false;
							                    
							                    */
							                  //Add the message to the Android messaging provider after it has completed sending
												//This might break in future releases
												ContentValues sendMessageValues = new ContentValues();
												sendMessageValues.put("address", args[1]);
												sendMessageValues.put("date", new Date().getTime());
												sendMessageValues.put("read", Integer.valueOf(1));
												sendMessageValues.put("subject", "");
												sendMessageValues.put("body", args[2]);
												sendMessageValues.put("status", Integer.valueOf(-1));
												sendMessageValues.put("type", Integer.valueOf(2));
												mainActivity.getContentResolver().insert(Uri.parse("content://sms/outbox"), sendMessageValues);
												/*
							                    
							                    break;
							                case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
							                    message = "Error.";
							                    break;
							                case SmsManager.RESULT_ERROR_NO_SERVICE:
							                    message = "Error: No service.";
							                    break;
							                case SmsManager.RESULT_ERROR_NULL_PDU:
							                    message = "Error: Null PDU.";
							                    break;
							                case SmsManager.RESULT_ERROR_RADIO_OFF:
							                    message = "Error: Radio off.";
							                    break;
						                }
						                Log.d("SMSTetherMessageCode", message);

						            }
						        }, new IntentFilter(SMS_SENT));
								*/
								
								
							}
						}

					}
					
					if(SettingsManager.getWantsDisconnect() || !stillConnected || !socket.isBound() || !socket.isConnected() || socket.isClosed())
					{
						Log.d("SMSTether-TCPLoop", "Disconnect signal received.");
						remainConnected = false;
						SettingsManager.setWantsDisconnect(false);
						this.handler.sendEmptyMessage(0);
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