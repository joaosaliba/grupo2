package com.game.pokedex.dtos.client;

import java.util.List;

public record Pokemon(String name, List<Stat> stats) {
}
