package com.capgemini.OnlineBookstore.controller;

import com.capgemini.OnlineBookstore.dto.Order;
import com.capgemini.OnlineBookstore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/getOrder/{userId}")
    public ResponseEntity<List<Order>> getUserOrders(@PathVariable Long userId) {
        List<Order> orders = orderService.getUserOrders(userId);
        return ResponseEntity.ok(orders);
    }

    @PostMapping("/createOrder/{userId}")
    public ResponseEntity<Order> createOrder(@PathVariable Long userId) {
        Order order = orderService.createOrder(userId);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/orderDetails/{orderId}")
    public ResponseEntity<Order> getOrderDetails(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.getOrderById(orderId));
    }
}