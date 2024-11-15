package com.capgemini.OnlineBookstore.service;

import com.capgemini.OnlineBookstore.dto.Order;
import com.capgemini.OnlineBookstore.model.OrderEntity;
import com.capgemini.OnlineBookstore.model.ShoppingCartEntity;
import com.capgemini.OnlineBookstore.repository.OrderRepository;
import com.capgemini.OnlineBookstore.repository.ShoppingCartRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final ModelMapper modelMapper;

    public List<Order> getUserOrders(Long userId) {

        ShoppingCartEntity userCart = shoppingCartRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("No shopping card found for user: " + userId));

        List<OrderEntity> userOrders = orderRepository.findByShoppingCartId(userCart.getId());
        if (userOrders.isEmpty()) {
            throw new EntityNotFoundException("No orders found for user: " + userId);
        }
        return userOrders.stream()
                .map(orderEntity -> modelMapper.map(orderEntity, Order.class))
                .collect(Collectors.toList());
    }

    public Order createOrder(Long userId) {
        ShoppingCartEntity userCart = shoppingCartRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("No shopping card found for user: " + userId));

        if (userCart.getItems().isEmpty()) {
            throw new IllegalStateException("Cannot create order with empty cart");
        }
        // else if (userCart.get)

        // Order order = Order.builder()
        //         .shoppingCart(cart)
        //         .user(cart.getUser())
        //         .orderDate(LocalDateTime.now())
        //         .status(OrderStatus.PLACED)
        //         .build();

        // // Calculate order amount based on cart items
        // double total = cart.getItems().stream()
        //         .mapToDouble(item -> item.getBook().getPrice() * item.getQuantity())
        //         .sum();
        // order.setOrderamount(total);
        // OrderEntity orderEntity = modelMapper.map(order, OrderEntity.class);
        // return modelMapper.map(orderRepository.save(orderEntity),Order.class);
        return null;
    }


    public Order getOrderById(Long orderId) {
        OrderEntity orderEntity = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found: " + orderId));
        return modelMapper.map(orderEntity,Order.class);
    }
}