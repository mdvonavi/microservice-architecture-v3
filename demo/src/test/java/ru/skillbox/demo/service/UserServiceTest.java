package ru.skillbox.demo.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.server.ResponseStatusException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import ru.skillbox.demo.entity.User;
import ru.skillbox.demo.repository.UserRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@ContextConfiguration(initializers = PostgreSQLInitializer.class)
class UserServiceTest {

    private MockMvc mockMvc;

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

    @BeforeEach
    public void setUp(WebApplicationContext context) {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    void createUser() {
        //given

        Mockito.when(userRepository.save(user)).thenReturn(savedUser);

        //when
        String result = userService.createUser(user);

        //then
        assertEquals(
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
        assertEquals(
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
        assertEquals(
                "User Ivanof updated with id = 1",
                result
        );
    }

    @Test
    void updateUserNotFound() {
        //given
        Mockito.when(userRepository.save(updatedUser)).thenReturn(updatedUser);
        Mockito.when(userRepository.existsById(1L)).thenThrow(ResponseStatusException.class);

        //when
        Executable executable = () -> userService.updateUser(updatedUser, 1L);

        //then
        assertThrows(ResponseStatusException.class, executable);
    }

    @Test
    void updateUserBadRequest() throws Exception {

        Mockito.when(userRepository.existsById(1L)).thenReturn(true);
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(savedUser);

        Map<String,Object> user = new HashMap<>();
        user.put("id",1);
        user.put("firstName","Ivan");
        user.put("lastName","Ivanov");
        user.put("middleName","Ivanovich");
        user.put("sex","true");
        user.put("birthDate","10-08-1990");
        user.put("city",1);
        user.put("avatar","dummyLink");
        user.put("info","some info string");
        user.put("nickname","ivanoff");
        user.put("email","mail@mail.ru");
        user.put("phone","79991234567");

        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(MockMvcRequestBuilders.put("/users/{id}", 2)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());

    }

    @Test
    void deleteUser() {
        //given
        Mockito.when(userRepository.existsById(1L)).thenReturn(true);

        //when
        userService.deleteUser(1L);

        //then
        assertEquals(
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
        assertEquals(
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
        assertEquals(
                "[" +
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
                        "}" +
                        "]",
                result.toString()
        );
    }
}