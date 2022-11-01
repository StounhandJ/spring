package com.example.demo.models;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne()
    public User user;

    @NotNull
    @Min(value = 0L)
    public int salary;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    public Date date_of_employment;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    public Date date_of_dismissal;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public Date getDate_of_employment() {
        return date_of_employment;
    }

    public void setDate_of_employment(Date date_of_employment) {
        this.date_of_employment = date_of_employment;
    }

    public Date getDate_of_dismissal() {
        return date_of_dismissal;
    }

    public void setDate_of_dismissal(Date date_of_dismissal) {
        this.date_of_dismissal = date_of_dismissal;
    }
}
