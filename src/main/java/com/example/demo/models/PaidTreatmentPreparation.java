package com.example.demo.models;

import com.example.demo.models.keys.PaidTreatmentPreparationKey;

import javax.persistence.*;

@Entity
public class PaidTreatmentPreparation {
    @EmbeddedId
    PaidTreatmentPreparationKey id;

    @ManyToOne
    @MapsId("paidTreatment")
    @JoinColumn(name = "paid_treatment_id")
    PaidTreatment paidTreatment;

    @ManyToOne
    @MapsId("medicalPreparation")
    @JoinColumn(name = "medical_preparation_id")
    MedicalPreparations medicalPreparation;

    @Column(name = "count")
    int count;

    public PaidTreatmentPreparationKey getId() {
        return id;
    }

    public void setId(PaidTreatmentPreparationKey id) {
        this.id = id;
    }

    public PaidTreatment getPaidTreatment() {
        return paidTreatment;
    }

    public void setPaidTreatment(PaidTreatment paidTreatment) {
        this.paidTreatment = paidTreatment;
    }

    public MedicalPreparations getMedicalPreparation() {
        return medicalPreparation;
    }

    public void setMedicalPreparation(MedicalPreparations medicalPreparation) {
        this.medicalPreparation = medicalPreparation;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
