package com.example.demo.models;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Book {
    public Book(String title, Author author, Date release_date, boolean for_sale, double price) {
        this.title = title;
        this.author = author;
        this.release_date = release_date;
        this.for_sale = for_sale;
        this.price = price;
    }

    public Book() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;

    public String title;

    @ManyToOne(fetch = FetchType.EAGER)
    public Author author;

    public Author getAuthor() {
        return author;
    }

    public Date release_date;

    public boolean for_sale;

    public double price;
}
