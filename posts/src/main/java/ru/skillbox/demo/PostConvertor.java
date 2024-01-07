package ru.skillbox.demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.skillbox.demo.entity.Post;

@Service
public class PostConvertor {
    private static final Logger log = LoggerFactory.getLogger("application");

    private final ObjectMapper objectMapper;

    public PostConvertor(ModelMapper modelMapper, ObjectMapper objectMapper) {

        this.objectMapper = objectMapper;
    }

    public Post convertToPost(String post) throws JsonProcessingException {

        Post out = objectMapper.readValue(post, Post.class);
        return out;
    }
}
