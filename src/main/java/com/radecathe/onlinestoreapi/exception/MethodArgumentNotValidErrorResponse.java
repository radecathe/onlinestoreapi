package com.radecathe.onlinestoreapi.exception;

/**
 * @author Raphael Theodoro
 * @version 1.0.0
 */

public class MethodArgumentNotValidErrorResponse extends CustomErrorResponse {
	private String field;
    private String fieldMessage;
    
    public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getFieldMessage() {
		return fieldMessage;
	}

	public void setFieldMessage(String fieldMessage) {
		this.fieldMessage = fieldMessage;
	}
}