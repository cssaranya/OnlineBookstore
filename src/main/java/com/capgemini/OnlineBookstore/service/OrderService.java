package com.capgemini.OnlineBookstore.service;

import com.capgemini.OnlineBookstore.dto.Order;
import com.capgemini.OnlineBookstore.exception.EntityNotFoundException;
import com.capgemini.OnlineBookstore.exception.InvalidRequestException;
import com.capgemini.OnlineBookstore.model.*;
import com.capgemini.OnlineBookstore.repository.OrderItemRepository;
import com.capgemini.OnlineBookstore.repository.OrderRepository;
import com.capgemini.OnlineBookstore.repository.ShoppingCartRepository;
import com.capgemini.OnlineBookstore.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final OrderItemRepository orderItemRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public List<Order> getUserOrders(Long userId) {
        List<OrderEntity> userOrders = orderRepository.findByUserId(userId);
        if (userOrders.isEmpty()) {
            throw new EntityNotFoundException("No orders found for user: " + userId);
        }
        return userOrders.stream()
                .map(orderEntity -> modelMapper.map(orderEntity, Order.class))
                .collect(Collectors.toList());
    }

    public Order createOrder(Long userId) {
        ShoppingCartEntity userCart = shoppingCartRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("No shopping cart found for user: " + userId));

        if (userCart.getItems().isEmpty()) {
            throw new InvalidRequestException("Cannot create order with empty cart");
        }

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + userId));

        OrderEntity orderEntity = getOrderEntity(userCart, user);

        Order order = modelMapper.map(orderRepository.save(orderEntity), Order.class);

        if (order != null && order.getStatus() == OrderStatus.PLACED) {
            userCart.getItems().clear();
        }

        return order;
    }

    private static OrderEntity getOrderEntity(ShoppingCartEntity userCart, UserEntity user) {
        OrderEntity orderEntity = OrderEntity.builder()
                .user(user)
                .orderDate(LocalDateTime.now())
                .status(OrderStatus.PLACED)
                .orderamount(userCart.getItems().stream().mapToDouble(item -> item.getBook().getPrice() * item.getQuantity()).sum())
                .build();

        orderEntity.setItems(userCart.getItems().stream()
                .map(cartItem -> OrderItemEntity.builder()
                        .order(orderEntity)
                        .book(cartItem.getBook())
                        .quantity(cartItem.getQuantity())
                        .build())
                .collect(Collectors.toList()));

        return orderEntity;
    }

    public Order getOrderById(Long orderId) {
        OrderEntity orderEntity = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found: " + orderId));
        return modelMapper.map(orderEntity, Order.class);
    }
}