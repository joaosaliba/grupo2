package com.game.pokedex.service;

import com.game.pokedex.dtos.UserDto;
import com.game.pokedex.entities.User;
import com.game.pokedex.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserService(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public void deleteUserByUsername(String username) throws Exception {
        User user = this.userRepository.findByEmail(username)
                .orElseThrow(()-> new Exception("Usuário não encontrado"));
        this.userRepository.delete(user);
    }

    public UserDto getUserByUsername(String username)throws Exception {
        User user = this.userRepository.findByEmail(username)
                .orElseThrow(()-> new Exception("Usuário não encontrado"));

     return this.modelMapper.map(user,UserDto.class);

    }
}
