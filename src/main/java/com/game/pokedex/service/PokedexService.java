package com.game.pokedex.service;

import com.game.pokedex.dtos.Evolution;
import com.game.pokedex.dtos.EvolutionChain;
import com.game.pokedex.dtos.PokemonSpecies;
import com.game.pokedex.entities.Pokedex;
import com.game.pokedex.repositories.PokedexEndPointRepository;
import com.game.pokedex.repositories.PokedexRepository;
import io.swagger.v3.core.util.Json;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PokedexService {

    private final PokedexRepository pokedexRepository;
    private final PokedexEndPointRepository pokedexEndPointRepository;

    public PokedexService(PokedexRepository pokedexRepository , PokedexEndPointRepository pokedexEndPointRepository){
        this.pokedexRepository = pokedexRepository;
        this.pokedexEndPointRepository = pokedexEndPointRepository;
    }


    @Transactional
    public void deletePokedexByUserId(Long id){
        this.pokedexRepository.deletePokedexByUserId(id);
    }


    public List<Pokedex> listPokedexByUserId(Long id){
        return this.pokedexRepository.findByUserId(id);
    }

    @Transactional
    public void deletePokemonById(Long id){
        this.pokedexRepository.deleteById(id);
    }

    @Transactional
    public void addPokemonToPokedex(Pokedex pokedex){
        this.pokedexRepository.save(pokedex);
    }


    public PokemonSpecies evolvePokemon(String username, String pokemon_name){
        return this.pokedexEndPointRepository.getByEvolutionChain(pokemon_name);
    }


    public EvolutionChain getEvolutionChain(PokemonSpecies pokemonSpecies){
        return this.pokedexEndPointRepository.getByChain(pokemonSpecies.evolution_chain().url());
    }
}
