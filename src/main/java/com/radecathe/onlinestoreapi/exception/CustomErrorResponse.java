package com.radecathe.onlinestoreapi.exception;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Raphael Theodoro
 * @version 1.0.0
 */

public class CustomErrorResponse {
	private String timestamp;
    private int status;
    private String error;
    private String message;
    
    @JsonIgnore
    private String trace;

	public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
    
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
    
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    public String getTrace() {
		return trace;
	}

	public void setTrace(String trace) {
		this.trace = trace;
	}
}