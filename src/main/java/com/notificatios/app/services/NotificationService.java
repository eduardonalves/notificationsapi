package com.notificatios.app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.notificatios.app.dto.CategoryDTO;
import com.notificatios.app.dto.NotificationDTO;
import com.notificatios.app.dto.NotificationsTypeDTO;
import com.notificatios.app.dto.UserDTO;
import com.notificatios.app.models.Category;
import com.notificatios.app.models.Notification;
import com.notificatios.app.models.NotificationsType;
import com.notificatios.app.models.User;
import com.notificatios.app.repositories.NotificationRepository;

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
	
}
