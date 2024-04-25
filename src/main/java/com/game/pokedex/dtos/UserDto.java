package com.game.pokedex.dtos;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * DTO for {@link com.game.pokedex.entities.User}
 */
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto entity = (UserDto) o;
        return Objects.equals(this.createdDate, entity.createdDate) &&
                Objects.equals(this.modifiedDate, entity.modifiedDate) &&
                Objects.equals(this.id, entity.id) &&
                Objects.equals(this.email, entity.email) &&
                Objects.equals(this.name, entity.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(createdDate, modifiedDate, id, email, name);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "createdDate = " + createdDate + ", " +
                "modifiedDate = " + modifiedDate + ", " +
                "id = " + id + ", " +
                "email = " + email + ", " +
                "name = " + name + ")";
    }


}