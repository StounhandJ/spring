package com.example.demo.repo;

import com.example.demo.models.PaidTreatment;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PaidTreatmentRepository extends CrudRepository<PaidTreatment, Long> {

    List<PaidTreatment> findByApplicationClient_Id(long id);

}