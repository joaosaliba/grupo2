package com.game.pokedex.controllers;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AuthRestControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Order(1)
    @Test
    public void fazerLoginComUsuarioInexistente_ResultaException() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.post("/login")
                        .content("""
                                {
                                    "email": "junior@gmail.com",
                                    "password": "1234"
                                }
                                """)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Order(2)
    @Test
    public void fazerLoginComUsuarioExistente_ResultaSucessoDevendoRetornarToken() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.post("/user")
                        .content("""
                                        {
                                          "createdDate": "2024-05-20T15:11:06.317Z",
                                          "email": "junin@gmail.com",
                                          "password": "1234",
                                          "name": "junin",
                                          "role": "ADMIN"
                                        }
                                        """)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
        mockMvc.perform(
                MockMvcRequestBuilders.post("/login")
                        .content("""
                                {
                                    "email": "junin@gmail.com",
                                    "password": "1234"
                                }
                                """)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }

}
