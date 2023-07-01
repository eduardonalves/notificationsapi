package com.notifications.app.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.notifications.app.dto.NotificationsTypeDTO;
import com.notifications.app.exceptions.ObjectNotFoundException;
import com.notifications.app.models.NotificationsType;
import com.notifications.app.repositories.NotificationsTypeRepository;

import jakarta.validation.Valid;

@Service
public class NotificationsTypeService {
	@Autowired
	private NotificationsTypeRepository notificationsTypeRepository;
		
	public NotificationsType create(NotificationsType notificationsType) {
		return notificationsTypeRepository.save(notificationsType);
	}
	
	public List<NotificationsType> findAll() {		
		return notificationsTypeRepository.findAll();
	}
	
	public NotificationsType findById(Long id) {
		Optional<NotificationsType> notificationsType =notificationsTypeRepository.findById(Math.toIntExact(id));
		return notificationsType.orElseThrow( () -> new ObjectNotFoundException("NotificationsType not found: "+ id +" NotificationsType: " + NotificationsType.class.getName()));
	}
	
	public NotificationsType createIfNotExist(NotificationsType notificationsType) {
		
		NotificationsType exitingNotificationsType= notificationsTypeRepository.findOneByType(notificationsType.getType());
		
		if(exitingNotificationsType == null) {
			return notificationsTypeRepository.save(notificationsType);
		}else {
			return exitingNotificationsType;
		}
	}
	
	public void updateNotificationsType(Long id, NotificationsType notificationsTypeUpdated) {
		Optional<NotificationsType> optionalNotificationsType = notificationsTypeRepository.findById(Math.toIntExact(id));
		NotificationsType notificationsType =optionalNotificationsType.orElseThrow( () -> new ObjectNotFoundException("NotificationsType not found: "+ id +" Category: " + NotificationsType.class.getName()));
		notificationsType.setType(notificationsTypeUpdated.getType());
		notificationsTypeRepository.save(notificationsType);
		
	}
	
	
	public NotificationsType fromDTO(@Valid NotificationsTypeDTO notificationsTypeDTO) {
		NotificationsType notificationsType = new NotificationsType(notificationsTypeDTO.getId(),notificationsTypeDTO.getType() );
		return notificationsType;
	}

	public void deleteNotificationsType(Long id) {
		Optional<NotificationsType> optionalCategory = notificationsTypeRepository.findById(Math.toIntExact(id));
		NotificationsType notificationsType =optionalCategory.orElseThrow( () -> new ObjectNotFoundException("notificationsType not found: "+ id +" notificationsType: " + NotificationsType.class.getName()));
		notificationsTypeRepository.delete(notificationsType);
		
	}
	
	
}
