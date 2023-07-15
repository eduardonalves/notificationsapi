package com.notifications.app;

import java.util.ArrayList;
import java.util.List;


import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.SimpleStepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.PlatformTransactionManager;

import com.notifications.app.models.Category;
import com.notifications.app.models.Notification;
import com.notifications.app.models.NotificationsType;
import com.notifications.app.models.User;
import com.notifications.app.repositories.CategoryRepository;
import com.notifications.app.services.CategoryService;
import com.notifications.app.services.NotificationService;
import com.notifications.app.services.NotificationsTypeService;
import com.notifications.app.services.UserService;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private UserService userService;
    
    @Autowired
    private CategoryService categoryService;
    
    @Autowired
    private NotificationsTypeService notificationsTypeService;
    
    @Autowired
    private NotificationService notificationService;

    @Autowired
    private JobRepository jobRepository;
    
    //@Autowired
    //private DataSource dataSource;

    @Autowired
    private JobLauncher jobLauncher;
    
    @Autowired
    private PlatformTransactionManager transactionManager;
     
    
    @Bean
    public JobBuilderFactory jobBuilderFactory(JobRepository jobRepository) {
        return new JobBuilderFactory(jobRepository);
        
    }
    @Bean
    public StepBuilderFactory stepBuilderFactory(JobRepository jobRepository) {
        return new StepBuilderFactory(jobRepository);
    }

    

    @Bean
    @StepScope
    public ItemReader<User> itemReader(@Value("#{jobParameters['category_id']}")Long category_id,@Value("#{jobParameters['message']}")String message) {
 
    	if(category_id != null) {
    		Category category = categoryService.findById(category_id);
        	
        	List<User> users = userService.findAllBySubscribed(category);
        	return new ListItemReader<>(users);
    	}else {
    		List<User> users = new ArrayList<>();
    		return new ListItemReader<>(users);
    	}
    }

    @Bean
    @StepScope
    public ItemProcessor<User, User> processor() {
    	
    	
        return new UserItemProcessor();
    }
    
    

    @Bean
    public ItemWriter<User> writer() {
    	
        return new UserItemWriter();
    }

    @Bean
    public Step step1(ItemReader<User> reader, ItemProcessor<User, User> processor,
            ItemWriter<User> writer) {
    	
        return stepBuilderFactory.get("step1")
                .<User, User> chunk(100)
                .reader(reader)
                .transactionManager(transactionManager)
                .processor(processor)
                .writer(writer)
                .build();
    }
    @Bean(name ="notificationJob")
    @Primary
    public Job notificationJob(Step step1) {
    	
        return jobBuilderFactory.get("notificationJob")
                .incrementer(new RunIdIncrementer())
                .flow(step1)
                .end()
                .build();
    }
    /*SMS Job*/
    
    @Bean
    @StepScope
    public ItemReader<Notification> sMSitemReader() {
    	Long id = 1L;
    	NotificationsType notificationsType =notificationsTypeService.findById(id);
    	List<Notification> notifications = notificationService.findAllByNotificationsType(notificationsType);	
    	return new ListItemReader<>(notifications);
    }
    
    @Bean
    @StepScope
    public ItemProcessor<Notification, Notification> sMSProcessor() {
    	
        return new SMSItemProcessor();
    }
    @Bean
    public ItemWriter<Notification> sMSWriter() {
    	
        return new SMSItemWriter();
    }
    
    @Bean
    public Step step2() {
     	
    	return stepBuilderFactory.get("step2")
                .<Notification, Notification> chunk(100)
                .reader(sMSitemReader())
                .transactionManager(transactionManager)
                .processor(sMSProcessor())
                .writer(sMSWriter())
                .build();
    }

    
    
    @Bean(name ="sMSNotificationJob")
    public Job sMSNotificationJob(Step step2) {
    	 return jobBuilderFactory.get("sMSNotificationJob")
                 .incrementer(new RunIdIncrementer())
                 .flow(step2)
                 .end()
                 .build(); 
    }
    /*Email Job*/
    
    @Bean
    @StepScope
    public ItemReader<Notification> emailItemReader() {
    	Long id = 2L;
    	NotificationsType notificationsType =notificationsTypeService.findById(id);
    	List<Notification> notifications = notificationService.findAllByNotificationsType(notificationsType);	
    	return new ListItemReader<>(notifications);
    }
    
    @Bean
    @StepScope
    public ItemProcessor<Notification, Notification> emailProcessor() {
    	
        return new EmailItemProcessor();
    }
    @Bean
    public ItemWriter<Notification> emailWriter() {
    	
        return new EmailItemWriter();
    }
    
    @Bean
    public Step step3() {
     	
    	return stepBuilderFactory.get("step3")
                .<Notification, Notification> chunk(100)
                .reader(emailItemReader())
                .transactionManager(transactionManager)
                .processor(emailProcessor())
                .writer(emailWriter())
                .build();
    }

    
    
    @Bean(name ="emailNotificationJob")
    public Job emailNotificationJob(Step step3) {
    	 return jobBuilderFactory.get("emailNotificationJob")
                 .incrementer(new RunIdIncrementer())
                 .flow(step3)
                 .end()
                 .build(); 
    }
    
    /*Push Job*/
    
    @Bean
    @StepScope
    public ItemReader<Notification> pushItemReader() {
    	Long id = 3L;
    	NotificationsType notificationsType =notificationsTypeService.findById(id);
    	List<Notification> notifications = notificationService.findAllByNotificationsType(notificationsType);	
    	return new ListItemReader<>(notifications);
    }
    
    @Bean
    @StepScope
    public ItemProcessor<Notification, Notification> pushProcessor() {
    	
        return new PushItemProcessor();
    }
    @Bean
    public ItemWriter<Notification> pushWriter() {
    	
        return new PushItemWriter();
    }
    
    @Bean
    public Step step4() {
     	
    	return stepBuilderFactory.get("step4")
                .<Notification, Notification> chunk(100)
                .reader(pushItemReader())
                .transactionManager(transactionManager)
                .processor(pushProcessor())
                .writer(pushWriter())
                .build();
    }

    
    
    @Bean(name ="pushNotificationJob")
    public Job pushNotificationJob(Step step4) {
    	 return jobBuilderFactory.get("pushNotificationJob")
                 .incrementer(new RunIdIncrementer())
                 .flow(step4)
                 .end()
                 .build(); 
    }
}