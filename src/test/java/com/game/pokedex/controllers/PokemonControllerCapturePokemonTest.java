package com.game.pokedex.controllers;

import com.game.pokedex.dtos.client.CaptureRate;
import com.game.pokedex.dtos.client.Pokemon;
import com.game.pokedex.dtos.client.Stat;
import com.game.pokedex.dtos.client.StatType;
import com.game.pokedex.entities.Pokedex;
import com.game.pokedex.entities.User;
import com.game.pokedex.repositories.PokedexEndPointRepository;
import com.game.pokedex.repositories.UserRepository;
import com.game.pokedex.service.PokedexService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.h2.H2ConsoleProperties;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.mockito.ArgumentCaptor;

import java.util.List;
import java.util.Optional;
import java.util.Collections;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PokemonControllerCapturePokemonTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    H2ConsoleProperties h2ConsoleProperties;

    @MockBean
    private PokedexEndPointRepository pokedexEndPointRepository;

    @MockBean
    private PokedexService pokedexService;

    @BeforeAll
    void setUp() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.post("/user")
                        .content("""
                                        {
                                          "createdDate": "2024-05-20T15:11:06.317Z",
                                          "email": "menino@gmail.com",
                                          "password": "1234",
                                          "name": "menino",
                                          "role": "MESTRE_POKEMON"
                                        }
                                        """)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isCreated());
    }


    @Test
    public void capturePokemon_shouldReturnTrue_whenCatchesPokemon() throws Exception {
        // Mock user repository to return a user when findByUsername is called
        User user = new User("menino@gmail.com", "1234");
        user.setName("menino");

        // Mock capture rate to 100%
        CaptureRate captureRate = new CaptureRate(100);
        when(pokedexEndPointRepository.getCaptureRateByName(anyString())).thenReturn(captureRate);

        // Mock Pokémon data
        var stats = List.of(
                new Stat(35L, new StatType("hp")),
                new Stat(55L, new StatType("attack")),
                new Stat(40L, new StatType("defense")),
                new Stat(50L, new StatType("special-attack")),
                new Stat(50L, new StatType("special-defense")),
                new Stat(90L, new StatType("speed"))
        );
        Pokemon pokemon = new Pokemon("pikachu", stats);
        when(pokedexEndPointRepository.getByName("pikachu")).thenReturn(pokemon);

        mockMvc.perform(MockMvcRequestBuilders.get("/pokemons/capturar/menino/pikachu")
                        .with(user("menino@gmail.com").password("1234").roles("MESTRE_POKEMON"))
                ).andExpect(MockMvcResultMatchers.status().isOk()) // Requisição feita com sucesso
                .andExpect(MockMvcResultMatchers.content().string("true")); // Pokemon capturado

        // Verifica se o pokemon foi salvo na pokedex
        ArgumentCaptor<Pokedex> pokedexArgumentCaptor = ArgumentCaptor.forClass(Pokedex.class);
        verify(pokedexService, times(1)).addPokemonToPokedex(pokedexArgumentCaptor.capture());
        Pokedex capturedPokemon = pokedexArgumentCaptor.getValue();
        Assertions.assertNotNull(capturedPokemon);
        Assertions.assertEquals("pikachu", capturedPokemon.getName());
    }

    @Test
    public void capturePokemon_shouldReturnFalse_whenTryCatchingPokemonFail() throws Exception {
        // Mock user repository to return a user when findByUsername is called
        User user = new User("menino@gmail.com", "1234");
        user.setName("menino");

        // Mock capture rate to 100%
        CaptureRate captureRate = new CaptureRate(0);
        when(pokedexEndPointRepository.getCaptureRateByName(anyString())).thenReturn(captureRate);

        mockMvc.perform(MockMvcRequestBuilders.get("/pokemons/capturar/menino/pikachu")
                        .with(user("menino@gmail.com").password("1234").roles("MESTRE_POKEMON"))
                ).andExpect(MockMvcResultMatchers.status().isOk()) // Requisição feita com sucesso
                .andExpect(MockMvcResultMatchers.content().string("false")); // Pokemon não capturado

        // Verifica se o pokemon não foi salvo na pokedex
        verify(pokedexService, never()).addPokemonToPokedex(any(Pokedex.class));
    }
}
