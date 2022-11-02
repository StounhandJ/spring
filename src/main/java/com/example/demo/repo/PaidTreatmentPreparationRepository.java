package com.example.demo.repo;

import com.example.demo.models.MedicalPreparations;
import com.example.demo.models.PaidTreatmentPreparation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaidTreatmentPreparationRepository extends JpaRepository<PaidTreatmentPreparation, Long> {

    List<PaidTreatmentPreparation> findByMedicalPreparationAndCount(MedicalPreparations medicalPreparation, int count);
}