package com.notifications.app.dto;


import java.io.Serializable;
import java.util.List;

import com.notifications.app.models.Category;
import com.notifications.app.models.NotificationsType;
import com.notifications.app.models.User;

import jakarta.validation.constraints.NotEmpty;



public class UserDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	@NotEmpty(message="Required field.")
    private String name;

	@NotEmpty(message="Required field.")
    private String email;
	
	@NotEmpty(message="Required field.")
    private String phone;
    
    private List<Category> subscribed;
    
    private List<NotificationsType> channels;
    
    
	public UserDTO(User user) {
		this.id = user.getId();
		this.name = user.getName();
		this.email = user.getEmail();
		this.phone = user.getPhone();
		this.channels = user.getChannels();
		this.subscribed = user.getSubscribed();
	}
	
	public UserDTO() {
		
	}
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public List<Category> getSubscribed() {
		return subscribed;
	}

	public void setSubscribed(List<Category> subscribed) {
		this.subscribed = subscribed;
	}

	public List<NotificationsType> getChannels() {
		return channels;
	}

	public void setChannels(List<NotificationsType> channels) {
		this.channels = channels;
	}
    

}

