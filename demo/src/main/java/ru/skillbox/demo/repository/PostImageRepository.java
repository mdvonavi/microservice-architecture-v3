package ru.skillbox.demo.repository;

import org.springframework.data.repository.CrudRepository;
import ru.skillbox.demo.entity.PostImage;

public interface PostImageRepository extends CrudRepository<PostImage, Long> {
}
