package com.example.demo.models;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
public class Warehouse {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;

    @NotBlank
    @Size(min = 1, max = 70)
    public String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
