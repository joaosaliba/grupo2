package com.game.pokedex.controllers;

import com.game.pokedex.dtos.client.CaptureRate;
import com.game.pokedex.dtos.client.ClientResultPokemons;
import com.game.pokedex.dtos.client.Pokemon;
import com.game.pokedex.entities.Pokedex;
import com.game.pokedex.entities.User;
import com.game.pokedex.repositories.PokedexEndPointRepository;
import com.game.pokedex.repositories.UserRepository;
import com.game.pokedex.service.PokedexService;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;

@RestController
@RequestMapping("/pokemons")
public class PokemonController {
    private final PokedexEndPointRepository repository;
    private final PokedexService pokedexService;
    private final UserRepository userRepository;

    public PokemonController(PokedexEndPointRepository repository, PokedexService pokedexService, UserRepository userRepository) {
        this.repository = repository;
        this.pokedexService = pokedexService;
        this.userRepository = userRepository;
    }

    @GetMapping
    public ClientResultPokemons getAll(){
        return repository.getAll(1302L);
    }

    @GetMapping("/{name}")
    public Pokemon getByName(@PathVariable String name){
        return repository.getByName(name);
    }

    @GetMapping("/capturar/{username}/{pokemon_name}")
    public Boolean capturePokemon(@PathVariable String username, @PathVariable String pokemon_name){
        Optional<User> optinalUser = userRepository.findByName(username);
        AtomicReference<Boolean> resp = new AtomicReference<>(false);
        Random random = new Random();
        int sortedNumber = random.nextInt(100);

        CaptureRate captureRate = repository.getCaptureRateByName(pokemon_name);

        optinalUser.ifPresent(user -> {
            if(sortedNumber < captureRate.capture_rate()) {
                Pokemon capturedPokemon = repository.getByName(pokemon_name);

                Pokedex pokedex = new Pokedex();
                pokedex.setUser(user);
                pokedex.setName(capturedPokemon.name());
                pokedex.setHp(capturedPokemon.stats().get(0).base_stat());
                pokedex.setAttack(capturedPokemon.stats().get(1).base_stat());
                pokedex.setDefense(capturedPokemon.stats().get(2).base_stat());
                pokedex.setSpecialAttack(capturedPokemon.stats().get(3).base_stat());
                pokedex.setSpecialDefense(capturedPokemon.stats().get(4).base_stat());
                pokedex.setSpeed(capturedPokemon.stats().get(5).base_stat());

                pokedexService.addPokemonToPokedex(pokedex);

                resp.set(true);
            }
        });

        return resp.get();
    }
}
