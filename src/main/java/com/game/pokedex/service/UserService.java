package com.game.pokedex.service;

import com.game.pokedex.dtos.UserDto;
import com.game.pokedex.dtos.UserRequest;
import com.game.pokedex.dtos.UserUpdateRequest;
import com.game.pokedex.entities.User;
import com.game.pokedex.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;
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

    public ResponseEntity<UserDto> createUser(UserRequest userRequest) throws Exception {
        if(userRequest == null || userRequest.getEmail() == null || userRequest.getPassword() == null || userRequest.getName() == null){
            throw new Exception("campo em falta");
        }
        User user;
        user = this.modelMapper.map(userRequest, User.class);
        user.setRole(User.Role.valueOf(userRequest.getRole()));
        user.setPassword(encoder.encode(userRequest.getPassword()));
        user = this.userRepository.save(user);
        return ResponseEntity.created(URI.create("/user/"+user.getId())).body(this.modelMapper.map(user, UserDto.class));
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

    public ResponseEntity<List<UserDto>> getAll() {
        return ResponseEntity.ok(
        this.userRepository.findAll()
                .stream()
                .map(
                        (element) -> modelMapper.map(element, UserDto.class)
                )
                .toList());
    }

    public ResponseEntity<UserDto> update(String username, UserUpdateRequest userUpdateRequest){
        User user = this.userRepository.findByName(username).orElse(null);
        if (user == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        user.setModifiedDate(userUpdateRequest.getModifiedDate());
        user.setPassword(userUpdateRequest.getPassword());
        User atalizado = this.userRepository.save(user);
        UserDto resposta = modelMapper.map(atalizado, UserDto.class);
        return ResponseEntity.ok().body(resposta);
    }
}
