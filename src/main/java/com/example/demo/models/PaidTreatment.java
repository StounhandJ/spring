package com.example.demo.models;


import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
public class PaidTreatment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @NotNull
    @Min(value = 0L)
    public int amount;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @FutureOrPresent
    public Date date_of_event;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    public Application application;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    public TypeTreatment typeTreatment;

    @OneToMany(mappedBy = "paidTreatment")
    Set<PaidTreatmentPreparation> medicalPreparations;

    @OneToMany(mappedBy = "paidTreatment", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<Cheque> cheques = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public TypeTreatment getTypeTreatment() {
        return typeTreatment;
    }

    public void setTypeTreatment(TypeTreatment typeTreatment) {
        this.typeTreatment = typeTreatment;
    }

    public Date getDate_of_event() {
        return date_of_event;
    }

    public void setDate_of_event(Date date_of_event) {
        this.date_of_event = date_of_event;
    }

    public Set<PaidTreatmentPreparation> getMedicalPreparations() {
        return medicalPreparations;
    }

    public void setMedicalPreparations(Set<PaidTreatmentPreparation> medicalPreparations) {
        this.medicalPreparations = medicalPreparations;
    }

    public boolean IsFullyPaid(){
        int chequeSum = 0;

        for (Cheque cheque:cheques) {
            chequeSum+=cheque.getAmount();
        }
        return chequeSum>=this.amount;
    }
}
