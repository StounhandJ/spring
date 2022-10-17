package com.example.demo.repo;

import com.example.demo.models.Author;
import com.example.demo.models.Post;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AuthorRepository extends CrudRepository<Author, Long> {

    List<Author> findByNameContains(String name);

    List<Author> findByName(String name);

}
