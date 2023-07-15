package com.notifications.app.services;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class NotificationJobSevice {
	
	
	Long categoryId;
	String message;
	
	@Autowired
	@Qualifier("notificationJob")
    private  Job notificationJob;
	
	
	@Autowired 
	@Qualifier("sMSNotificationJob")
	private Job sMSNotificationJob;
	
	
	@Autowired 
	@Qualifier("emailNotificationJob")
	private Job emailNotificationJob;
	
	@Autowired 
	@Qualifier("pushNotificationJob")
	private Job pushNotificationJob;
	
	
	
	
	@Autowired
    JobLauncher jobLauncher;
	
	
	public void runUsersSetup(Long categoryId, String message) throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
		JobParameters jobParameters = new JobParametersBuilder()
        		.addLong("category_id", categoryId)
        		.addString("message", message)
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();
        jobLauncher.run(notificationJob, jobParameters);
	}
	
	public void sendSMSNotification() throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
		JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();
        jobLauncher.run(sMSNotificationJob, jobParameters);
	}
	
	public void sendEmailNotification() throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
		JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();
        jobLauncher.run(emailNotificationJob, jobParameters);
	}
	
	public void sendPushNotification() throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
		JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();
        jobLauncher.run(pushNotificationJob, jobParameters);
	}
}
