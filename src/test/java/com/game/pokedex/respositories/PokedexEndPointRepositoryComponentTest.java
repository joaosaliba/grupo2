package com.game.pokedex.respositories;

import com.game.pokedex.dtos.client.ClientResultPokemons;
import com.game.pokedex.dtos.client.PokemonClient;
import com.game.pokedex.repositories.PokedexEndPointRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class PokedexEndPointRepositoryComponentTest {
    @Autowired
    private PokedexEndPointRepository repository;

    @Test
    public void buscarPokemonDeveResultarNaoNullo(){
        ClientResultPokemons pokemons = repository.getAll(20L,20L);

        Assertions.assertNotNull(pokemons);
    }

    @Test
    public void buscar20PokemonsDeveResultarEmListaNaoNula(){
        ClientResultPokemons pokemons = repository.getAll(20L,20L);
        List<PokemonClient> listDePokemons = pokemons.results();
        Assertions.assertNotNull(listDePokemons);
        PokemonClient pokemonClient = listDePokemons.get(0);
        Assertions.assertNotNull(pokemonClient);
    }
}
