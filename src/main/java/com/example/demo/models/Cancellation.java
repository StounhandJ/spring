package com.example.demo.models;


import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class Cancellation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;

    @NotNull
    @Min(value = 0L)
    public int count;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    public Date date;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    public MedicalPreparations medicalPreparations;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public MedicalPreparations getMedicalPreparations() {
        return medicalPreparations;
    }

    public void setMedicalPreparations(MedicalPreparations medicalPreparations) {
        this.medicalPreparations = medicalPreparations;
    }
}
