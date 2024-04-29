package com.game.pokedex.repositories;

import com.game.pokedex.entities.Pokedex;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PokedexRepository extends JpaRepository<Pokedex, Long> {

    void deletePokedexByUserId(Long id);

    List<Pokedex> findByUserId(Long id);

    void deleteById(Long id);

    Pokedex save(Pokedex pokedex);
}
