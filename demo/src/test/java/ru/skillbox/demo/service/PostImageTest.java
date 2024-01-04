package ru.skillbox.demo.service;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@ContextConfiguration(initializers = {PostgreSQLInitializer.class, MinioInitializer.class})
public class PostImageTest {

    private static final Logger log = LoggerFactory.getLogger("application");

    private MockMvc mockMvc;

    @Autowired
    private MinioClient minioClient;

    @BeforeEach
    public void setUp(WebApplicationContext context) {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void createBucketTest() throws Exception {
        minioClient.ignoreCertCheck();
        String bucketName = "test1";
        minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        Assertions.assertTrue(minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build()));
    }
}
