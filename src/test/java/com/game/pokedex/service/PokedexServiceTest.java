package com.game.pokedex.service;



import com.game.pokedex.dtos.Evolution;
import com.game.pokedex.dtos.PokemonSpecies;

import com.game.pokedex.dtos.client.Pokemon;
import com.game.pokedex.dtos.client.Stat;
import com.game.pokedex.dtos.client.StatType;

import com.game.pokedex.entities.Pokedex;
import com.game.pokedex.entities.User;
import com.game.pokedex.repositories.PokedexEndPointRepository;
import com.game.pokedex.repositories.PokedexRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;


import java.util.ArrayList;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
public class PokedexServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private PokedexRepository pokedexRepository;

    @Mock
    private PokedexEndPointRepository pokedexEndPointRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private PokedexService pokedexService;

    private User user;
    private Pokedex pokedex;
    private List<Stat> stats;
    private Pokemon pokemon;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setId(1L);
        stats = new ArrayList<>();
        stats.add(new Stat(45L, new StatType("hp")));
        stats.add(new Stat(45L, new StatType("attack")));
        stats.add(new Stat(45L, new StatType("defense")));
        stats.add(new Stat(45L, new StatType("special-attack")));
        stats.add(new Stat(45L, new StatType("special-defense")));
        stats.add(new Stat(45L, new StatType("speed")));
        pokemon = new Pokemon("bulbasaur",stats);
        pokedex = new Pokedex(user,pokemon);
        pokedex.setId(1L);
        Mockito.when(
                pokedexRepository.findByUserId(user.getId())
        ).thenReturn(pokedex);

        Mockito.when(pokedexRepository.findByUserId(2L)).thenReturn(null);

    }

    @Test
    public void findByUserId_whenUserExists_returnIt() {
        var foundPokedex = pokedexService.listPokedexByUserId(user.getId());

        //assertNotNull(foundPokedex);
        assertEquals(user.getId(), foundPokedex.getUser().getId());
    }

    @Test
    public void findByUserId_whenUserNotExists_returnEmptyList() {
        var foundPokedex = pokedexService.listPokedexByUserId(2L);

        assertNull(foundPokedex);
    }



    @Test
    public void deletePokedexByUserId_whenUserExists_returnIt() {

        pokedexService.deletePokedexByUserId(user.getId());

        Mockito.verify(pokedexRepository, times(1)).deletePokedexByUserId(user.getId());
    }

    @Test
    public void deletePokedexByUserId_whenUserNotExists_EntityNotFoundException() {
        doThrow(new EmptyResultDataAccessException(1)).when(pokedexRepository).deletePokedexByUserId(2L);

        assertThrows(EntityNotFoundException.class, () -> {
            pokedexService.deletePokedexByUserId(2L);
        });

       Mockito.verify(pokedexRepository, times(1)).deletePokedexByUserId(2L);
    }

    @Test
    public void deletePokemonById_whenPokemonExists_returnIt() {
        pokedexService.deletePokemonById(pokedex.getId());

        Mockito.verify(pokedexRepository, times(1)).deleteById(pokedex.getId());
    }

    @Test
    public void deletePokemonById_whenPokemonNotExists_EntityNotFoundException() {
        doThrow(new EmptyResultDataAccessException(1)).when(pokedexRepository).deleteById(2L);

        assertThrows(EntityNotFoundException.class, () -> {
            pokedexService.deletePokemonById(2L);
        });

        Mockito.verify(pokedexRepository, times(1)).deleteById(2L);
    }


    @Test
    public void addPokemonToPokedex_whenPokemonExists_returnIt() {
        pokedexService.addPokemonToPokedex(pokedex);

        Mockito.verify(pokedexRepository, times(1)).save(pokedex);
    }


    @Test
    public void evolvePokemon_whenPokemonExists_returnIt() {
        //PokemonSpecies
        Evolution evolution = new Evolution("url");
        Mockito.when(
                pokedexEndPointRepository.getByEvolutionChain("pokemon_name")
        ).thenReturn(new PokemonSpecies(evolution));

        var pokemonSpecies = pokedexService.evolvePokemon("username", "pokemon_name");

        assertNotNull(pokemonSpecies);
    }

    @Test
    public void evolvePokemon_whenPokemonNotExists_returnNull() {
        Mockito.when(
                pokedexEndPointRepository.getByEvolutionChain("pokemon_name")
        ).thenReturn(null);

        var pokemonSpecies = pokedexService.evolvePokemon("username", "pokemon_name");

        assertNull(pokemonSpecies);
    }

    @Test
    public void addPokemonToPokedex_whenPokedex_resultSucessiful(){
        List<Stat> stats = new ArrayList<>();
        stats.add(new Stat(45L, new StatType("hp")));
        stats.add(new Stat(45L, new StatType("attack")));
        stats.add(new Stat(45L, new StatType("defense")));
        stats.add(new Stat(45L, new StatType("special-attack")));
        stats.add(new Stat(45L, new StatType("special-defense")));
        stats.add(new Stat(45L, new StatType("speed")));
        Pokemon pokemon = new Pokemon("bulbasaur",stats);
        Pokedex pokedex1 = new Pokedex(user,pokemon);
        Mockito.when(pokedexRepository.save(Mockito.any())).thenReturn(pokedex1);
        pokedexService.addPokemonToPokedex(pokedex1);
        Mockito.verify(pokedexRepository, Mockito.times(1)).save(pokedex1);
    }


}
