package com.capgemini.OnlineBookstore.controller;

import com.capgemini.OnlineBookstore.model.UserEntity;
import com.capgemini.OnlineBookstore.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void testRegisterUser() throws Exception {
        UserEntity user = UserEntity.builder()
                .phonenumber("9809079")
                .email("email")
                .address("india")
                .username("css")
                .password("css")
                .build();
        mockMvc.perform((post("/users/register"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(content().string("UserEntity registered successfully"));;

        Optional<UserEntity> savedUser = userRepository.findByUsername("css");
        assertThat(savedUser).isNotNull();
        assertEquals(savedUser.get().getUsername(),"css");
        assertThat(passwordEncoder.matches("css", savedUser.get().getPassword())).isTrue();
    }

    @Test
    void testWhenNoUser() throws Exception {
        UserEntity user = new UserEntity();
        mockMvc.perform((post("/users/register"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(user)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testWhenUsernameNull() throws Exception {
        UserEntity user = UserEntity.builder()
                .phonenumber("9809079")
                .email("email")
                .address("india")
                .password("css")
                .build();
        mockMvc.perform((post("/users/register"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(user)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testWhenPasswordNull() throws Exception {
        UserEntity user = UserEntity.builder()
                .phonenumber("9809079")
                .email("email")
                .address("india")
                .username("CSS")
                .build();
        mockMvc.perform((post("/users/register"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(user)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testWhenUsernameEmpty() throws Exception {
        UserEntity user = UserEntity.builder()
                .phonenumber("9809079")
                .email("email")
                .address("india")
                .password("css")
                .username("")
                .build();
        mockMvc.perform((post("/users/register"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(user)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testWhenPasswordEmpty() throws Exception {
        UserEntity user = UserEntity.builder()
                .phonenumber("9809079")
                .email("email")
                .address("india")
                .username("css")
                .password("")
                .build();
        mockMvc.perform((post("/users/register"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(user)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "user")
    void testGetUserById() throws  Exception {
        mockMvc.perform(get("/users/1").with(httpBasic("user", "password")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("Saranya"));
    }

    @Test
    @WithMockUser(username = "user")
    void testUserUpdate() throws  Exception {
        UserEntity user = UserEntity.builder()
                .phonenumber("9809079")
                .email("email")
                .address("india")
                .username("css")
                .password("css")
                .build();
        mockMvc.perform(put("/users/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(user))
                        .with(httpBasic("user", "password")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("css"));
    }
}