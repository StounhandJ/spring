package com.example.demo.models;


import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @NotBlank
    @Size(min = 1)
    private String text;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    public Date application_date;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    public Date date_of_treatment;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    public User client;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    public User formalizing_doctor;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    public User attending_doctor;

    @OneToMany(mappedBy = "application", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<PaidTreatment> paidTreatments = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getApplication_date() {
        return application_date;
    }

    public void setApplication_date(Date application_date) {
        this.application_date = application_date;
    }

    public Date getDate_of_treatment() {
        return date_of_treatment;
    }

    public void setDate_of_treatment(Date date_of_treatment) {
        this.date_of_treatment = date_of_treatment;
    }

    public User getClient() {
        return client;
    }

    public void setClient(User client) {
        this.client = client;
    }

    public User getFormalizing_doctor() {
        return formalizing_doctor;
    }

    public void setFormalizing_doctor(User formalizing_doctor) {
        this.formalizing_doctor = formalizing_doctor;
    }

    public User getAttending_doctor() {
        return attending_doctor;
    }

    public void setAttending_doctor(User attending_doctor) {
        this.attending_doctor = attending_doctor;
    }

    public String[] cvs() {
        return new String[]{
                id.toString(),
                text
        };
    }

    public static Application cvsToModel(List<String> data) {
        Application ap = new Application();
        ap.setId(Long.parseLong(data.get(0), 10));
        ap.setText(data.get(1));
        return ap;
    }
}
