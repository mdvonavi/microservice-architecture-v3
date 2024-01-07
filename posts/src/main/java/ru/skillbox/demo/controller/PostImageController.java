package ru.skillbox.demo.controller;

import io.minio.ObjectWriteResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.skillbox.demo.service.PostImageService;

@RestController
@RequestMapping(value = "/images")
public class PostImageController {
    private final PostImageService postImageService;

    public PostImageController(PostImageService postImageService) {
        this.postImageService = postImageService;
    }

    @Operation(summary = "Загрузка файла в хранилище")
    @PostMapping
    ResponseEntity uploadFile(@RequestParam("file")MultipartFile file) {
        try {
            ObjectWriteResponse response = postImageService.put(file);
            return ResponseEntity.ok(response.etag());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error uploading file");
        }
    }

}
