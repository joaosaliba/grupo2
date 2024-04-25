package com.game.pokedex.controllers;

import com.game.pokedex.dtos.UserDto;
import com.game.pokedex.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
 private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @DeleteMapping("{username}")
    public void deleteUserByUsername(@PathVariable("username") String username) throws Exception {
        this.userService.deleteUserByUsername(username);
    }

    @GetMapping("{username}")
    public UserDto getUserByUsername(@PathVariable("username") String username) throws Exception {
      return  this.userService.getUserByUsername(username);
    }
}
