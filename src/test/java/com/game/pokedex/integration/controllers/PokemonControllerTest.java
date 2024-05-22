package com.game.pokedex.integration.controllers;

import com.game.pokedex.controllers.PokemonController;
import com.game.pokedex.dtos.client.CaptureRate;
import com.game.pokedex.dtos.client.Stat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.junit.jupiter.api.Assertions;

import com.game.pokedex.dtos.client.ClientResultPokemons;
import com.game.pokedex.dtos.client.Pokemon;
import com.game.pokedex.entities.Pokedex;
import com.game.pokedex.entities.User;
import com.game.pokedex.repositories.PokedexEndPointRepository;
import com.game.pokedex.repositories.UserRepository;
import com.game.pokedex.service.PokedexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JsonContent;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = "spring.h2.console.enabled=true")
public class PokemonControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PokedexEndPointRepository repository;

    @MockBean
    private PokedexService pokedexService;

    @MockBean
    private UserRepository userRepository;


    @Test
    @WithMockUser(username = "admin@example.com", roles = "ADMIN")
    public void testGetAllPokemons() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/pokemons")
                        .param("offset", "20")
                        .param("limit", "20")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(username = "admin@example.com", roles = "ADMIN")
    public void testGetByName() throws Exception {
        Pokemon expected = new Pokemon("Pikachu",null);
        Mockito.when(repository.getByName("Pikachu")).thenReturn(expected);

        mockMvc.perform(MockMvcRequestBuilders.get("/pokemons/Pikachu"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }


    @Test
    @WithMockUser(username = "admin@example.com", roles = "ADMIN")
    public void testCapturePokemonSuccess() throws Exception {
        // Setup
        String username = "Ash";
        String pokemonName = "Pikachu";
        User user = new User();
        Pokemon pokemon = new Pokemon("Pikachu",
                List.of(new Stat(45L,null),
                        new Stat(49L,null),
                        new Stat(49L,null),
                        new Stat(65L,null),
                        new Stat(65L,null),
                        new Stat(45L,null)));
        CaptureRate captureRate = new CaptureRate(100);

        Mockito.when(userRepository.findByName(username)).thenReturn(Optional.of(user));
        Mockito.when(repository.getByName(pokemonName)).thenReturn(pokemon);
        Mockito.when(repository.getCaptureRateByName(pokemonName)).thenReturn(captureRate);

        Random spyRandom = Mockito.spy(new Random());
        Mockito.doReturn(10).when(spyRandom).nextInt(100);

        mockMvc.perform(MockMvcRequestBuilders.get("/pokemons/capturar/{username}/{pokemon_name}", username, pokemonName))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("true"));

        Mockito.verify(pokedexService, Mockito.times(1)).addPokemonToPokedex(Mockito.any(Pokedex.class));
    }

    @Test
    @WithMockUser(username = "admin@example.com", roles = "ADMIN")
    public void testCapturePokemonFailure() throws Exception {
        // Setup
        String username = "Ash";
        String pokemonName = "Charizard";
        User user = new User();
        CaptureRate captureRate = new CaptureRate(0);

        Mockito.when(userRepository.findByName(username)).thenReturn(Optional.of(user));
        Mockito.when(repository.getCaptureRateByName(pokemonName)).thenReturn(captureRate);

        mockMvc.perform(MockMvcRequestBuilders.get("/pokemons/capturar/{username}/{pokemon_name}", username, pokemonName))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("false"));

        Mockito.verify(pokedexService, Mockito.never()).addPokemonToPokedex(Mockito.any(Pokedex.class));
    }
}
