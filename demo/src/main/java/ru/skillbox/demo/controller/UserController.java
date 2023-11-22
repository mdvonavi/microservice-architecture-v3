package ru.skillbox.demo.controller;

import org.springframework.web.bind.annotation.*;
import ru.skillbox.demo.entity.User;
import ru.skillbox.demo.service.UserService;

@RestController
@RequestMapping(value = "/users")
public class UserController {

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
    String getUsers(){
        return userService.getUsers();
    }
}
