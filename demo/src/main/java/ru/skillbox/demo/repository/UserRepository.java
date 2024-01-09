package ru.skillbox.demo.repository;

import org.springframework.data.repository.CrudRepository;
import ru.skillbox.demo.entity.User;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {

    List<User> findByLastName(String lastName);

    List<User> findAll();

}
