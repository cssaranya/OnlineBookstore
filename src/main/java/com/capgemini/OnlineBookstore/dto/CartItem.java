package com.capgemini.OnlineBookstore.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItem {
    private Long cartitemId;
    private Long shoppingcartId;
    private Book book;
    private int quantity;
}