package com.example.demo.models.keys;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class UserRoleKey implements Serializable {

    @Column(name = "user_id")
    Long userId;

    @Column(name = "role")
    String role;
}
