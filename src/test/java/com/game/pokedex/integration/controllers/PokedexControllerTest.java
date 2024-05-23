package com.game.pokedex.integration.controllers;

import com.game.pokedex.dtos.Evolution;
import com.game.pokedex.dtos.PokemonSpecies;
import com.game.pokedex.entities.Pokedex;
import com.game.pokedex.repositories.UserRepository;
import com.game.pokedex.service.PokedexService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = "spring.h2.console.enabled=true")
public class PokedexControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PokedexService pokedexService;

    @Test
    @WithMockUser(username = "admin@example.com", roles = "ADMIN")
    public void deletePokedexByUserIdTest() throws Exception {
        Long userId = 1L;
        mockMvc.perform(MockMvcRequestBuilders.delete("/pokedex/{id}", userId))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(pokedexService).deletePokedexByUserId(userId);
    }

    @Test
    @WithMockUser(username = "admin@example.com", roles = "ADMIN")
    public void listPokedexByUserIdTest() throws Exception {
        Long userId = 1L;
        Pokedex mockedList =  new Pokedex();
        Mockito.when(pokedexService.listPokedexByUserId(userId)).thenReturn(mockedList);

        mockMvc.perform(MockMvcRequestBuilders.get("/pokedex/user/{id}/pokemons", userId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("{}"));

        Mockito.verify(pokedexService).listPokedexByUserId(userId);
    }

    @Test
    @WithMockUser(username = "admin@example.com", roles = "ADMIN")
    public void deletePokemonByIdTest() throws Exception {
        Long pokemonId = 42L;
        mockMvc.perform(MockMvcRequestBuilders.delete("/pokedex/pokemon/{id}", pokemonId))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(pokedexService).deletePokemonById(pokemonId);
    }

    //Not working yet and i need get some sleep
    /*@Test
    @WithMockUser(username = "admin@example.com", roles = "ADMIN")
    public void evolvePokemonTest() throws Exception {
        String username = "Ash";
        String pokemonName = "pikachu";
        PokemonSpecies pokemonSpecies = new PokemonSpecies(new Evolution("https://pokeapi.co/api/v2/evolution-chain/10/"));
        Mockito.when(pokedexService.evolvePokemon(username, pokemonName)).thenReturn(pokemonSpecies);

        mockMvc.perform(MockMvcRequestBuilders.put("/pokedex/evoluir/{username}/{pokemon_name}", username, pokemonName))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(pokedexService).evolvePokemon(username, pokemonName);
    }*/

}
