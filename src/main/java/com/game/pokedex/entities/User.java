package com.game.pokedex.entities;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

@Entity
@Table(name = "user_app")
public class User implements UserDetails {
    @Column(name = "created_date", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    protected Instant createdDate;

    @Column(name = "modified_date")
    @Temporal(TemporalType.TIMESTAMP)
    protected Instant modifiedDate;
    @Id()
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Instant getModifiedDate() {
        return modifiedDate;
    }

    public String getEmail() {
        return email;
    }

    public Role getRole() {
        return role;
    }

    @Column(name = "email", unique = true)
    private String email;
    private String password;
    private String name;

    @Enumerated(EnumType.STRING)
    private Role role;
    public enum Role {
        ADMIN, MESTRE_POKEMON
    }

    public User() {
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }


    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>(Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + this.role.name())));
    }
    public void setModifiedDate(Instant modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id){
        this.id = id;
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

    public void setRole(Role role) {
        this.role = role;
    }

    public void setEmail(String email){
        this.email = email;
    }


}