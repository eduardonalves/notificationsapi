package com.notifications.app;


import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


import com.notifications.app.models.Notification;
import com.notifications.app.services.EmailService;




@Component
@Scope(value = "step")
public class EmailItemProcessor implements ItemProcessor<Notification, Notification> {
	
	@Autowired
	private EmailService emailService;
    @Override
    public Notification process(Notification item) throws Exception {
       
    	
	  	System.out.println("processing email...");
	  	System.out.println(item);
	  	emailService.sendNofification(item);
	    return item;
    }
}
