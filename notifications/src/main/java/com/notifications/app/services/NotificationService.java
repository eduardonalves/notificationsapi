package com.notifications.app.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.notifications.app.dto.CategoryDTO;
import com.notifications.app.dto.NotificationDTO;
import com.notifications.app.dto.NotificationsTypeDTO;
import com.notifications.app.dto.UserDTO;
import com.notifications.app.models.Category;
import com.notifications.app.models.Notification;
import com.notifications.app.models.NotificationsType;
import com.notifications.app.models.User;
import com.notifications.app.repositories.NotificationRepository;

@Service
public class NotificationService {

	@Autowired
	private NotificationRepository notificationRepository;
		
	public Notification create(Notification notification) {
		return notificationRepository.save(notification);
	}
	
	public Notification fromDTO(NotificationDTO notificationDTO) {
		
		CategoryDTO categoryDTO = notificationDTO.getCategoryDTO();
		Category category = new Category(categoryDTO.getId(), categoryDTO.getName());
		
		NotificationsTypeDTO notificationsTypeDTO = notificationDTO.getNotificationsTypeDTO();
		NotificationsType notificationsType = new NotificationsType(notificationsTypeDTO.getId(), notificationsTypeDTO.getType());
		
		UserDTO userDTO =  notificationDTO.getUserDTO();
		User user = new User(userDTO.getId(), userDTO.getName(),userDTO.getEmail(), userDTO.getPhone());
		
		Notification notification = new Notification();
		notification.setCategory(category); 
		notification.setMessage(notification.getMessage());
		notification.setNotificationsType(notificationsType);
		notification.setUser(user);
		
		return notification;
	}
	public List<Notification> findAllByNotificationsType(NotificationsType notificationsType){
		return notificationRepository.findAllByNotificationsType(notificationsType);
	}
}
