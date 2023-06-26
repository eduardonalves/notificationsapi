package com.notificatios.app.dto;

import java.io.Serializable;

import com.notificatios.app.models.NotificationsType;

import jakarta.validation.constraints.NotEmpty;

public class NotificationsTypeDTO implements Serializable {

	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
	 
	 @NotEmpty(message="Required field.")
	 private String type;

	public NotificationsTypeDTO(NotificationsType  notificationsTyp) {
		this.id = notificationsTyp.getId();
		this.type = notificationsTyp.getType();
	}

	public NotificationsTypeDTO() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	 
	 
	 
}