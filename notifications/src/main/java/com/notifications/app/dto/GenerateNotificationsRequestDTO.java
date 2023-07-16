package com.notifications.app.dto;

import java.io.Serializable;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class GenerateNotificationsRequestDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@NotNull(message="Required field.")
	private Long categoryId;
	
	@NotBlank(message="Required field.")
	private String message;

	public GenerateNotificationsRequestDTO(@NotEmpty(message = "Required field.") Long categoryId,
			@NotEmpty(message = "Required field.") String message) {
		super();
		this.categoryId = categoryId;
		this.message = message;
	}

	public GenerateNotificationsRequestDTO() {
        
    }


	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
