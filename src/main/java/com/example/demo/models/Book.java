package com.example.demo.models;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
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

    @NotBlank
    @Size(min = 1, max = 70)
    public String title;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    public Author author;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @PastOrPresent
    public Date release_date;

    @NotNull
    public boolean for_sale;

    @NotNull
    @Positive
    public double price;

    public Author getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Date getRelease_date() {
        return release_date;
    }

    public void setRelease_date(Date release_date) {
        this.release_date = release_date;
    }

    public boolean isFor_sale() {
        return for_sale;
    }

    public void setFor_sale(boolean for_sale) {
        this.for_sale = for_sale;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
