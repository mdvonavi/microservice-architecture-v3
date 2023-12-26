package ru.skillbox.demo;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.skillbox.demo.dto.UserDto;
import ru.skillbox.demo.entity.User;

@Component
public class UserConvertor {

    private final ModelMapper modelMapper;

    public UserConvertor() {
        this.modelMapper = new ModelMapper();
    }

    public UserDto convertToDto(User entity){
        return modelMapper.map(entity, UserDto.class);
    }
}