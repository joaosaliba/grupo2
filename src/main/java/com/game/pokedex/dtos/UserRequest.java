package com.game.pokedex.dtos;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

public class UserRequest implements Serializable {
    private Instant createdDate;
    private String email;
    private String password;
    private String name;
    private String role;

    public UserRequest() {
    }


    public UserRequest(Instant createdDate, String email, String password, String name, String role) {
        this.createdDate = createdDate;
        this.email = email;
        this.password = password;
        this.name = name;
        this.role = role;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}