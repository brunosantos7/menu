package br.com.menu.menudigital.utils;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

public class CustomErrorResponse {
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime timestamp;
    private HttpStatus status;
    private String debugMessage;
    private String message;
    
	public CustomErrorResponse(LocalDateTime timestamp, HttpStatus status, Throwable throwable, String message) {
		super();
		this.timestamp = timestamp;
		this.status = status;
		this.debugMessage = throwable.getLocalizedMessage();
		this.message = message;
		this.timestamp = LocalDateTime.now();
	}
	
	public CustomErrorResponse(HttpStatus status, Throwable throwable, String message) {
		super();
		this.status = status;
		this.debugMessage = throwable.getLocalizedMessage();
		this.message = message;
		this.timestamp = LocalDateTime.now();
	}

	public CustomErrorResponse(LocalDateTime timestamp, HttpStatus status) {
		super();
		this.timestamp = timestamp;
		this.status = status;
		this.timestamp = LocalDateTime.now();
	}

	public CustomErrorResponse(HttpStatus status) {
		super();
		this.status = status;
		this.timestamp = LocalDateTime.now();
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public String getDebugMessage() {
		return debugMessage;
	}

	public void setDebugMessage(String debugMessage) {
		this.debugMessage = debugMessage;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
