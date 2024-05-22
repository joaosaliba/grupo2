package com.game.pokedex.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.game.pokedex.dtos.PokemonSpecies;
import com.game.pokedex.entities.Pokedex;
import com.game.pokedex.repositories.PokedexEndPointRepository;
import com.game.pokedex.repositories.PokedexRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class PokedexService {

    private final PokedexRepository pokedexRepository;
    private final PokedexEndPointRepository pokedexEndPointRepository;
    private final RestTemplate restTemplate;


    public PokedexService(PokedexRepository pokedexRepository , PokedexEndPointRepository pokedexEndPointRepository,
                RestTemplate restTemplate) {
            this.pokedexRepository = pokedexRepository;
            this.pokedexEndPointRepository = pokedexEndPointRepository;
            this.restTemplate = restTemplate;
        }


    @Transactional
    public void deletePokedexByUserId(Long id){
        try {
            pokedexRepository.deletePokedexByUserId(id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("O Usuário " + id + " não possui Pokedex");
        }
    }


    public Pokedex listPokedexByUserId(Long id){
        return this.pokedexRepository.findByUserId(id);
    }

    @Transactional
    public void deletePokemonById(Long id){
        try {
            this.pokedexRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("O Pokemon " + id + " não foi encontrado");
        }
    }

    @Transactional
    public void addPokemonToPokedex(Pokedex pokedex){
        this.pokedexRepository.save(pokedex);
    }

    @Transactional
    public void savePokedexEntry(Pokedex pokedex) {
        pokedexRepository.save(pokedex);
    }

    public PokemonSpecies evolvePokemon(String username, String pokemon_name){
        return this.pokedexEndPointRepository.getByEvolutionChain(pokemon_name);
    }

    public JsonNode getEvolutionChainJson(String url) {
        return restTemplate.getForObject(url, JsonNode.class);
    }

    public List<String> extractSpeciesNames(JsonNode evolutionNode) {
        List<String> names = new ArrayList<>();
        collectSpeciesNames(evolutionNode, names);
        return names;
    }

    private void collectSpeciesNames(JsonNode node, List<String> names) {
        if (node == null || !node.has("species")) {
            return;
        }

        JsonNode species = node.get("species");
        String name = species.get("name").asText();
        names.add(name);

        JsonNode evolvesTo = node.get("evolves_to");
        if (evolvesTo != null) {
            for (JsonNode child : evolvesTo) {
                collectSpeciesNames(child, names);
            }
        }
    }
}
