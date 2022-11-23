package com.example.demo.repo;

import com.example.demo.models.Cheque;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ChequeRepository extends CrudRepository<Cheque, Long> {

    List<Cheque> findByPaidTreatmentApplicationClient_Id(long id);

}