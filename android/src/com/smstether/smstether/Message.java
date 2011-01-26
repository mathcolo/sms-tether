package com.smstether.smstether;

public class Message {

	private String recipient;
	private String content;
	
	public Message()
	{
		this.recipient = "";
		this.content = "";
	}
	
	public Message(String recipient, String content)
	{
		this.recipient = recipient;
		this.content = content;
	}

	public String getRecipient() {
		return recipient;
	}

	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
}
