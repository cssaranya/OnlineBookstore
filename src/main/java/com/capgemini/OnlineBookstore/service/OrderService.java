package com.capgemini.OnlineBookstore.service;

import com.capgemini.OnlineBookstore.dto.Order;
import com.capgemini.OnlineBookstore.dto.ShoppingCart;
import com.capgemini.OnlineBookstore.model.OrderEntity;
import com.capgemini.OnlineBookstore.model.OrderStatus;
import com.capgemini.OnlineBookstore.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ShoppingCartService cartService;
    private final ModelMapper modelMapper;

    public List<Order> getUserOrders(Long userId) {
        List<Order> userOrders = orderRepository.findAll().stream()
                .filter(order -> order.getUser().getId().equals(userId))
                .toList();

        if (userOrders.isEmpty()) {
            throw new EntityNotFoundException("No orders found for user: " + userId);
        }
        return userOrders;
    }

    public Order createOrder(Long userId) {
        ShoppingCart cart = cartService.getCartByUserId(userId);
        Order order = Order.builder()
                .shoppingCart(cart)
                .user(cart.getUser())
                .orderDate(LocalDateTime.now())
                .status(OrderStatus.PLACED)
                .build();

        // Calculate order amount based on cart items
        double total = cart.getItems().stream()
                .mapToDouble(item -> item.getBook().getPrice() * item.getQuantity())
                .sum();
        order.setOrderamount(total);
        OrderEntity orderEntity = modelMapper.map(order, OrderEntity.class);
        return modelMapper.map(orderRepository.save(orderEntity),Order.class);
    }


    public Order getOrderById(Long orderId) {
        OrderEntity orderEntity = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found: " + orderId));
        return modelMapper.map(orderEntity,Order.class);
    }
}