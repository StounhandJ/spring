package com.example.demo.models;


import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class Entrance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @NotNull
    @Min(value = 0L)
    public int count;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    public Date date_of_receipt;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    public Date expiration_date;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    public MedicalPreparations medicalPreparations;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Date getDate_of_receipt() {
        return date_of_receipt;
    }

    public void setDate_of_receipt(Date date_of_receipt) {
        this.date_of_receipt = date_of_receipt;
    }

    public Date getExpiration_date() {
        return expiration_date;
    }

    public void setExpiration_date(Date expiration_date) {
        this.expiration_date = expiration_date;
    }

    public MedicalPreparations getMedicalPreparations() {
        return medicalPreparations;
    }

    public void setMedicalPreparations(MedicalPreparations medicalPreparations) {
        this.medicalPreparations = medicalPreparations;
    }
}
