package com.game.pokedex.dtos;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

public class UserUpdateRequest implements Serializable {
    private Instant modifiedDate;
    private String password;

    public UserUpdateRequest() {
    }

    public UserUpdateRequest(Instant modifiedDate, String password) {
        this.modifiedDate = modifiedDate;
        this.password = password;
    }

    public Instant getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Instant modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}