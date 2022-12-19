package com.example.demo.repo;

import com.example.demo.models.PaidTreatment;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PaidTreatmentRepository extends CrudRepository<PaidTreatment, Long> {

    @Modifying
    @Query(value = "SELECT paid_treatment.* FROM paid_treatment JOIN application ON application.id=paid_treatment.application_id WHERE application.client_id=?;", nativeQuery = true)
    List<PaidTreatment> findByApplicationClient_Id(long id);

}