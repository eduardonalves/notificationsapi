package com.notificatios.app.controllers;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notifications")
public class NotificationsController {
	
	@Autowired
    JobLauncher jobLauncher;

    
	@Autowired
    private  Job notificationJob;
    
    
   
    
    @PostMapping("/process")
    public ResponseEntity<String> processNotifications(@RequestParam("category_id") Long  category_id,
                                                       @RequestParam("message") String message) {
    	try {
            JobParameters jobParameters = new JobParametersBuilder()
            		.addLong("category_id", category_id)
            		.addString("message", message)
                    .addLong("time", System.currentTimeMillis())
                    .toJobParameters();
            jobLauncher.run(notificationJob, jobParameters);
            return ResponseEntity.ok("Success.");
        } catch (Exception e) {
        	System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error. " + e.getMessage());
        }
    }
}
