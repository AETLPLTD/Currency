package com.example.currency.exception;

import java.util.Date;

public class ErrorDetails {

	private String message;
	private String details;

	public ErrorDetails(Date timestamp, String message, String details) {
		super();

		this.message = message;

	}



	public String getMessage() {
		return message;
	}


}

