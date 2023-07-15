package com.notifications.app.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.notifications.app.models.Notification;
import com.notifications.app.models.NotificationsType;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {
	
	List<Notification> findAllByNotificationsType(NotificationsType notificationsType); 
}
