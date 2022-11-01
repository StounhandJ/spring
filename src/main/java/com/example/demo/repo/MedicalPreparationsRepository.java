package com.example.demo.repo;

import com.example.demo.models.MedicalPreparations;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MedicalPreparationsRepository extends CrudRepository<MedicalPreparations, Long> {
    List<MedicalPreparations> findByNameContains(String title);

    List<MedicalPreparations> findByName(String title);
}