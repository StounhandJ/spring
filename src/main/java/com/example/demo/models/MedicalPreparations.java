package com.example.demo.models;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class MedicalPreparations {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;

    @NotBlank
    @Size(min = 1, max = 70)
    public String name;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    public Shelving shelving;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    public TypePreparation typePreparation;

    @OneToMany(mappedBy = "medicalPreparations", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<Cancellation> cancellations = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Shelving getShelving() {
        return shelving;
    }

    public void setShelving(Shelving shelving) {
        this.shelving = shelving;
    }

    public TypePreparation getTypePreparation() {
        return typePreparation;
    }

    public void setTypePreparation(TypePreparation typePreparation) {
        this.typePreparation = typePreparation;
    }
}
