package com.game.pokedex.repositories;

import com.game.pokedex.dtos.client.ClientResultPokemons;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "podekerendpoint", url ="${app.pokemon.url}" )
public interface PokedexEndPointRepository {

    @GetMapping(path = "pokemon",params = "limit")
    ClientResultPokemons getAll(@RequestParam Long limit);

}
