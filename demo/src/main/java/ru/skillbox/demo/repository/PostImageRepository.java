package ru.skillbox.demo.repository;

import org.springframework.data.repository.CrudRepository;
import ru.skillbox.demo.entity.PostImage;

import java.util.List;

public interface PostImageRepository extends CrudRepository<PostImage, Long> {
    List<PostImage> findByPostId(Long userId);
}
