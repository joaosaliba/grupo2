package com.game.pokedex.integration.controllers;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.game.pokedex.config.security.JwtUtil;
import com.game.pokedex.controllers.AuthRestController;
import com.game.pokedex.dtos.AuthRequest;
import com.game.pokedex.entities.User;
import com.game.pokedex.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = "spring.h2.console.enabled=true")
public class AuthRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository usuarioRepository;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private JwtUtil jwtService;

    @Test
    public void doingLoginShouldReturnToken() throws Exception {
        AuthRequest authRequest = new AuthRequest("user@example.com", "password123");
        User mockUser = new User("user@example.com", "password123"); // Adjust if User has different constructor
        Authentication auth = new UsernamePasswordAuthenticationToken(authRequest.email(), authRequest.password());

        when(authenticationManager.authenticate(any(Authentication.class))).thenReturn(auth);
        given(usuarioRepository.findByEmail("user@example.com")).willReturn(Optional.of(mockUser));
        when(jwtService.createToken(mockUser)).thenReturn("mockToken");

        ObjectMapper objectMapper = new ObjectMapper();
        String authRequestJson = objectMapper.writeValueAsString(authRequest);

        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authRequestJson))
                .andExpect(status().isOk())
                .andExpect(content().string("mockToken"));
    }

    @Test
    public void LoginFailureUserNotFound() throws Exception {
        AuthRequest authRequest = new AuthRequest("user@example.com", "password123");
        when(usuarioRepository.findByEmail("user@example.com")).thenReturn(Optional.empty());

        ObjectMapper objectMapper = new ObjectMapper();
        String authRequestJson = objectMapper.writeValueAsString(authRequest);

        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authRequestJson))
                .andExpect(status().is4xxClientError());
    }

}
