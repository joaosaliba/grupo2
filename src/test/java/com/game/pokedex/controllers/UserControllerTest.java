package com.game.pokedex.controllers;

import com.game.pokedex.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
@SpringBootTest
@AutoConfigureMockMvc
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
                                          "email": "junin@gmail.com",
                                          "password": "1234",
                                          "name": "junin",
                                          "role": "ADMIN"
                                        }
                                        """)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                ).andExpect(MockMvcResultMatchers.status().isCreated());
    }
}