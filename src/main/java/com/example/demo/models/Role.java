package com.example.demo.models;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    CLIENT, ADMIN, DOCTOR;

    @Override
    public String getAuthority() {
        return name();
    }
}
