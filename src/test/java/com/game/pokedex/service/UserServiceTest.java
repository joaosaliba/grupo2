package com.game.pokedex.service;

import com.game.pokedex.dtos.UserDto;
import com.game.pokedex.dtos.UserRequest;
import com.game.pokedex.dtos.UserUpdateRequest;
import com.game.pokedex.entities.User;
import com.game.pokedex.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserServiceTest {

    private UserService userService;

    private UserRepository repository;

    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setup(){
        passwordEncoder = Mockito.mock(PasswordEncoder.class);
        repository = Mockito.mock(UserRepository.class);
        userService = new UserService(repository, new ModelMapper(), passwordEncoder);
    }

    @Test
    public void cadastrarUsuarioValido_ResultaSucesso(){
        UserRequest userRequest = new UserRequest(Instant.now(),
                "francisco@gmail.com",
                "nunes",
                "nunes",
                User.Role.ADMIN.name()
        );
        User usu = new User();
        new ModelMapper().map(userRequest,usu);
        usu.setId(1L);
        Mockito.when(repository.save(Mockito.any())).thenReturn(usu);

        ResponseEntity<UserDto> response = userService.createUser(userRequest);
        Assertions.assertEquals(HttpStatus.CREATED,response.getStatusCode());
    }

    @Test
    public void cadastrarUsuarioInvalido_ResultaExcessao(){
        UserRequest userRequest = new UserRequest();
        userRequest.setRole(User.Role.ADMIN.name());
        userRequest.setCreatedDate(Instant.now());
        userRequest.setName("jasdfl");

        Assertions.assertThrows(NullPointerException.class, () -> userService.createUser(userRequest));
    }

    @Test
    public void buscarTodosResultaSucesso(){
        List<User> list = new ArrayList<>();
        for(int i = 0 ; i < 5 ; i ++){
            User user = new User("usuario"+i,"passowod"+i);
            list.add(user);
        }
        Mockito.when(repository.findAll()).thenReturn(list);

        ResponseEntity<List<UserDto>> listUser = userService.getAll();
        Assertions.assertEquals(5, listUser.getBody().size());
    }

    @Test
    public void atualizarUsuarioJaCadastrado_ResultaSucesso(){
        User user = new User();
        user.setName("Francisco");
        user.setPassword("antiga");
        user.setId(1L);
        Instant agora = Instant.now();
        UserUpdateRequest userupdate = new UserUpdateRequest(agora,"modificada");
        Mockito.when(repository.save(Mockito.any())).thenReturn(user);
        Mockito.when(repository.findByName("Francisco")).thenReturn(Optional.of(user));

        ResponseEntity<UserDto> response = userService.update("Francisco", userupdate);
        Assertions.assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    @Test void atualizarUsuarioNaoCadastrado_ResultaException(){
        User usernull = null;
        Optional<User> userOptional = Optional.ofNullable(usernull);
        Mockito.when(repository.findByName(Mockito.any())).thenReturn(userOptional);
        UserUpdateRequest user = new UserUpdateRequest(Instant.now(),"name");

        ResponseEntity<UserDto> response = userService.update("francisco",user);
        Assertions.assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());

    }
}
