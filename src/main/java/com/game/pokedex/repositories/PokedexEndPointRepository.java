package com.game.pokedex.repositories;

import com.game.pokedex.dtos.client.CaptureRate;
import com.game.pokedex.dtos.client.ClientResultPokemons;
import com.game.pokedex.dtos.client.Pokemon;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "pokemonClient", url ="${app.pokemon.url}" )
public interface PokedexEndPointRepository {

    @GetMapping(path = "pokemon",params = {"limit", "offset"})
    ClientResultPokemons getAll(@RequestParam(name = "limit") Long limit, @RequestParam(name = "offset") Long offset);

    @GetMapping(path = "pokemon/{pokemon_name}")
    Pokemon getByName(@PathVariable String pokemon_name);

    @GetMapping(path = "pokemon-species/{pokemon_name}")
    CaptureRate getCaptureRateByName(@PathVariable String pokemon_name);
}
