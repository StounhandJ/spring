package com.example.demo.models;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    CLIENT, ADMIN, DOCTOR, STOREKEEPER, HR, ACCOUNTANT;

    @Override
    public String getAuthority() {
        return name();
    }
}
