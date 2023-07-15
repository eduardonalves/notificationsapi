package com.notifications.app.controllers;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.notifications.app.exceptions.ObjectNotFoundException;
import com.notifications.app.models.Category;
import com.notifications.app.services.CategoryService;
import com.notifications.app.services.NotificationJobSevice;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Validated
@RestController
@RequestMapping("/notifications")
public class NotificationsController {
	
	@Autowired
    JobLauncher jobLauncher;

    
	@Autowired
    private  Job notificationJob;
	
	
	
    
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	NotificationJobSevice notificationJobSevice;
    
   
    
    @GetMapping("/generate-notifications")
    public ResponseEntity<String> processNotifications(@Valid @NotNull(message="Field category_id is required.") @RequestParam("category_id") Long  category_id,
    		@Valid @NotBlank(message="Field message is required.")  @RequestParam("message") String message) throws ObjectNotFoundException {
    	Category category = categoryService.findById(category_id);
    	
    	
    	try {
    		notificationJobSevice.runUsersSetup(category_id,message);
            return ResponseEntity.ok("Success.");
        } catch (Exception e) {
        	System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error. " + e.getMessage());
        }
    }
    
    @GetMapping("/send-sms-notification")
    public ResponseEntity<String> sendSMS() throws ObjectNotFoundException {
    	
    	try {
    		notificationJobSevice.sendSMSNotification();
            return ResponseEntity.ok("Success.");
        } catch (Exception e) {
        	System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error. " + e.getMessage());
        }
    }
    
    @GetMapping("/send-email-notification")
    public ResponseEntity<String> sendEmails() throws ObjectNotFoundException {
    	
    	try {
    		notificationJobSevice.sendEmailNotification();
            return ResponseEntity.ok("Success.");
        } catch (Exception e) {
        	System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error. " + e.getMessage());
        }
    }
    @GetMapping("/send-push-notification")
    public ResponseEntity<String> sendPush() throws ObjectNotFoundException {
    	
    	try {
    		notificationJobSevice.sendPushNotification();
            return ResponseEntity.ok("Success.");
        } catch (Exception e) {
        	System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error. " + e.getMessage());
        }
    }
}
