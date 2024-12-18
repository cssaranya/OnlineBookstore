package com.capgemini.OnlineBookstore.controller;

import com.capgemini.OnlineBookstore.model.ShoppingCartEntity;
import com.capgemini.OnlineBookstore.repository.ShoppingCartRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ShoppingCartControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ShoppingCartRepository shoppingCartRepository;

    @Test
    @WithMockUser(username = "user")
    void testGetCart() throws Exception {

        mockMvc.perform(get("/cart/getCart/1").with(httpBasic("user", "password")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.user.id").value(1))
                .andExpect(jsonPath("$.user.username").value("Saranya"))
                .andExpect(jsonPath("$.user.email").value("saranya@test.com"))
                .andExpect(jsonPath("$.items[0].book.title").value("Angles & Demons"))
                .andExpect(jsonPath("$.items[0].quantity").value(2));
    }

    @Test
    @WithMockUser(username = "user")
    void testCreateShoppingCart() throws Exception {

        mockMvc.perform(post("/cart/createCart/2").with(httpBasic("user", "password")))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.user.id").value(2))
                .andExpect(jsonPath("$.user.username").value("Suptha"))
                .andExpect(jsonPath("$.user.email").value("suptha@test.com"));
    }

    @Test
    @WithMockUser(username = "user")
    void testCartExistsException() throws Exception {
        mockMvc.perform(post("/cart/createCart/1").with(httpBasic("user", "password")))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Cart already exists for the user 1"));
    }

    @Test
    @WithMockUser(username = "user")
    void testAddItemToCart() throws Exception {
        mockMvc.perform(post("/cart/add/{userId}/items/{bookId}", 1, 2).with(httpBasic("user", "password"))
                        .param("quantity", String.valueOf(3)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.user.id").value(1))
                .andExpect(jsonPath("$.user.username").value("Saranya"))
                .andExpect(jsonPath("$.user.email").value("saranya@test.com"))
                .andExpect(jsonPath("$.items[0].book.title").value("Angles & Demons"))
                .andExpect(jsonPath("$.items[0].quantity").value(2))
                .andExpect(jsonPath("$.items[1].book.title").value("The Hades Factor"))
                .andExpect(jsonPath("$.items[1].quantity").value(4));
    }

    @Test
    @WithMockUser(username = "user")
    void testBookNotFound() throws Exception {
        int nonExistantBookId = 6;
        ShoppingCartEntity shoppingCartEntity = new ShoppingCartEntity();
        mockMvc.perform(post("/cart/add/{userId}/items/{bookId}", 1, nonExistantBookId).with(httpBasic("user", "password"))
                        .param("quantity", String.valueOf(3)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Book not found for id: "+nonExistantBookId));
    }

    @Test
    @WithMockUser(username = "user")
    void testUpdateItemQuantity() throws Exception {
        mockMvc.perform(put("/cart/update/items/{itemId}", 1).with(httpBasic("user", "password"))
                        .param("quantity", String.valueOf(5)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.book.title").value("Angles & Demons"))
                .andExpect(jsonPath("$.quantity").value(5));
    }

    @Test
    @WithMockUser(username = "user")
    void testCartItemNotFound() throws Exception {
        mockMvc.perform(put("/cart/update/items/{itemId}", 3).with(httpBasic("user", "password"))
                        .param("quantity", String.valueOf(5)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Cart item not found: 3"));
    }

    @Test
    @WithMockUser(username = "user")
    void testNegativeQuantity() throws Exception {
        mockMvc.perform(put("/cart/update/items/{itemId}", 1).with(httpBasic("user", "password"))
                        .param("quantity", String.valueOf(-5)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("ItemId and Quantity are required"));
    }

    @Test
    @WithMockUser(username = "user")
    void testRemoveCartItem() throws Exception {
        mockMvc.perform(delete("/cart/delete/items/{itemId}", 1).with(httpBasic("user", "password")))
                .andExpect(status().isOk())
                .andExpect(content().string("Cart item deleted successfully"));
    }
}