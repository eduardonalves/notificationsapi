package com.notificatios.app;

import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.notificatios.app.models.Category;
import com.notificatios.app.models.NotificationsType;
import com.notificatios.app.models.User;
import com.notificatios.app.services.CategoryService;
import com.notificatios.app.services.NotificationsTypeService;
import com.notificatios.app.services.UserService;


@SpringBootApplication
@EnableBatchProcessing
public class NotificatiosApplication implements CommandLineRunner  {
	
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	NotificationsTypeService notificationsTypeService;
	
	@Autowired
	UserService userService;
	
	public static void main(String[] args) {
		SpringApplication.run(NotificatiosApplication.class, args);
	}
	
	
	@Override
	public void run(String... args) throws Exception {
		System.out.println("Creating Basic Data");
		
		Category category1 = new Category();
		category1.setName("Sports");
		category1 = categoryService.createIfNotExist(category1);
		
		Category category2 = new Category();
		category2.setName("Finance");
		category2 =categoryService.createIfNotExist(category2);
		
		Category category3 = new Category();
		category3.setName("Films");
		category3 = categoryService.createIfNotExist(category3);
		
		
		NotificationsType notificationsType1 = new NotificationsType();
		notificationsType1.setType("SMS");
		notificationsType1 = notificationsTypeService.createIfNotExist(notificationsType1);
		
		
		NotificationsType notificationsType2 = new NotificationsType();
		notificationsType2.setType("E-mail");
		notificationsType2 = notificationsTypeService.createIfNotExist(notificationsType2);
		
		NotificationsType notificationsType3 = new NotificationsType();
		notificationsType3.setType("Push Notificacion");
		notificationsType3 =notificationsTypeService.createIfNotExist(notificationsType3);
		
		User user1 = new User();
		user1.setName("Michael Jacson");
		user1.setEmail("mjacson@gmail.com");
		user1.setPhone("888888888888");
		
		List<Category> subscribed1 = new ArrayList<>();
		subscribed1.add(category1);
		subscribed1.add(category2);
		user1.setSubscribed(subscribed1);
		
		List<NotificationsType> chanels1 = new ArrayList<>();
		chanels1.add(notificationsType1);
		chanels1.add(notificationsType2);
		user1.setChannels(chanels1);
		userService.createIfNotExist(user1);
		
		
		
		User user2 = new User();
		user2.setName("Rocky Balboa");
		user2.setEmail("rbalboa@gmail.com");
		user2.setPhone("998888888888");
		
		List<Category> subscribed2 = new ArrayList<>();
		subscribed2.add(category1);
		subscribed2.add(category3);
		user2.setSubscribed(subscribed2);
		
		List<NotificationsType> chanels2 = new ArrayList<>();
		chanels2.add(notificationsType2);
		chanels2.add(notificationsType3);
		user2.setChannels(chanels2);
		userService.createIfNotExist(user2);
		
		
		User user3 = new User();
		user3.setName("Ringo Star");
		user3.setEmail("rstar@gmail.com");
		user3.setPhone("9777888888");
		
		List<Category> subscribed3 = new ArrayList<>();
		subscribed3.add(category1);
		subscribed3.add(category3);
		user3.setSubscribed(subscribed3);
		
		List<NotificationsType> chanels3 = new ArrayList<>();
		chanels3.add(notificationsType1);
		chanels3.add(notificationsType3);
		user3.setChannels(chanels3);
		userService.createIfNotExist(user3);
		
		
	}

}
