package com.notificatios.app.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.notificatios.app.models.Category;
import com.notificatios.app.models.User;
import com.notificatios.app.repositories.UserRepository;

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
}
