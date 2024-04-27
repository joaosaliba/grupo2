package com.game.pokedex.entities;

import com.game.pokedex.dtos.client.Pokemon;
import jakarta.persistence.*;
import org.apache.catalina.LifecycleState;

import java.util.List;

@Entity
@Table(name = "pokedex_app")
public class Pokedex {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @JoinColumn(name = "pokemon_name")
    private String name;
    @JoinColumn(name = "hp_stats")
    private Long hp;
    @JoinColumn(name = "attack_stats")
    private Long attack;
    @JoinColumn(name = "defense_stats")
    private Long defense;
    @JoinColumn(name = "special_attack_stats")
    private Long specialAttack;
    @JoinColumn(name = "special_defense_stats")
    private Long specialDefense;
    @JoinColumn(name = "speed_stats")
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
