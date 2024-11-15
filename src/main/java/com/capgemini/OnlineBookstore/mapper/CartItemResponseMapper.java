package com.capgemini.OnlineBookstore.mapper;

import com.capgemini.OnlineBookstore.dto.CartItem;
import com.capgemini.OnlineBookstore.dto.ShoppingCart;
import com.capgemini.OnlineBookstore.model.CartItemEntity;
import com.capgemini.OnlineBookstore.model.ShoppingCartEntity;

public class CartItemResponseMapper {
    BookResponseMapper bookResponseMapper = new BookResponseMapper();
    public CartItem map(CartItemEntity cartItemEntity){
        return CartItem.builder()
                .book(bookResponseMapper.map(cartItemEntity.getBook()))
                .cartitemId(cartItemEntity.getId())
                .quantity(cartItemEntity.getQuantity())
                .shoppingCartId(cartItemEntity.getShoppingCart().getId())
                .build();
    }
}
