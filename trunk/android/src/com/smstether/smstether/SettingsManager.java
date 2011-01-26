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
