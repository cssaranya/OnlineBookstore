package com.capgemini.OnlineBookstore.controller;

import com.capgemini.OnlineBookstore.repository.BookRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class BookEntityControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookRepository bookRepository;

    @Test
    @WithMockUser(username = "user")
    void testGetAllBooks() throws Exception {

        mockMvc.perform(get("/books").with(httpBasic("user", "password")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("length()").value(2))
                .andExpect(jsonPath("$[0].title").value("Angles & Demons"))
                .andExpect(jsonPath("$[1].title").value("The Hades Factor"));
    }

    @Test
    @WithMockUser(username = "user")
    void testSaveBook() throws Exception {
        String bookJson = "{\"id\":3,\"title\":\"Da vinci code\",\"author\":\"Dan Brown\",\"price\":11}";

        mockMvc.perform(post("/books").with(httpBasic("user", "password"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson))
                .andExpect(status().isOk());

        assertEquals(3, bookRepository.findAll().size());
    }

    @Test
    @WithMockUser(username = "user")
    void testEmptyAuthor() throws Exception {
        String bookJson = "{\"title\":\"Da vinci code\",\"author\":\"\",\"price\":11}";

        mockMvc.perform(post("/books").with(httpBasic("user", "password"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "user")
    void testTitleNull() throws Exception {
        String bookJson = "{\"author\":\"Dan Brown\",\"price\":11}";

        mockMvc.perform(post("/books").with(httpBasic("user", "password"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "user")
    void testGetBookById() throws Exception {
        mockMvc.perform(get("/books/2").with(httpBasic("user", "password")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price").value(11));
    }

    @Test
    @WithMockUser(username = "user")
    void testNoBookById() throws Exception {
        mockMvc.perform(get("/books/6").with(httpBasic("user", "password")))
                .andExpect(status().isNotFound())
                .andExpect(content().string("BookEntity not found for id 6"));
    }

    @Test
    @WithMockUser(username = "user")
    void testGetBookByTitle() throws Exception {
        mockMvc.perform(get("/books/title").param("title","Angles & Demons").with(httpBasic("user", "password")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Angles & Demons"))
                .andExpect(jsonPath("$.author").value("Dan Brown"));
    }

    @Test
    @WithMockUser(username = "user")
    void testNoBookByTitle() throws Exception {
        mockMvc.perform(get("/books/title").param("title","Da vinci code").with(httpBasic("user", "password")))
                .andExpect(status().isNotFound())
                .andExpect(content().string("No book found with the title Da vinci code"));
    }

    @Test
    @WithMockUser(username = "user")
    void testGetBookByAuthor() throws Exception {
        mockMvc.perform(get("/books/author").param("author","Dan Brown").with(httpBasic("user", "password")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Angles & Demons"))
                .andExpect(jsonPath("$[0].author").value("Dan Brown"));
    }
}