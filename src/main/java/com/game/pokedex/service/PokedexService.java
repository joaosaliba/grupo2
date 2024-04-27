package com.game.pokedex.service;

import com.game.pokedex.entities.Pokedex;
import com.game.pokedex.repositories.PokedexRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PokedexService {

    private final PokedexRepository pokedexRepository;

    public PokedexService(PokedexRepository pokedexRepository){
        this.pokedexRepository = pokedexRepository;
    }


    @Transactional
    public void deletePokedexByUserId(Long id){
        this.pokedexRepository.deletePokedexByUserId(id);
    }

}
