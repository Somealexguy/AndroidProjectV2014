package com.nicklase.bilteori.logic;

import com.nicklase.bilteori.gui.SettingsActivity;

import android.content.Intent;
import android.net.MailTo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class Email {

	private String fromEmail;
	private String[] toEmail;
	private String subject;
	private String textMessage;
	private String[] headers;
	/// <summary>
	/// Constructor for email class
	/// </summary>
	public Email( String[] toEmail, String subject,
			String textMessage) {
		super();
		this.toEmail = toEmail;
		this.subject = subject;
		this.textMessage = textMessage;
	}
	/// <summary>
	/// Constructor for email class
	/// </summary>
	public Email(String[] toEmail, String subject) {
		super();
		this.toEmail = toEmail;
		this.subject = subject;
	}
	/// <summary>
	/// Constructor for email class
	/// </summary>
	public Email(String subject,
			String textMessage) {
		super();
		this.subject = subject;
		this.textMessage = textMessage;
	}


	public String getFromEmail() {
		return fromEmail;
	}

	public String[] getToEmail() {
		return toEmail;
	}

	public String getSubject() {
		return subject;
	}

	public String getTextMessage() {
		return textMessage;
	}

	public void setTextMessage(String textMessage) {
		this.textMessage = textMessage;
	}

	public String[] getHeaders() {
		return headers;
	}

	public void setHeaders(String[] headers) {
		this.headers = headers;
	}



}
