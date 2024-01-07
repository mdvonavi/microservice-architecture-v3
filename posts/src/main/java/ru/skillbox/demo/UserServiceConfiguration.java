package ru.skillbox.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserServiceConfiguration {
    @Value("${microservice.users.host}")
    private String userServiceUrl;

    @Bean
    public UserHandler userHandler(){
        return new UserHandler(userServiceUrl);
    }
}
