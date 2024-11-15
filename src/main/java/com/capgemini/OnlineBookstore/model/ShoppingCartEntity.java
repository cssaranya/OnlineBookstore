package com.capgemini.OnlineBookstore.model;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "shoppingcart")
public class ShoppingCartEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shoppingcart_id")
    private Long id;

    @OneToMany(mappedBy = "shoppingCart", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<CartItemEntity> items;

    @OneToOne(mappedBy = "shoppingCart", cascade = CascadeType.ALL)
    @JsonManagedReference
    private OrderEntity order;

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
