package com.notifications.app.controllers;

import org.springframework.batch.core.Job;


import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.notifications.app.dto.GenerateNotificationsRequestDTO;

import com.notifications.app.exceptions.ObjectNotFoundException;

import com.notifications.app.services.CategoryService;
import com.notifications.app.services.NotificationJobSevice;

import jakarta.validation.Valid;



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
    
   
    
	@PostMapping("/generate-notifications")
    public ResponseEntity<String> processNotifications(@Valid @RequestBody GenerateNotificationsRequestDTO generateNotificationsRequestDTO) throws MethodArgumentNotValidException {
    
    	
    	try {
    		notificationJobSevice.runUsersSetup(generateNotificationsRequestDTO.getCategoryId(),generateNotificationsRequestDTO.getMessage());
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
