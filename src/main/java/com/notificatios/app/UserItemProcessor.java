package com.notificatios.app;

import java.util.ArrayList;
import java.util.List;


import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.notificatios.app.models.Category;
import com.notificatios.app.models.Notification;
import com.notificatios.app.models.NotificationsType;
import com.notificatios.app.models.User;
import com.notificatios.app.services.CategoryService;
import com.notificatios.app.services.NotificationService;



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
