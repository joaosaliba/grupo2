package com.game.pokedex.integration.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.game.pokedex.dtos.Evolution;
import com.game.pokedex.dtos.PokemonSpecies;
import com.game.pokedex.entities.Pokedex;
import com.game.pokedex.entities.User;
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
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = "spring.h2.console.enabled=true")
public class PokedexControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PokedexService pokedexService;
    @MockBean
    private UserRepository userRepository;

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
    @Test
    @WithMockUser(username = "admin@example.com", roles = "ADMIN")
    public void evolvePokemonTest() throws Exception {
        String username = "Ash";
        String pokemonName = "bulbasaur";
        String url ="https://pokeapi.co/api/v2/pokemon-species/1/";

        PokemonSpecies pokemonSpecies = new PokemonSpecies(new Evolution(url));
        Mockito.when(pokedexService.evolvePokemon(username, pokemonName)).thenReturn(pokemonSpecies);

        ObjectMapper mapper = new ObjectMapper();
        String jsonContent = """
                {
                    "baby_trigger_item": null,
                    "chain": {
                        "evolution_details": [],
                        "evolves_to": [
                            {
                                "evolution_details": [
                                    {
                                        "gender": null,
                                        "held_item": null,
                                        "item": null,
                                        "known_move": null,
                                        "known_move_type": null,
                                        "location": null,
                                        "min_affection": null,
                                        "min_beauty": null,
                                        "min_happiness": null,
                                        "min_level": 16,
                                        "needs_overworld_rain": false,
                                        "party_species": null,
                                        "party_type": null,
                                        "relative_physical_stats": null,
                                        "time_of_day": "",
                                        "trade_species": null,
                                        "trigger": {
                                            "name": "level-up",
                                            "url": "https://pokeapi.co/api/v2/evolution-trigger/1/"
                                        },
                                        "turn_upside_down": false
                                    }
                                ],
                                "evolves_to": [
                                    {
                                        "evolution_details": [
                                            {
                                                "gender": null,
                                                "held_item": null,
                                                "item": null,
                                                "known_move": null,
                                                "known_move_type": null,
                                                "location": null,
                                                "min_affection": null,
                                                "min_beauty": null,
                                                "min_happiness": null,
                                                "min_level": 32,
                                                "needs_overworld_rain": false,
                                                "party_species": null,
                                                "party_type": null,
                                                "relative_physical_stats": null,
                                                "time_of_day": "",
                                                "trade_species": null,
                                                "trigger": {
                                                    "name": "level-up",
                                                    "url": "https://pokeapi.co/api/v2/evolution-trigger/1/"
                                                },
                                                "turn_upside_down": false
                                            }
                                        ],
                                        "evolves_to": [],
                                        "is_baby": false,
                                        "species": {
                                            "name": "venusaur",
                                            "url": "https://pokeapi.co/api/v2/pokemon-species/3/"
                                        }
                                    }
                                ],
                                "is_baby": false,
                                "species": {
                                    "name": "ivysaur",
                                    "url": "https://pokeapi.co/api/v2/pokemon-species/2/"
                                }
                            }
                        ],
                        "is_baby": false,
                        "species": {
                            "name": "bulbasaur",
                            "url": "https://pokeapi.co/api/v2/pokemon-species/1/"
                        }
                    },
                    "id": 1
                }
                """;
        JsonNode dummyJsonNode = mapper.readTree(jsonContent);
        Mockito.when(pokedexService.getEvolutionChainJson(url)).thenReturn(dummyJsonNode);
        List<String> mockedSpeciesNames = Arrays.asList("bulbasaur", "ivysaur", "venusaur");
        Mockito.when(pokedexService.extractSpeciesNames(Mockito.any(JsonNode.class))).thenReturn(mockedSpeciesNames);

        Mockito.when(userRepository.findByName(username)).thenReturn(Optional.of(new User("admin@example.com", "")));

        mockMvc.perform(MockMvcRequestBuilders.put("/pokedex/evoluir/{username}/{pokemon_name}", username, pokemonName))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }


}
