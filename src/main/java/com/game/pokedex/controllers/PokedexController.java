package com.game.pokedex.controllers;


import com.game.pokedex.entities.Pokedex;
import com.game.pokedex.service.PokedexService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/pokedex")
public class PokedexController {

    private final PokedexService pokedexService;

    public PokedexController(PokedexService pokedexService){
        this.pokedexService = pokedexService;
    }

    @DeleteMapping("/delete_user/{id}")
    public void deletePokedexByUserId(@PathVariable("id") Long id){
        this.pokedexService.deletePokedexByUserId(id);
    }

}
