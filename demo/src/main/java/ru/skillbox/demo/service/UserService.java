package ru.skillbox.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.skillbox.demo.entity.User;
import ru.skillbox.demo.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String createUser(User user) {
        User savedUser = userRepository.save(user);
        return String.format("User %s added with id = %s", savedUser.getLastName(), savedUser.getId());
    }

    public User getUser(long id) {
        return userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    }

    public String updateUser(User user, long id) {
        if (user.getId() != id) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    String.format("user id %s not equal to path id %s", user.getId(), id)
            );
        }

        if (!userRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        User updatedUser = userRepository.save(user);
        return String.format("User %s updated with id = %s", updatedUser.getLastName(), updatedUser.getId());
    }

    public String deleteUser(long id) {
        if (!userRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        userRepository.deleteById(id);
        return String.format("User deleted with id = %s", id);
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public void deleteAllUsers() {
        userRepository.deleteAll();
    }

    public void subscribe(long user, long subs) {
    }

    public void unsubscribe(long user, long subs) {
    }

    public List<String> getSubscribers(long user) {
        return new ArrayList<>();
    }
}
