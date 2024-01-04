package ru.skillbox.demo.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockPart;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.skillbox.demo.entity.Post;
import ru.skillbox.demo.entity.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@ContextConfiguration(initializers = {PostgreSQLInitializer.class, MinioInitializer.class})

public class PostServiceTest {
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

    Post post = new Post(
            "some title",
            "some descriptions"
            );



    public PostServiceTest() throws ParseException {
    }

    @BeforeEach
    public void setUp(WebApplicationContext context) {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    void createPost() throws Exception {
        log.info("start createPost test");

        Long savedUserId = createTestUser();
        Long savedPostId = createTestPost(savedUserId);
    }

    @Test
    void getPost() throws Exception {
        log.info("start getPost test");

        Long savedUserId = createTestUser();
        Long savedPostId = createTestPost(savedUserId);

        log.info("try to get post");
        log.info("run get request");
        mockMvc.perform(MockMvcRequestBuilders.get("/posts/{id}", savedPostId)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void createPostUnknownUser() throws Exception {
        log.info("create new post with user no exist");

        post.setUserId(999L);
        post.setTimestamp(new Date());

        log.info("run post request");
        MockPart postPart = new MockPart("post", post.toString().getBytes());
        MockMultipartFile file = new MockMultipartFile(
                "imageFiles",
                "test_image.jpg",
                MediaType.IMAGE_JPEG_VALUE,
                (byte[]) null
        );
        mockMvc.perform(multipart("/posts")
                .file(file)
                .part(postPart)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllPostForUser() throws Exception {
        log.info("start getAllPostForUser test");

        Long savedUserId = createTestUser();
        Long savedPostId = createTestPost(savedUserId);

        log.info("try to get all posts");
        log.info("run get request");
        mockMvc.perform(MockMvcRequestBuilders.get("/posts/user/{id}", savedUserId)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    private Long createTestUser() throws Exception {
        log.info("create user for test");
        log.info("run post request");

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(user.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        return Long.parseLong(result.andReturn().getResponse().getContentAsString().split("=")[1].strip());
    }

    private Long createTestPost(Long savedUserId) throws Exception {
        log.info("create post for test");

        post.setUserId(savedUserId);
        post.setTimestamp(new Date());

        MockPart postPart = new MockPart("post", post.toString().getBytes());
        MockMultipartFile file = new MockMultipartFile(
                "imageFiles",
                "test_image.jpg",
                MediaType.IMAGE_JPEG_VALUE,
                (byte[]) null
        );

        log.info("run post request");
        ResultActions result = mockMvc.perform(multipart("/posts")
                .file(file)
                .part(postPart)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        return Long.parseLong(result.andReturn().getResponse().getContentAsString().split("=")[1].strip());
    }
}

