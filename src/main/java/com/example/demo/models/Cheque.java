package com.example.demo.models;


import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Entity
public class Cheque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @NotNull
    public int amount;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @PastOrPresent
    public Date date;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    public Application application;

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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public String[] cvs() {
        return new String[]{
                id.toString(),
                String.valueOf(amount),
                date.toString(),
                application.id.toString()
        };
    }

    public static Cheque cvsToModel(List<String> data) {
        Cheque ch = new Cheque();
        ch.setId(Long.parseLong(data.get(0), 10));
        ch.setAmount(Integer.parseInt(data.get(1)));
        LocalDate l = DateTimeFormatter.ofPattern("yyyy-M-dd hh:mm:ss.S").parse(data.get(2), LocalDate::from);
        ch.setDate(Date.from(l.atStartOfDay(ZoneId.systemDefault()).toInstant()));

        Application ap = new Application();
        ap.setId(Long.parseLong(data.get(3), 10));
        ch.setApplication(ap);
        return ch;
    }
}
