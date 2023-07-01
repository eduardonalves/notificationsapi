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
    
   
    
    @GetMapping("/process")
    public ResponseEntity<String> processNotifications(@Valid @NotNull(message="Field category_id is required.") @RequestParam("category_id") Long  category_id,
    		@Valid @NotBlank(message="Field message is required.")  @RequestParam("message") String message) throws ObjectNotFoundException {
    	Category category = categoryService.findById(category_id);
    	
    	
    	try {
            JobParameters jobParameters = new JobParametersBuilder()
            		.addLong("category_id", category.getId())
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
