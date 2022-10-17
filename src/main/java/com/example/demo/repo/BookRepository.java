package com.example.demo.repo;

import com.example.demo.models.Book;
import com.example.demo.models.Post;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BookRepository extends CrudRepository<Book, Long> {

    List<Book> findByTitleContains(String title);

    List<Book> findByTitle(String title);

}
