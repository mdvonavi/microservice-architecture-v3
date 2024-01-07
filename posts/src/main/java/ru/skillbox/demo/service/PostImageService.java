package ru.skillbox.demo.service;

import io.minio.MinioClient;
import io.minio.ObjectWriteResponse;
import io.minio.PutObjectArgs;
import io.minio.errors.ErrorResponseException;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InvalidResponseException;
import io.minio.errors.XmlParserException;
import io.minio.errors.InternalException;
import io.minio.errors.ServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Service
public class PostImageService {

    @Autowired
    private MinioClient minioClient;

    @Value("${minio.bucketName}")
    private String bucketName;

    public ObjectWriteResponse put(MultipartFile file)
            throws
            IOException,
            InsufficientDataException,
            ErrorResponseException,
            NoSuchAlgorithmException,
            InvalidKeyException,
            InvalidResponseException,
            XmlParserException,
            InternalException,
            ServerException {

        try (InputStream stream = file.getInputStream()) {
            return minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(file.getOriginalFilename())
                            .stream(stream, file.getSize(), -1)
                            .build()
            );
        }

    }
}
