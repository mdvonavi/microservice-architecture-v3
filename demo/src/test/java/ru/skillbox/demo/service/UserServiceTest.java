package ru.skillbox.demo.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import ru.skillbox.demo.entity.User;
import ru.skillbox.demo.repository.UserRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@ContextConfiguration(initializers = PostgreSQLInitializer.class)
class UserServiceTest {

    SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");

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
            "79991234567"
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
            "79991234567"
    );

    User updatedUser = new User(
            1L,
            "Vanya",
            "Ivanof",
            "Ivanovi4",
            false,
            formatter.parse("10.08.19911"),
            2,
            "dummyLink2",
            "another info string",
            "ivanof",
            "vanya@mail.ru",
            "77771234567"
    );

    UserRepository userRepository = Mockito.mock(UserRepository.class);

    UserService userService = new UserService(userRepository);

    UserServiceTest() throws ParseException {
    }

    @Test
    void createUser() {
        //given

        Mockito.when(userRepository.save(user)).thenReturn(savedUser);

        //when
        String result = userService.createUser(user);

        //then
        Assertions.assertEquals(
                "User Ivanov added with id = 1",
                result
        );


    }

    @Test
    void getUser() {
        //given
        Mockito.when(userRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(savedUser));

        //when
        User user = userService.getUser(1L);

        //then
        Assertions.assertEquals(
                "{" +
                        "id:1," +
                        "sex:true," +
                        "birthDate:10-08-1990," +
                        "city:1," +
                        "avatar:dummyLink," +
                        "info:some info string," +
                        "firstName:Ivan," +
                        "lastName:Ivanov," +
                        "middleName:Ivanovich," +
                        "nickname:ivanoff," +
                        "email:mail@mail.ru," +
                        "phone:79991234567" +
                        "}",
                user.toString()
        );
    }

    @Test
    void updateUser() {
        //given
        Mockito.when(userRepository.save(updatedUser)).thenReturn(updatedUser);
        Mockito.when(userRepository.existsById(1L)).thenReturn(true);

        //when
        String result = userService.updateUser(updatedUser, 1L);

        //then
        Assertions.assertEquals(
                "User Ivanof updated with id = 1",
                result
        );
    }

    @Test
    void deleteUser() {
        //given
        Mockito.when(userRepository.existsById(1L)).thenReturn(true);

        //when
        userService.deleteUser(1L);

        //then
        Assertions.assertEquals(
                "User Ivanof updated with id = 1",
                "User Ivanof updated with id = 1"
        );
    }

    @Test
    void getUsersEmptyList() {
        //given
        List<User> userList = new ArrayList<>();

        Mockito.when(userRepository.findAll()).thenReturn(userList);

        //when
        List<User> result = userService.getUsers();

        //then
        Assertions.assertEquals(
                "[]",
                result.toString()
        );
    }

    @Test
    void getUsersNotEmptyList() {
        //given
        List<User> userList = new ArrayList<>();
        userList.add(user);

        Mockito.when(userRepository.findAll()).thenReturn(userList);

        //when
        List<User> result = userService.getUsers();

        //then
        Assertions.assertEquals(
                "[" +
                        "{" +
                        "id:null," +
                        "sex:true," +
                        "birthDate:10-08-1990," +
                        "city:1," +
                        "avatar:dummyLink," +
                        "info:some info string," +
                        "firstName:Ivan," +
                        "lastName:Ivanov," +
                        "middleName:Ivanovich," +
                        "nickname:ivanoff," +
                        "email:mail@mail.ru," +
                        "phone:79991234567" +
                        "}" +
                        "]",
                result.toString()
        );
    }
}