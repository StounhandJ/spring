package com.example.demo.models;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="author_id")
    public Author author;

    @NotBlank
    @Size(min = 1, max = 70)
    public String name;

    @ManyToMany
    @JoinTable (name="genres_books",
            joinColumns=@JoinColumn (name="genre_id"),
            inverseJoinColumns=@JoinColumn(name="book_id"))
    private Set<Book> books;

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
