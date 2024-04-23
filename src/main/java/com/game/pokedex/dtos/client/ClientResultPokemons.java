package com.game.pokedex.dtos.client;

import java.util.List;

public record ClientResultPokemons(List<PokenomClient> results, Long count, String next, String previous) {
}
