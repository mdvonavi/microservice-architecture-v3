package ru.skillbox.demo.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skillbox.demo.PostConvertor;
import ru.skillbox.demo.entity.Post;
import ru.skillbox.demo.service.PostService;

import java.util.List;

@RestController
@RequestMapping(value = "/posts")
public class PostController {
    private final PostService postService;
    private final PostConvertor postConvertor = new PostConvertor();

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @Operation(summary = "Создание поста")
    @PostMapping(headers = "Content-Type= multipart/form-data")
    String createPost(@RequestParam("post") String postString, @RequestParam(value = "imageFiles", required = false) List<MultipartFile> imageFiles) {

        Post post = postConvertor.convertToPost(postString);

        return postService.savePost(post, imageFiles);
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

    @Operation(summary = "Удаление поста")
    @DeleteMapping(path="/{id}")
    String deletePost(@PathVariable long id){
        return postService.deletePost(id);
    }

    @Operation(summary = "Обновление поста")
    @PutMapping(path="/{id}")
    String updatePost(@RequestParam("post") String postString, @RequestParam(value = "imageFiles", required = false) List<MultipartFile> imageFiles){
        return postService.updatePost(postString, imageFiles);
    }
}
