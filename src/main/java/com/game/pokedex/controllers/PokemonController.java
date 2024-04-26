package com.game.pokedex.controllers;

import com.game.pokedex.dtos.client.ClientResultPokemons;
import com.game.pokedex.repositories.PokedexEndPointRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pokemons")
public class PokemonController {
    private final PokedexEndPointRepository repository;

    public PokemonController(PokedexEndPointRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public ClientResultPokemons getAll(){
        return repository.getAll(1302L);
    }
}
