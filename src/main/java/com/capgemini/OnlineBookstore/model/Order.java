package com.capgemini.OnlineBookstore.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "shopping_cart_id")
    private ShoppingCart shoppingCart;

    private LocalDateTime orderDate;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
}

