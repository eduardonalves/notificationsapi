package com.notificatios.app;

import java.util.ArrayList;
import java.util.List;


import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.notificatios.app.models.Category;
import com.notificatios.app.models.Notification;
import com.notificatios.app.models.NotificationsType;
import com.notificatios.app.models.User;
import com.notificatios.app.services.CategoryService;
import com.notificatios.app.services.NotificationService;



@Component
public class UserItemProcessor implements ItemProcessor<User, User> {
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private NotificationService notificationService;
    @Override
    public User process(User item) throws Exception {
        // Implement your processing logic here
    	System.out.println("processing 1");
    	List<Notification> list = new ArrayList<>();
    	List<NotificationsType> chanels = new ArrayList<>();
    	chanels = item.getChannels();
    	//@TODO GET category_id FROM JOB PARAMETER
    	Category category = categoryService.findById(Math.toIntExact(1));
    	//@TODO GET message FROM JOB PARAMETER
    	String message="Test message";
    	
    	for (NotificationsType chanel : chanels) {
           System.out.println(chanel);
           Notification newNotification = new Notification();
           newNotification.setCategory(category);
           newNotification.setMessage(message);
           newNotification.setNotificationsType(chanel);
           newNotification.setUser(item);
           notificationService.create(newNotification);
        }
        return item;
    }
}
