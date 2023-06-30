package com.notificatios.app.controllers;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.notificatios.app.models.User;
import com.notificatios.app.dto.UserDTO;
import com.notificatios.app.exceptions.ObjectNotFoundException;
import com.notificatios.app.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	 @GetMapping("/list")
	 public ResponseEntity<List<UserDTO>> list() {
		 List<User> users = userService.findAll();
		 List<UserDTO> usersDTO = new ArrayList<>();
		 users.stream().forEach(user-> usersDTO.add(new UserDTO(user)));
		 return ResponseEntity.ok().body(usersDTO);
	 }
	 
	 @GetMapping("/view/{id}")
	 public ResponseEntity<UserDTO> view(@PathVariable(value = "id") Long id) throws ObjectNotFoundException {
		 User user = userService.findById(id);
		 return ResponseEntity.ok().body(new UserDTO(user));
	 }
	 
	 @PostMapping("/add")
	 public ResponseEntity<User> add(@Valid @RequestBody UserDTO userDTO) throws MethodArgumentNotValidException {
		 User user = userService.fromDTO(userDTO);
		 user=userService.createIfNotExist(user);
		 return new ResponseEntity<>(user, HttpStatus.CREATED);
	 }
	 
	@PutMapping("/edit/{id}")
	public ResponseEntity<Void> update(@PathVariable(value = "id") Long id, @Valid @RequestBody UserDTO userDTO) throws ObjectNotFoundException{
		User user = userService.fromDTO(userDTO);
		userService.updateUser(id, user);
		return ResponseEntity.noContent().build();
	}
	 
	@DeleteMapping("/delete/{id}")
	public Map<String, Boolean>  delete(@PathVariable(value="id") Long id) throws ObjectNotFoundException{
		userService.deleteUser(id);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
}


