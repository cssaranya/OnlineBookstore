package com.capgemini.OnlineBookstore.mapper;

import com.capgemini.OnlineBookstore.dto.CartItem;
import com.capgemini.OnlineBookstore.model.CartItemEntity;
import org.springframework.stereotype.Component;

@Component
public class CartItemResponseMapper {
    BookResponseMapper bookResponseMapper = new BookResponseMapper();
    public CartItem map(CartItemEntity cartItemEntity){
        return CartItem.builder()
                .book(bookResponseMapper.map(cartItemEntity.getBook()))
                .id(cartItemEntity.getId())
                .quantity(cartItemEntity.getQuantity())
                .shoppingcartId(cartItemEntity.getShoppingCart().getId())
                .build();
    }
}
