package com.example.demo.models;

import javax.persistence.*;
import java.sql.Date;
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

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<Book> books = new ArrayList<>();

    public String name;

    public Date birthday;

    public boolean gender;

    public int start_year;

    public double budget;

}
