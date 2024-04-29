package com.game.pokedex.service;

import com.game.pokedex.dtos.UserDto;
import com.game.pokedex.dtos.UserRequest;
import com.game.pokedex.dtos.UserUpdateRequest;
import com.game.pokedex.entities.User;
import com.game.pokedex.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private  final PasswordEncoder encoder;

    public UserService(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.encoder = encoder;
    }

    public UserDto createUser(UserRequest userRequest){
        User user;
        user = this.modelMapper.map(userRequest, User.class);
        user.setRole(User.Role.valueOf(userRequest.getRole()));
        user.setPassword(encoder.encode(userRequest.getPassword()));
        user = this.userRepository.save(user);
        return this.modelMapper.map(user, UserDto.class);
    }

    public void deleteUserByUsername(String username) throws Exception {
        User user = this.userRepository.findByEmail(username)
                .orElseThrow(()-> new Exception("Usuário não encontrado"));
        this.userRepository.delete(user);
    }

    public UserDto getUserByUsername(String username)throws Exception {
        User user = this.userRepository.findByName(username)
                .orElseThrow(()-> new Exception("Usuário não encontrado"));

     return this.modelMapper.map(user,UserDto.class);

    }

    public List<UserDto> getAll() {
        return this.userRepository.findAll()
                .stream()
                .map(
                        (element) -> modelMapper.map(element, UserDto.class)
                )
                .toList();
    }

    public UserDto update(String username, UserUpdateRequest userUpdateRequest) throws Exception {
        User user = this.userRepository.findByName(username)
                .orElseThrow(()-> new Exception("Usuário não encontrado"));
        user.setModifiedDate(userUpdateRequest.getModifiedDate());
        user.setPassword(userUpdateRequest.getPassword());
        return modelMapper.map(this.userRepository.save(user), UserDto.class);
    }
}
