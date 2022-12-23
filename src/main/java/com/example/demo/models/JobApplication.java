package com.example.demo.models;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;

@Entity
public class JobApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    public User candidate;

    @NotBlank
    @Size(min = 5, max = 255)
    public String work_experience;

    @NotBlank
    @Size(min = 5, max = 255)
    public String desired_position;

    @NotBlank
    @Size(min = 7, max = 20)
    public String phone;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    public Date date;

    @NotNull
    @Min(value = 0L)
    @Max(value = 5L)
    public int mark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getCandidate() {
        return candidate;
    }

    public void setCandidate(User candidate) {
        this.candidate = candidate;
    }

    public String getWork_experience() {
        return work_experience;
    }

    public void setWork_experience(String work_experience) {
        this.work_experience = work_experience;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDesired_position() {
        return desired_position;
    }

    public void setDesired_position(String desired_position) {
        this.desired_position = desired_position;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
