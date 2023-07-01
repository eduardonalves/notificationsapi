package com.notifications.app.controllers;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.notifications.app.dto.NotificationsTypeDTO;
import com.notifications.app.exceptions.ObjectNotFoundException;
import com.notifications.app.models.NotificationsType;
import com.notifications.app.services.NotificationsTypeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/notifications-type")
public class NotificationsTypeController {
	@Autowired
	NotificationsTypeService notificationsTypeService;
	
	@GetMapping("/list")
	public ResponseEntity<List<NotificationsTypeDTO>> list() {
		List<NotificationsType> notificationsTypes = notificationsTypeService.findAll();
		 List<NotificationsTypeDTO> notificationsTypeDTO = new ArrayList<>();
		 notificationsTypes.stream().forEach(notificationType-> notificationsTypeDTO.add(new NotificationsTypeDTO(notificationType)));
		 return ResponseEntity.ok().body(notificationsTypeDTO);
	}
	
	@GetMapping("/view/{id}")
	public ResponseEntity<NotificationsTypeDTO> view(@PathVariable(value = "id") Long id) throws ObjectNotFoundException {
		NotificationsType notificationsType = notificationsTypeService.findById(id);
		 return ResponseEntity.ok().body(new NotificationsTypeDTO(notificationsType));
	}
	
	 @PostMapping("/add")
	 public ResponseEntity<NotificationsType> add(@Valid @RequestBody NotificationsTypeDTO notificationsTypeDTO) {
		 NotificationsType notificationsType = notificationsTypeService.fromDTO(notificationsTypeDTO);
		 notificationsType=notificationsTypeService.createIfNotExist(notificationsType);
		 return new ResponseEntity<>(notificationsType, HttpStatus.CREATED);
	 }
	 
	@PutMapping("/edit/{id}")
	public ResponseEntity<Void> update(@PathVariable(value = "id") Long id, @Valid @RequestBody NotificationsTypeDTO notificationsTypeDTO) throws ObjectNotFoundException{
		NotificationsType notificationsType = notificationsTypeService.fromDTO(notificationsTypeDTO);
		notificationsTypeService.updateNotificationsType(id, notificationsType);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/delete/{id}")
	public Map<String, Boolean>  delete(@PathVariable(value="id") Long id) throws ObjectNotFoundException{
		notificationsTypeService.deleteNotificationsType(id);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
}
