package com.game.pokedex.controllers;


import com.game.pokedex.service.PokedexService;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/pokedex")
public class PokedexController {

    private final PokedexService pokedexService;

    public PokedexController(PokedexService pokedexService){
        this.pokedexService = pokedexService;
    }

    @DeleteMapping("/{id}")
    public void deletePokedexById(@PathVariable("id") Long id){
        this.pokedexService.deletePokedexById(id);
    }


}
