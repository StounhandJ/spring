package com.example.demo.models;


import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    public User user;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    public Date date_of_attachment;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    public Date date_detachments;

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

    public Date getDate_of_attachment() {
        return date_of_attachment;
    }

    public void setDate_of_attachment(Date date_of_attachment) {
        this.date_of_attachment = date_of_attachment;
    }

    public Date getDate_detachments() {
        return date_detachments;
    }

    public void setDate_detachments(Date date_detachments) {
        this.date_detachments = date_detachments;
    }
}
