//
//  ReceiverManager.java
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

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

public class ReceiverManager extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d("SMSTether-Receiver", "Incoming SMS detected via broadcast.");
        Bundle bundle = intent.getExtras();        
        SmsMessage[] msgs = null;
        Message[] messages = null;
         
        if (bundle != null)
        {
            Object[] pdus = (Object[]) bundle.get("pdus");
            msgs = new SmsMessage[pdus.length];
            messages = new Message[msgs.length];
            for (int i=0; i<msgs.length; i++){
                msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);                
                messages[i] = new Message(msgs[i].getOriginatingAddress(),msgs[i].getMessageBody().toString());
                Log.d("SMSTether-Receiver", "Message content iterated.");
            }
        }
        SettingsManager.setMessages(messages);
        SettingsManager.setMessageReceived(true);
        Log.d("SMSTether-Receiver", "messages[0] is " + messages[0].getContent());
	}
}
