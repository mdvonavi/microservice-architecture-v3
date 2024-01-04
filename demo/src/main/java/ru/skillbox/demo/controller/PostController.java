package ru.skillbox.demo.controller;

import com.google.gson.Gson;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skillbox.demo.entity.Post;
import ru.skillbox.demo.service.PostService;

import java.util.List;

@RestController
@RequestMapping(value = "/posts")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @Operation(summary = "Создание поста")
    @PostMapping(headers = "Content-Type= multipart/form-data")
    String createPost(@RequestParam("post") String postString, @RequestParam("imageFiles") List<MultipartFile> imageFiles) {
        Gson g = new Gson();
        Post post = g.fromJson(postString, Post.class);

        return postService.savePostWithImages(post, imageFiles);
    }

    @Operation(summary = "Получение поста по id")
    @GetMapping(path="/{id}")
    String getPost(@PathVariable long id){
        return postService.getPost(id).toString();
    }

    @Operation(summary = "Получение списка постов")
    @GetMapping(path="user/{id}")
    public String getAllPosts(@PathVariable long id)  {
        return postService.getUserPosts(id).toString();
    }
}
