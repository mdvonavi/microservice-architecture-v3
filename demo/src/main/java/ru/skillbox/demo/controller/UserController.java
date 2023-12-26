package ru.skillbox.demo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.demo.UserConvertor;
import ru.skillbox.demo.entity.User;
import ru.skillbox.demo.service.UserService;

import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/users")
public class UserController {
    private final UserConvertor userConvertor = new UserConvertor();
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    String createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @GetMapping(path="/{id}")
    String getUser(@PathVariable long id){
        return userService.getUser(id).toString();
    }

    @PutMapping(path="/{id}")
    String updateUser(@RequestBody User user, @PathVariable long id){
        return userService.updateUser(user, id);
    }

    @DeleteMapping(path="/{id}")
    String deleteUser(@PathVariable long id){
        return userService.deleteUser(id);
    }

    @GetMapping
    public String getUsers() throws JsonProcessingException {
        return userService.getUsers()
                .stream()
                .map(userConvertor::convertToDto)
                .collect(Collectors.toList())
                .toString();
    }


}
