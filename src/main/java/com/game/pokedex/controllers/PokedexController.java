package com.game.pokedex.controllers;


import com.game.pokedex.dtos.EvolutionChain;
import com.game.pokedex.dtos.PokemonSpecies;
import com.game.pokedex.entities.Pokedex;
import com.game.pokedex.service.PokedexService;
import io.swagger.v3.core.util.Json;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/pokedex")
public class PokedexController {

    private final PokedexService pokedexService;

    public PokedexController(PokedexService pokedexService){
        this.pokedexService = pokedexService;
    }

    @DeleteMapping("/delete_user/{id}")
    public void deletePokedexByUserId(@PathVariable("id") Long id){
        this.pokedexService.deletePokedexByUserId(id);
    }

    @GetMapping("/list_pokemons/{id}")
    public List<Pokedex> listPokedexByUserId(@PathVariable("id") Long id){
        return pokedexService.listPokedexByUserId(id);
    }

    @DeleteMapping("/delete_pokemon/{id}")
    public void deletePokemonById(@PathVariable("id") Long id){
        this.pokedexService.deletePokemonById(id);
    }

    @GetMapping("/evoluir/{username}/{pokemon_name}")
    public EvolutionChain evolvePokemon(@PathVariable String username, @PathVariable String pokemon_name){
        PokemonSpecies pokemonSpecies = this.pokedexService.evolvePokemon(username, pokemon_name);
        EvolutionChain evolutionChain = this.pokedexService.getEvolutionChain(pokemonSpecies);

        return evolutionChain;
    }
}

