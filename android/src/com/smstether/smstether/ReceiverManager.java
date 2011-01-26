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
