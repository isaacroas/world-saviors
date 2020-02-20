package com.saviors.domain;

public class SseMessage {
	
	private String text;
	
	public SseMessage(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
}
