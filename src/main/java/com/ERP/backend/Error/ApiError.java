package com.ERP.backend.Error;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class ApiError {
	
	private String error;
	private HttpStatus statusCode;
	private LocalDateTime timeStamp;
	
	public ApiError() { this.timeStamp = LocalDateTime.now(); }
	
	public ApiError(String error, HttpStatus statusCode) {
		this();
		this.error = error;
		this.statusCode = statusCode;
	}
	
}
