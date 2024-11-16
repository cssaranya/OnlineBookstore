package com.capgemini.OnlineBookstore.service;

import com.capgemini.OnlineBookstore.dto.Order;
import com.capgemini.OnlineBookstore.model.OrderEntity;
import com.capgemini.OnlineBookstore.model.ShoppingCartEntity;
import com.capgemini.OnlineBookstore.model.UserEntity;
import com.capgemini.OnlineBookstore.repository.OrderRepository;
import com.capgemini.OnlineBookstore.repository.ShoppingCartRepository;
import com.capgemini.OnlineBookstore.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DirtiesContext
@RunWith(MockitoJUnitRunner.class)
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ShoppingCartRepository shoppingCartRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private OrderService orderService;


    @Test
    void testNoOrdersException() {
        when(orderRepository.findByUserId(1L)).thenReturn(List.of());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> orderService.getUserOrders(1L));

        assertEquals("No orders found for user: 1", exception.getMessage());
    }

    @Test
    void testGetUserOrders() {
        OrderEntity orderEntity1 = OrderEntity.builder().id(1L).build();
        OrderEntity orderEntity2 = OrderEntity.builder().id(2L).build();

        when(orderRepository.findByUserId(1L)).thenReturn(Arrays.asList(orderEntity1, orderEntity2));
        when(modelMapper.map(orderEntity1, Order.class)).thenReturn(new Order(1L, null, null, null, 20.0, null));
        when(modelMapper.map(orderEntity2, Order.class)).thenReturn(new Order(2L, null, null, null, 30.0, null));

        List<Order> orders = orderService.getUserOrders(1L);

        assertNotNull(orders);
        assertEquals(2, orders.size());
        assertEquals(1L, orders.get(0).getId());
        assertEquals(2L, orders.get(1).getId());
    }

    @Test
    void testOrderNotFoundException() {
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> orderService.getOrderById(1L));

        assertEquals("Order not found: 1", exception.getMessage());
    }

    @Test
    void testGetOrderById() {
        OrderEntity orderEntity = OrderEntity.builder().id(1L).build();

        when(orderRepository.findById(1L)).thenReturn(Optional.of(orderEntity));
        when(modelMapper.map(orderEntity, Order.class)).thenReturn(new Order(1L, null, null, null, 20.0, null));

        Order order = orderService.getOrderById(1L);

        assertNotNull(order);
        assertEquals(1L, order.getId());
    }

    @Test
    void testNoCartException() {
        when(shoppingCartRepository.findByUserId(1L)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> orderService.createOrder(1L));

        assertEquals("No shopping cart found for user: 1", exception.getMessage());
    }

    @Test
    void testCreateOrder() {
        ShoppingCartEntity shoppingCartEntity = ShoppingCartEntity.builder()
                .id(1L)
                .build();
        OrderEntity orderEntity = OrderEntity.builder().id(1L).build();
        UserEntity userEntity = UserEntity.builder().id(1L).build();

        when(shoppingCartRepository.findByUserId(1L)).thenReturn(Optional.of(shoppingCartEntity));
        when(userRepository.findById(1L)).thenReturn(Optional.of(userEntity));
        when(orderRepository.save(any(OrderEntity.class))).thenReturn(orderEntity);
        when(modelMapper.map(any(OrderEntity.class), eq(Order.class)))
                .thenReturn(new Order(1L, null, null, null, 20.0, null));

        Order order = orderService.createOrder(1L);

        assertNotNull(order);
        assertEquals(1L, order.getId());
    }
}