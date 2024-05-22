package com.game.pokedex.integration.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.game.pokedex.dtos.UserDto;
import com.game.pokedex.dtos.UserRequest;
import com.game.pokedex.dtos.UserUpdateRequest;
import com.game.pokedex.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = "spring.h2.console.enabled=true")
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    @WithMockUser(roles = "ADMIN")
    public void getUsersShouldReturnUserList() throws Exception {
        UserDto userDto = new UserDto(
                Instant.now(),
                Instant.now(),
                1L,
                "user@example.com",
                "Example User"
        );
        Mockito.when(userService.getAll()).thenReturn(new ResponseEntity<>(List.of(userDto), HttpStatus.OK));

        mockMvc.perform(get("/user"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].email").value("user@example.com"));
    }

    @Test
    @WithMockUser(username = "admin@example.com", roles = "ADMIN")
    public void deleteUserByUsernameShouldReturnForbiddenForUnauthorizedUser() throws Exception {
        Mockito.doThrow(new ResponseStatusException(HttpStatus.FORBIDDEN, "Acesso negado")).when(userService).deleteUserByUsername("user1");

        mockMvc.perform(delete("/user/user1"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "admin@example.com", roles = "ADMIN")
    public void updateUserShouldProcessSuccessfully() throws Exception {
        UserUpdateRequest updateRequest = new UserUpdateRequest(Instant.now(), "ADMIN");
        UserDto updatedUser = new UserDto(
                Instant.now(),
                Instant.now(),
                1L,
                "user@example.com",
                "Example User"
        );
        Mockito.when(userService.update("user@example.com", updateRequest)).thenReturn(new ResponseEntity<>(updatedUser, HttpStatus.OK));

        mockMvc.perform(put("/user/user@example.com")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void createUserShouldReturnCreatedStatus() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                        {
                                          "createdDate": "2024-05-20T20:10:06.317Z",
                                          "email": "user@example.com",
                                          "password": "1234",
                                          "name": "Example User",
                                          "role": "MESTRE_POKEMON"
                                        }
                                        """)
                ).andExpect(MockMvcResultMatchers.status().isCreated());
    }
}
