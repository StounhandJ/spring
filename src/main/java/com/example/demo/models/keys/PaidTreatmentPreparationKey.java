package com.example.demo.models.keys;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class PaidTreatmentPreparationKey implements Serializable {

    @Column(name = "paid_treatment_id")
    Long paidTreatmentId;

    @Column(name = "medical_preparation_id")
    Long medicalPreparationId;

    public Long getPaidTreatmentId() {
        return paidTreatmentId;
    }

    public void setPaidTreatmentId(Long paidTreatmentId) {
        this.paidTreatmentId = paidTreatmentId;
    }

    public Long getMedicalPreparationId() {
        return medicalPreparationId;
    }

    public void setMedicalPreparationId(Long medicalPreparationId) {
        this.medicalPreparationId = medicalPreparationId;
    }
}
