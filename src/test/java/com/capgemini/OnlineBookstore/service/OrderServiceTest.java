package com.capgemini.OnlineBookstore.service;

import com.capgemini.OnlineBookstore.dto.Order;
import com.capgemini.OnlineBookstore.model.OrderEntity;
import com.capgemini.OnlineBookstore.model.ShoppingCartEntity;
import com.capgemini.OnlineBookstore.repository.OrderRepository;
import com.capgemini.OnlineBookstore.repository.ShoppingCartRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ShoppingCartRepository shoppingCartRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetUserOrders_UserNotFound() {
        when(shoppingCartRepository.findByUserId(1L)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> orderService.getUserOrders(1L));

        assertEquals("No shopping card found for user: 1", exception.getMessage());
    }

    @Test
    void testGetUserOrders_NoOrdersFound() {
        ShoppingCartEntity shoppingCartEntity = new ShoppingCartEntity();
        shoppingCartEntity.setId(1L);
        when(shoppingCartRepository.findByUserId(1L)).thenReturn(Optional.of(shoppingCartEntity));
        when(orderRepository.findByShoppingCartId(1L)).thenReturn(List.of());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> orderService.getUserOrders(1L));

        assertEquals("No orders found for user: 1", exception.getMessage());
    }

    @Test
    void testGetUserOrders_Success() {
        ShoppingCartEntity shoppingCartEntity = new ShoppingCartEntity();
        shoppingCartEntity.setId(1L);
        OrderEntity orderEntity1 = new OrderEntity();
        orderEntity1.setId(1L);
        OrderEntity orderEntity2 = new OrderEntity();
        orderEntity2.setId(2L);
        when(shoppingCartRepository.findByUserId(1L)).thenReturn(Optional.of(shoppingCartEntity));
        when(orderRepository.findByShoppingCartId(1L)).thenReturn(Arrays.asList(orderEntity1, orderEntity2));
        when(modelMapper.map(orderEntity1, Order.class)).thenReturn(new Order(1L, null, null, null, 20.0));
        when(modelMapper.map(orderEntity2, Order.class)).thenReturn(new Order(2L, null, null, null, 30.0));

        List<Order> orders = orderService.getUserOrders(1L);

        assertNotNull(orders);
        assertEquals(2, orders.size());
        assertEquals(1L, orders.get(0).getId());
        assertEquals(2L, orders.get(1).getId());
    }

    @Test
    void testGetOrderById_OrderNotFound() {
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> orderService.getOrderById(1L));

        assertEquals("Order not found: 1", exception.getMessage());
    }

    @Test
    void testGetOrderById_Success() {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setId(1L);
        when(orderRepository.findById(1L)).thenReturn(Optional.of(orderEntity));
        when(modelMapper.map(orderEntity, Order.class)).thenReturn(new Order(1L, null, null, null, 20.0));

        Order order = orderService.getOrderById(1L);

        assertNotNull(order);
        assertEquals(1L, order.getId());
    }

    @Test
    void testCreateOrder_UserNotFound() {
        when(shoppingCartRepository.findByUserId(1L)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> orderService.createOrder(1L));

        assertEquals("No shopping card found for user: 1", exception.getMessage());
    }
}