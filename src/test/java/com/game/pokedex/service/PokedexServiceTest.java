package com.game.pokedex.service;


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
}
