package com.game.pokedex.service;

import com.game.pokedex.entities.User;
import com.game.pokedex.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void deleteUserByUsername(String username) throws Exception {
        User user = this.userRepository.findByEmail(username)
                .orElseThrow(()-> new Exception("Usúário não encontrado"));
        this.userRepository.delete(user);
    }
}
