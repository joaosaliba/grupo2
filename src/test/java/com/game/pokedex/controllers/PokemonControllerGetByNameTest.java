package com.game.pokedex.controllers;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.h2.H2ConsoleProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PokemonControllerGetByNameTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    H2ConsoleProperties h2ConsoleProperties;

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
    public void getByName_shouldReturnPokemon_whenValidPokemonName() throws Exception {
        String validPokemonName = "pikachu";
        mockMvc.perform(
                MockMvcRequestBuilders.get("/pokemons/" + validPokemonName)
                        .with(user("menino@gmail.com").password("1234").roles("MESTRE_POKEMON"))
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void getByName_shouldNotReturnPokemon_whenInvalidPokemonName() throws Exception {
        String validPokemonName = "invalidPokemonName";
        mockMvc.perform(
                MockMvcRequestBuilders.get("/pokemons/" + validPokemonName)
                        .with(user("menino@gmail.com").password("1234").roles("MESTRE_POKEMON"))
        ).andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    public void getByName_shouldReturnPokemon_whenPokemonNameHasSpecialCharacter() throws Exception {
        String validPokemonName = "mr-mime";
        mockMvc.perform(
                MockMvcRequestBuilders.get("/pokemons/" + validPokemonName)
                        .with(user("menino@gmail.com").password("1234").roles("MESTRE_POKEMON"))
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

}