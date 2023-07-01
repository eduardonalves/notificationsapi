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
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import com.notifications.app.models.Category;
import com.notifications.app.models.Notification;
import com.notifications.app.models.User;
import com.notifications.app.repositories.CategoryRepository;
import com.notifications.app.services.CategoryService;
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
    	
    	Category category = categoryService.findById(category_id);
    	
    	List<User> users = userService.findAllBySubscribed(category);
    	
        
        return new ListItemReader<>(users);
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

    @Bean
    public Job notificationJob(Step step1) {
    	
        return jobBuilderFactory.get("notificationJob")
                .incrementer(new RunIdIncrementer())
                .flow(step1)
                .end()
                .build();
    }
}