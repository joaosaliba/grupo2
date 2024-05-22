package com.game.pokedex.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void cadastrarUserValido_ResultaSucesso() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.post("/user")
                                .content("""
                                        {
                                          "createdDate": "2024-05-20T15:11:06.317Z",
                                          "email": "menino1@gmail.com",
                                          "password": "12341",
                                          "name": "menino1",
                                          "role": "MESTRE_POKEMON"
                                        }
                                        """)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                ).andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void cadastrarUserInvalido_ResultaException() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.post("/user")
                        .content("""
                                {
                                "createdDate": "2024-05-20T15:11:06.317Z",
                                          "email": ,
                                          "password": "1234",
                                          "name": "junin",
                                }
                                """)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    public void buscarUserCadastrado_ResultaSucesso()throws Exception{
        mockMvc.perform(
                MockMvcRequestBuilders.post("/user")
                        .content("""
                                        {
                                          "createdDate": "2024-05-20T15:11:06.317Z",
                                          "email": "menino@gmail.com",
                                          "password": "1234",
                                          "name": "menino",
                                          "role": "MESTRE_POKEMON"
                                        }
                                        """)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isCreated());

        mockMvc.perform(
                MockMvcRequestBuilders.get("/user/menino")
        ).andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }


}