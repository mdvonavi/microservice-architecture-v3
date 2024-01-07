package ru.skillbox.demo;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MappConfig {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
