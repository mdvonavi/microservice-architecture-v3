package ru.skillbox.demo.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.skillbox.demo.entity.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@ContextConfiguration(initializers = PostgreSQLInitializer.class)
class UserServiceTest {
    private static final Logger log = LoggerFactory.getLogger("application");

    private MockMvc mockMvc;

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    User user = new User(
            "Ivan",
            "Ivanov",
            "Ivanovich",
            true,
            formatter.parse("1990-08-15"),
            1,
            "dummyLink",
            "some info string",
            "ivanoff",
            "mail@mail.ru",
            "79991234567"
    );

    User minUser = new User(
            "ivanoff",
            "mail@mail.ru"
    );

    User savedUser = new User(
            1L,
            "Ivan",
            "Ivanov",
            "Ivanovich",
            true,
            formatter.parse("1990-08-15"),
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
            formatter.parse("1990-08-16"),
            2,
            "dummyLink2",
            "another info string",
            "ivanof",
            "vanya@mail.ru",
            "77771234567"
    );

    UserServiceTest() throws ParseException {
    }

    @BeforeEach
    public void setUp(WebApplicationContext context) {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    void getUsersEmptyList() throws Exception {
        log.info("start getUsersEmptyList test");
        log.info("run get request");
        mockMvc.perform(MockMvcRequestBuilders.get("/users")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("[]"));
    }

    @Test
    void getUsersNotEmptyList() throws Exception {
        log.info("start getUsersNotEmptyList test");
        log.info("run post request");
        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(user.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        log.info("run get request");
        mockMvc.perform(MockMvcRequestBuilders.get("/users")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
//                .andExpect(content().string(String.format("[%s]", savedUser.toString())));
    }

    @Test
    void createUser() throws Exception {
        log.info("start createUser test");
        log.info("run post request");
        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(user.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void minUserTest() throws Exception {
        log.info("start minUserTest test");

        log.info("run post request");
        ResultActions postResult = mockMvc.perform(MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(minUser.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        String savedUserId = postResult.andReturn().getResponse().getContentAsString().split("=")[1].strip();

        log.info("run get request");
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/users/{id}", savedUserId)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        User resultUser = new ObjectMapper().readValue(result.andReturn().getResponse().getContentAsString(), User.class);

        Assertions.assertEquals(resultUser.getEmail(), user.getEmail());
        Assertions.assertEquals(resultUser.getNickname(), user.getNickname());

    }

    @Test
    void getUser() throws Exception {
        log.info("start getUser test");

        log.info("run post request");
        ResultActions postResult = mockMvc.perform(MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(user.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        String savedUserId = postResult.andReturn().getResponse().getContentAsString().split("=")[1].strip();
        savedUser.setId(Long.valueOf(savedUserId));

        log.info("run get request");
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/users/{id}", savedUserId)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
//                .andExpect(content().json(savedUser.toString()));

        User resultUser = new ObjectMapper().readValue(result.andReturn().getResponse().getContentAsString(), User.class);

        Assertions.assertEquals(resultUser.getFirstName(), user.getFirstName());
        Assertions.assertEquals(resultUser.getLastName(), user.getLastName());
        Assertions.assertEquals(resultUser.getMiddleName(), user.getMiddleName());
        Assertions.assertEquals(resultUser.getEmail(), user.getEmail());
        Assertions.assertEquals(resultUser.getPhone(), user.getPhone());
        Assertions.assertEquals(resultUser.getSex(), user.getSex());
        Assertions.assertEquals(resultUser.getNickname(), user.getNickname());
        Assertions.assertEquals(resultUser.getAvatar(), user.getAvatar());
        Assertions.assertEquals(resultUser.getCity(), user.getCity());
        Assertions.assertEquals(resultUser.getInfo(), user.getInfo());
        //Assertions.assertEquals(resultUser.getBirthDate(), user.getBirthDate());

    }

    @Test
    void getUserNotFound() throws Exception {
        log.info("start getUserNotFound test");

        log.info("run get request");
        mockMvc.perform(MockMvcRequestBuilders.get("/users/{id}", 999)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteUserNotFound() throws Exception {
        log.info("start deleteUserNotFound test");

        log.info("run delete request");
        mockMvc.perform(MockMvcRequestBuilders.delete("/users/{id}", 999)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateUser() throws Exception {
        log.info("start updateUser test");

        log.info("run post request");
        ResultActions postResult = mockMvc.perform(MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(user.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        String savedUserId = postResult.andReturn().getResponse().getContentAsString().split("=")[1].strip();
        updatedUser.setId(Long.valueOf(savedUserId));
        log.info("run put request");
        mockMvc.perform(MockMvcRequestBuilders.put("/users/{id}", savedUserId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedUser.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void updateUserNotFound() throws Exception {
        log.info("start updateUserNotFound test");
        Long badUserId = 999L;
        updatedUser.setId(badUserId);

        log.info("run put request");
        mockMvc.perform(MockMvcRequestBuilders.put("/users/{id}", badUserId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedUser.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateUserBadRequest() throws Exception {
        log.info("start updateUserBadRequest test");
        Integer badUserId = 999;

        log.info("run post request");
        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(user.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        log.info("run put request");
        mockMvc.perform(MockMvcRequestBuilders.put("/users/{id}", badUserId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedUser.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteUser() throws Exception {
        log.info("start deleteUser test");

        log.info("run post request");
        ResultActions postResult = mockMvc.perform(MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(user.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        String savedUserId = postResult.andReturn().getResponse().getContentAsString().split("=")[1].strip();

        log.info("run delete request");
        mockMvc.perform(MockMvcRequestBuilders.delete("/users/{id}", savedUserId)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        log.info("run get request");
        mockMvc.perform(MockMvcRequestBuilders.get("/users/{id}", savedUserId)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }


}