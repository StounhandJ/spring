package com.example.demo.models;


import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.util.Date;

@Entity
public class PaidTreatment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @NotNull
    public int amount;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    public Application application;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    public TypeTreatment typeTreatment;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @PastOrPresent
    public Date date_of_event;

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
}
