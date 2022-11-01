package com.example.demo.repo;

import com.example.demo.models.Shelving;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ShelvingRepository extends CrudRepository<Shelving, Long> {
    List<Shelving> findByNameContains(String title);

    List<Shelving> findByName(String title);
}