package com.game.pokedex.controllers;

import com.game.pokedex.dtos.UserDto;
import com.game.pokedex.dtos.UserRequest;
import com.game.pokedex.dtos.UserUpdateRequest;
import com.game.pokedex.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.security.core.Authentication;

import java.util.List;

import static com.game.pokedex.config.PermissionValitation.validatePermission;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @PreAuthorize("hasRole(T(com.game.pokedex.entities.User.Role).ADMIN.name())")
    public ResponseEntity<List<UserDto>> getUsers(){
        return this.userService.getAll();
    }

    @DeleteMapping("/{username}")
    public void deleteUserByUsername(@PathVariable("username") String username, Authentication authentication) throws Exception {
        if (validatePermission.apply(authentication, username)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Acesso negado");
        }
        this.userService.deleteUserByUsername(username);
    }

    @GetMapping("/{username}")
    public UserDto getUserByUsername(@PathVariable("username") String username) throws Exception {
      return  this.userService.getUserByUsername(username);
    }

    @PutMapping("/{username}")
    public ResponseEntity<UserDto> updateUser(@PathVariable String username, @RequestBody UserUpdateRequest userUpdateRequest) throws Exception {
        return this.userService.update(username, userUpdateRequest);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserDto> createUser(@RequestBody UserRequest request){
        return this.userService.createUser(request);
    }
}
