package com.game.pokedex.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record Chain(List<Chain> evolves_to, Species species){
}
