package com.game.pokedex.controllers;


import com.fasterxml.jackson.databind.JsonNode;
import com.game.pokedex.dtos.PokemonSpecies;
import com.game.pokedex.dtos.client.Pokemon;
import com.game.pokedex.entities.Pokedex;
import com.game.pokedex.entities.User;
import com.game.pokedex.repositories.PokedexEndPointRepository;
import com.game.pokedex.repositories.UserRepository;
import com.game.pokedex.service.PokedexService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/pokedex")
public class PokedexController {

    private final PokedexService pokedexService;
    private final PokedexEndPointRepository repository;
    private final UserRepository userRepository;

    public PokedexController(PokedexService pokedexService, PokedexEndPointRepository repository
                , UserRepository userRepository) {
            this.pokedexService = pokedexService;
            this.repository = repository;
            this.userRepository = userRepository;
        }
    @DeleteMapping("/{id}")
    public void deletePokedexByUserId(@PathVariable("id") Long id){
        this.pokedexService.deletePokedexByUserId(id);
    }

    @GetMapping("user/{id}/pokemons")
    public List<Pokedex> listPokedexByUserId(@PathVariable("id") Long id){
        return pokedexService.listPokedexByUserId(id);
    }

    @DeleteMapping("/pokemon/{id}")
    public void deletePokemonById(@PathVariable("id") Long id){
        this.pokedexService.deletePokemonById(id);
    }

    @PutMapping("/evoluir/{username}/{pokemon_name}")
    public void evoluir(@PathVariable String username, @PathVariable String pokemon_name) throws Exception {

        PokemonSpecies pokemonSpecies = this.pokedexService.evolvePokemon(username, pokemon_name);
        JsonNode evolutionChain = pokedexService.getEvolutionChainJson(pokemonSpecies.evolution_chain().url());

        List<String> speciesNames = pokedexService.extractSpeciesNames(evolutionChain.get("chain"));
        if (speciesNames.size() == 1)
            return;

        String atual = speciesNames.get(0);
        for (int i = 1; i < speciesNames.size(); i++) {
            String proximo = speciesNames.get(i);
            if (atual.equals(pokemon_name)) {
                User user = userRepository.findByName(username)
                        .orElseThrow(() -> new Exception("Usuário não encontrado"));

                Pokedex pokedex = new Pokedex();
                pokedex.setUser(user);
                Pokemon pokemon = repository.getByName(proximo);
                pokedex.setName(pokemon.name());
                pokedex.setHp(pokemon.stats().get(0).base_stat());
                pokedex.setAttack(pokemon.stats().get(1).base_stat());
                pokedex.setDefense(pokemon.stats().get(2).base_stat());
                pokedex.setSpecialAttack(pokemon.stats().get(3).base_stat());
                pokedex.setSpecialDefense(pokemon.stats().get(4).base_stat());
                pokedex.setSpeed(pokemon.stats().get(5).base_stat());
                pokedexService.savePokedexEntry(pokedex);
            }
            atual = proximo;
        }
    }
}
