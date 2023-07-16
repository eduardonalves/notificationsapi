package com.notifications.app;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.notifications.app.controllers.NotificationsController;
import com.notifications.app.dto.GenerateNotificationsRequestDTO;
import com.notifications.app.services.NotificationJobSevice;
import com.notifications.app.services.UserService;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class NotificationControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;
    
    @Mock
    private NotificationJobSevice notificationJobSevice;

    @InjectMocks
    private NotificationsController notificationsController;
    
  
    
    private Long categoryId;
    private String message;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(notificationsController).build();
        
    	categoryId = 1L;
    	message= "Test message";
    }

    @Test
    public void ShouldProcessNotifications() throws Exception {
        
        GenerateNotificationsRequestDTO requestDTO = new GenerateNotificationsRequestDTO(categoryId, message);
       
        
        Mockito.doNothing().when(notificationJobSevice).runUsersSetup(categoryId, message);
  
           
        mockMvc.perform(post("/notifications/generate-notifications")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(content().string("Success."));
        
    }
    
    @Test
    public void ShoulNotdProcessNotifications() throws Exception {
       
        GenerateNotificationsRequestDTO requestDTO = new GenerateNotificationsRequestDTO(categoryId, "");
       
        Exception exception = new RuntimeException("Test exception");
        doThrow(exception).when(notificationJobSevice).runUsersSetup(categoryId, "");
        
             
        mockMvc.perform(post("/notifications/generate-notifications")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(requestDTO)))
                .andExpect(status().isBadRequest());
                
    }
    
    @Test
    public void ShouldSendSMSNotifications() throws Exception {
        
        Mockito.doNothing().when(notificationJobSevice).sendSMSNotification();
  
		mockMvc.perform(get("/notifications/send-sms-notification"))
		        .andExpect(status().isOk())
		        .andExpect(content().string("Success."));
		
		Mockito.verify(notificationJobSevice, Mockito.times(1)).sendSMSNotification();

    }
    
    @Test
    public void ShouldSendEmailNotifications() throws Exception {
        
        Mockito.doNothing().when(notificationJobSevice).sendEmailNotification();
  
		mockMvc.perform(get("/notifications/send-email-notification"))
		        .andExpect(status().isOk())
		        .andExpect(content().string("Success."));
		
		Mockito.verify(notificationJobSevice, Mockito.times(1)).sendEmailNotification();

    }
    
    @Test
    public void ShouldSendPushNotifications() throws Exception {
        
        Mockito.doNothing().when(notificationJobSevice).sendPushNotification();
  
		mockMvc.perform(get("/notifications/send-push-notification"))
		        .andExpect(status().isOk())
		        .andExpect(content().string("Success."));
		
		Mockito.verify(notificationJobSevice, Mockito.times(1)).sendPushNotification();

    }

    // Helper method to convert objects to JSON string
    private String asJsonString(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }
}