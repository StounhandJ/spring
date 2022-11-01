package com.example.demo.repo;

import com.example.demo.models.Application;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ApplicationRepository extends CrudRepository<Application, Long> {

    List<Application> findByClient_Id(long id);

}