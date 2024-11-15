package com.capgemini.OnlineBookstore.dto;

import com.capgemini.OnlineBookstore.model.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {
    private Long orderId;
    private ShoppingCart shoppingCart;
    private LocalDateTime orderDate;
    private OrderStatus status;
    private double orderamount;
    private User user;
}