package ru.skillbox.demo.service;

import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.Testcontainers;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.HttpWaitStrategy;
import org.testcontainers.junit.jupiter.Container;

import java.time.Duration;

public class MinioInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
//    private static final Integer exposedPort = 9000;
//    private static final String accessKey = "12345678";
//    private static final String secretKey = "87654321";

//    @Container
//    private static final GenericContainer<?> minioContainer =
//            new GenericContainer<>(DockerImageName.parse("quay.io/minio/minio"))
//                    .withExposedPorts(exposedPort)
//                    .withEnv("MINIO_ROOT_USER", accessKey)
//                    .withEnv("MINIO_ROOT_PASSWORD", secretKey)
//                    .withCommand("server", "/data")
//                    .withCreateContainerCmdModifier(cmd -> cmd.withHostConfig(
//                            new HostConfig().withPortBindings(new PortBinding(Ports.Binding.bindPort(exposedPort), new ExposedPort(exposedPort)))));
    private static final String ADMIN_ACCESS_KEY = "admin";
    private static final String ADMIN_SECRET_KEY = "12345678";
    private static final String USER_ACCESS_KEY = "bob";
    private static final String USER_SECRET_KEY = "87654321";
    private static final Integer port = 9000;
    private static final String bucketName = "test";

    private static String minioServerUrl;

    private static final Logger log = LoggerFactory.getLogger("application");

    @Container
    private static final GenericContainer<?> minioServer = new GenericContainer("minio/minio:RELEASE.2023-09-04T19-57-37Z")
                .withEnv("MINIO_ACCESS_KEY", ADMIN_ACCESS_KEY)
                .withEnv("MINIO_SECRET_KEY", ADMIN_SECRET_KEY)
                .withCommand("server /data")
                .withExposedPorts(port)
                .waitingFor(new HttpWaitStrategy()
                        .forPath("/minio/health/ready")
                        .forPort(port)
                        .withStartupTimeout(Duration.ofSeconds(10)));


    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        minioServer.start();
        Integer mappedPort = minioServer.getFirstMappedPort();
        Testcontainers.exposeHostPorts(mappedPort);
        String minioServerUrl = String.format("http://%s:%s", minioServer.getHost(), mappedPort);

        TestPropertyValues.of(
                "minio.endpoint=" + minioServerUrl,
                "minio.accessKey=" + ADMIN_ACCESS_KEY,
                "minio.secretKey=" + ADMIN_SECRET_KEY,
                "minio.bucketName=" + bucketName
        ).applyTo(applicationContext.getEnvironment());

        MinioClient client = MinioClient
                .builder()
                .endpoint(minioServerUrl)
                .credentials(ADMIN_ACCESS_KEY, ADMIN_SECRET_KEY)
                .build();
        try{
            client.ignoreCertCheck();
            client.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        }
        catch (Exception e) {
            log.error(e.getMessage());
        }


    }
}
