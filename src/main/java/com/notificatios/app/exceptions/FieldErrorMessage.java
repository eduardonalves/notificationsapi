package com.notificatios.app.exceptions;

import java.io.Serializable;

public class FieldErrorMessage implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String fieldName;
	private String message;
	public FieldErrorMessage() {
		
	}
	public FieldErrorMessage(String fieldName, String message) {
		super();
		this.fieldName = fieldName;
		this.message = message;
	}
	public String getFildName() {
		return fieldName;
	}
	public void setFildName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
