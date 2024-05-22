package com.game.pokedex.service;


import com.game.pokedex.dtos.Evolution;
import com.game.pokedex.dtos.PokemonSpecies;
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

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setId(1L);
        pokedex = new Pokedex();
        pokedex.setId(1L);
        pokedex.setUser(user);

        //pokedexRepository.save(pokedex);

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
    public void savePokedexEntry_whenPokemonExists_returnIt() {
        pokedexService.savePokedexEntry(pokedex);

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
}
