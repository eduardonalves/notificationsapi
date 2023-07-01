package com.notifications.app.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.notifications.app.dto.UserDTO;
import com.notifications.app.exceptions.ObjectNotFoundException;
import com.notifications.app.models.Category;
import com.notifications.app.models.User;
import com.notifications.app.repositories.UserRepository;

import java.util.Optional;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
		
	public User create(User user) {
		return userRepository.save(user);
	}
	public User createIfNotExist(User user) {
		User exitingUser = userRepository.findOneByName(user.getName());
		if(exitingUser == null) {
			return userRepository.save(user);
		}else {
			return exitingUser;
		}
	}
	
	public List<User> findAllBySubscribed(Category category){
		return userRepository.findAllBySubscribed(category);
	}
	
	public User findById(Long id){
		Optional<User> optionalUser = userRepository.findById(Math.toIntExact(id));
		User user =optionalUser.orElseThrow( () -> new ObjectNotFoundException("User not found: "+ id +" Tipo: " + User.class.getName()));
		return user;
	}
	
	public List<User> findAll(){
		return userRepository.findAll();
	}
	public User updateUser(Long id, User updatedUser) {
		Optional<User> optionalUser = userRepository.findById(Math.toIntExact(id));
		User user =optionalUser.orElseThrow( () -> new ObjectNotFoundException("User not found: "+ id +" User: " + User.class.getName()));
		user.setName(updatedUser.getName());
		user.setEmail(updatedUser.getEmail());
		user.setPhone(updatedUser.getPhone());
		user.setSubscribed(updatedUser.getSubscribed());
		user.setChannels(updatedUser.getChannels());
		userRepository.save(user);
		return user;
	}
	
	public void deleteUser(Long id) {
		Optional<User> optionalUser = userRepository.findById(Math.toIntExact(id));
		User user =optionalUser.orElseThrow( () -> new ObjectNotFoundException("User not found: "+ id +" User: " + User.class.getName()));
		userRepository.delete(user);
	}
	public User fromDTO(UserDTO userDTO) {
		User user = new User();
		user.setId(userDTO.getId());
		user.setName(userDTO.getName());
		user.setEmail(userDTO.getEmail());
		user.setPhone(userDTO.getPhone());
		user.setChannels(userDTO.getChannels());
		user.setSubscribed(userDTO.getSubscribed());
		return user;
	}
	
}





























































































