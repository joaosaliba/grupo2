package com.game.pokedex.controllers;

import com.game.pokedex.dtos.UserDto;
import com.game.pokedex.dtos.UserRequest;
import com.game.pokedex.dtos.UserUpdateRequest;
import com.game.pokedex.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getUsers(){
        return this.userService.getAll();
    }

    @DeleteMapping("/{username}")
    public void deleteUserByUsername(@PathVariable("username") String username) throws Exception {
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
