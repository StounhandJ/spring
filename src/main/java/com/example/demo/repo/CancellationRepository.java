package com.example.demo.repo;

import com.example.demo.models.Cancellation;
import org.springframework.data.repository.CrudRepository;

public interface CancellationRepository extends CrudRepository<Cancellation, Long> {


}