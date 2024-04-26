package com.game.pokedex.dtos;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

public class UserRequest implements Serializable {
    private Instant createdDate;
    private String email;
    private String password;
    private String name;

    public UserRequest() {
    }

    public UserRequest(Instant createdDate, String email, String password, String name) {
        this.createdDate = createdDate;
        this.email = email;
        this.password = password;
        this.name = name;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRequest entity = (UserRequest) o;
        return Objects.equals(this.createdDate, entity.createdDate) &&
                Objects.equals(this.email, entity.email) &&
                Objects.equals(this.password, entity.password) &&
                Objects.equals(this.name, entity.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(createdDate, email, password, name);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "createdDate = " + createdDate + ", " +
                "email = " + email + ", " +
                "password = " + password + ", " +
                "name = " + name + ")";
    }
}