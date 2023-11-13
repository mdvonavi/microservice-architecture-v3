package ru.skillbox.demo.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.skillbox.demo.entity.User;
import ru.skillbox.demo.repository.UserRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");


    @Test
    void createUser() throws ParseException {
        //given
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        User user = new User(
                "Ivan",
                "Ivanov",
                "Ivanovich",
                true,
                formatter.parse("10.08.1990"),
                1,
                "dummyLink",
                "some info string",
                "ivanoff",
                "mail@mail.ru",
                "+79991234567"
        );
        User savedUser = new User(
                1L,
                "Ivan",
                "Ivanov",
                "Ivanovich",
                true,
                formatter.parse("10.08.1990"),
                1,
                "dummyLink",
                "some info string",
                "ivanoff",
                "mail@mail.ru",
                "+79991234567"
        );
        Mockito.when(userRepository.save(user)).thenReturn(savedUser);
        UserService userService = new UserService(userRepository);

        //when
        String result = userService.createUser(user);

        //then
        Assertions.assertEquals(
                "User Ivanov added with id = 1",
                result
        );


    }
}