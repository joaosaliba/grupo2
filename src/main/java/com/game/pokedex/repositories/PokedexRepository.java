package com.game.pokedex.repositories;

import com.game.pokedex.entities.Pokedex;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PokedexRepository extends JpaRepository<Pokedex, Long> {

    void deleteById(Long id);

}
