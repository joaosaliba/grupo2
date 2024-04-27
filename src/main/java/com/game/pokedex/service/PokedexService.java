package com.game.pokedex.service;

import com.game.pokedex.repositories.PokedexRepository;
import org.springframework.stereotype.Service;

@Service
public class PokedexService {

    private final PokedexRepository pokedexRepository;

    public PokedexService(PokedexRepository pokedexRepository){
        this.pokedexRepository = pokedexRepository;
    }

    public void deletePokedexById(Long id){
        this.pokedexRepository.deleteById(id);
    }
}
