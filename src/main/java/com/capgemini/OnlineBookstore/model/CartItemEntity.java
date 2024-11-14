package com.capgemini.OnlineBookstore.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cartitem")
public class CartItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cartitem_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "shoppingcart_id")
    @JsonBackReference
    private ShoppingCartEntity shoppingCart;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private BookEntity book;

    private int quantity;
}
