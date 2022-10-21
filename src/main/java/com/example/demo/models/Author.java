package com.example.demo.models;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Author {

    public Author(String name, Date birthday, boolean gender, int start_year, double budget) {
        this.name = name;
        this.birthday = birthday;
        this.gender = gender;
        this.start_year = start_year;
        this.budget = budget;
    }

    public Author() {

    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;

    public Long getId() {
        return id;
    }

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<Book> books = new ArrayList<>();

    @NotNull
    @Size(min = 1, max = 70)
    @NotBlank
    public String name;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @PastOrPresent
    public Date birthday;

    @NotNull
    public boolean gender;

    @NotNull
    public int start_year;

    @NotNull
    public double budget;

    @OneToOne(mappedBy = "author")
    public Genre genre;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public int getStart_year() {
        return start_year;
    }

    public void setStart_year(int start_year) {
        this.start_year = start_year;
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    public String getBirthdayString() {
        return new SimpleDateFormat("yyyy-MM-dd").format(this.birthday);
    }
}
