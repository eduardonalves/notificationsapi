package com.notificatios.app.dto;

import java.io.Serializable;

import com.notificatios.app.models.Notification;

import jakarta.validation.constraints.NotEmpty;

public class NotificationDTO  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	@NotEmpty(message="Required field.")
	private String message; 
	
	private CategoryDTO categoryDTO;
	
	private NotificationsTypeDTO notificationsTypeDTO;
	
	private UserDTO userDTO;

	public NotificationDTO(Notification notification) {
		
		this.message = notification.getMessage();
		
		this.categoryDTO =  new CategoryDTO (notification.getCategory());
		this.notificationsTypeDTO = new NotificationsTypeDTO(notification.getNotificationsType());
		this.userDTO = new UserDTO(notification.getUser());
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public CategoryDTO getCategoryDTO() {
		return categoryDTO;
	}

	public void setCategoryDTO(CategoryDTO categoryDTO) {
		this.categoryDTO = categoryDTO;
	}

	public NotificationsTypeDTO getNotificationsTypeDTO() {
		return notificationsTypeDTO;
	}

	public void setNotificationsTypeDTO(NotificationsTypeDTO notificationsTypeDTO) {
		this.notificationsTypeDTO = notificationsTypeDTO;
	}

	public UserDTO getUserDTO() {
		return userDTO;
	}

	public void setUserDTO(UserDTO userDTO) {
		this.userDTO = userDTO;
	}
	
}
