package com.notificatios.app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.notificatios.app.models.Category;
import com.notificatios.app.models.NotificationsType;
import com.notificatios.app.repositories.NotificationsTypeRepository;

@Service
public class NotificationsTypeService {
	@Autowired
	private NotificationsTypeRepository notificationsTypeRepository;
		
	public NotificationsType create(NotificationsType notificationsType) {
		return notificationsTypeRepository.save(notificationsType);
	}
	public NotificationsType createIfNotExist(NotificationsType notificationsType) {
		
		NotificationsType exitingNotificationsType= notificationsTypeRepository.findOneByType(notificationsType.getType());
		
		if(exitingNotificationsType == null) {
			return notificationsTypeRepository.save(notificationsType);
		}else {
			return exitingNotificationsType;
		}
	}
	
}
