package com.notifications.app;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;


import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import com.notifications.app.controllers.UserController;
import com.notifications.app.dto.UserDTO;
import com.notifications.app.exceptions.DataIntegrityException;
import com.notifications.app.exceptions.ResourceExceptionHandler;
import com.notifications.app.models.Category;
import com.notifications.app.models.NotificationsType;
import com.notifications.app.models.User;
import com.notifications.app.services.UserService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;
    
    private String name;
    private String email;
    private String phone;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        
        Faker faker = new Faker();
        name = faker.address().firstName();
        email= faker.internet().emailAddress();
        phone = faker.phoneNumber().phoneNumber();
    }

    @Test
    public void testShouldAddUser() throws Exception {
        // Create a fake user DTO
        UserDTO userDTO = new UserDTO(); 
        userDTO.setName(name);
        userDTO.setEmail(email);
        userDTO.setPhone(phone);

        Category category = new Category(1L, "test");
        userDTO.setSubscribed(Arrays.asList(category));

        NotificationsType notificationsType = new NotificationsType(1L, "SMS");
        userDTO.setChannels(Arrays.asList(notificationsType));

        // Create a fake user from the user DTO
        User user = new User();
        
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPhone(userDTO.getPhone());
        user.setSubscribed(userDTO.getSubscribed());
        user.setChannels(userDTO.getChannels());

        when(userService.fromDTO(userDTO)).thenReturn(user);
        when(userService.createIfNotExist(user)).thenReturn(user);
        
        userService.fromDTO(userDTO);
        userService.createIfNotExist(user);

        mockMvc.perform(post("/users/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userDTO)))
                .andExpect(status().isCreated());

        verify(userService, times(1)).createIfNotExist(user);
    }
    
    @Test
    public void testShouldNotAddUser() throws Exception {
        // Create a fake user DTO with empty name
        UserDTO userDTO = new UserDTO(); 
        //userDTO.setName(name);
        userDTO.setEmail(email);
        userDTO.setPhone(phone);

        Category category = new Category(1L, "test");
        userDTO.setSubscribed(Arrays.asList(category));

        NotificationsType notificationsType = new NotificationsType(1L, "SMS");
        userDTO.setChannels(Arrays.asList(notificationsType));

        // Create a fake user from the user DTO
        User user = new User();
        
        //user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPhone(userDTO.getPhone());
        user.setSubscribed(userDTO.getSubscribed());
        user.setChannels(userDTO.getChannels());

        when(userService.fromDTO(userDTO)).thenReturn(user);
        when(userService.createIfNotExist(user)).thenThrow(new DataIntegrityException("Error message"));

        userService.fromDTO(userDTO);
        
        Assertions.assertThrows(DataIntegrityException.class, () -> {
            userService.createIfNotExist(user);
        });
        
        mockMvc.perform(post("/users/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userDTO)))
                .andExpect(status().isBadRequest());
        
        verify(userService, times(1)).createIfNotExist(user);
    }
    @Test
    public void testShouldNotUpdateUser() throws Exception {
        // Create a fake user DTO
        UserDTO userDTO = new UserDTO();
        //userDTO.setName(name);
        userDTO.setEmail(email);
        userDTO.setPhone(phone);

        Category category = new Category(1L, "test");
        userDTO.setSubscribed(Arrays.asList(category));

        Long userId = 1L;
        Long nofificationId= 1L;
        NotificationsType notificationsType = new NotificationsType(nofificationId, "SMS");
        userDTO.setChannels(Arrays.asList(notificationsType));

        // Create a fake user from the user DTO
        User user = new User();
        user.setId(userId);
        //user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPhone(userDTO.getPhone());
        user.setSubscribed(userDTO.getSubscribed());
        user.setChannels(userDTO.getChannels());

        

        when(userService.fromDTO(userDTO)).thenReturn(user);
        when(userService.findById(userId)).thenReturn(user);
        when(userService.updateUser(anyLong(), any(User.class))).thenThrow(new DataIntegrityException("Error message"));
        
        
        
        userService.fromDTO(userDTO);
        userService.findById(userId);
        
        
        Assertions.assertThrows(DataIntegrityException.class, () -> {
            userService.updateUser(userId,user);
        });

        mockMvc.perform(put("/users/edit/{id}", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userDTO)))
                .andExpect(status().isBadRequest());

        verify(userService, times(1)).updateUser(anyLong(), any(User.class));

    }
    
    @Test
    public void testShouldUpdateUser() throws Exception {
        // Create a fake user DTO
        UserDTO userDTO = new UserDTO();
        userDTO.setName(name);
        userDTO.setEmail(email);
        userDTO.setPhone(phone);

        Category category = new Category(1L, "test");
        userDTO.setSubscribed(Arrays.asList(category));

        Long userId = 1L;
        Long nofificationId= 1L;
        NotificationsType notificationsType = new NotificationsType(nofificationId, "SMS");
        userDTO.setChannels(Arrays.asList(notificationsType));

        // Create a fake user from the user DTO
        User user = new User();
        user.setId(userId);
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPhone(userDTO.getPhone());
        user.setSubscribed(userDTO.getSubscribed());
        user.setChannels(userDTO.getChannels());

        

        when(userService.fromDTO(userDTO)).thenReturn(user);
        when(userService.findById(userId)).thenReturn(user);
        when(userService.updateUser(anyLong(), any(User.class))).thenReturn(user);
        
        userService.fromDTO(userDTO);
        userService.findById(userId);
        userService.updateUser(userId,user);

        mockMvc.perform(put("/users/edit/{id}", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userDTO)))
                .andExpect(status().isNoContent());

        verify(userService, times(1)).updateUser(anyLong(), any(User.class));
    }
    
    @Test
    public void testShouldDeleteUser() throws Exception {
    	
    	Long userId = 1L;
        Mockito.doNothing().when(userService).deleteUser(userId);
        
        Map<String, Boolean> expectedResponse = new HashMap<>();
        expectedResponse.put("deleted", true);
        Map<String, Boolean> actualResponse = userController.delete(userId);
        
        verify(userService).deleteUser(userId);
        
        Assertions.assertEquals(expectedResponse, actualResponse);
    }

    // Helper method to convert objects to JSON string
    private String asJsonString(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }
}