package com.notifications.app;


import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


import com.notifications.app.models.Notification;
import com.notifications.app.services.CategoryService;
import com.notifications.app.services.SMSService;



@Component
@Scope(value = "step")
public class SMSItemProcessor implements ItemProcessor<Notification, Notification> {
	
	@Autowired
	private SMSService sMSService;
	

    @Override
    public Notification process(Notification item) throws Exception {
       
    	
	  	System.out.println("processing sms...");
	  	System.out.println(item);
	  	
	  	sMSService.sendNofification(item);
	    return item;
    }
}
