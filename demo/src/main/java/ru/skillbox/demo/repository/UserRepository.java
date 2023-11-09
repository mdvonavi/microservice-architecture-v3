package ru.skillbox.demo.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import ru.skillbox.demo.entity.User;

public interface UserRepository extends CrudRepository<User, Long> {
    List<User> findByLastName(String lastName);
    List<User> findAll();
}
