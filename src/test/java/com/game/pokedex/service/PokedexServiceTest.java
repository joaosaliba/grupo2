package com.game.pokedex.service;


import com.game.pokedex.dtos.client.Pokemon;
import com.game.pokedex.dtos.client.Stat;
import com.game.pokedex.dtos.client.StatType;
import com.game.pokedex.entities.Pokedex;
import com.game.pokedex.entities.User;
import com.game.pokedex.repositories.PokedexEndPointRepository;
import com.game.pokedex.repositories.PokedexRepository;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
public class PokedexServiceTest {

    @Autowired
    private MockMvc mockMvc;

    /*@Autowired
    private PokedexRepository pokedexRepository;

    @Autowired
    private PokedexEndPointRepository pokedexEndPointRepository;

    @Autowired
    private RestTemplate restTemplate;*/
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
