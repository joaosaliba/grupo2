package com.game.pokedex.entities;

import com.game.pokedex.dtos.client.Pokemon;
import jakarta.persistence.*;



@Entity
@Table(name = "pokedex_app", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "pokemon_name"})
})
public class Pokedex {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "pokemon_name")
    private String name;

    @Column(name = "hp")
    private Long hp;

    @Column(name = "attack")
    private Long attack;

    @Column(name = "defense")
    private Long defense;

    @Column(name = "special_attack")
    private Long specialAttack;

    @Column(name = "special_defense")
    private Long specialDefense;

    @Column(name = "speed")
    private Long speed;

    public Pokedex() {
    }

    // ver com o grupo para alterar base_stat para baseStat
    public Pokedex(User user, Pokemon pokemon) {
        this.user = user;
        this.name = pokemon.name();
        this.hp = pokemon.stats().get(0).base_stat();
        this.attack = pokemon.stats().get(1).base_stat();
        this.defense = pokemon.stats().get(2).base_stat();
        this.specialAttack = pokemon.stats().get(3).base_stat();
        this.specialDefense = pokemon.stats().get(4).base_stat();
        this.speed = pokemon.stats().get(5).base_stat();
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
}
