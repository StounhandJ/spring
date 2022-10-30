package com.example.demo.repo;

import com.example.demo.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByLogin(String username);

    List<User> findByActive(boolean active);

    default List<User> findActive() {
        return this.findByActive(true);
    }
}
