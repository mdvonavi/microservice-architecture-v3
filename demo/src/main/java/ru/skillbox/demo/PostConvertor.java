package ru.skillbox.demo;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.skillbox.demo.entity.Post;

@Component
public class PostConvertor {
    private final ModelMapper modelMapper;

    public PostConvertor() {
        this.modelMapper = new ModelMapper();
    }

    public Post convertToPost(String post){
        return modelMapper.map(post, Post.class);
    }
}
