package com.notifications.app;


import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


import com.notifications.app.models.Notification;
import com.notifications.app.services.PushNotificationService;




@Component
@Scope(value = "step")
public class PushItemProcessor implements ItemProcessor<Notification, Notification> {
	
	@Autowired
	private PushNotificationService pushNotificationService;
    @Override
    public Notification process(Notification item) throws Exception {
	  	System.out.println("processing push notifications...");
	  	System.out.println(item);
	  	pushNotificationService.sendNofification(item);
	    return item;
    }
}
