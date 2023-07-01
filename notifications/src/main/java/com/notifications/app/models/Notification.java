package com.notifications.app.models;


import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;



@Entity
@Table(name = "notificaitons")
public class Notification {
	
	
	
	public Notification() {
		super();
	}

	public Notification(Long id, String message, Category category, NotificationsType notificationsType, User user) {
		super();
		this.id = id;
		this.message = message;
		this.category = category;
		this.notificationsType = notificationsType;
		this.user = user;
	}

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@Column(name = "message", nullable = false)
    private String message;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "notifications_type_id")
    private NotificationsType notificationsType;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public NotificationsType getNotificationsType() {
		return notificationsType;
	}

	public void setNotificationsType(NotificationsType notificationsType) {
		this.notificationsType = notificationsType;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public int hashCode() {
		return Objects.hash(category, id, message, notificationsType, user);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Notification other = (Notification) obj;
		return Objects.equals(category, other.category) && Objects.equals(id, other.id)
				&& Objects.equals(message, other.message) && Objects.equals(notificationsType, other.notificationsType)
				&& Objects.equals(user, other.user);
	}

	@Override
	public String toString() {
		return "Notification [id=" + id + ", message=" + message + ", category=" + category + ", notificationsType="
				+ notificationsType + ", user=" + user + "]";
	}
    
    
}
