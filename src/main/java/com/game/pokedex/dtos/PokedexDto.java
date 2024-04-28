package com.game.pokedex.dtos;

import com.game.pokedex.entities.User;

import java.util.Objects;

public class PokedexDto {

    private Long id;
    private User user;
    private String name;
    private Long hp;
    private Long attack;
    private Long defense;
    private Long specialAttack;
    private Long specialDefense;
    private Long speed;

    public PokedexDto() {
    }

    public PokedexDto(Long id, User user, String name, Long hp, Long attack, Long defense, Long specialAttack, Long specialDefense, Long speed) {
        this.id = id;
        this.user = user;
        this.name = name;
        this.hp = hp;
        this.attack = attack;
        this.defense = defense;
        this.specialAttack = specialAttack;
        this.specialDefense = specialDefense;
        this.speed = speed;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getHp() {
        return hp;
    }

    public void setHp(Long hp) {
        this.hp = hp;
    }

    public Long getAttack() {
        return attack;
    }

    public void setAttack(Long attack) {
        this.attack = attack;
    }

    public Long getDefense() {
        return defense;
    }

    public void setDefense(Long defense) {
        this.defense = defense;
    }

    public Long getSpecialAttack() {
        return specialAttack;
    }

    public void setSpecialAttack(Long specialAttack) {
        this.specialAttack = specialAttack;
    }

    public Long getSpecialDefense() {
        return specialDefense;
    }

    public void setSpecialDefense(Long specialDefense) {
        this.specialDefense = specialDefense;
    }

    public Long getSpeed() {
        return speed;
    }

    public void setSpeed(Long speed) {
        this.speed = speed;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof PokedexDto that)) return false;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getUser(), that.getUser()) && Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUser(), getName());
    }

    @Override
    public String toString() {
        return "PokedexDto{" +
                "id=" + id +
                ", user=" + user +
                ", name='" + name + '\'' +
                ", hp=" + hp +
                ", attack=" + attack +
                ", defense=" + defense +
                ", specialAttack=" + specialAttack +
                ", specialDefense=" + specialDefense +
                ", speed=" + speed +
                '}';
    }
}
