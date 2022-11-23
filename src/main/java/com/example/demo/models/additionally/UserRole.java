package com.example.demo.models.additionally;

import com.example.demo.models.keys.UserRoleKey;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "user_role")
public class UserRole {

    @EmbeddedId
    UserRoleKey id;
}
