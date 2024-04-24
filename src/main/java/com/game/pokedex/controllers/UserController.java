package com.game.pokedex.controllers;

import com.game.pokedex.service.UserService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
