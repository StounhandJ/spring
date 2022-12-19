package com.example.demo.repo;

import com.example.demo.models.Cheque;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ChequeRepository extends CrudRepository<Cheque, Long> {

    @Modifying
    @Query(value = "SELECT cheque.* FROM cheque JOIN paid_treatment ON paid_treatment.id=cheque.paid_treatment_id JOIN application ON application.id=paid_treatment.application_id WHERE application.client_id=?;", nativeQuery = true)
    List<Cheque> findByPaidTreatmentApplicationClient_Id(long id);

}