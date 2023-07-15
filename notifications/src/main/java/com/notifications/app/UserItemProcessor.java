package com.notifications.app;

import java.util.ArrayList;
import java.util.List;


import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.notifications.app.models.Category;
import com.notifications.app.models.Notification;
import com.notifications.app.models.NotificationsType;
import com.notifications.app.models.User;
import com.notifications.app.services.CategoryService;
import com.notifications.app.services.NotificationService;



@Component
@Scope(value = "step")
public class UserItemProcessor implements ItemProcessor<User, User> {
	
	@Value("#{jobParameters['message']}")
	private String messageParam;
	@Autowired
	
	@Value("#{jobParameters['category_id']}")
	private Long categoryIdParam;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private NotificationService notificationService;
    @Override
    public User process(User item) throws Exception {
       
    	
	  	System.out.println("processing...");
	  	
		List<NotificationsType> chanels = new ArrayList<>();
		chanels = item.getChannels();
		//@TODO GET category_id FROM JOB PARAMETER
		Category category = categoryService.findById(categoryIdParam);
		
		
		
		for (NotificationsType chanel : chanels) {
	       System.out.println(chanel);
	       Notification newNotification = new Notification();
	       newNotification.setCategory(category);
	       newNotification.setMessage(messageParam);
	       newNotification.setNotificationsType(chanel);
	       newNotification.setUser(item);
	       notificationService.create(newNotification);
	    }
	    return item;
    }
}
