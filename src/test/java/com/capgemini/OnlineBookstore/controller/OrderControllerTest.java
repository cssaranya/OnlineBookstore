package com.capgemini.OnlineBookstore.controller;

import com.capgemini.OnlineBookstore.dto.Order;
import com.capgemini.OnlineBookstore.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext
@Transactional
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @Test
    @WithMockUser(username = "user")
    void testGetUserOrders() throws Exception {
        List<Order> orders = Arrays.asList(
                new Order(1L, null, null, null, 20.0),
                new Order(2L, null, null, null, 30.0)
        );
        when(orderService.getUserOrders(1L)).thenReturn(orders);

        mockMvc.perform(get("/orders/getOrder/1").with(httpBasic("user", "password")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].orderamount").value(20.0))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].orderamount").value(30.0));
    }

    @Test
    @WithMockUser(username = "user")
    void testCreateOrder() throws Exception {
        Order order = new Order(1L, null, null, null, 20.0);
        when(orderService.createOrder(1L)).thenReturn(order);

        mockMvc.perform(post("/orders/createOrder/1").with(httpBasic("user", "password"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(order)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.orderamount").value(20.0));
    }

    @Test
    @WithMockUser(username = "user")
    void testGetOrderDetails() throws Exception {
        Order order = new Order(1L, null, null, null, 20.0);
        when(orderService.getOrderById(1L)).thenReturn(order);

        mockMvc.perform(get("/orders/orderDetails/1").with(httpBasic("user", "password")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.orderamount").value(20.0));
    }
}