package ru.skillbox.demo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "Создание пользователя")
    @PostMapping
    String createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @Operation(summary = "Получение пользователя")
    @GetMapping(path = "/{id}")
    String getUser(@PathVariable long id) {
        return userService.getUser(id).toString();
    }

    @Operation(summary = "Обновление пользователя")
    @PutMapping(path = "/{id}")
    String updateUser(@RequestBody User user, @PathVariable long id) {
        return userService.updateUser(user, id);
    }

    @Operation(summary = "Удаление пользователя")
    @DeleteMapping(path = "/{id}")
    String deleteUser(@PathVariable long id) {
        return userService.deleteUser(id);
    }

    @Operation(summary = "Получение списка пользователей")
    @GetMapping
    public String getUsers() throws JsonProcessingException {
        return userService.getUsers()
                .stream()
                .map(userConvertor::convertToDto)
                .collect(Collectors.toList())
                .toString();
    }

    @Operation(summary = "Удаление ВСЕХ пользователей")
    @DeleteMapping(path = "/all")
    public void deleteAllUsers() {
        userService.deleteAllUsers();
    }

//    @Operation(summary = "Подписка на пользователя")
//    @PostMapping(path = "/{id}/subscribe/{id}")
//    public void subscribe(@PathVariable long user, @PathVariable long subs) {
//        userService.subscribe(user, subs);
//    }

//    @Operation(summary = "Удаление подписки на пользователя")
//    @DeleteMapping(path = "/{id}/unsubscribe/{id}")
//    public void unsubscribe(@PathVariable long user, @PathVariable long subs) {
//        userService.unsubscribe(user, subs);
//    }

//    @Operation(summary = "Все подписки на пользователя")
//    @GetMapping(path = "/{id}/subscribers")
//    public List<String> getSubscribers(@PathVariable long user) {
//        return userService.getSubscribers(user);
//    }


}
