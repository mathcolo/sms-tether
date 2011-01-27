//
//  SettingsManager.java
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

public class SettingsManager {

	private static String hostname = "";
	private static Boolean messageReceived = false;
	private static Message[] messages = null;
	
	private static Boolean wantsDisconnect = false;
	private static Boolean connected = false;
	
	public static String getHostname() {
		return hostname;
	}
	public static void setHostname(String hostname) {
		SettingsManager.hostname = hostname;
	}
	public static Boolean getMessageReceived() {
		return messageReceived;
	}
	public static void setMessageReceived(Boolean messageReceived) {
		SettingsManager.messageReceived = messageReceived;
	}
	public static Message[] getMessages() {
		return messages;
	}
	public static void setMessages(Message[] messages) {
		SettingsManager.messages = messages;
	}
	public static Boolean getWantsDisconnect() {
		return wantsDisconnect;
	}
	public static void setWantsDisconnect(Boolean wantsDisconnect) {
		SettingsManager.wantsDisconnect = wantsDisconnect;
	}
	public static Boolean getConnected() {
		return connected;
	}
	public static void setConnected(Boolean connected) {
		SettingsManager.connected = connected;
	}

	
}
