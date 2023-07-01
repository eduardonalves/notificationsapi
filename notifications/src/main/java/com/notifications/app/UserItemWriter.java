package com.notifications.app;

import java.util.List;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.notifications.app.models.User;
import com.notifications.app.repositories.NotificationRepository;

@Component
public class UserItemWriter implements ItemWriter<User>{
	@Autowired
    private NotificationRepository notificationRepository;

    
    public void write(List<? extends User> user) throws Exception {
        // 
    	System.out.println("Saving");
    	
       // notificationRepository.saveAll(notifications);
    }


	@Override
	public void write(Chunk<? extends User> chunk) throws Exception {
		// TODO Auto-generated method stub
		System.out.println(chunk);
		System.out.println("writing");
	}

	
}