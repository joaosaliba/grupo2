package com.game.pokedex.repositories;

import com.game.pokedex.dtos.Evolution;
import com.game.pokedex.dtos.EvolutionChain;
import com.game.pokedex.dtos.PokemonSpecies;
import com.game.pokedex.dtos.client.ClientResultPokemons;
import com.game.pokedex.dtos.client.Pokemon;
import io.swagger.v3.core.util.Json;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "pokemonClient", url ="${app.pokemon.url}" )
public interface PokedexEndPointRepository {

    @GetMapping(path = "pokemon",params = "limit")
    ClientResultPokemons getAll(@RequestParam Long limit);

    @GetMapping(path = "pokemon/{pokemon_name}")
    Pokemon getByName(@PathVariable String pokemon_name);

    @GetMapping(path = "pokemon-species/{pokemon_name}")
    PokemonSpecies getByEvolutionChain(@PathVariable String pokemon_name);

    @GetMapping
    EvolutionChain getByChain(@RequestParam("url") String url);
}
