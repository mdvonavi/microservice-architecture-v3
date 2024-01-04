package ru.skillbox.demo.repository;

import org.springframework.data.repository.CrudRepository;
import ru.skillbox.demo.entity.Post;

import java.util.List;

public interface PostRepository extends CrudRepository<Post, Long> {
    List<Post> findAll();
    List<Post> findByUserId(Long userId);
}
