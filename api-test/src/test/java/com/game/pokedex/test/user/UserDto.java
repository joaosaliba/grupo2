package com.game.pokedex.test.user;

import java.io.Serializable;
import java.time.Instant;

public class UserDto implements Serializable {
    private  Instant createdDate;
    private  Instant modifiedDate;
    private  Long id;
    private  String email;
    private  String name;

    public UserDto() {
    }

    public UserDto(Instant createdDate, Instant modifiedDate, Long id, String email, String name) {
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.id = id;
        this.email = email;
        this.name = name;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public Instant getModifiedDate() {
        return modifiedDate;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public void setModifiedDate(Instant modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }
}