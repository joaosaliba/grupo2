package com.game.pokedex.controllers;

import com.game.pokedex.config.security.JwtUtil;
import com.game.pokedex.dtos.AuthRequest;
import com.game.pokedex.entities.User;
import com.game.pokedex.repositories.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@RestController
@RequestMapping("/login")
public class AuthRestController {

    private final UserRepository usuarioRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtService;

    public AuthRestController(UserRepository usuarioRepository, AuthenticationManager authenticationManager, JwtUtil jwtService) {
        this.usuarioRepository = usuarioRepository;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping
    public String login(@RequestBody AuthRequest request) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(request.email(), request.password());
        this.authenticationManager.authenticate(authentication);
        User usuario = this.usuarioRepository.findByEmail(request.email()).orElseThrow();
        return this.jwtService.createToken(usuario);
    }
}